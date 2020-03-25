package poke.core.engine.input;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {

	private static Input instance;

	private GLFWKeyCallback keyboard;
	private GLFWMouseButtonCallback mouse;
	private GLFWScrollCallback scroll;
	private GLFWCursorPosCallback cursor;

	private ArrayList<Integer> pushedKeys;
	private ArrayList<Integer> keysHolding;
	private ArrayList<Integer> releasedKeys;

	private ArrayList<Integer> pushedButtons;
	private ArrayList<Integer> buttonsHolding;
	private ArrayList<Integer> releasedButtons;

	private float scrollOffSet;

	private Vector2f cursorPosition;
	private Vector2f lockedCursorPosition;

	public static Input getInstance() {
		if (instance == null)
			instance = new Input();
		return instance;
	}

	public Input() {
		instance = this;
		this.pushedKeys = new ArrayList<Integer>();
		this.keysHolding = new ArrayList<Integer>();
		this.releasedKeys = new ArrayList<Integer>();

		this.pushedButtons = new ArrayList<Integer>();
		this.buttonsHolding = new ArrayList<Integer>();
		this.releasedButtons = new ArrayList<Integer>();

		this.cursorPosition = new Vector2f();
		this.lockedCursorPosition = new Vector2f();
	}

	public void init(long window) {

		GLFW.glfwSetKeyCallback(window, (keyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (action == GLFW.GLFW_PRESS) {
					if (!pushedKeys.contains(key)) {
						pushedKeys.add(key);
						keysHolding.add(key);
					}
				}
				if (action == GLFW.GLFW_RELEASE) {
					keysHolding.remove(new Integer(key));
					releasedKeys.add(key);
				}

			}
		}));

		GLFW.glfwSetMouseButtonCallback(window, (mouse = new GLFWMouseButtonCallback() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				if (button == 2 && action == GLFW.GLFW_PRESS) {
					lockedCursorPosition = new Vector2f(cursorPosition);
					GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
				}

				if (button == 2 && action == GLFW.GLFW_RELEASE) {
					GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
				}
				if (action == GLFW.GLFW_PRESS) {
					if (!pushedButtons.contains(button)) {
						pushedButtons.add(button);
						buttonsHolding.add(button);
					}
				}
				if (action == GLFW.GLFW_RELEASE) {
					buttonsHolding.remove(new Integer(button));
					releasedButtons.add(button);
				}
			}
		}));

		GLFW.glfwSetCursorPosCallback(window, (cursor = new GLFWCursorPosCallback() {

			@Override
			public void invoke(long window, double x, double y) {
				cursorPosition.x = (float) x;
				cursorPosition.y = (float) y;
			}
		}));

		GLFW.glfwSetScrollCallback(window, (scroll = new GLFWScrollCallback() {

			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				setScrollOffSet((float) yoffset);
			}
		}));
	}

	public void update() {
		setScrollOffSet(0);
		pushedKeys.clear();
		releasedKeys.clear();

		pushedButtons.clear();
		releasedButtons.clear();
	}

	public boolean isKeyPushed(int key) {
		return pushedKeys.contains(key);
	}

	public boolean isKeyReleased(int key) {
		return releasedKeys.contains(key);
	}

	public boolean isKeyHolding(int key) {
		return keysHolding.contains(key);
	}

	public boolean isButtonPushed(int key) {
		return pushedButtons.contains(key);
	}

	public boolean isButtonReleased(int key) {
		return releasedButtons.contains(key);
	}

	public boolean isButtonHolding(int key) {
		return buttonsHolding.contains(key);
	}

	public void cleanUp() {
		keyboard.free();
		mouse.free();
		scroll.free();
		cursor.free();
	}

	public float getScrollOffSet() {
		return scrollOffSet;
	}

	public void setScrollOffSet(float scrollOffSet) {
		this.scrollOffSet = scrollOffSet;
	}

	public Vector2f getCursorPosition() {
		return cursorPosition;
	}

	public void setCursorPosition(Vector2f cursorPosition, long window) {
		this.cursorPosition = cursorPosition;
		GLFW.glfwSetCursorPos(window, cursorPosition.x, cursorPosition.y);
	}

	public ArrayList<Integer> getPushedKeys() {
		return pushedKeys;
	}

	public ArrayList<Integer> getKeysHolding() {
		return keysHolding;
	}

	public ArrayList<Integer> getReleasedKeys() {
		return releasedKeys;
	}

	public ArrayList<Integer> getPushedButtons() {
		return pushedButtons;
	}

	public ArrayList<Integer> getButtonsHolding() {
		return buttonsHolding;
	}

	public ArrayList<Integer> getReleasedButtons() {
		return releasedButtons;
	}

	public Vector2f getLockedCursorPosition() {
		return lockedCursorPosition;
	}

}
