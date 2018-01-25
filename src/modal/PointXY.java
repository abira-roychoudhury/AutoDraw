package modal;

public class PointXY {
	
	private double x;
	private double y;
	
	public PointXY() {
	}
	
	public PointXY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public boolean equals(Object inputPoint) {
		
		PointXY pointA = (PointXY) inputPoint;
		
		if( ( this.getX() >= ( pointA.getX() - 5 ) && this.getX() <= ( pointA.getX() + 5 ) ) && ( this.getY() >= ( pointA.getY() - 5 ) && this.getY() <= ( pointA.getY() +5 ) ) ) {
			return true;
		}
		
		return false;
	}
	
	public String toString() {
		return "( "+this.getX() + ", "+this.getY()+" )"; 
	}
}
