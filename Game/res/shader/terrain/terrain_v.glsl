#version 430 core

layout (location = 0) in vec2 in_Position;

uniform mat4 m_Local;
uniform mat4 m_Face;
uniform mat4 m_World;

void main() {

	vec3 local_Position = (m_Local * vec4(in_Position.x, in_Position.y, 0.0, 1.0)).xyz;
	vec3 face_Position = (m_Face * vec4(local_Position, 1.0)).xyz;
	vec3 world_Position = (m_World * vec4(normalize(face_Position), 1.0)).xyz;
	gl_Position = vec4(world_Position, 1.0);

}
