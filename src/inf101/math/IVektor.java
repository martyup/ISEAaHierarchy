package inf101.math;

public interface IVektor<V> {
	/**
	 * Addisjon
	 * 
	 * @param v
	 * @return
	 */
	V add(V v);

	/**
	 * Subtraksjon
	 * 
	 * @param v
	 * @return
	 */
	V sub(V v);

	/**
	 * Multiplikasjon (elementvis)
	 * 
	 * @param v
	 * @return
	 */
	V mul(V v);

	/**
	 * Divisjon (elementvis)
	 * 
	 * @param v
	 * @return
	 */
	V div(V v);

	/**
	 * Addisjon med skalar
	 * 
	 * @param d
	 * @return
	 */
	V add(double d);

	/**
	 * Subtraksjon med skalar
	 * 
	 * @param d
	 * @return
	 */
	V sub(double d);

	/**
	 * Multiplikasjon med skalar
	 * 
	 * @param d
	 * @return
	 */
	V mul(double d);

	/**
	 * Divisjon med skalar
	 * 
	 * @param d
	 * @return
	 */
	V div(double d);

	/**
	 * Addisjon med komponenter
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	V add(double x, double y);

	/**
	 * Subtraksjon med komponenter
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	V sub(double x, double y);

	/**
	 * Multiplikasjon med komponenter
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	V mul(double x, double y);

	/**
	 * Divisjon med komponenter
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	V div(double x, double y);

	/**
	 * @return [-x, -y]
	 */
	V neg();

	/**
	 * @return X-komponenten
	 */
	double x();

	/**
	 * @return Y-komponenten
	 */
	double y();

	/**
	 * @param x
	 * @return Ny vektor med oppdatert X
	 */
	V settX(double x);

	/**
	 * @param y
	 * @return Ny vektor med oppdatert Y
	 */
	V settY(double y);

	boolean equals(Object o);

	boolean checkState();

	String toString();

	/**
	 * @param x
	 * @param y
	 * @return Ny vektor [x, y]
	 */
	V ny(double x, double y);

	/**
	 * @return Ny kopi av denne vektoren
	 */
	V ny();

	/**
	 * Lengden av vektoren.
	 * 
	 * v.lengde() == sqrt(pow(v.x(),2) + pow(v.y(),2))
	 * 
	 * @return
	 */
	double lengde();

	/**
	 * Skalarproduktet.
	 * 
	 * v1.dot(v2) = v1.x*v2.x + v1.y*v2.y
	 * 
	 * @return Skalarproduktet av denne vektoren og v.
	 * @see http://en.wikipedia.org/wiki/Dot_product
	 */
	double dot(V v);

	/**
	 * Normaliser vektoren, slik at lengden blir 1 (uten at retningen endrer
	 * seg). Hvis lengden er lik, eller veldig nær 0, er resultatet en vilkårlig
	 * vektor med lengde 1.
	 * 
	 * v.normaliser().x() == v.x()/v.lengde() v.normaliser().y() ==
	 * v.y()/v.lengde()
	 * 
	 * @return Vektoren, normalisert slik at lengden er 1
	 */
	V normaliser();
}
