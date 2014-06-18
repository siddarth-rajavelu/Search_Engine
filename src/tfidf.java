import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class tfidf {
	public static HashMap <String,Integer> df()
	{
		File folder = new File("/media/8238548D38548259/newcrawlStoragetext");
	    File [] fileList=folder.listFiles();
	    HashSet <String> stopWords=new HashSet<String>();
	    HashMap <String,Integer> docfreq=new HashMap<String,Integer>();
	    File stopwordfile =new File("/media/8238548D38548259/eclipse/crawler_new/stopwords.txt");
	    List <String> stopwordslist=Utilities.tokenizeFile(stopwordfile);
		for(int i=0;i<stopwordslist.size();i++)
		   stopWords.add(stopwordslist.get(i));
		for(int i=0;i<fileList.length;i++)
		{
			try
	    	{
    	    //BufferedReader br = new BufferedReader(new FileReader(fileList[i]));
    	    HashSet <String> words=new HashSet <String>();
    	    List <String> tokens=Utilities.tokenizeFile(fileList[i]);
    	    for(int j=0;j<tokens.size();j++)
    	    {
    	    	if(stopWords.contains(tokens.get(j))||tokens.get(j).length()<=1||tokens.get(j).length()>=100)
    	    		continue;
    	    	words.add(tokens.get(j));
    	    }
    	    Iterator <String> it=words.iterator();
    	    while(it.hasNext())
    	    {
    	    	String w=it.next();
    	    	if(docfreq.containsKey(w))
    	    	{
    	    		docfreq.put(w, docfreq.get(w)+1);
    	    		continue;
    	    	}
    	    	docfreq.put(w,1);
    	    }
	    	}
			catch(Exception e)
			{
				
			}
		}
		return docfreq;
	}
}
