#version 330 core

in vec3 worldPosition;

out vec4 out_Color;

const vec4 skyColor = vec4(0.52, 0.80, 0.92, 1);
const vec4 white = vec4(1, 1, 1, 1);

void main() {


	if (worldPosition.y < 50) {
		float perc = (50 - worldPosition.y)/50;
		out_Color = mix(skyColor, white, perc);
	} else {
		out_Color = skyColor;
	}

}
