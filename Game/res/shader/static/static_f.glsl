#version 430 core

in vec4 pass_Position;
in vec4 pass_Color;
in vec4 pass_Normal;

layout (location = 0) out vec4 out_Albedo;
layout (location = 1) out vec4 out_Position;
layout (location = 2) out vec4 out_Normal;

void main(void) {

	out_Albedo = pass_Color;
	out_Position = pass_Position;
	out_Normal = normalize(pass_Normal);

}
