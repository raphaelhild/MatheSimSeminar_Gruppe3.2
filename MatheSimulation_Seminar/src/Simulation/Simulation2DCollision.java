package Simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

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
	}
	
	public static Circle[] AllCircles = new Circle[2];
	
	
	public static void main(String[] args) {
		Simulation2DCollision testObjekt = new Simulation2DCollision();
		
		
		int m1 = 1;
		int m2 = 1;
		int r1 = 30;
		int r2 = 30;
		AllCircles[0] = new Circle(150, _0_Constants.WINDOW_HEIGHT / 2, r1, 1, m1);
		AllCircles[1] = new Circle(_0_Constants.WINDOW_WIDTH / 2, _0_Constants.WINDOW_HEIGHT - r2*2, r2, 1, m2);		
		
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
	

	
	void draw(double absT) {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(Color.LIGHT_GRAY);
		bbg.fillRect(0, 0, _0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);
		
		//Abfragen auf Kolision
		for (Circle circle : AllCircles) {
			circle.handleWallCollision();
		}
		
		AllCircles[0].handleCollision(AllCircles[1]);
		AllCircles[1].handleCollision(AllCircles[0]);
	
		
		//Zeichnen der Objekte
		bbg.setColor(Color.RED);
		
		//erster circle
		AllCircles[0].positionX += absT/2 * AllCircles[0].velocity;
		bbg.fillOval((int)(AllCircles[0].positionX),(int) AllCircles[0].positionY, (int)AllCircles[0].radius * 2, (int)AllCircles[0].radius * 2);
		
		//zweiter circle
		AllCircles[1].positionY -= absT/2 * AllCircles[1].velocity;
		bbg.fillOval((int)(AllCircles[1].positionX),(int) AllCircles[1].positionY, (int)AllCircles[1].radius * 2, (int)AllCircles[1].radius * 2);

		//schwerpunkt
		bbg.setColor(Color.GREEN);
		Point temp = getMainEmphasis(AllCircles[0], AllCircles[1]);
		bbg.fillOval(temp.x, temp.y, 10, 10);
		
		//Linie zweischen den beiden Mittelpunkten
		bbg.setColor(Color.YELLOW);
		bbg.drawLine((int)(AllCircles[0].positionX + AllCircles[0].radius), (int)(AllCircles[0].positionY + AllCircles[0].radius), (int)(AllCircles[1].positionX + AllCircles[1].radius), (int)(AllCircles[1].positionY + + AllCircles[1].radius));

		//Bild zeichnen/ ausgaben
		g.drawImage(backBuffer, 0, 0, null);

	}
	
	//Koordinaten des Schwerpunktes bestimmen
	public Point getMainEmphasis(Circle c1, Circle c2) {
		int pX = (int)(((c1.masse * c1.positionX)+(c2.masse * c2.positionX)) / (c1.masse + c2.masse));
		int pY = (int)(((c1.masse * c1.positionY)+(c2.masse * c2.positionY)) / (c1.masse + c2.masse));
		
		return new Point(pX, pY);
	}
}
