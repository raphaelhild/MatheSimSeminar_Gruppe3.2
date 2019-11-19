package Simulation;

public class Circle {
	
	Vektor vektor = new Vektor();
	double radius;
	double velocityX;
	double velocityY;
	double masse;
	double k;
	double l;
	
	Circle(double x, double y, double _r, double _vX, double _vY, double _m){
		vektor.pX = x;
		vektor.pY = y;
		radius = _r;
		velocityX = _vX;
		velocityY = _vY;
		masse = _m;
		k = 20;
		l = 20;
	}

	public void handleWallCollision() {
		if (vektor.pX + radius*2 >= _0_Constants.WINDOW_WIDTH || vektor.pX <= 0) {
			velocityX *= -1;
		}
		if (vektor.pY + radius*2 >= _0_Constants.WINDOW_HEIGHT || vektor.pY <= radius) {
			velocityY *= -1;
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
		
		return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 + r2) * (r1 + r2));
		
		//Sobald Summe beider Radien unterschritten besteht eine Kollision
		//"(r1 + r2) / 2", da nur Abstand der Radien zählt und nicht der Durchmesser
		/*if(distance <= (r1 + r2) / 2) { 
			return true; }
		return false;*/
	}
	
	public void handleCollision(Circle circle2) {
		/*
		if(hasCollision(circle2)) {
			velocityX *= -1;
			velocityY *= -1;
		}*/
		
		
		



	if(hasCollision(circle2)) {
		
		//Rechnung für vollelastischen Stoß
		Vektor u1 = this.getU(this.vektor, this.getV(this.vektor, circle2.vektor));
		Vektor u2 = this.getU(circle2.vektor, this.getV(this.vektor, circle2.vektor));

		Vektor b = this.vektor.sub(circle2.vektor);
		
		
		//!!!!!!
		
		if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && circle2.velocityX == 0 && circle2.velocityY == 0)
		{
			l = 0;
		}else if(this.vektor.getRelativeAngleBetween(circle2.vektor) == 0 && this.velocityX == 0 && circle2.velocityY == 0) {
			k = 0;
		}
		else {
		    k =  2 * u1.pX * b.pX;
		    l = 2 * u2.pX * b.pX;
		}

		
		//this.vektor.pX = this.vektor.pX - k * b.pX;
		//this.vektor.pY = this.vektor.pY - k * b.pY;
		
		
		
		this.vektor.add(new Vektor(this.vektor.pX - k * b.pX, this.vektor.pY - k * b.pY));
		
		
		//circle2.vektor.pX = circle2.vektor.pX - l * b.pX;
		//circle2.vektor.pY = circle2.vektor.pY - l * b.pY;
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
		
		return new Vektor(UX, UY);
	}
	
}

