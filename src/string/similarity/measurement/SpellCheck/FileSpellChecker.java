package string.similarity.measurement.SpellCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import com.swabunga.spell.event.TeXWordFinder;

public class FileSpellChecker implements SpellCheckListener 
{

    private SpellChecker spellChecker;
    private List<String> misspelledWords;
    private static SpellDictionaryHashMap dictionaryHashMap;

    static 
    {
        File dict = new File("F:/StringSimilarity/StringSimilarity/src/string/similarity/measurement/SpellCheck/dictionary.txt");
        try 
        {
            dictionaryHashMap = new SpellDictionaryHashMap(dict);
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private void initialize() 
    {
        spellChecker = new SpellChecker(dictionaryHashMap);
        spellChecker.addSpellCheckListener(this);
    }

    public FileSpellChecker() 
    {
        misspelledWords = new ArrayList<String>();
        initialize();
    }

    //Check for Spelling errors
    public String checking_spellings(String sentence1) 
    {
        sentence1 = getCorrectedLine(sentence1);
        return sentence1;
    }
    
    //correct the misspelled words in the input string and return the result
    public String getCorrectedLine(String line) 
    {
        List<String> misSpelledWords = getMisspelledWords(line);

        for (String misSpelledWord : misSpelledWords) 
        {
            List<String> suggestions = getSuggestions(misSpelledWord);
            if (suggestions.size() == 0) 
            {
                continue;
            }
			String bestSuggestion = suggestions.get(0);
            line = line.replace(misSpelledWord, bestSuggestion);
            
        }
        return line;
    }
    
    //get a list of misspelled words from the text
    public List<String> getMisspelledWords(String text) 
    {
        StringWordTokenizer texToken = new StringWordTokenizer(text,new TeXWordFinder());
        spellChecker.checkSpelling(texToken);
        return misspelledWords;
    }

    //get suggestions for the wrong words
    @SuppressWarnings("unchecked")
	public List<String> getSuggestions(String misspelledWord) 
    {
        List<Word> suggestedWords = spellChecker.getSuggestions(misspelledWord, 0);
        List<String> suggestions = new ArrayList<String>();
        for (Word suggestion : suggestedWords) 
        {
            suggestions.add(suggestion.getWord());
        }
       // System.out.println("!!!"+suggestions);
        return suggestions;
    }

    public String getCorrectedText(String line) 
    {
        StringBuilder builder = new StringBuilder();
        String[] tempWords = line.split(" ");
        for (String tempWord : tempWords) 
        {
            if (!spellChecker.isCorrect(tempWord)) 
            {
                List<Word> suggestions = spellChecker.getSuggestions(tempWord, 0);
                if (suggestions.size() > 0) 
                {
                    builder.append(spellChecker.getSuggestions(tempWord, 0).get(0).toString());
                } 
                else 
                {
                    builder.append(tempWord);
                }
            } 
            else 
            {
                builder.append(tempWord);
            }
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    @Override
    public void spellingError(SpellCheckEvent event) 
    {
        event.ignoreWord(true);
        misspelledWords.add(event.getInvalidWord());
    }
}
