package Simulation;

public class Circle {
	double positionX;
	double positionY;
	double radius;
	double velocity;
	double masse;
	
	Circle(double _x, double _y, double _r, double _v, double _m){
		positionX = _x;
		positionY = _y;
		radius = _r;
		velocity = _v;
		masse = _m;
	}
	
	public void handleWallCollision() {
		if (positionX + radius*2 >= _0_Constants.WINDOW_WIDTH || positionX <= 0) {
			velocity *= -1;
		}
		if (positionY + radius*2 >= _0_Constants.WINDOW_HEIGHT || positionY <= radius) {
			velocity *= -1;
		}
	}	
	
	public boolean distanceBetweenCenters(Circle circle2){
		double x1 = this.positionX;
		double y1 = this.positionY;
		double x2 = circle2.positionX;
		double y2 = circle2.positionY;
		double r1 = this.radius;
		double r2 = circle2.radius;
		
		//Distanz zwischen den beiden Mittelpunkten
		//double distance = java.lang.Math.sqrt(((x1 - x2)*(x1 - x2)) + ((y1 - y2) * (y1 - y2)));
		System.out.println("Abstand: " + java.lang.Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2)));
		System.out.println("r1: " + r1);
		System.out.println("r2: " + r2);
		System.out.println(x1 + " " + y1);
		System.out.println(x2 + " " + y2);
		System.out.println("Radien: " + (r1 + r2));
		
		if((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 + r2)){
			return true;
		}
		
		return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) < (r1 + r2) * (r1 + r2));
		
		//System.out.println(distance);
		//System.out.println((r1 + r2) / 2);
		
		//Sobald Summe beider Radien unterschritten besteht eine Kollision
		//"(r1 + r2) / 2", da nur Abstand der Radien zählt und nicht der Durchmesser
		/*if(distance <= (r1 + r2) / 2) { 
			return true; }
		return false;*/
	}
	
	public void handleCollision(Circle circle2) {
		if(distanceBetweenCenters(circle2)) {
			velocity *= -1;
		}
	}
	/*
	if(positionX >= circle2.positionX - 30) {
		
	}
	if(positionY >= circle2.positionY - 30) {
		velocity *= -1;
	}*/
	
}
