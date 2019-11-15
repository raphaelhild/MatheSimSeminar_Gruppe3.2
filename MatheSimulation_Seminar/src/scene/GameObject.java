package scene;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameObject {

	public GameObject() {
		children = new ArrayList<>();
	}

	public double positionX;
	public double positionY;

	public ArrayList<GameObject> children;// = new ArrayList<>();
	public Color color;

	// Physics
	public double velocityX;
	public double velocityY;

	public void AddGameObject(GameObject go) {
		children.add(go);
	}

	public void update(double dt) {
		positionX += velocityX * dt;
		positionY += velocityY * dt;

		if (children != null)
			for (GameObject go : children)
				go.update(dt);
	}

	public void draw(Graphics g) {
		if (children != null)
			for (GameObject go : children)
				go.draw(g);
	}
}
