package string.similarity.measurement.interfaces;

import java.io.Serializable;

public interface StringSimilarity extends Serializable
{
	public double similarity(String s1, String s2);
}
