package basic;

import java.util.Arrays;

/******************************************************************************
 *  Compilation:  javac Matrix.java
 *  Execution:    java Matrix
 *
 *  A bare-bones collection of static methods for manipulating
 *  matrices.
 *
 ******************************************************************************/

public class Matrix {

	// return a random m-by-n matrix with values between 0 and 1
	public static double[][] random(int m, int n) {
		double[][] a = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				a[i][j] = Math.random();
		return a;
	}

	// return n-by-n identity matrix I
	public static double[][] identity(int n) {
		double[][] a = new double[n][n];
		for (int i = 0; i < n; i++)
			a[i][i] = 1;
		return a;
	}

	// return x^T y
	public static double dot(double[] x, double[] y) {
		if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
		double sum = 0.0;
		for (int i = 0; i < x.length; i++)
			sum += x[i] * y[i];
		return sum;
	}

	// return B = A^T
	public static double[][] transpose(double[][] a) {
		int m = a.length;
		int n = a[0].length;
		double[][] b = new double[n][m];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				b[j][i] = a[i][j];
		return b;
	}

	// return c = a + b
	public static double[][] add(double[][] a, double[][] b) {
		int m = a.length;
		int n = a[0].length;
		double[][] c = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i][j] = a[i][j] + b[i][j];
		return c;
	}

	// return c = a + b
	public static double[] add(double[] a, double[] b) {
		int m = a.length;
		double[] c = new double[m];
		for (int i = 0; i < m; i++)
			c[i] = a[i] + b[i];
		return c;
	}

	// return c = a + b
	public static double[] add(double[] a, double b) {
		int m = a.length;
		double[] c = new double[m];
		for (int i = 0; i < m; i++)
			c[i] = a[i] + b;
		return c;
	}

	// return vertical summation
	public static double[] addVertical(double[][] a) {
		int m = a.length;
		int n = a[0].length;
		double[] c = new double[m]
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i] += a[i][j];	
		return c;
	}
	
	// return c = a - b
	public static double[][] subtract(double[][] a, double[][] b) {
		int m = a.length;
		int n = a[0].length;
		double[][] c = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i][j] = a[i][j] - b[i][j];
		return c;
	}

	// return c = a - b
	public static double[] subtract(double[] a, double[] b) {
		int m = a.length;
		double[] c = new double[m];
		for (int i = 0; i < m; i++)
			c[i] = a[i] - b[i];
		return c;
	}

	// return c = a - b
	public static double[][] subtract(double a, double[][] b) {
		int m = b.length;
		int n = b[0].length;
		double[][] c = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i][j] = a - b[i][j];
		return c;
	}

	// return c = a * b
	public static double[][] multiply(double[][] a, double[][] b) {
		int m1 = a.length;
		int n1 = a[0].length;
		int m2 = b.length;
		int n2 = b[0].length;
		if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
		double[][] c = new double[m1][n2];
		for (int i = 0; i < m1; i++)
			for (int j = 0; j < n2; j++)
				for (int k = 0; k < n1; k++)
					c[i][j] += a[i][k] * b[k][j];
		return c;
	}

	// matrix-vector multiplication (y = A * x)
	public static double[] multiply(double[][] a, double[] x) {
		int m = a.length;
		int n = a[0].length;
		if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
		double[] y = new double[m];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				y[i] += a[i][j] * x[j];
		return y;
	}

	// matrix-vector multiplication (y = A * x)
	public static double[][] multiply1(double[][] a, double[] x) {
		int m = a.length;
		int n = a[0].length;
		if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
		double[][] y = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				y[i][j] += a[i][j] * x[j];
		return y;
	}

	// return c = a * b
	public static double[][] multiply2(double[] a, double[] b) {
		int m = a.length;
		int n = b.length;
		double[][] c = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i][j] += a[i] * b[j];
		return c;
	}

	// vector-matrix multiplication (y = x^T A)
	public static double[] multiply(double[] x, double[][] a) {
		int m = a.length;
		int n = a[0].length;
		if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
		double[] y = new double[n];
		for (int j = 0; j < n; j++)
			for (int i = 0; i < m; i++)
				y[j] += a[i][j] * x[i];
		return y;
	}

	// vector-matrix multiplication (y = x^T A)
	public static double[] multiply(double[] a, double b) {
		int m = a.length;
		double[] y = new double[m];
		for (int i = 0; i < m; i++)
			y[i] += a[i] * b;
		return y;
	}

	// matrix-vector multiplication (y = A * x)
	public static double[][] multiply(double[][] a, double b) {
		int m = a.length;
		int n = a[0].length;
		double[][] y = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				y[i][j] += a[i][j] * b;
		return y;
	}

	// test client
	public static void main(String[] args) {
		System.out.println("D");
		System.out.println("--------------------");
		double[][] d = { { 1, 2, 3 }, { 4, 5, 6 }, { 9, 1, 3 } };
		System.out.println(Arrays.deepToString(d));
		System.out.println();

		System.out.println("I");
		System.out.println("--------------------");
		double[][] c = Matrix.identity(5);
		System.out.println(Arrays.deepToString(c));
		System.out.println();

		System.out.println("A");
		System.out.println("--------------------");
		double[][] a = Matrix.random(5, 5);
		System.out.println(Arrays.deepToString(a));
		System.out.println();

		System.out.println("A^T");
		System.out.println("--------------------");
		double[][] b = Matrix.transpose(a);
		System.out.println(Arrays.deepToString(b));
		System.out.println();

		System.out.println("A + A^T");
		System.out.println("--------------------");
		double[][] e = Matrix.add(a, b);
		System.out.println(Arrays.deepToString(e));
		System.out.println();

		System.out.println("A * A^T");
		System.out.println("--------------------");
		double[][] f = Matrix.multiply(a, b);
		System.out.println(Arrays.deepToString(f));
		System.out.println();
	}
}
