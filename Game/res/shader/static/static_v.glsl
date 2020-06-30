#version 430 core

layout(location = 0) in vec3 in_Position;
layout(location = 1) in vec3 in_Normal;
layout(location = 2) in vec4 in_Color;

out vec4 pass_Color;
out vec4 pass_Position;
out vec4 pass_Normal;

layout (std140) uniform Camera {
	vec3 c_Position;
	mat4 c_Projection;
	mat4 c_View;
};

uniform mat4 m_World;

void main(void) {

	gl_Position = c_Projection * c_View * m_World * vec4(in_Position, 1.0);
	pass_Color = in_Color;
	pass_Position = m_World * vec4(in_Position, 1.0);
	pass_Normal = vec4(in_Normal, 1.0);

}
