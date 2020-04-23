#version 430 core

struct PointLight {
	vec4 diffuse;
	vec3 attenuation;
	vec3 position;
	float intensity;
};

layout (local_size_x = 16, local_size_y = 16) in;
layout (rgba32f, binding = 0) uniform readonly image2D img_position;
layout (rgba16f, binding = 1) uniform readonly image2D img_albedo;
layout (rgba32f, binding = 2) uniform readonly image2D img_normal;
layout (rgba16f, binding = 5) uniform writeonly image2D img_output;

layout (std140) uniform Camera {
	vec3 c_Position;
	mat4 c_Projection;
	mat4 c_View;
};

const vec4 ambiant = vec4(0);

uniform int num_PointLight;
uniform PointLight[20] pointLights;

// Blinn-Phong
vec3 calcDiffuse(PointLight light, vec3 position, vec3 normal) {
	vec3 diffuse = light.diffuse.rgb;

	vec3 toLight = normalize(light.position - position);
	float distance = length(toLight);
	float attenuationFactor = light.attenuation.x
			+ light.attenuation.y * distance
			+ light.attenuation.z * distance * distance;
	vec3 viewDir = normalize(-position);
	vec3 halfDir = normalize(toLight + viewDir);
	float brightness = light.intensity * max(dot(halfDir, normal), 0.0);
	diffuse = brightness * diffuse / attenuationFactor;
	return diffuse;
}

void main() {

	ivec2 coords = ivec2(gl_GlobalInvocationID.x, gl_GlobalInvocationID.y);

	vec3 position = imageLoad(img_position, coords).xyz;
	vec3 albedo = imageLoad(img_albedo, coords).rgb;
	vec3 normal = imageLoad(img_normal, coords).xyz;

	// BLINN PHONG
	vec3 lighting = vec3(0);
	for (int i = 0; i < num_PointLight; i++) {
		lighting += calcDiffuse(pointLights[i], position, normal);
	}
	lighting *= albedo;

	vec4 pixel = vec4(lighting, 1);
	imageStore(img_output, coords, pixel);

}
