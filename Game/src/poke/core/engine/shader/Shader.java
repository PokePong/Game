package poke.core.engine.shader;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.Buffer;
import poke.core.engine.utils.ResourceLoader;

public abstract class Shader {

	private int programId;
	private int vertexShaderId;
	private int geometryShaderId;
	private int fragmentShaderId;

	private HashMap<String, Integer> uniforms;

	public Shader() {
		this.programId = GL20.glCreateProgram();
		this.uniforms = new HashMap<String, Integer>();
		if (programId == 0) {
			throw new IllegalStateException("[Shader] Failed to create shader program!");
		}
	}

	public void validateShader() {
		GL20.glLinkProgram(programId);

		if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programId, 1024));
			throw new IllegalStateException("[Shader] Failed to link program!");
		}

		GL20.glValidateProgram(programId);

		if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programId, 1024));
			throw new IllegalStateException("[Shader] Failed to validate shader!");
		}
	}

	private int addProgram(String source, int type) {

		String shaderSource = ResourceLoader.loadShader(source);

		int shaderId = GL20.glCreateShader(type);
		if (shaderId == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programId, 1024));
			throw new IllegalStateException("[Shader] Failed to create shader!");
		}

		GL20.glShaderSource(shaderId, shaderSource);
		GL20.glCompileShader(shaderId);

		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programId, 1024));
			throw new IllegalStateException("[Shader] Failed to compile shader!");
		}

		GL20.glAttachShader(programId, shaderId);
		return shaderId;
	}

	public void addVertexShader(String source) {
		this.vertexShaderId = addProgram(source, GL20.GL_VERTEX_SHADER);
	}

	public void addGeometryShader(String source) {
		this.geometryShaderId = addProgram(source, GL32.GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String source) {
		this.fragmentShaderId = addProgram(source, GL20.GL_FRAGMENT_SHADER);
	}

	public void bind() {
		GL20.glUseProgram(programId);
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}

	public void addUniform(String uniform) {
		int uniformLocation = GL20.glGetUniformLocation(programId, uniform);
		if (uniformLocation == -1) {
			System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
			throw new IllegalStateException("Failed to find uniform location!");
		}
		if (!uniforms.containsKey(uniform)) {
			uniforms.put(uniform, uniformLocation);
		}
	}

	public void setUniform(String uniformName, int value) {
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, float value) {
		GL20.glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, boolean value) {
		if (value) {
			GL20.glUniform1i(uniforms.get(uniformName), 1);
		} else {
			GL20.glUniform1i(uniforms.get(uniformName), 0);
		}
	}

	public void setUniform(String uniformName, Vector2f value) {
		GL20.glUniform2f(uniforms.get(uniformName), value.x, value.y);
	}

	public void setUniform(String uniformName, Vector3f value) {
		GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}

	public void setUniform(String uniformName, Vector4f value) {
		GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Matrix4f value) {
		GL20.glUniformMatrix4fv(uniforms.get(uniformName), true, Buffer.createFlippedBuffer(value));
	}

	public void cleanUp() {
		unbind();
		uniforms.clear();
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, geometryShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(geometryShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(programId);
	}

	public abstract void updateUniforms(GameObject object);

	public int getProgram() {
		return programId;
	}

}
