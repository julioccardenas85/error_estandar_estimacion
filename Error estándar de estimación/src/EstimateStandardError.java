/*Error estándar de estimación
Descripción: se calcula valor de error estándar de estimación con base en dos arreglos
ingresados por usuario, así como los valores de regresión simple a y b
Desarrollador: Julio César Cárdenas Alvarado
Institución: CUCEA MTI
Realización: 29/10/2021*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class EstimateStandardError {
	public static void main (String args []) {
		Scanner dataIn = new Scanner(System.in);
		boolean repeat = false;
		boolean repeatB = false;
		char option;
		int projects = 0;
		double regressionA = 0;
		double regressionB = 0;
		double estimateStandardError = 0;
		
		do {
			System.out.print("Error estándar de estimación\n"
					+ "\n¿Cuántos proyectos se evaluarán?: ");
			projects = dataIn.nextInt();
			System.out.println();
			int ldc [] = new int [projects];
			int y [] = new int [projects];
			double y2 [] = new double [projects];
			double y_y [] = new double [projects];
			double y_y2 [] = new double [projects];
			
			System.out.println("Ingrese valores de tamaño de líneas de código para todos los proyectos:");
			getVector(dataIn, ldc, repeat);
			System.out.println("Ingrese valores de esfuerzo para codificar cada proyecto:");
			getVector(dataIn, y, repeat);
			System.out.print("Ingrese el valor de regresión lineal simple a:");
			regressionA = dataIn.nextDouble();
			System.out.print("Ingrese el valor de regresión lineal simple b:");
			regressionB = dataIn.nextDouble();
			calcY2(regressionA, regressionB, ldc, y2);
			calcY_Y(y, y2, y_y);
			calcY_Y2(y_y, y_y2);
			estimateStandardError = calcEstimateStandardError(y_y2, estimateStandardError);
			System.out.println("El error estándar de estimación es: " + estimateStandardError + "\n");
			
			do {
				System.out.println("¿Desea hacer un nuevo cálculo? s/n");
				option = dataIn.next().charAt(0);
				if (option == 's') {
					System.out.println();
					repeat = true;
					repeatB = false;
					break;
				}
				else if (option == 'n') {
					System.out.println("Programa terminado. ¡Hasta luego!");
					repeat = false;
					repeatB = false;
					break;
				}
				
				else {
					System.out.println("Opción incorrecta");
					repeatB = true;
					break;
				}
			}
			while (repeatB == true);
		}
		
		while (repeat == true);
	}
	
	public static void printVector(float data []) {
		System.out.print("Los datos ingresados son: ");
		
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		
		System.out.println();
	}
	
	public static void getVector (Scanner dataIn, int vector[], boolean repeat) {
		for (int i = 0; i < vector.length; i++) {
			do {
				try {
					System.out.print("Ingrese el valor del proyecto " + (i+1) + ":");
					vector[i] = dataIn.nextInt();
					repeat = false;
				}
				catch (InputMismatchException e) {
					System.err.println("Valor incorrecto. Introduzca un número.");
					dataIn = new Scanner( System.in );
					repeat = true;
				}
			} while (repeat);
		}
		System.out.println();
	}
	
	public static void calcY2 (double regressionA, double regressionB, int ldc [], double y2 []) {
		for (int i = 0; i < ldc.length; i++) {
			y2[i] = regressionA + (regressionB * ldc[i]);
		}
	}
	
	public static void calcY_Y (int y[], double y2[], double y_y[]) {
		for (int i = 0; i < y.length; i++) {
			y_y[i] = y[i] - y2[i];
		}
	}
	
	public static void calcY_Y2 (double y_y[], double y_y2[]) {
		for (int i = 0; i < y_y.length; i++) {
			y_y2[i] = y_y[i] * y_y[i];
		}
	}
	
	public static double calcEstimateStandardError (double y_y2[], double estimateStandardError) {
		double sum = 0;
		for (int i = 0; i < y_y2.length; i++) {
			sum += y_y2[i];
		}
		estimateStandardError = Math.sqrt(sum/(y_y2.length - 2));
		return estimateStandardError;
	}
}
