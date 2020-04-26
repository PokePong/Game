package poke.instance.terrain;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import poke.core.engine.renderer.Renderer;
import poke.core.engine.scene.GameObject;
import poke.core.engine.scene.Node;
import poke.core.engine.utils.Constants;
import poke.core.gl.buffer.vbo.Patch2DVBO;
import poke.core.gl.config.Default;
import poke.instance.GameCamera;

public class QuadtreeNode extends GameObject {

	private QuadtreeFace face;
	private boolean leaf;
	private int lod;
	private float width;
	private Vector2f location;
	private Vector3f position;
	private float distanceFromCamera;
	private Patch2DVBO patch;

	public QuadtreeNode(QuadtreeFace face, int lod, Vector2f location, Patch2DVBO patch) {
		this.face = face;
		this.leaf = true;
		this.lod = lod;
		this.width = 1f / (float) Math.pow(2, lod);
		this.location = location;
		this.patch = patch;
	}

	@Override
	public void _init_() {
		getLocalTransform().scaleTo(width, width, 0);
		getLocalTransform().translateTo(location.x, location.y, 0);
		setWorldTransform(face.getLocalTransform());

		computePosition();
		updateQuadTree();

		setVbo(patch);

		Renderer renderer = new Renderer(QuadtreeShader.getInstance(), new Default());
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}

	@Override
	public void _update_(double delta) {
		computePosition();
		updateQuadTree();
	}

	@Override
	public void render() {
		if (leaf && renderable()) {
			super.render();
		} else {
			for (Node child : getChildren()) {
				child.render();
			}
		}
	}

	private void updateQuadTree() {
		updateChildNode();
		for (Node child : getChildren())
			((QuadtreeNode) child).updateQuadTree();
	}

	private void updateChildNode() {
		this.distanceFromCamera = distanceFromCamera(position);
		float range = face.getLodRanges().get(lod);
		if (distanceFromCamera < range && leaf && lod < QuadtreeFace.MAX_LOD) {
			addChildQuadTreeNode(lod + 1);
		} else if (distanceFromCamera >= range) {
			removeChildQuadTreeNode();
		}

	}

	public void addChildQuadTreeNode(int lod) {
		if (leaf)
			leaf = false;

		if (getChildren().size() == 0) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					QuadtreeNode child = new QuadtreeNode(face, lod,
							new Vector2f(location.x + i * width / 2f, location.y + j * width / 2f), patch);
					child._init_();
					addChild(child);
				}
			}
		}
	}

	public void removeChildQuadTreeNode() {
		if (!leaf) {
			leaf = true;
		}
		if (getChildren().size() != 0) {
			getChildren().clear();
		}
	}

	private void computePosition() {
		Vector4f local_Position = new Vector4f(location.x + width / 2f, location.y + width / 2f, 0, 1);
		Vector4f face_Position = local_Position.mul(getWorldTransform().getWorldMatrix());
		face_Position.normalize3();
		Vector4f world_Position = face_Position.mul(face.getWorldTransform().getWorldMatrix());
		this.position = new Vector3f(world_Position.x, world_Position.y, world_Position.z);
	}

	private float distanceFromCamera(Vector3f position) {
		Vector3f camera = GameCamera.getInstance().getPosition();
		return position.distance(camera);
	}

	private boolean renderable() {
		Vector3f center = ((Planet) face.getParent()).getWorldTransform().getTranslation();
		float centerCamera = distanceFromCamera(center);
		return distanceFromCamera <= centerCamera ? true : false;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public float getWidth() {
		return width;
	}

	public Vector2f getLocation() {
		return location;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Patch2DVBO getPatch() {
		return patch;
	}

	public QuadtreeFace getFace() {
		return face;
	}

	public int getLod() {
		return lod;
	}

}
