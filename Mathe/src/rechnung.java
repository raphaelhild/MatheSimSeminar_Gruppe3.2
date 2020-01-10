
public class rechnung {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double x1 = 1;
		double y1 = 0;
		
		double x2 = 0;
		double y2 = 0;
		
		double px1 = 1;
		double py1 = 2;
		
		double px2 = 10;
		double py2 = 2;
		
		double ux1 = 0;
		double uy1 = 0;
		
		double ux2 = 0;
		double uy2 = 0;
		
		double vx1 = 0;
		double vy1 = 0;
		
		double vx2 = 0;
		double vy2 = 0;
		
		double k = 0;
		double l = 0;
		
		double Vx = 0;
		double Vy = 0;
		
		
		double bx = 0;
		double by = 0;
		double b = 0;
		
		int Epsilon	= 1;
		
		
		Vx = (x1 + x2)/2;
		Vy = (y1 + y2)/2;
		
		bx = px2 - px1;
		by = py2 - py1;
		
		double b_length = Math.sqrt((bx*bx)+(by*by));
		bx *= 1 / b_length;
		by *= 1 / b_length;
		
		ux1= x1 - Vx;
		uy1= y1 - Vy;
		ux2= x2 - Vx;
		uy2= y2 - Vy;
		
		if(Epsilon == 1) {
		 k = 2 * ((bx * ux1) + (by * uy1));
		 l = (-2) * ((bx * ux2) + (by * uy2));
		}
		else if (Epsilon == 0) {
		 k = +((bx * ux1) + (by * uy1));
		 l = -((bx * ux2) + (by * uy2));
		 }
		
		 double v1_length = Math.sqrt((x1 * x1) + (y1 * y1));
			double velocityx1_einheits = (1 / v1_length) * x1;
			double velocityy1_einheits = (1 / v1_length) * y1;
			
			double v2_length = Math.sqrt((x2 * x2) + (y2 * y2));
			double velocityx2_einheits = (1 / v2_length) * x2;
			double velocityy2_einheits = (1 / v2_length) * y2;
			//System.out.println("(velocityx1"+" "+x1);
			//System.out.println("velocityy1"+ " "+y1);
			vx1 = velocityx1_einheits - k * bx;
			vy1 = velocityy1_einheits - k * by;
			
			vx2 = velocityx2_einheits + l * bx;
			vy2 = velocityy2_einheits + l * by;
		
			System.out.println("Mit Epsilon: " + Epsilon);

			System.out.println("Vx:"+" "+Vx);
			System.out.println("Vy:"+ " "+Vy);
			System.out.println("bx:"+" "+bx);
			System.out.println("by:"+ " "+by);
			System.out.println("(richtung x1:"+" "+x1);
			System.out.println("richtung y1:"+ " "+y1);
			System.out.println("(richtung x2:"+" "+x2);
			System.out.println("richtung y2:"+ " "+y2);
			
			System.out.println("----k und l----");
			System.out.println("k:"+" "+k);
			System.out.println("l:"+ " "+l);
			
			System.out.println("----u1 und u2----");
			System.out.println("ux1: " + ux1);
			System.out.println("uy1: " + uy1);
			System.out.println("ux2: " + ux2);
			System.out.println("uy2: " + uy2);
			
			System.out.println("----Als Einheitsvektoren----");
			System.out.println("velocityx1_einheits:"+" "+velocityx1_einheits);
			System.out.println("velocityy1_einheits:"+ " "+velocityy1_einheits);
			System.out.println("(velocityx2_einheits:"+" "+velocityx2_einheits);
			System.out.println("velocityy2_einheits:"+ " "+velocityy2_einheits);
		
			System.out.println("----Nicht als Einheitsvektoren----");
			System.out.println("v1x: " + (x1 - k * bx));
			System.out.println("v1y: " + (y1 - k * by));
			System.out.println("v2x: " + (x2 + l * bx));
			System.out.println("v2y: " + (y2 + l * by));
		
		
	}

}
