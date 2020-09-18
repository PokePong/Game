package poke.core.engine.core;

import poke.core.engine.core.engine.Engine;
import poke.core.engine.core.engine.EngineSystem;
import poke.core.gl.config.Default;
import poke.core.gl.deferred.DeferredRendering;
import poke.core.module.gui.GuiConstraint;
import poke.core.module.gui.GuiElement;
import poke.core.module.gui.GuiScreen;
import poke.core.module.gui.constraint.CenterConstraint;
import poke.core.module.gui.constraint.RelativeConstraint;

public class RenderingEngine {

	private EngineSystem system;
	private Game game;
	private DeferredRendering deferredRendering;

	public RenderingEngine() {
		this.system = Engine.getInstance().getEngineSystem();
		this.game = system.getGame();
		this.deferredRendering = new DeferredRendering();
	}

	public void init() {
		Default.init();
		deferredRendering.init();

		GuiScreen screen = new GuiScreen();
		GuiElement element = new GuiElement();
		element.setTexture(deferredRendering.getScene());
		GuiConstraint c = new GuiConstraint();
		c.setX(new CenterConstraint());
		c.setY(new CenterConstraint());
		c.setWidth(new RelativeConstraint(1f));
		c.setHeight(new RelativeConstraint(1f));
		element.setConstraint(c);
		screen.addChild(element);
		screen.setEnable(true);
		game.addGuiScreen(screen);

	}

	public void render() {
		Default.clearScreen();

		deferredRendering.bind();
		Default.clearScreen();
		game.getScenegraph().render();
		deferredRendering.unbind();

		deferredRendering.render();
		// game.getScenegraph().render();

		game.getGui().render();

	}

	public void cleanUp() {
		deferredRendering.cleanUp();
	}

	public DeferredRendering getDeferredRendering() {
		return deferredRendering;
	}

}
