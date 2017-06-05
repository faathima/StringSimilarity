package string.similarity.measurement.jaccard;

import java.util.ArrayList;
import java.util.HashSet;

import com.ibm.icu.util.StringTokenizer;

public class JaccardC {

	public static String calculateJaccardC(String one, String two) {
		ArrayList<String> j1 = new ArrayList<String>();
		ArrayList<String> j2 = new ArrayList<String>();
		HashSet<String> set1 = new HashSet<String>();
		HashSet<String> set2 = new HashSet<String>();

		String result = null;
		double Thresh = 0.5;
		StringTokenizer tok = new StringTokenizer(one, " ");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			j1.add(token.toLowerCase());
			// System.out.print(token);

		}
		StringTokenizer tok2 = new StringTokenizer(two, " ");
		while (tok2.hasMoreTokens()) {
			String token = tok2.nextToken();
			j2.add(token.toLowerCase());
			// System.out.print(token);

		}
		set2.addAll(j2);
		set2.addAll(j1);
		set1.addAll(j1);
		set1.retainAll(j2);
		System.out.print(" Union=" + set2.size());
		System.out.print(" Intersection=" + set1.size());

		double j_coeffecient = ((double) set1.size()) / ((double) set2.size());
		System.out.print(" Jaccard coeffecient=" + j_coeffecient);
		if (j_coeffecient >= Thresh) {
			result = "True";
		} else {
			result = "False";
		}

		System.out.println("--" + result);
		return result;
	}
}