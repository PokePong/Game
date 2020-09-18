package poke.core.engine.input;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import poke.core.engine.core.Window;

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

	private Vector2f previousCursorPosition;
	private Vector2f cursorPosition;
	private Vector2f displVec;
	private Vector2f lockedCursorPosition;

	private boolean cursorLocked;

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

		this.previousCursorPosition = new Vector2f();
		this.cursorPosition = new Vector2f();
		this.displVec = new Vector2f();
		this.lockedCursorPosition = new Vector2f();

		this.cursorLocked = false;
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
				if (cursorLocked) {
					setCursorPosition(lockedCursorPosition, Window.getInstance().getWindow());
				}
				previousCursorPosition = new Vector2f(cursorPosition);
				cursorPosition = new Vector2f((float) x, (float) y);
				double dx = cursorPosition.x - previousCursorPosition.x;
				double dy = cursorPosition.y - previousCursorPosition.y;
				displVec = new Vector2f((float) dx, (float) dy);
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

		if (Math.abs(displVec.x) > 0.001f || Math.abs(displVec.y) > 0.001f) {
			displVec.mul(0.90f);
		} else {
			displVec = new Vector2f(0);
		}
		
	}

	public void lockCursor() {
		lockedCursorPosition = new Vector2f(cursorPosition);
		setCursorLocked(true);
		GLFW.glfwSetInputMode(Window.getInstance().getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
	}

	public void unlockCursor() {
		setCursorLocked(false);
		setCursorPosition(lockedCursorPosition, Window.getInstance().getWindow());
		GLFW.glfwSetInputMode(Window.getInstance().getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
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

	public void setLockedCursorPosition(Vector2f lockedCursorPosition) {
		this.lockedCursorPosition = lockedCursorPosition;
	}

	public void setCursorPosition(Vector2f cursorPosition, long window) {
		this.cursorPosition = cursorPosition;
		GLFW.glfwSetCursorPos(window, cursorPosition.x, cursorPosition.y);
	}

	public Vector2f getDisplVec() {
		return displVec;
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

	public Vector2f getPreviousCursorPosition() {
		return previousCursorPosition;
	}

	public Vector2f getLockedCursorPosition() {
		return lockedCursorPosition;
	}

	public boolean isCursorLocked() {
		return cursorLocked;
	}

	public void setCursorLocked(boolean cursorLocked) {
		this.cursorLocked = cursorLocked;
	}

}
