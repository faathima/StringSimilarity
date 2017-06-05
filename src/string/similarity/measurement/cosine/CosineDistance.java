package string.similarity.measurement.cosine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class CosineDistance {

	private static final Pattern SPACE_REG = Pattern.compile("\\s+");
	private int k = 3;
	private boolean idfEnable = false;
	private boolean termFrequencyEnable = false;
    public CosineDistance(final int k) {
        this.k = k;
    }

    public CosineDistance() {
    }
    
    public double getSimilarityScore (String str1, String str2) {
    	
    	CosineDistance cosine = new CosineDistance();
    	HashMap<String ,Double> string1Data = null;
    	HashMap<String ,Double> string2Data = null;
    	
//    	System.out.println(cosine.calculateSimilarity(string1Data, string2Data));
    	
    	if (cosine.isIdfEnable()) {
    		cosine.setTermFrequencyEnable(true);
    		
    		string1Data = cosine.getNormalizedMapIDF(cosine.getCommonSetIDF(getProfile(str1), getProfile(str2)), getProfile(str1));
    		string2Data = cosine.getNormalizedMapIDF(cosine.getCommonSetIDF(getProfile(str1), getProfile(str2)), getProfile(str2));
    		    		
    	} else {
    		string1Data = cosine.getNormalizedMap(cosine.getCommonSet(getProfile(str1), getProfile(str2)), getProfile(str1));
    		string2Data = cosine.getNormalizedMap(cosine.getCommonSet(getProfile(str1), getProfile(str2)), getProfile(str2));
    	}
    	
    	return cosine.calculateSimilarity(string1Data, string2Data);
    }
    
    public static Map<String, Integer> getProfile(String string) {
        HashMap<String, Integer> shingles = new HashMap<String, Integer>();

        String string_no_space = SPACE_REG.matcher(string).replaceAll(" ");
        String[] string_array = string_no_space.split(" ");
        
        for (int i = 0; i < string_array.length; i++) {
        	
        	try {
            	
                String shingle = string_array[i];
                if (null != shingles.get(shingle)) {
                    shingles.put(shingle, shingles.get(shingle) + 1);
                } else {
                    shingles.put(shingle, 1);
                }
        		
        	} catch (Exception e) {}
        }

//        System.out.println(shingles);
        
        return Collections.unmodifiableMap(shingles);
    }
    
    private HashSet<String> getCommonSet (Map<String, Integer> profile1, Map<String, Integer> profile2){
    	
    	HashSet<String> commonDicionarySet = new HashSet<String>();
    	    	
    	for (Map.Entry<String, Integer> entry : profile1.entrySet()) {
    		commonDicionarySet.add(entry.getKey());
    	}
    	
    	for (Map.Entry<String, Integer> entry : profile2.entrySet()) {
    		commonDicionarySet.add(entry.getKey());
    	}
    	
//    	System.out.println("commonDicionaryMap--->" + commonDicionarySet);
    	
    	return commonDicionarySet;
    	
    }
    
    private HashMap<String, Double> getCommonSetIDF (Map<String, Integer> profile1, Map<String, Integer> profile2){
    	
    	HashMap<String, Integer> commonDicionaryMap = new HashMap<String, Integer>();
    	HashMap<String, Double> commonDicionaryMapIdf = new HashMap<String, Double>();
    	    	
    	for (Map.Entry<String, Integer> entry : profile1.entrySet()) {
    		if (null == commonDicionaryMap.get(entry.getKey()))
    			commonDicionaryMap.put(entry.getKey(), entry.getValue());
    		else 
    			commonDicionaryMap.put(entry.getKey(), commonDicionaryMap.get(entry.getKey()) + entry.getValue());
    	}
    	
    	for (Map.Entry<String, Integer> entry : profile2.entrySet()) {
    		if (null == commonDicionaryMap.get(entry.getKey()))
    			commonDicionaryMap.put(entry.getKey(), entry.getValue());
    		else 
    			commonDicionaryMap.put(entry.getKey(), commonDicionaryMap.get(entry.getKey()) + entry.getValue());
    	}
    	
//    	System.out.println("commonDicionaryMap--->" + commonDicionaryMap);
    	
    	for (Map.Entry<String, Integer> entry : commonDicionaryMap.entrySet()) {
    		commonDicionaryMapIdf.put(entry.getKey(), Math.log10(2/entry.getValue()));
    	}
    	
//    	System.out.println("commonDicionaryMapIdf--->" + commonDicionaryMapIdf);
    	
    	return commonDicionaryMapIdf;
    	
    }
    
    private HashMap<String ,Double> getNormalizedMap (HashSet<String> commonDictionary, Map<String, Integer> profile) {
    	
    	Iterator<String> dictionaryIter = commonDictionary.iterator();
    	HashMap<String, Double> logFrequencyMap = new HashMap<String, Double>();
    	HashMap<String, Double> normalizedMap = new HashMap<String, Double>();
    	double normVal = 0.0;
    	
    	while (dictionaryIter.hasNext()) {
    		String key = dictionaryIter.next();
    		
    		double logFrequency = 0.0;
    		
    		if (null == profile.get(key)) logFrequency = 0.0;
    		else logFrequency = 1 + Math.log10(profile.get(key));
    		
    		normVal += (logFrequency*logFrequency);
    		
    		logFrequencyMap.put(key, logFrequency);
    		
    	}
    	
    	normVal = Math.sqrt(normVal);

//    	System.out.println("normValue--->" + normVal);
//    	System.out.println("logFrequencyMap--->" + logFrequencyMap);
    	
    	for (Map.Entry<String, Double> entry : logFrequencyMap.entrySet()) {
    		normalizedMap.put(entry.getKey(), entry.getValue()/normVal);
    	}
    	
//    	System.out.println("normalizedMap--->" + normalizedMap);
    	
    	return normalizedMap;
    	
    }
    
    private HashMap<String ,Double> getNormalizedMapIDF (HashMap<String, Double> commonDictionary, Map<String, Integer> profile) {
    	
    	Iterator<String> dictionaryIter = commonDictionary.keySet().iterator();
    	HashMap<String, Double> logFrequencyMap = new HashMap<String, Double>();
    	HashMap<String, Double> normalizedMap = new HashMap<String, Double>();
    	double normVal = 0.0;
    	
    	while (dictionaryIter.hasNext()) {
    		String key = dictionaryIter.next().trim();
    		
    		double logFrequency = 0.0;
    		double idf = commonDictionary.get(key);
    		
    		if (isTermFrequencyEnable()) {
    			if (null == profile.get(key)) logFrequency = 0.0;
        		else logFrequency = profile.get(key) * idf;
        		    		    		
        		logFrequencyMap.put(key, logFrequency);
        		normVal += (logFrequency*logFrequency);
    		} else {
	    		
        		if (null == profile.get(key) || profile.get(key) == 0) logFrequency = 0.0;
        		else logFrequency = (1 + Math.log10(profile.get(key))) * idf;
        		    		    		    		
        		logFrequencyMap.put(key, logFrequency);
        		normVal += (logFrequency*logFrequency);
    			
    		}
    		
    	}
    	
    	normVal = Math.sqrt(normVal);
    	
//    	System.out.println("normValue--->" + normVal);
//    	System.out.println("logFrequencyMap--->" + logFrequencyMap);
    	
    	for (Map.Entry<String, Double> entry : logFrequencyMap.entrySet()) {
    		normalizedMap.put(entry.getKey(), (entry.getValue() != 0.0) ? entry.getValue()/normVal : 0.0);
    	}
    	
//    	System.out.println("normalizedMap--->" + normalizedMap);
    	
    	return normalizedMap;
    	
    }
    
    private double calculateSimilarity (HashMap<String ,Double> string1Data, HashMap<String ,Double> string2Data) {
    	
		double similarity = 0.0;
    	
    	if (string1Data.size() == string2Data.size()) {
    		    		
    		for (Map.Entry<String, Double> entry : string1Data.entrySet()) {
    			double dotProduct = entry.getValue() * string2Data.get(entry.getKey());
    			similarity += dotProduct;
    		}
    		
    	}
    	
    	return similarity;
    	
    }
 
	public boolean isIdfEnable() {
		return idfEnable;
	}

	public void setIdfEnable(boolean idfEnable) {
		this.idfEnable = idfEnable;
	}

	public boolean isTermFrequencyEnable() {
		return termFrequencyEnable;
	}

	public void setTermFrequencyEnable(boolean termFrequencyEnable) {
		this.termFrequencyEnable = termFrequencyEnable;
	}
    
}
