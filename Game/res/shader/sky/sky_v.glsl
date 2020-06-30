#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;
layout(location = 2) in vec4 in_Color;

out vec3 worldPosition;

layout (std140) uniform Camera {
	mat4 m_Projection;
	mat4 m_View;
};

uniform mat4 m_World;

void main() {

	gl_Position = m_Projection * m_View * m_World * vec4(in_Position, 1.0);
	worldPosition = (m_World * vec4(in_Position, 1)).xyz;

}



