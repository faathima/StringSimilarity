package string.similarity.measurement.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import string.similarity.measurement.Stemmer.Stemmer;

public class AlgorithmTest {
	public AlgorithmTest() {
	}

	public static void main(String[] args) {
		// Source file - Training
		// String sourceFile =
		// "F:/StringSimilarity/StringSimilarity/src/string/similarity/measurement/Test/train.txt";

		// Source file - Testing
		String sourceFile = "F:/StringSimilarity/StringSimilarity/src/string/similarity/measurement/Test/test.txt";

		// File path where all files are available
		String filePath = "F:/StringSimilarity/StringSimilarity/src/string/similarity/measurement";

		// File for Stemmer
		String fileStemmer = "Stemmer.txt";

		String fileStemName = filePath + fileStemmer;
		File xx = new File(fileStemName);
		if (xx.exists()) {
			xx.delete();
		}
		List<Integer> measures = new ArrayList<Integer>();

		System.out.println("=========  Algorithms Tesing =============");
		for (int i = 1; i < 5; i++) {
			switch (i) {
			case 1:
				System.out.println(" ------- Jaccard ------- ");
				break;
			case 2:
				System.out.println(" ------- Levenshtein ------- ");
				break;
			case 3:
				System.out.println(" ------- JaroWinkler ------- ");
				break;
			case 4:
				System.out.println(" ------- Cosine Similarity ------- ");
				break;
			}
			measures = ReadFile.readFile(sourceFile, null, null, 3, 6, 9, i);
			System.out.println(measures);
			Measures.stringSimilarity(measures);
			measures.removeAll(measures);
		}

		System.out.println(" ========= Spell Checker ========= ");
		System.out
				.println(" -------(to check improvement in accurcy) ------- ");
		for (int i = 5; i < 9; i++) {
			switch (i) {
			case 5:
				System.out.println(" ------- Jaccard ------- ");
				break;
			case 6:
				System.out.println(" ------- Levenshtein ------- ");
				break;
			case 7:
				System.out.println(" ------- JaroWinkler ------- ");
				break;
			case 8:
				System.out.println(" ------- Cosine Similarity ------- ");
				break;
			}
			measures = ReadFile.readFile(sourceFile, null, null, 3, 6, 9, i);
			System.out.println(measures);
			Measures.stringSimilarity(measures);
			measures.removeAll(measures);
		}

		System.out.println(" ========== Stemmer ========== ");
		System.out
				.println(" -------(to check improvement in accurcy) ------- ");
		Stemmer stemmer = new Stemmer();
		try {
			stemmer.Stemming(sourceFile, fileStemName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 1; i < 5; i++) {
			switch (i) {
			case 1:
				System.out.println(" ------- Jaccard ------- ");
				break;
			case 2:
				System.out.println(" ------- Levenshtein ------- ");
				break;
			case 3:
				System.out.println(" ------- JaroWinkler ------- ");
				break;
			case 4:
				System.out.println(" ------- Cosine Similarity ------- ");
				break;
			}
			measures = ReadFile.stemmerReadFile(fileStemName, 3, 6, 9, i);
			System.out.println(measures);
			Measures.stringSimilarity(measures);
			measures.removeAll(measures);
		}

		System.out.println(" ========= lemmatizer ========= ");
		System.out
				.println(" -------(to check improvement in accurcy) ------- ");

		for (int i = 1; i < 5; i++) {
			switch (i) {
			case 1:
				System.out.println(" ------- Jaccard ------- ");
				break;
			case 2:
				System.out.println(" ------- Levenshtein ------- ");
				break;
			case 3:
				System.out.println(" ------- JaroWinkler ------- ");
				break;
			case 4:
				System.out.println(" ------- Cosine Similarity ------- ");
				break;
			}
			measures = ReadFile.LemmaReadFile(sourceFile, 3, 6, 9, i);
			System.out.println(measures);
			Measures.stringSimilarity(measures);
			measures.removeAll(measures);
		}
	}
}
