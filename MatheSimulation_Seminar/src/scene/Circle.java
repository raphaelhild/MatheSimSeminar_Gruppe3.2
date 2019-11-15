package scene;

import java.awt.Graphics;

import Simulation.*;

public class Circle extends GameObject {

	public double diameter;

	@Override
	public void update(double dt) {
		super.update(dt);

		if (positionX + diameter >= _0_Constants.WINDOW_WIDTH || positionX <= 0) {
			velocityX *= -1;
		}

		if (positionY + diameter >= _0_Constants.WINDOW_HEIGHT || positionY <= 0) {
			velocityY *= -1;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) positionX, (int) positionY, (int) diameter, (int) diameter);
	}

}
