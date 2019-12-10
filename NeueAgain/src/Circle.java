
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
public class Circle {
	
	Vektor vektor = new Vektor();
	double radius;
	double velocityx1;
	double velocityy1;
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
	
	int Epsilon = 1;
	
	//muss noch änderbar gemacht werden
	
	boolean status = false; 
	
	//wenn false, dann wird bei Zusammenstoß neu berechnet
	
	Circle(double x, double y, double _r, double _vx, double _vy, double _m){
		vektor.pX = x;
		vektor.pY = y;
		radius = _r;
		velocityx1= _vx;
		velocityy1= _vy;
		masse = _m;
	}
	public void handleWallCollision() {
		if (vektor.pX + radius*2 >= Constants.WINDOW_WIDTH || vektor.pX <= 0 ) {
			velocityx1 *= -1;

		}
		if (vektor.pY + radius*2 >= Constants.WINDOW_HEIGHT || vektor.pY <= radius) {
			velocityy1 *= -1;
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
		
		if(Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1))) <= (r1 + r2)){
			
			return true;
			
		}
		else { return false;
		}
	}

	public void handleCollision(Circle circle2) {
		
		if(hasCollision(circle2)==false) { status = false; }
		
		if(hasCollision(circle2)==true  && status==false) {
			
		status = true;

		//Einheitsvektor b^ berechnen
		bx = this.vektor.pX - circle2.vektor.pX;
		by = this.vektor.pY - circle2.vektor.pY;
		
		double b_length = Math.sqrt((bx*bx)+(by*by));

		bx *= 1 / b_length;
		by *= 1 / b_length;

		
		//V berechnen
		this.Vx = (this.velocityx1 + circle2.velocityx1)/2;
		this.Vy = (this.velocityy1 + circle2.velocityy1)/2;

		//U1 und U2 (Schwerpunktgeschwindigkeit) berechnen
		U1x= this.velocityx1 - Vx;
		U1y= this.velocityy1 - Vy;
		U2x= circle2.velocityx1 - Vx;
		U2y= circle2.velocityy1 - Vy;

		
	
		
		if(this.Epsilon == 1) {

			//k und l berechnen
			
			double k = 2 * ((bx * U1x) + (by * U1y));
			double l = (-2) * ((bx * U2x) + (by * U2y));

			//neue Geschwindigkeiten v1 und v2 berechnen
			double v1_length = Math.sqrt((velocityx1 * velocityx1) + (velocityy1 * velocityy1));
			double velocityx1_einheits = (1 / v1_length) * velocityx1;
			double velocityy1_einheits = (1 / v1_length) * velocityy1;
			double v2_length = Math.sqrt((circle2.velocityx1 * circle2.velocityx1) + (circle2.velocityy1 * circle2.velocityy1));
			double velocityx2_einheits = (1 / v2_length) * circle2.velocityx1;
			double velocityy2_einheits = (1 / v2_length) * circle2.velocityy1;

			
			velocityx1 = velocityx1_einheits - k * bx;
			velocityy1 = velocityy1_einheits - k * by;

			circle2.velocityx1 = velocityx2_einheits + l * bx;
			circle2.velocityy1 = velocityy2_einheits + l * by;
		}
	
		else if(this.Epsilon == 0) {
			
			//k und l
			
			double k = +((bx * U1x) + (by * U1y));

			double l = -((bx * U2x) + (by * U2y));
			
			double v1_length = Math.sqrt((velocityx1 * velocityx1) + (velocityy1 * velocityy1));

			double velocityx1_einheits = (1 / v1_length) * velocityx1;

			double velocityy1_einheits = (1 / v1_length) * velocityy1;

			double v2_length = Math.sqrt((circle2.velocityx1 * circle2.velocityx1) + (circle2.velocityy1 * circle2.velocityy1));

			double velocityx2_einheits = (1 / v2_length) * circle2.velocityx1;

			double velocityy2_einheits = (1 / v2_length) * circle2.velocityy1;
			
			velocityx1 = velocityx1_einheits - k * bx;

			velocityy1 = velocityy1_einheits - k * by;

			circle2.velocityx1 = velocityx2_einheits + l * bx;
			circle2.velocityy1 = velocityy2_einheits + l * by;
			
			}	
		}
	}	
}

