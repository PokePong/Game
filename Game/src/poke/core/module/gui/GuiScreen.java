package poke.core.module.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiScreen {

	private List<GuiElement> children;
	private boolean enable;

	public GuiScreen() {
		this.children = new ArrayList<GuiElement>();
	}

	public void addChild(GuiElement child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void addChildren(GuiElement... children) {
		this.children.addAll(Arrays.asList(children));
		Arrays.stream(children).forEach(e -> {
			e.setParent(this);
		});
	}

	public void init() {
		for (GuiElement element : children) {
			element.init();
		}
	}

	public void update(double delta) {
		for (GuiElement element : children) {
			if (element.isEnable())
				element.update(delta);
		}
	}

	public void render() {
		for (GuiElement element : children) {
			if (element.isEnable())
				element.render();
		}
	}

	public void cleanUp() {
		for (GuiElement element : children) {
			element.cleanUp();
		}
	}

	public List<GuiElement> getChildren() {
		return children;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
