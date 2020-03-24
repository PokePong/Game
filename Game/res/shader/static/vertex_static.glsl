#version 330 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;
layout(location = 2) in vec4 in_Color;

out vec4 pass_Color;

layout (std140) uniform Camera {
	vec3 eyePosition;
	vec3 eyeDirection;
	mat4 m_Projection;
	mat4 m_View;
	mat4 m_ViewProjection;
};

uniform mat4 m_World;

void main(void) {

	gl_Position = m_Projection * m_World * vec4(in_Position, 1.0);
	pass_Color = in_Color;

}
