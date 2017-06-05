package string.similarity.measurement.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import string.similarity.measurement.SpellCheck.*;
import string.similarity.measurement.jaccard.JaccardC;
import string.similarity.measurement.lemmatizer.StanfordLemmatizer;

public class ReadFile {
	public static List<Integer> readFile(String sourceFile, String filePath,
			String fileName, int arrayIndex, int arrayIndex2, int arrayIndex3,
			int Option) {
		int RelevantRetrieved = 0;
		int NRelevantNRetrieved = 0;
		int RelevantNRetrieved = 0;
		int NRelevantRetrieved = 0;
		List<Integer> List = new ArrayList<Integer>();
		try {
			String line;
			String result = null;
			String jcResult;
			String levenshtein;
			String jaroWinkler;
			String cosine;
			String s;
			String s1;
			String s2;
			String s3;

			FileReader inputFile = new FileReader(sourceFile);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			while ((line = bufferReader.readLine()) != null) {

				Measures measures = new Measures();
				s = splitString(line, 0);
				s1 = splitString(line, arrayIndex);
				s2 = splitString(line, arrayIndex2);
				s3 = splitString(line, arrayIndex3);
				// System.out.println(s+"----"+s1+"----"+s2+"-----"+s3);
				System.out.print(s3);

				FileSpellChecker spellCheck = new FileSpellChecker();
				String s1new = spellCheck.checking_spellings(s1);
				String s2new = spellCheck.checking_spellings(s2);

				switch (Option) {

				case 1: // Jaccard
					jcResult = JaccardC.calculateJaccardC(s1, s2);
					result = jcResult;
					break;

				case 2: // Levenshtein
					levenshtein = Measures.calculateLevenshtein(s1, s2);
					result = levenshtein;
					break;

				case 3: // jaroWinkler
					jaroWinkler = Measures.calculateJaroWinkler(s1, s2);
					result = jaroWinkler;
					break;

				case 4: // cosine with TF-IDf
					cosine = Measures.calculateCosine(s1, s2);
					result = cosine;
					break;

				case 5:// Jaccard
					jcResult = JaccardC.calculateJaccardC(s1new, s2new);
					result = jcResult;
					break;

				case 6: // Levenshtein
					levenshtein = Measures.calculateLevenshtein(s1new, s2new);
					result = levenshtein;
					break;

				case 7: // jaroWinkler
					jaroWinkler = Measures.calculateJaroWinkler(s1new, s2new);
					result = jaroWinkler;
					break;

				case 8: // cosine with TF-IDf
					cosine = Measures.calculateCosine(s1new, s2new);
					result = cosine;
					break;
				}

				if ((s3.equals("True")) && (result.equals("True"))) {
					RelevantRetrieved = RelevantRetrieved + 1;
				} else if ((s3.equals("False")) && (result.equals("False"))) {
					NRelevantNRetrieved = NRelevantNRetrieved + 1;
				} else if ((s3.equals("True")) && (result.equals("False"))) {
					RelevantNRetrieved = RelevantNRetrieved + 1;
				} else if ((s3.equals("False")) && (result.equals("True"))) {
					NRelevantRetrieved = NRelevantRetrieved + 1;
				}
			}
			bufferReader.close();

			List.add(RelevantRetrieved); // TP
			List.add(NRelevantNRetrieved); // TN
			List.add(RelevantNRetrieved); // FN
			List.add(NRelevantRetrieved); // FP

		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
		return List;
	}

	public static String splitString(String line, int arrayIndex) {
		@SuppressWarnings("unused")
		String s = null;
		String s3 = null;

		if (line.contains("|||")) {
			line = line.trim();
			String[] tokens = line.split("[|||]");
			s = tokens[0].trim();
			s3 = tokens[arrayIndex].trim();
			// System.out.println("s3 : "+s3);
		}
		return s3;
	}

	public static List<Integer> stemmerReadFile(String fileName,
			int arrayIndex, int arrayIndex2, int arrayIndex3, int Option) {
		int RelevantRetrieved = 0;
		int NRelevantNRetrieved = 0;
		int RelevantNRetrieved = 0;
		int NRelevantRetrieved = 0;
		List<Integer> List = new ArrayList<Integer>();

		try {
			String line;
			String result = null;
			String jcResult;
			String levenshtein;
			String jaroWinkler;
			String cosine;
			String s;
			String s1;
			String s2;
			String s3;

			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			while ((line = bufferReader.readLine()) != null) {

				Measures measures = new Measures();
				// System.out.println(" "+line);
				s = splitString(line, 0);
				s1 = splitString(line, arrayIndex);
				s2 = splitString(line, arrayIndex2);
				s3 = splitString(line, arrayIndex3);
				// System.out.println(" "+s1+" "+s2+" "+s3);
				System.out.print(s3);

				switch (Option) {
				case 1: // Jaccard
					jcResult = JaccardC.calculateJaccardC(s1, s2);
					result = jcResult;
					break;

				case 2: // Levenshtein
					levenshtein = Measures.calculateLevenshtein(s1, s2);
					result = levenshtein;
					break;

				case 3: // jaroWinkler
					jaroWinkler = Measures.calculateJaroWinkler(s1, s2);
					result = jaroWinkler;
					break;

				case 4: // cosine with TF-IDf
					cosine = Measures.calculateCosine(s1, s2);
					result = cosine;
					break;
				}

				if ((s3.equals("true")) && (result.equals("True"))) {
					RelevantRetrieved = RelevantRetrieved + 1;
				} else if ((s3.equals("fals")) && (result.equals("False"))) {
					NRelevantNRetrieved = NRelevantNRetrieved + 1;
				} else if ((s3.equals("true")) && (result.equals("False"))) {
					RelevantNRetrieved = RelevantNRetrieved + 1;
				} else if ((s3.equals("fals")) && (result.equals("True"))) {
					NRelevantRetrieved = NRelevantRetrieved + 1;
				}
			}
			bufferReader.close();

			List.add(RelevantRetrieved); // TP
			List.add(NRelevantNRetrieved); // TN
			List.add(RelevantNRetrieved); // FN
			List.add(NRelevantRetrieved); // FP

		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
		return List;
	}

	public static List<Integer> LemmaReadFile(String fileName, int arrayIndex,
			int arrayIndex2, int arrayIndex3, int Option) {
		int RelevantRetrieved = 0;
		int NRelevantNRetrieved = 0;
		int RelevantNRetrieved = 0;
		int NRelevantRetrieved = 0;
		List<Integer> List = new ArrayList<Integer>();
		try {
			String line;
			String result = null;
			String jcResult;
			String levenshtein;
			String jaroWinkler;
			String cosine;
			String s;
			String s1;
			String s2;
			String s3;

			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			while ((line = bufferReader.readLine()) != null) {
				Measures measures = new Measures();

				s = splitString(line, 0);
				s1 = splitString(line, arrayIndex);
				s2 = splitString(line, arrayIndex2);
				s3 = splitString(line, arrayIndex3);
				System.out.print(s3);

				StringBuilder lemaString1 = new StringBuilder();
				StringBuilder lemaString2 = new StringBuilder();

				StanfordLemmatizer l = new StanfordLemmatizer();

				for (String sm1 : l.lemmatize(s1))
					lemaString1.append(sm1 + " ");
				for (String sm2 : l.lemmatize(s2))
					lemaString2.append(sm2 + " ");

				System.out.println(lemaString1 + " " + lemaString2);

				switch (Option) {
				case 1: // Jaccard
					jcResult = JaccardC.calculateJaccardC(
							lemaString1.toString(), lemaString2.toString());
					result = jcResult;
					break;

				case 2: // Levenshtein
					levenshtein = Measures.calculateLevenshtein(
							lemaString1.toString(), lemaString2.toString());
					result = levenshtein;
					break;

				case 3: // jaroWinkler
					jaroWinkler = Measures.calculateJaroWinkler(
							lemaString1.toString(), lemaString2.toString());
					result = jaroWinkler;
					break;

				case 4: // cosine with TF-IDf
					cosine = Measures.calculateCosine(s1, s2);
					result = cosine;
					break;
				}

				if ((s3.equals("True")) && (result.equals("True"))) {
					RelevantRetrieved = RelevantRetrieved + 1;
				} else if ((s3.equals("False")) && (result.equals("False"))) {
					NRelevantNRetrieved = NRelevantNRetrieved + 1;
				} else if ((s3.equals("True")) && (result.equals("False"))) {
					RelevantNRetrieved = RelevantNRetrieved + 1;
				} else if ((s3.equals("False")) && (result.equals("True"))) {
					NRelevantRetrieved = NRelevantRetrieved + 1;
				}
			}
			bufferReader.close();

			List.add(RelevantRetrieved); // TP
			List.add(NRelevantNRetrieved); // TN
			List.add(RelevantNRetrieved); // FN
			List.add(NRelevantRetrieved); // FP

		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
		return List;
	}

}