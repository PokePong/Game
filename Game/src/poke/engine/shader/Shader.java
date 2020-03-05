package poke.engine.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import poke.engine.utils.ResourceLoader;

public abstract class Shader {

	private int programId;
	private int vertexShaderId;
	private int geometryShaderId;
	private int fragmentShaderId;

	public Shader() {
		this.programId = GL20.glCreateProgram();
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

	public void cleanUp() {
		unbind();
		GL20.glDetachShader(programId, vertexShaderId);
		GL20.glDetachShader(programId, geometryShaderId);
		GL20.glDetachShader(programId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(geometryShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(programId);
	}

	public int getProgram() {
		return programId;
	}

}
