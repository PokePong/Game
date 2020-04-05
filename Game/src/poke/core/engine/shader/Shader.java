package poke.core.engine.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryStack;

import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.ResourceLoader;
import poke.core.module.gui.GuiElement;

public abstract class Shader {

	private int programId;
	private int vertexShaderId;
	private int geometryShaderId;
	private int fragmentShaderId;
	private int computeShaderId;

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

	public void addComputeShader(String source) {
		this.computeShaderId = addProgram(source, GL43.GL_COMPUTE_SHADER);
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
			throw new IllegalStateException("[Shader] Failed to find uniform location!");
		}
		if (!uniforms.containsKey(uniform)) {
			uniforms.put(uniform, uniformLocation);
		}
	}

	public void addUniformBlock(String uniform) {
		int uniformLocation = GL31.glGetUniformBlockIndex(programId, uniform);
		if (uniformLocation == -1) {
			System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
			throw new IllegalStateException("[Shader] Failed to find uniform block location!");
		}
		if (!uniforms.containsKey(uniform)) {
			uniforms.put(uniform, uniformLocation);
		}
	}

	public void setUniform(String uniformName, int value) {
		if (uniforms.get(uniformName) == null)
			return;
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, float value) {
		if (uniforms.get(uniformName) == null)
			return;
		GL20.glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, boolean value) {
		if (uniforms.get(uniformName) == null)
			return;
		if (value) {
			GL20.glUniform1i(uniforms.get(uniformName), 1);
		} else {
			GL20.glUniform1i(uniforms.get(uniformName), 0);
		}
	}

	public void setUniform(String uniformName, Vector2f value) {
		if (uniforms.get(uniformName) == null)
			return;
		GL20.glUniform2f(uniforms.get(uniformName), value.x, value.y);
	}

	public void setUniform(String uniformName, Vector3f value) {
		if (uniforms.get(uniformName) == null)
			return;
		GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}

	public void setUniform(String uniformName, Vector4f value) {
		if (uniforms.get(uniformName) == null)
			return;
		GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
	}

	public void setUniform(String uniformName, Matrix4f value) {
		if (uniforms.get(uniformName) == null)
			return;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
		}
	}

	public void setUniformBlock(String uniformName, int bindingIndex) {
		if (uniforms.get(uniformName) == null)
			return;
		GL31.glUniformBlockBinding(programId, uniforms.get(uniformName), bindingIndex);
	}

	public void cleanUp() {
		unbind();
		uniforms.clear();
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, geometryShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDetachShader(programId, computeShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(geometryShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteShader(computeShaderId);
		GL20.glDeleteProgram(programId);
	}

	public abstract void updateUniforms(GameObject object);

	public void updateUniforms(GuiElement element) {

	}

	public int getProgram() {
		return programId;
	}

}
