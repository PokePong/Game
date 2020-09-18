package poke.core.gl.deferred;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT5;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;

import org.lwjgl.opengl.GL11;

import poke.core.engine.core.Window;
import poke.core.engine.model.Texture2D;
import poke.core.engine.utils.Buffer;
import poke.core.gl.buffer.FBO;

public class DeferredRendering {

	private FBO fbo;
	private GBuffer gbuffer;
	private Texture2D scene;
	private DeferredShader shader;
	private DeferredConfig config;

	public DeferredRendering() {
		this.fbo = new FBO();
		this.gbuffer = new GBuffer(Window.width, Window.height);
		this.scene = new Texture2D(Window.width, Window.height);
		this.config = new DeferredConfig();
	}

	public void init() {
		this.shader = DeferredShader.getInstance();
		
		scene.bind();
		scene.allocateImage2D(GL_RGBA16F, GL11.GL_RGBA);
		scene.noFilter();
		scene.unbind();

		fbo.bind();
		fbo.createColorTextureAttachment(gbuffer.getPosition().getId(), 0);
		fbo.createColorTextureAttachment(gbuffer.getAlbedo().getId(), 1);
		fbo.createColorTextureAttachment(gbuffer.getNormal().getId(), 2);
		fbo.createDepthTextureAttachment(gbuffer.getDepth().getId());
		fbo.createColorTextureAttachment(scene.getId(), 5);
		fbo.setDrawBuffers(Buffer.createFlippedBuffer(GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2,
				GL_COLOR_ATTACHMENT5));
		fbo.checkStatus();
		fbo.unbind();
	}

	public void render() {
		config.enable();
		shader.bind();
		shader.updateUniforms(scene, gbuffer.getPosition(), gbuffer.getAlbedo(), gbuffer.getNormal());
		shader.unbind();
		config.disable();
	}

	public void bind() {
		fbo.bind();
	}

	public void unbind() {
		fbo.unbind();
	}

	public void cleanUp() {
		scene.cleanUp();
		fbo.cleanUp();
		shader.cleanUp();
	}
	
	public void resize(int width, int height) {
		gbuffer.resize(width, height);
		scene.resize(width, height);
	}

	public Texture2D getScene() {
		return scene;
	}

}
