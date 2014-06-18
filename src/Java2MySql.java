import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
 
 
public class Java2MySql
{
    public static void main(String[] args) {
          String url = "jdbc:mysql://localhost:3306/";
          String dbName = "search_engine";
          String driver = "com.mysql.jdbc.Driver";
          String userName = "root";
          String password = "simple_password";
          try {
          Class.forName(driver).newInstance();
          Connection conn = DriverManager.getConnection(url+dbName,userName,password);
          Statement st = conn.createStatement();
          File folder = new File("/media/8238548D38548259/newcrawlStoragetext");
  	      File [] fileList=folder.listFiles();
  	      HashSet <String> stopWords=new HashSet<String>();
  	      File stopwordfile =new File("/media/8238548D38548259/eclipse/crawler_new/stopwords.txt");
	      List <String> stopwordslist=Utilities.tokenizeFile(stopwordfile);
		  for(int i=0;i<stopwordslist.size();i++)
		     stopWords.add(stopwordslist.get(i));
		  HashMap <String,Integer> docfreq=tfidf.df();
		  int n=35000;
	      //////////////////////////////////////////////////////////////////
		  int count=0;
		  for(int i=0;i<fileList.length;i++)
		    {
		    	System.out.println(count);
		    	count++;
			  //System.out.print(i+" ");
		    	if(!fileList[i].isFile())
		    		continue;
		    	try
		    	{
	    	    BufferedReader br = new BufferedReader(new FileReader(fileList[i]));
	    	    url=br.readLine();
	    	    br.close();
	    	    HashMap <String,ArrayList<Integer> > positions=new HashMap<String,ArrayList<Integer> >();
	    	    List <String> tokens=Utilities.tokenizeFile(fileList[i]);
	    	    for(int j=0;j<tokens.size();j++)
	    	    {
	    	    	if(stopWords.contains(tokens.get(j))||tokens.get(j).length()<=1||tokens.get(j).length()>=100)
	    	    		continue;
	    	    	if(positions.containsKey(tokens.get(j)))
	    	    	{
	    	    		ArrayList <Integer> p=positions.get(tokens.get(j));
	    	    		p.add(j);
	    	    		positions.put(tokens.get(j), p);
	    	    		continue;
	    	    	}
	    	    	ArrayList <Integer> p=new ArrayList <Integer>();
	    	    	p.add(j);
	    	    	positions.put(tokens.get(j),p);
	    	    	//ResultSet res = st.executeQuery("SELECT * FROM  indexer where term='"+tokens.get(j)+"';");
	    	    	//st.executeUpdate("insert into indexer values('"+tokens.get(j)+"','"+url+"');");
	    	    }
	    	    Set <String> ks=positions.keySet();
	    	    Iterator <String> ksIterator=ks.iterator();
	    	    while(ksIterator.hasNext())
	    	    {
	    	    	String k=ksIterator.next();
	    	    	ArrayList <Integer> p=positions.get(k);
	    	    	String pos="";
	    	    	for(int j=0;j<p.size();j++)
	    	    	{
	    	    		pos+=p.get(j)+" ";
	    	    	}
	    	    	int tfidf=(int) ((1+Math.log10(pos.length()))*Math.log10(n/docfreq.get(k)));
	    	    	st.executeUpdate("insert into indexer values('"+k+"','"+url+"','"+pos+"',"+tfidf+");");
	    	    }
	    	    }
		    	catch(Exception e)
		    	{
		    		//e.printStackTrace();
		    		
		    	}
		    
		    }
		  /////////////////////////////////////////////////////////////////
		  
		  
  	      
          //while (res.next()) {
          //int age = res.getInt("age");
          //String name = res.getString("name");
          //System.out.println(name + "\t" + age);
          //}

          //conn.close();
          } catch (Exception e) {
          e.printStackTrace();
          }
          }
}


