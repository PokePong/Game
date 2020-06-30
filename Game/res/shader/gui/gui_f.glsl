#version 430 core

in vec2 pass_Texture;
in vec4 pass_Color;

out vec4 out_Color;

uniform sampler2D texture;
uniform int textured;
uniform vec4 color;

void main() {

	vec4 rgba;
	if(textured == 0) {
		rgba = color;
	} else {
		rgba = texture2D(texture, pass_Texture) * color;
	}

	out_Color = rgba;

}
