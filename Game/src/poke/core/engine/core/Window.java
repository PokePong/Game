package poke.core.engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import poke.core.engine.core.engine.Engine;
import poke.core.engine.core.engine.EngineConfig;

public class Window {

	private static Window instance;

	public static int width;
	public static int height;
	private String title;
	private long window;

	public static Window getInstance() {
		if (instance == null)
			instance = new Window();
		return instance;
	}

	public Window() {
		instance = this;
		EngineConfig config = Engine.getInstance().getConfig();
		width = config.getWindow_width();
		height = config.getWindow_height();
		this.title = config.getWindow_title() + " | " + config.getVersion();
	}

	public void create() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			throw new IllegalStateException("[GLFW] Unable to initialize GLFW!");
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		this.window = glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0) {
			throw new RuntimeException("[GLFW] Failed to create window!");
		}

		GLFW.glfwSetFramebufferSizeCallback(window, (window, nwidth, nheight) -> {
			width = nwidth;
			height = nheight;
			GL11.glViewport(0, 0, nwidth, nheight);
			Engine.getInstance().getEngineSystem().getGame().getScenegraph().getCamera()
					.updateProjection(getAspectRatio());
			Engine.getInstance().getEngineSystem().getRenderingEngine().getDeferredRendering().resize(nwidth, nheight);
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwShowWindow(window);
	}

	public void draw() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public void destroy() {
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	public float getAspectRatio() {
		return (float) width / height;
	}

	public long getWindow() {
		return window;
	}

	public static Matrix4f getOrthoMatrix() {
		return new Matrix4f().ortho2D(-width / 2, width / 2, -height / 2, height / 2);
	}

}
