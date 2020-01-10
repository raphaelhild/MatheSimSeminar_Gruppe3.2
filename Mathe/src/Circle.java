
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
public class Circle {
	
	Vektor vektor = new Vektor();
	double radius;
	double velocityx1;
	double velocityy1;
	double velocityx2;
	double velocityy2;
	double masse;
	double bx;
	double by;
	double V;
	double Vx;
	double Vy;
	double U1y;
	double U1x;
	double U2y;
	double U2x;
	
	int Epsilon = 1; //muss noch änderbar gemacht werden
	boolean status = false; //wenn false, dann wird bei Zusammenstoß neu berechnet
	
	Circle(double x, double y, double _r, double _v, double _m){
		vektor.pX = x;
		vektor.pY = y;
		radius = _r;
		velocityx1= _v;
		velocityy1= 0;
		velocityx2= 0;
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
			//System.out.println("Hallo");
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
		if(Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1))) <= (r1 + r2)){
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
		if(hasCollision(circle2)==false) { status = false; }
		
		
		if(hasCollision(circle2)==true && status==false) {
			
		status = true;

			//velocity=-1;
			
			//circle2.vektor.pY=vektor.pY*velocity1;
			
		
		//Rechnung für vollelastischen Stoß
		//Vektor u1 = this.getU(this.vektor, this.getV(this.vektor, circle2.vektor));
		//Vektor u2 = this.getU(circle2.vektor, this.getV(this.vektor, circle2.vektor));

		//Vektor b = this.vektor.sub(circle2.vektor);
		
		
		V = (this.velocityx1/this.velocityy1+this.velocityx2/+this.velocityy2)/2;
		
		//k= 2*bdach-
		
		
		//Einheitsvektor b^ berechnen
		bx = this.vektor.pX - circle2.vektor.pX;
		by = this.vektor.pY - circle2.vektor.pY;
		
		double b_length = Math.sqrt((bx*bx)+(by*by));
		bx *= 1 / b_length;
		by *= 1 / b_length;
		
		//V berechnen
		Vx = (this.velocityx1 + this.velocityx2)/2;
		Vy = (this.velocityy1 +this.velocityy2)/2;
		
		//U1 und U2 (Schwerpunktgeschwindigkeit) berechnen
		U1x= this.velocityx1 - Vx;
		U1y= this.velocityy1 - Vy;
		U2x= this.velocityx2 - Vx;
		U2y= this.velocityy2 - Vy;
		
		//System.out.println(Vx);
		//System.out.println(this.velocityx1);
		//System.out.println(U1x);
		
		if(this.Epsilon == 1) {
			//k und l berechnen
			
			double k = 2 * ((bx * U1x) + (by * U1y));
			double l = 2 * ((bx * U2x) + (by * U2y));
			System.out.println("bx"+bx);
			System.out.println(U1x);
			//System.out.println(velocityx1);
			//System.out.println(velocityx1);
			//System.out.println(velocityy1);
			System.out.println(k+" "+l);
			
			//neue Geschwindigkeiten v1 und v2 berechnen
			double v1_length = Math.sqrt((velocityx1 * velocityx1) + (velocityy1 * velocityy1));
			double velocityx1_einheits = (1 / v1_length) * velocityx1;
			double velocityy1_einheits = (1 / v1_length) * velocityy1;
			
			double v2_length = Math.sqrt((velocityx2 * velocityx2) + (velocityy2 * velocityy2));
			double velocityx2_einheits = (1 / v2_length) * velocityx2;
			double velocityy2_einheits = (1 / v2_length) * velocityy2;
			System.out.println("(velocityx1"+" "+velocityx1);
			System.out.println("velocityy1"+ " "+velocityy1);
			velocityx1 = velocityx1_einheits - k * bx;
			velocityy1 = velocityy1_einheits - k * by;
			
			velocityx2 = velocityx2_einheits+ l * bx;
			velocityy2 = velocityy2_einheits+ l * by;
			//velocityy2 = velocityy2 + l * by;
			
			

			
			//System.out.println("hallo");
			System.out.println("velocityx1"+" "+velocityx1);
			System.out.println("velocityy1"+" "+velocityy1);
			
		}
	
		else if(this.Epsilon == 0) {
			//k und l
			velocityx1 = Vx;
			velocityy1 = Vy;
			velocityx2 = Vx;
			velocityy2 = Vy;
		}	
		
		
		}
		
		
		
		
		/*
		if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && circle2.velocity == 0)
		{
			l = 0;
		}else if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && this.velocity == 0) {
		//	k = 0;
		}
		else {
		//    k =  2 * u1.pX * b.pX;
		    l = -2 * u2.pY * b.pY;
		}
		*/
		
		//this.vektor.aX = this.vektor.pX - k * b.pX;
		//this.vektor.aY = this.vektor.pY - k * b.pY;
		//this.velocityx1 = -vektor.pX - k * b.pX;
		//this.velocityy1 = -vektor.pY - l * b.pY;
		//circle2.vektor.pX = circle2.vektor.pX - l * b.pX;
		//circle2.vektor.pY = circle2.vektor.pY - l * b.pY;
		//this.velocityx2 = -circle2.vektor.pX - k * b.pX;
		//this.velocityy2 = -circle2.vektor.pY - l * b.pY;
		/*
		velocityx1 *= 1;
		velocityx2 *= 1;
		velocityy1 *= -1;
		velocityy2 *= -1;
		*/
		
		
		
		//System.out.println("C2X: " + circle2.vektor.pX);
		//System.out.println("C2Y: " + circle2.vektor.pY);
		//System.out.println("C1X: " + this.vektor.pX);
		//System.out.println("C1Y: " + this.vektor.pY);
		 
		 
	
			
	
	
	
	}
	public void handlenoCollision(Circle circle2) {
		if(hasCollision(circle2)==false){
			//System.out.println("now1: " + circle2.vektor.pX);
			//velocityx1 *= velocity;
			//velocityy1 *= velocity;
			//System.out.println("now2: " + circle2.vektor.pX);
		}
		else {
			//velocityx2 *= velocity;
			//velocityy2 *= velocity;
			
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

