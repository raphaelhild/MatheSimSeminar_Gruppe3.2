package Simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;
import java.io.Console;

import javax.swing.JFrame;

public class Simulation2DCollision extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage backBuffer;

	public Simulation2DCollision() {
		setTitle("2D-Demo der Wirkung von Zwischenpuffern");
		setSize(_0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		backBuffer = new BufferedImage(_0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);

		// Denkbar ist die Verwendung von RGBA-Bildern
	}

	public static void main(String[] args) {
		Simulation2DCollision testObjekt = new Simulation2DCollision();

		testObjekt.setVisible(true);
		double absT = 0;
		while (true) {
			testObjekt.draw(absT);
			try {
				Thread.sleep((int) (_0_Constants.TPF * 1000));
				absT += _0_Constants.TPF;
			} catch (InterruptedException e) {
			}
		}

	}

	int velocityX = 1;
	int velocityY = 1;
	double positionX1 = 0;
	double positionX2 = _0_Constants.WINDOW_WIDTH - 30;
	
	double positionY1 = _0_Constants.WINDOW_HEIGHT / 2;
	double positionY2 = _0_Constants.WINDOW_HEIGHT / 2;
	
	void draw(double absT) {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(Color.LIGHT_GRAY);
		bbg.fillRect(0, 0, _0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);
		bbg.setColor(Color.RED);
		
		//WallDetection
		if (positionX1 + 30 >= _0_Constants.WINDOW_WIDTH || positionX1 <= 0) {
			velocityX *= -1;
		}
		if (positionY1 + 30 >= _0_Constants.WINDOW_HEIGHT || positionY1 <= 0) {
			velocityY *= -1;
		}
		
		//CollisionDetection
		if(positionX1 >= positionX2 - 30) {
			velocityX *= -1;
		}
		
		
		//first circle
		positionX1 += absT/2 * velocityX;
		bbg.fillOval((int)(positionX1),(int) positionY1, 30, 30);
		
		//second circle
		positionX2 -= absT/2 * velocityX;
		bbg.fillOval((int)(positionX2),(int) positionY2, 30, 30);
			
		
		g.drawImage(backBuffer, 0, 0, null);

	}
}
