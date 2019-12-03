
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
public class Circle {
	
	Vektor vektor = new Vektor();
	double radius;
	double velocity;
	double velocity1;
	double velocityx1;
	double velocityy1;
	double velocityx2;
	double velocityy2;
	double masse;
	
	Circle(double x, double y, double _r, double _v, double _m){
		vektor.pX = x;
		vektor.pY = y;
		radius = _r;
		velocity = _v;
		velocity1= _v;
		velocityx1= _v;
		velocityy1= _v;
		velocityx2= _v;
		velocityy2= _v;
		masse = _m;
	}
	
	public void handleWallCollision() {
		if (vektor.pX + radius*2 >= Constants.WINDOW_WIDTH || vektor.pX <= 0) {
			velocityx1 *= -1;
			velocityx2 *= -1;
		}
		if (vektor.pY + radius*2 >= Constants.WINDOW_HEIGHT || vektor.pY <= radius) {
			velocityy1 *= -1;
			velocityy2 *= -1;
			System.out.println("Hallo");
		}
	}	
	
	public boolean hasCollision(Circle circle2){
		double x1 = vektor.pX;
		double y1 = vektor.pY;
		double x2 = circle2.vektor.pX;
		double y2 = circle2.vektor.pY;
		double r1 = this.radius;
		double r2 = circle2.radius;
		
		//Distanz zwischen den beiden Mittelpunkten
		//double distance = java.lang.Math.sqrt(((x1 - x2)*(x1 - x2)) + ((y1 - y2) * (y1 - y2)));
		//System.out.println("a"+(x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		//System.out.println("b"+(r1 + r2));
		if(Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1))) < (r1 + r2)){
			//System.out.println("a"+(x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
			//System.out.println("b"+(r1 + r2));
			return true;
		}
		else { return false;
			
			
		}
		
		//return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 + r2) * (r1 + r2));
		
		//System.out.println(distance);
		//System.out.println((r1 + r2) / 2);
		
		//Sobald Summe beider Radien unterschritten besteht eine Kollision
		//"(r1 + r2) / 2", da nur Abstand der Radien zählt und nicht der Durchmesser
		/*if(distance <= (r1 + r2) / 2) { 
			return true; }
		return false;*/
	}

	public void handleCollision(Circle circle2) {
		if(hasCollision(circle2)==true) {
			//velocity=-1;
			//...
			//circle2.vektor.pY=vektor.pY*velocity1;
			
		
		//Rechnung für vollelastischen Stoß
		Vektor u1 = this.getU(this.vektor, this.getV(this.vektor, circle2.vektor));
		Vektor u2 = this.getU(circle2.vektor, this.getV(this.vektor, circle2.vektor));

		Vektor b = this.vektor.sub(circle2.vektor);
		
		
		//!!!!!!
		double k = 20;
		double l = 20;
		if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && circle2.velocity == 0)
		{
			l = 0;
		}else if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && this.velocity == 0) {
			k = 0;
		}
		else {
		    k =  2 * u1.pX * b.pX;
		    l = -2 * u2.pY * b.pY;
		}
		
		//this.vektor.aX = this.vektor.pX - k * b.pX;
		//this.vektor.aY = this.vektor.pY - k * b.pY;
		//this.velocityx1 = -vektor.pX - k * b.pX;
		//this.velocityy1 = -vektor.pY - l * b.pY;
		//circle2.vektor.pX = circle2.vektor.pX - l * b.pX;
		//circle2.vektor.pY = circle2.vektor.pY - l * b.pY;
		//this.velocityx2 = -circle2.vektor.pX - k * b.pX;
		//this.velocityy2 = -circle2.vektor.pY - l * b.pY;
		velocityx1 *= 1;
		velocityx2 *= 1;
		velocityy1 *= -1;
		velocityy2 *= -1;
		
		
		
		
		//System.out.println("C2X: " + circle2.vektor.pX);
		//System.out.println("C2Y: " + circle2.vektor.pY);
		//System.out.println("C1X: " + this.vektor.pX);
		//System.out.println("C1Y: " + this.vektor.pY);
	}
			
	
	
	
	}
	public void handlenoCollision(Circle circle2) {
		if(hasCollision(circle2)==false){
			//System.out.println("now1: " + circle2.vektor.pX);
			velocityx1 *= velocity;
			velocityy1 *= velocity;
			//System.out.println("now2: " + circle2.vektor.pX);
		}
		else {
			velocityx2 *= velocity;
			velocityy2 *= velocity;
			
		}
		
	}
	
	public Vektor getV(Vektor v1, Vektor v2) {
		double VX = (v1.pX + v2.pX) / 2;
		double VY = (v1.pY + v2.pY) / 2;
		
		return new Vektor(VX, VY);
	}
	
	public Vektor getU(Vektor v1, Vektor V) {
		double UX = v1.pX - V.pX;
		double UY = v1.pY - V.pY;
		//velocity=-velocity;
		
		return new Vektor(UX, UY);
	}
	
	
}

