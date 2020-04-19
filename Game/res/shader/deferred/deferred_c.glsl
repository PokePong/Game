#version 430 core

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

void main() {

	ivec2 coords = ivec2(gl_GlobalInvocationID.x, gl_GlobalInvocationID.y);

	vec3 position = imageLoad(img_position, coords).rgb;
	vec3 albedo = imageLoad(img_albedo, coords).rgb;
	vec3 normal = imageLoad(img_normal, coords).rgb;

	vec3 lighting = albedo * 0.1f;
	vec3 viewDir = normalize(c_Position - position);
	vec3 lightDir = normalize(vec3(0, 0, 0) - position);
	vec3 diffuse = max(dot(normal, lightDir), 0.1) * albedo * vec3(1);
	lighting += diffuse;

	vec4 pixel = vec4(lighting, 1);

	imageStore(img_output, coords, pixel);

}
