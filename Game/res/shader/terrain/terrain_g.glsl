#version 430 core

layout(triangles) in;
layout(triangle_strip, max_vertices = 4) out;

layout (std140) uniform Camera {
	vec3 c_Position;
	mat4 c_Projection;
	mat4 c_View;
};

void main() {

	for (int i = 0; i < gl_in.length(); i++) {
		vec4 position = gl_in[i].gl_Position;
		gl_Position = c_Projection * c_View * position;
		EmitVertex();
	}

	vec4 position = gl_in[0].gl_Position;
	gl_Position = c_Projection * c_View * position;
	EmitVertex();

	EndPrimitive();

}
