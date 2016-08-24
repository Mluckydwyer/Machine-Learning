package NEAT.algorithm;

public class Loger {

	public static void log(Object o) {
		System.out.println(preface() + " " + o.toString());
	}
	
	private static String preface() {
		return "";
	}
	
}
