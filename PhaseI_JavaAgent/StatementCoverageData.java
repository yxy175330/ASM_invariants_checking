package JavaAgent;

import java.io.*;
import java.util.*;

public class StatementCoverageData {
	static HashSet<String> h = new HashSet<String>();
		
	public static void lineExecuted(String str) 
	{
		  h.add(str);
	}
	
	public static void writeIntoFile(FileWriter writer)
	{
		try 
		{		  
		    Iterator<String> iterator = h.iterator();  
            String temp="";			
            while (iterator.hasNext())
                temp+= iterator.next().replaceAll("/", "\\.");
			writer.write(temp);	
			h.clear();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();	
		}
	}		
}