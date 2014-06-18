
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	public static boolean alphanum(char a)
	{
	    if(a>='a' && a<='z')
	    return true;
	    if(a>='A' && a<='Z')
	    return true;
	    if(a>='0' && a<='9')
	    return true;
	    return false;
	}
	public static ArrayList<String> tokenizeFile(File input) {
		// TODO Write body!
		ArrayList <String> result=new ArrayList<String>();
		try{
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count=0;
		while ((line = br.readLine()) != null)
		{
		    count++;
		    if(count==1)
		    	continue;
			int lineLength=line.length();
		    int i=0,j=0;
		    line=line.toLowerCase();
		    while(i<lineLength)
		    {
		        while(i<lineLength && !alphanum(line.charAt(i)))
		        i++;
		        j=i;
		        while(j<lineLength && alphanum(line.charAt(j)))
		        j++;
		        if(i!=j)
		        {
		        String token=line.substring(i,j);
		        result.add(token);
		        }
		        i=j;
		    }
		}
		br.close();
		}
		catch(IOException e)
		{
		}
		return result;
	}
	//////////////////////////////////////////////////////////////
	public static ArrayList<String> tokenizeTitle(File input) {
		// TODO Write body!
		ArrayList <String> result=new ArrayList<String>();
		String tmp=""; 
		try{
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count=0;
		while ((line = br.readLine()) != null)
		{
		    count++;
		    if(count==1)
		    	continue;
		    line=line.toLowerCase();
		    tmp+=line;
		}
		int i=tmp.indexOf("<title>")+6,j;
		int end=tmp.indexOf("</title>");
		if(end==-1)
			return result;
		while(i<end)
	    {
	        while(i<end && !alphanum(tmp.charAt(i)))
	        i++;
	        j=i;
	        while(j<end && alphanum(tmp.charAt(j)))
	        j++;
	        if(i!=j)
	        {
	        String token=tmp.substring(i,j);
	        result.add(token);
	        }
	        i=j;
	    }
		br.close();
		}
		catch(IOException e)
		{
		}
		return result;
	}
	////////////////////////////////////////////////////////////////
	
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies,int num) {
		// TODO Write body!
		if(frequencies.size()==0)
		{
			System.out.print("empty");
			return;
		}
		int total=0;
		for(int i=0;i<frequencies.size();i++)
			total+=frequencies.get(i).getFrequency();
		String f=frequencies.get(0).toString();
		boolean twoGram=false;
		for(int i=0;i<f.length();i++)
			if(f.charAt(i)==' ')
				twoGram=true;
		if(twoGram)
		{
			System.out.print("Total 2-gram count: "+total+"\n");
			System.out.print("Unique 2-gram count: "+frequencies.size()+"\n\n");
		}
		else
		{
			System.out.print("Total item count: "+total+"\n");
			System.out.print("Unique item count: "+frequencies.size()+"\n\n");
		}
		for(int i=0;i<frequencies.size()&&i<num;i++)
			System.out.print(frequencies.get(i).toString()+"\n");
	}
}
