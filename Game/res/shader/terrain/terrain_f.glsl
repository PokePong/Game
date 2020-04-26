#version 430 core

layout(location = 0) out vec4 out_Color;

uniform int lod;

void main() {

	if(lod == 0) {
		out_Color = vec4(1.0, 1.0, 1.0, 1.0);
	} else if (lod == 1) {
		out_Color = vec4(0.0, 0.0, 1.0, 1.0);
	} else if (lod == 2) {
		out_Color = vec4(0.0, 1.0, 0.0, 1.0);
	} else if (lod == 3) {
		out_Color = vec4(1.0, 0.0, 0.0, 1.0);
	}

}
