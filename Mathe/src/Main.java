import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


import javax.swing.JFrame;



public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage backBuffer;

	public Main() {
		setTitle("2D-Demo der Wirkung von Zwischenpuffern");
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		backBuffer = new BufferedImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
	}

	public static Circle[] AllCircles = new Circle[2] ;
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Thread.sleep(500);
		Main testObjekt = new Main();
		int m1 = 1;
		int m2 = 1;
		int r1 = 30;
		AllCircles[0] = new Circle(150, Constants.WINDOW_HEIGHT / 2, r1, 1, m1);
		int r2 = 30;
		AllCircles[1] = new Circle(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - r2*2-5, r2, 1, m2);		
		
		testObjekt.setVisible(true);
		double absT = 0;
		while (true) {
			testObjekt.draw(absT);
			try {
				Thread.sleep((int) (Constants.TPF * 1000));
				absT += Constants.TPF;
			} catch (InterruptedException e) {
			}
		}

	}
	void draw(double absT) {
		
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(Color.LIGHT_GRAY);
		bbg.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		
		
		for (Circle circle : AllCircles) {
			circle.handleWallCollision();
		}
		
		AllCircles[0].handleCollision(AllCircles[1]);
		AllCircles[1].handleCollision(AllCircles[0]);
		AllCircles[0].handlenoCollision(AllCircles[1]);
		AllCircles[1].handlenoCollision(AllCircles[0]);
		//System.out.println("pXnow3: " + AllCircles[0].vektor.pX);
		bbg.setColor(Color.RED);
		
		//first circle
		
		AllCircles[0].vektor.pX += AllCircles[0].velocityx1;
		AllCircles[0].vektor.pY += AllCircles[0].velocityy1;
		//AllCircles[0].vektor.pY += AllCircles[0].velocity;
		//System.out.println("pX1: " + AllCircles[0].vektor.pX);
		bbg.fillOval((int)(AllCircles[0].vektor.pX),(int) AllCircles[0].vektor.pY, (int)AllCircles[0].radius * 2, (int)AllCircles[0].radius * 2);
		
		//second circle
		AllCircles[1].vektor.pX +=  AllCircles[1].velocityx2;
		AllCircles[1].vektor.pY +=  AllCircles[1].velocityy2;
		System.out.println("pY2: " + AllCircles[1].vektor.pY);
		bbg.fillOval((int)(AllCircles[1].vektor.pX),(int) AllCircles[1].vektor.pY, (int)AllCircles[1].radius * 2, (int)AllCircles[1].radius * 2);

		
		bbg.setColor(Color.GREEN);
		Vektor temp = getCenterOfMass(AllCircles[0], AllCircles[1]);
		bbg.fillOval((int)temp.pX, (int)temp.pY, 10, 10);
		
		bbg.setColor(Color.YELLOW);
		bbg.drawLine((int)(AllCircles[0].vektor.pX + AllCircles[0].radius), (int)(AllCircles[0].vektor.pY + AllCircles[0].radius), (int)(AllCircles[1].vektor.pX + AllCircles[1].radius), (int)(AllCircles[1].vektor.pY + + AllCircles[1].radius));
		
		
		g.drawImage(backBuffer, 0, 0, null);

	}
	public Vektor getCenterOfMass(Circle c1, Circle c2) {
		double pX = ((c1.masse * c1.vektor.pX)+(c2.masse * c2.vektor.pX)) / (c1.masse + c2.masse);
		double pY = ((c1.masse * c1.vektor.pY)+(c2.masse * c2.vektor.pY)) / (c1.masse + c2.masse);
		
		return new Vektor(pX, pY);
	}
	}

