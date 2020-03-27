#version 330 core

in vec3 worldPosition;

out vec4 out_Color;

const vec3 skyColor = vec3(0.18, 0.27, 0.8);

void main() {

	float red = -0.00022 * abs(worldPosition.y) + skyColor.x;
	float green = -0.00025 * abs(worldPosition.y) + skyColor.y;
	float blue = -0.0019 * abs(worldPosition.y) + skyColor.z;

	out_Color = vec4(red, green, blue, 1);
}
