#version 430 core

layout (location = 0) out vec4 out_Position;
layout (location = 1) out vec4 out_Albedo;
layout (location = 2) out vec4 out_Normal;

in vec4 pass_Position;
in vec4 pass_Normal;
in vec2 pass_Texture;

uniform sampler2D texture;

void main() {


	out_Position = pass_Position;
	out_Albedo = texture2D(texture, pass_Texture) * vec4(1);
	out_Normal = pass_Normal;

}
