import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage backBuffer;

	public Main() {
		setTitle("Stöße von Massen im 2D-Raum");
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		backBuffer = new BufferedImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}

	public static Circle[] AllCircles = new Circle[2] ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Main testObjekt = new Main();
		
		int m1 = 1;
		int m2 = 1;
		int r1 = 30;
		AllCircles[0] = new Circle(150, Constants.WINDOW_HEIGHT / 2, r1, 2, 3, m1);
		int r2 = 30;
		System.out.println("velocity x1 : " + AllCircles[0].velocity_x);
		AllCircles[1] = new Circle(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - r2*2-5, r2, 1, 2, m2);	
		
		testObjekt.setVisible(true);
		while (true) {
			testObjekt.draw();
			try {
				Thread.sleep((int) (Constants.TPF * 1000));
			} catch (InterruptedException e) {
			}
		}
	}
	
	void draw() {
		//Vorbereitung (Hintergrundfarbe etc.)
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		bbg.setColor(Color.LIGHT_GRAY);
		bbg.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		
		//Kollision mit einer der Wände?
		for (Circle circle : AllCircles) {
			circle.handleWallCollision();
		}

		//Kollision mit dem anderen Kreis?
		AllCircles[0].handleCollision(AllCircles[1]);
	
		//Farbe der Kreise festlegen
		bbg.setColor(Color.RED);
		
		//Erster Kreis
		AllCircles[0].vektor.pX += AllCircles[0].velocity_x;
		AllCircles[0].vektor.pY += AllCircles[0].velocity_y;
		bbg.fillOval((int)(AllCircles[0].vektor.pX),(int) AllCircles[0].vektor.pY, (int)AllCircles[0].radius * 2, (int)AllCircles[0].radius * 2);
		
		//Zweiter Kreis
		AllCircles[1].vektor.pX +=  AllCircles[1].velocity_x; 
		AllCircles[1].vektor.pY +=  AllCircles[1].velocity_y;
		bbg.fillOval((int)(AllCircles[1].vektor.pX),(int) AllCircles[1].vektor.pY, (int)AllCircles[1].radius * 2, (int)AllCircles[1].radius * 2);

		//Schwerpunkt anzeigen
		bbg.setColor(Color.GREEN);
		Vektor temp = getCenterOfMass(AllCircles[0], AllCircles[1]);
		bbg.fillOval((int)temp.pX, (int)temp.pY, 10, 10);
		
		//Verbindungslinie zeichnen (zw. den beiden Mittelpunkten)
		bbg.setColor(Color.YELLOW);
		bbg.drawLine((int)(AllCircles[0].vektor.pX + AllCircles[0].radius), (int)(AllCircles[0].vektor.pY + AllCircles[0].radius), (int)(AllCircles[1].vektor.pX + AllCircles[1].radius), (int)(AllCircles[1].vektor.pY + + AllCircles[1].radius));
		
		//Bild zeichnen
		g.drawImage(backBuffer, 0, 0, null);
	}
	
	//Berechnung der Schwerpunktposition
	public Vektor getCenterOfMass(Circle c1, Circle c2) {
		double pX = (c1.vektor.pX + c2.vektor.pX) / 2 + (c1.radius * 0.85);
		double pY = (( c1.vektor.pY +  c2.vektor.pY) / 2) + (c1.radius * 0.85);
		return new Vektor(pX, pY);
	}
	
}
