package inf101.math;

public class Vektor implements IVektor<Vektor> {
	private double x, y;

	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Vektor add(Vektor v) {
		return new Vektor(x + v.x, y + v.y);
	}

	@Override
	public Vektor add(double d) {
		return new Vektor(x + d, y + d);
	}

	@Override
	public Vektor add(double x, double y) {
		return new Vektor(this.x + x, this.y + y);
	}

	@Override
	public boolean checkState() {
		return true;
	}

	@Override
	public Vektor div(Vektor v) {
		return new Vektor(x / v.x, y / v.x);
	}

	@Override
	public Vektor div(double d) {
		return new Vektor(x / d, y / d);
	}

	@Override
	public Vektor div(double x, double y) {
		return new Vektor(this.x / x, this.y / y);
	}

	@Override
	public double dot(Vektor v) {
		return x * v.x + y * v.y;
	}

	@Override
	public double lengde() {
		return Math.sqrt(x * x + y * y);
	}

	@Override
	public Vektor mul(Vektor v) {
		return new Vektor(x * v.x, y * v.y);
	}

	@Override
	public Vektor mul(double d) {
		return new Vektor(x * d, y * d);
	}

	@Override
	public Vektor mul(double x, double y) {
		return new Vektor(this.x * x, this.y * y);
	}

	@Override
	public Vektor normaliser() {
		double l = lengde();
		if (l >= 0.00001)
			return new Vektor(x / l, y / l);
		else if (x > 0)
			return new Vektor(1, 0);
		else if (x < 0)
			return new Vektor(-1, 0);
		else if (y > 0)
			return new Vektor(0, 1);
		else if (y < 0)
			return new Vektor(0, -1);
		else
			return new Vektor(1, 0);
	}

	@Override
	public Vektor settX(double x) {
		return new Vektor(x, y);
	}

	@Override
	public Vektor settY(double y) {
		return new Vektor(x, y);
	}

	@Override
	public Vektor sub(Vektor v) {
		return new Vektor(x - v.x, y - v.y);
	}

	@Override
	public Vektor sub(double d) {
		return new Vektor(x - d, y - d);
	}

	@Override
	public Vektor sub(double x, double y) {
		return new Vektor(this.x - x, this.y - y);
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public Vektor ny(double x, double y) {
		return new Vektor(x, y);
	}

	@Override
	public Vektor ny() {
		return new Vektor(0, 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vektor other = (Vektor) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vektor(" + x + "," + y + ")";
	}

	@Override
	public Vektor neg() {
		return new Vektor(-x, -y);
	}
}
