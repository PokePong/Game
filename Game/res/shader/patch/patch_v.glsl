#version 430 core

// Patch
layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 morph;
// Instances
layout (location = 2) in vec3 a;
layout (location = 3) in vec3 r;
layout (location = 4) in vec3 s;
layout (location = 5) in float level;

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

float morphFactor(float dist, int level) {
	if(level == maxLevel) {
		return 1;
	}
	float low = distanceLUT[level - 1];
	float high = distanceLUT[level];
	float delta = low - high;
	float toNext = dist - high;
	float perc = toNext / delta;
	return clamp(perc / morphRange, 0, 1);;
}

void main() {

	vec3 P = a + r * pos.x + s * pos.y;
	vec3 M = r * morph.x + s * morph.y;

	vec3 Ploc = normalize(P) * radius;

	float dist = length(c_Position - Ploc);
	int leveli = int(level);
	float mPerc = morphFactor(dist, leveli);
	P += mPerc * M;
	P = normalize(P) * radius;

	gl_Position = c_Projection * c_View * m_World * vec4(P, 1.0f);
}
