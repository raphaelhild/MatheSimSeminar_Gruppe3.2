public class Circle {
	
	Vektor vektor = new Vektor();
	double radius;
	double velocity_x;
	double velocity_y;
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
	
	int Epsilon = 0;				//Hier entweder 0 oder 1	
	boolean status = false; 		//wenn false, dann wird bei Zusammenstoß neu berechnet
	
	Circle(double x, double y, double _r, double _vx, double _vy, double _m){
		vektor.pX = x;
		vektor.pY = y;
		radius = _r;
		velocity_x= _vx;
		velocity_y= _vy;
		masse = _m;					//Masse hier bisher nur "1"
	}
	
	//Kollision mit der Wand?
	public void handleWallCollision(Circle circle2) {
		
		if(vektor.pX + radius*2 >= Constants.WINDOW_WIDTH && hasCollision(circle2)==true && vektor.pY== circle2.vektor.pY || vektor.pX <= 0 && hasCollision(circle2)==true && vektor.pY== circle2.vektor.pY){
			circle2.velocity_x *= -1;
			velocity_x *=-1;
			
			
		}
		else if (vektor.pX + radius*2 >= Constants.WINDOW_WIDTH || vektor.pX <= 0 && status==false) {
			velocity_x *= -1;
		}
		if(vektor.pY + radius*2 >= Constants.WINDOW_HEIGHT && hasCollision(circle2)==true && vektor.pX== circle2.vektor.pX || vektor.pY <= radius && hasCollision(circle2)==true && vektor.pX== circle2.vektor.pX) {
			circle2.velocity_y *= -1;
			velocity_y *=-1;
		}
		else if (vektor.pY + radius*2 >= Constants.WINDOW_HEIGHT || vektor.pY <= radius && status == false) {
			velocity_y *= -1;
		}
	}	
	
	//Kollision mit anderem Kreis?
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
		//Status wieder auf false setzen für spätere Abfrage
		if(hasCollision(circle2) == false ) { status = false; }
			
		//Bei Kollision und keiner schon erfolgten Neuberechnung
		if(hasCollision(circle2) == true  && status ==false) {
			//Kontrollausgabe DAVOR
			System.out.println("DAVOR - velocity x1 : " + this.velocity_x + " velocity y1 : " + this.velocity_y);
			System.out.println("DAVOR - velocity x2 : " + circle2.velocity_x + " velocity y2 : " + circle2.velocity_y);
					
			//Wurden die neuen Bewegungsrichtungen schon berechnet?
			status = true;
		
			//Einheitsvektor b^ berechnen
			bx = this.vektor.pX - circle2.vektor.pX;
			by = this.vektor.pY - circle2.vektor.pY;
			double b_length = Math.sqrt((bx*bx)+(by*by));
			bx *= 1 / b_length;
			by *= 1 / b_length;
		
			//V berechnen
			this.Vx = (this.velocity_x + circle2.velocity_x)/2;
			this.Vy = (this.velocity_y + circle2.velocity_y)/2;
		
			//U1 und U2 (Schwerpunktgeschwindigkeit) berechnen
			U1x= this.velocity_x - Vx;
			U1y= this.velocity_y - Vy;
			U2x= circle2.velocity_x - Vx;
			U2y= circle2.velocity_y - Vy;
	
			//Neue Bewegungsrichtungen berechnen
			if(this.Epsilon == 1) {
				//k und l berechnen
				double k = 2 * ((bx * U1x) + (by * U1y));
				double l = (-2) * ((bx * U2x) + (by * U2y));
					
				//neue Geschwindigkeiten v1 und v2 berechnen
				double v1_length = Math.sqrt((velocity_x * velocity_x) + (velocity_y * velocity_y));
				double velocity_x_einheits;
				double velocity_y_einheits;
				if(v1_length==0) {
					
					velocity_x_einheits=0;
					velocity_y_einheits=0;
					
				}
				else {
				 velocity_x_einheits = (1 / v1_length) * velocity_x;
				 velocity_y_einheits = (1 / v1_length) * velocity_y;
				}
				double v2_length = Math.sqrt((circle2.velocity_x * circle2.velocity_x) + (circle2.velocity_y * circle2.velocity_y));
				double  velocityx2_einheits;
				 double velocityy2_einheits ;
				
				
				
				if(v2_length==0) {
					 velocityx2_einheits = 0;
					 velocityy2_einheits = 0;
					
				}
				else {
				  velocityx2_einheits = (1 / v2_length) * circle2.velocity_x;
				  velocityy2_einheits = (1 / v2_length) * circle2.velocity_y;
				}
		
				velocity_x = velocity_x_einheits - k * bx;
				velocity_y = velocity_y_einheits - k * by;
		
				//Neue Bewegungsrichtungen zuweisen
				circle2.velocity_x = velocityx2_einheits + l * bx;
				circle2.velocity_y = velocityy2_einheits + l * by;
			}
			else if(this.Epsilon == 0) {
				//k und l berechnen
				double k = +(((bx * U1x) + (by * U1y))+(((U1x*U1x+U1y*U1y)*((bx*bx)+(by*by)))-((U1x*U1x)+(U1y*U1y))));
				double l = -(((bx * U2x) + (by * U2y))-(((U2x*U2x+U2y*U2y)*((bx*bx)+(by*by)))-((U2x*U2x)+(U2y*U2y))));
					
				//neue Geschwindigkeiten v1 und v2 berechnen
				double v1_length = Math.sqrt((velocity_x * velocity_x) + (velocity_y * velocity_y));
				double velocity_x_einheits;
				double velocity_y_einheits;
				
				if(v1_length==0) {
					velocity_x_einheits=0;
					velocity_y_einheits=0;
					
					
				}else {
				
				
				velocity_x_einheits = (1 / v1_length) * velocity_x;
				velocity_y_einheits = (1 / v1_length) * velocity_y;
				}
				double v2_length = Math.sqrt((circle2.velocity_x * circle2.velocity_x) + (circle2.velocity_y * circle2.velocity_y));
				double velocityx2_einheits;
				double velocityy2_einheits;
				if(v2_length==0) {
					 velocityx2_einheits = 0;
					 velocityy2_einheits = 0;
					
				}
				else {
				  velocityx2_einheits = (1 / v2_length) * circle2.velocity_x;
				  velocityy2_einheits = (1 / v2_length) * circle2.velocity_y;
				}
		
					
				velocity_x = velocity_x_einheits - k * bx;
				velocity_y = velocity_y_einheits - k * by;
		
				//Neue Bewegungsrichtungen zuweisen
				circle2.velocity_x = velocityx2_einheits + l * bx;
				circle2.velocity_y = velocityy2_einheits + l * by;
			}	
				
			//Kontrollausgabe
			System.out.println("DANACH - velocity x1 : " + this.velocity_x + " velocity y1 : " + this.velocity_y);
			System.out.println("DANACH - velocity x2 : " + circle2.velocity_x + " velocity y2 : " + circle2.velocity_y);
			
		}
		
	}	
}

