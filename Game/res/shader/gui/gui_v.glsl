#version 430 core

layout(location = 0) in vec2 in_Position;

out vec2 pass_Texture;

uniform mat4 m_Ortho;

void main() {

	gl_Position = m_Ortho * vec4(in_Position, 0, 1.0);
	pass_Texture = vec2((in_Position.x * 2  + 1.0) / 2.0, (in_Position.y * 2  + 1.0) / 2.0);

}

