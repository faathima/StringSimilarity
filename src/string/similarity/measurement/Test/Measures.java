package string.similarity.measurement.Test;

import java.util.List;
import string.similarity.measurement.Jaro.JaroWinkler;
import string.similarity.measurement.Levenshtein.Levenshtein;
import string.similarity.measurement.cosine.CosineDistance;

public class Measures {
	public Measures() {

	}

	// Calculate levenshtein
	public static double levenshteinDistance(String one, String two) {
		Levenshtein levenshtein = new Levenshtein();
		double result = levenshtein.distance(one, two);
		return result;
	}

	// Calculate levenshtein with Threshold values
	public static String calculateLevenshtein(String one, String two) {
		double distance = 0.00;
		int threshold = 30;
		String result;
		distance = levenshteinDistance(one, two);

		if (distance <= threshold) {
			result = "True";
		} else {
			result = "False";
		}
		System.out.println("--" + result);
		return result;
	}

	// Calculate jaroWinkler Distance
	public static double jaroWinklerDistance(String one, String two) {
		JaroWinkler jaroDistance = new JaroWinkler();
		double result = jaroDistance.similarity(one, two);
		return result;
	}

	// Calculate jaroWinkler with Threshold values
	public static String calculateJaroWinkler(String one, String two) {
		double distance = 0.00;
		double Thresh = 0.64;
		String result;
		distance = jaroWinklerDistance(one, two);
		System.out.print("--" + distance);

		if (distance >= Thresh) {
			result = "True";
		} else {
			result = "False";
		}
		System.out.println("--" + result);
		return result;
	}

	// Calculate cosineSimilarity
	public static double cosineSimilarity(String one, String two) {
		CosineDistance cosSimilarity = new CosineDistance();
		cosSimilarity.setIdfEnable(true);
		double result =cosSimilarity.getSimilarityScore(one, two);
		return result;
	}

	// Calculate cosineSimilarity with Threshold values
	public static String calculateCosine(String one, String two) {
		double distance = 0.00;
		String result;
		double threshold = 0.51;
		distance = cosineSimilarity(one, two);
		System.out.print("--" + distance);
		if (distance >= threshold) {
			result = "True";
		} else {
			result = "False";
		}
		System.out.println("--" + result);
		return result;
	}

	public double accuracy(int TP, int TN, int FP, int FN) {
		double accuracy = 0.00;
		double upper = 0.00;
		double lower = 0.00;

		upper = (TP + TN);
		lower = (TP + TN + FP + FN);
		accuracy = (upper / lower);
		return accuracy;
	}

	public double precision(int TP, int FP) {
		double precision = 0.00;
		double lower = 0.00;

		lower = (TP + FP);
		precision = (TP / lower);
		return precision;
	}

	public double recall(int TP, int FN) {
		double recall = 0.00;
		double lower = 0.00;

		lower = (TP + FN);
		recall = (TP / lower);
		return recall;
	}

	public double f1Measure(double Precision, double Recall) {
		double f1Measure = 0.00;
		double B = 1.00;
		double upper = 0.00;
		double lower = 0.00;
		double PR = 0.00;
		double B2P = 0.00;

		PR = Precision * Recall;
		upper = ((B * B) + 1) * PR;

		B2P = (B * B) * Precision;
		lower = (B * B * Precision) + Recall;

		f1Measure = (upper / lower);
		return f1Measure;
	}

	public static void stringSimilarity(List<Integer> measures) {
		int TP;
		int TN;
		int FP;
		int FN;
		TP = measures.remove(0);
		TN = measures.remove(0);
		FN = measures.remove(0);
		FP = measures.remove(0);
		System.out.println("TP : " + TP + "\n" + "TN : " + TN + "\n" + "FN : "
				+ FN + "\n" + "FP : " + FP);

		Measures jmeasures = new Measures();

		double accuracy = 0.00;
		accuracy = Math.round((jmeasures.accuracy(TP, TN, FP, FN)) * 100.0) / 100.0;
		System.out.println("A  : " + accuracy);

		double precision = 0.00;
		precision = Math.round((jmeasures.precision(TP, FP)) * 100.0) / 100.0;
		System.out.println("P  : " + precision);

		double recall = 0.00;
		recall = Math.round((jmeasures.recall(TP, FN)) * 100.0) / 100.0;
		System.out.println("R  : " + recall);

		double f1Measure = 0.00;
		f1Measure = Math
				.round((jmeasures.f1Measure(precision, recall)) * 100.0) / 100.0;
		System.out.println("F1 : " + f1Measure);
	}
}
