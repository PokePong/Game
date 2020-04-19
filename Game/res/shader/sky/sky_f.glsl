#version 330 core

in vec3 worldPosition;

layout (location = 0) out vec4 out_Albedo;
layout (location = 1) out vec4 out_Albedo;
layout (location = 2) out vec4 out_Albedo;

const vec4 skyColor = vec4(0.52, 0.80, 0.92, 1);
const vec4 white = vec4(1, 1, 1, 1);

void main() {


	if (worldPosition.y < 50) {
		float perc = (50 - worldPosition.y)/50;
		out_Albedo = mix(skyColor, white, perc);
	} else {
		out_Albedo = skyColor;
	}

}
