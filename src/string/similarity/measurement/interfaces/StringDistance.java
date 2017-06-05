package string.similarity.measurement.interfaces;

import java.io.Serializable;

public interface StringDistance extends Serializable
{
	public double distance(String s1, String s2);
}
