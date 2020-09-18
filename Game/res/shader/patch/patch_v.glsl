#version 430 core

#define PI 3.1415926535897932384626433832795

// Patch
layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 morph;
// Instances
layout (location = 2) in vec3 a;
layout (location = 3) in vec3 r;
layout (location = 4) in vec3 s;
layout (location = 5) in float level;

out vec4 pass_Position;
out vec4 pass_Normal;
out vec2 pass_Texture;

layout (std140) uniform Camera {
	vec3 c_Position;
	mat4 c_Projection;
	mat4 c_View;
};

uniform mat4 m_World;
uniform int maxLevel;
uniform float morphRange;
uniform float radius;
uniform float distanceLUT[32];

uniform int random[512];
uniform float strength;
uniform float roughness;
uniform vec3 center;

int fastFloor(float x) {
	int index = int(x);
	if (x >= 0) {
		return index;
	} else {
		return index - 1;
	}
}

float dot(vec3 g, float x, float y, float z) {
	return g.x * x + g.y * y + g.z * z;
}

float evaluateNoise(vec3 point) {

	float F3 = 1.0 / 3.0;
	float G3 = 1.0 / 6.0;

	vec3[] grad3 = {
		vec3(1, 1, 0),
		vec3(-1, 1, 0),
		vec3(1, -1, 0),
		vec3(-1, -1, 0),
		vec3(1, 0, 1),
		vec3(-1, 0, 1),
		vec3(1, 0, -1),
		vec3(-1, 0, -1),
		vec3(0, 1, 1),
		vec3(0, -1, 1),
		vec3(0, 1, -1),
		vec3(0, -1, -1)
	};

	float x = point.x;
	float y = point.y;
	float z = point.z;
	float n0 = 0, n1 = 0, n2 = 0, n3 = 0;

	// Noise contributions from the four corners
	// Skew the input space to determine which simplex cell we're in
	float s = (x + y + z) * F3;

	// for 3D
	int i = fastFloor(x + s);
	int j = fastFloor(y + s);
	int k = fastFloor(z + s);

	float t = (i + j + k) * G3;

	// The x,y,z distances from the cell origin
	float x0 = x - (i - t);
	float y0 = y - (j - t);
	float z0 = z - (k - t);

	// For the 3D case, the simplex shape is a slightly irregular
	// tetrahedron.
	// Determine which simplex we are in.
	// Offsets for second corner of simplex in (i,j,k)
	int i1, j1, k1;

	// coords
	int i2, j2, k2;		// Offsets for third corner of simplex in (i,j,k) coords

	if (x0 >= y0) {
		if (y0 >= z0) {
			// X Y Z order
			i1 = 1;
			j1 = 0;
			k1 = 0;
			i2 = 1;
			j2 = 1;
			k2 = 0;
		} else if (x0 >= z0) {
			// X Z Y order
			i1 = 1;
			j1 = 0;
			k1 = 0;
			i2 = 1;
			j2 = 0;
			k2 = 1;
		} else {
			// Z X Y order
			i1 = 0;
			j1 = 0;
			k1 = 1;
			i2 = 1;
			j2 = 0;
			k2 = 1;
		}
	} else {
		// x0 < y0
		if (y0 < z0) {
			// Z Y X order
			i1 = 0;
			j1 = 0;
			k1 = 1;
			i2 = 0;
			j2 = 1;
			k2 = 1;
		} else if (x0 < z0) {
			// Y Z X order
			i1 = 0;
			j1 = 1;
			k1 = 0;
			i2 = 0;
			j2 = 1;
			k2 = 1;
		} else {
			// Y X Z order
			i1 = 0;
			j1 = 1;
			k1 = 0;
			i2 = 1;
			j2 = 1;
			k2 = 0;
		}
	}

	// A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
	// a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z),
	// and
	// a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z),
	// where c = 1/6.

	// Offsets for second corner in (x,y,z) coords
	float x1 = x0 - i1 + G3;
	float y1 = y0 - j1 + G3;
	float z1 = z0 - k1 + G3;

	// Offsets for third corner in (x,y,z)
	float x2 = x0 - i2 + F3;
	float y2 = y0 - j2 + F3;
	float z2 = z0 - k2 + F3;

	// Offsets for last corner in (x,y,z)
	float x3 = x0 - 0.5;
	float y3 = y0 - 0.5;
	float z3 = z0 - 0.5;

	// Work out the hashed gradient indices of the four simplex corners
	int ii = i & 0xff;
	int jj = j & 0xff;
	int kk = k & 0xff;

	// Calculate the contribution from the four corners
	float t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
	if (t0 > 0) {
		t0 *= t0;
		int gi0 = random[ii + random[jj + random[kk]]] % 12;
		n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
	}

	float t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
	if (t1 > 0) {
		t1 *= t1;
		int gi1 = random[ii + i1 + random[jj + j1 + random[kk + k1]]] % 12;
		n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
	}

	float t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
	if (t2 > 0) {
		t2 *= t2;
		int gi2 = random[ii + i2 + random[jj + j2 + random[kk + k2]]] % 12;
		n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
	}

	float t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
	if (t3 > 0) {
		t3 *= t3;
		int gi3 = random[ii + 1 + random[jj + 1 + random[kk + 1]]] % 12;
		n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
	}

	// Add contributions from each corner to get the final noise value.
	// The result is scaled to stay just inside [-1,1]
	return (n0 + n1 + n2 + n3) * 32;
}

float evaluate(vec3 pointOnUnitSphere) {
	float noiseValue = evaluateNoise(pointOnUnitSphere * roughness + center) * 0.5f;
	return noiseValue * strength;
}

float morphFactor(float dist, int level) {
	if (level == maxLevel) {
		return 1;
	}
	float low = distanceLUT[level - 1];
	float high = distanceLUT[level];
	float delta = low - high;
	float toNext = dist - high;
	float perc = toNext / delta;
	return clamp(perc / morphRange, 0, 1);;
}

vec2 uvMapping(vec3 position) {
	return vec2(0.5 + atan(-position.z, position.x) / (2 * PI),
			0.5 - asin(-position.y) / PI);
}

void main() {

	vec3 P = a + r * pos.x + s * pos.y;
	vec3 M = r * morph.x + s * morph.y;

	vec3 Ploc = (m_World * vec4(normalize(P) * radius, 1.0f)).xyz;

	float dist = length(c_Position - Ploc);
	int leveli = int(level);
	float mPerc = morphFactor(dist, leveli);
	P += mPerc * M;

	vec3 Punit = normalize(P);
	P = Punit * radius * (1 + evaluate(Punit));

	pass_Position = m_World * vec4(P, 1.0f);
	pass_Normal = normalize(m_World * vec4(P, 1.0f));
	pass_Texture = uvMapping(normalize(P));

	gl_Position = c_Projection * c_View * m_World * vec4(P, 1.0f);

}
