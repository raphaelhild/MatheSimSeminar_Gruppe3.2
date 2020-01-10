

public class Vektor {
	
	public double pX;
	public double pY;
	public double aY;
	public double aX;

	public Vektor() {
		
	}
	
	public Vektor(double pX, double pY) {
		this.pX = pX;
		this.pY = pY;
		
	}

	
	public Vektor add(Vektor v) {
        return new Vektor(pX + v.pX, pY + v.pY);
    }

    public Vektor sub(Vektor v) {
        return new Vektor(pX - v.pX, pY - v.pY);
    }
	
    public double getRelativeAngleBetween(Vektor v) {
        return getSign(v) * Math.acos(dot(v) / (getSize() * v.getSize()));
    }
  
    public double getSize() {
        return Math.sqrt(pX * pX + pY * pY);
    }
    
    public int getSign(Vektor v) {
        return (pY*v.pX > pX*v.pY)?-1:1;
    }
    
    public double dot(Vektor v) {
        return pX * v.pX + pY * v.pY;
    }
}
