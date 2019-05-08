package JavaAgent;

import java.io.*;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class Listener extends RunListener
{
	static FileWriter writer;
	
	public void testRunStarted(Description description) throws java.lang.Exception 
	{
          try 
	  {
		 File file = new File("stmt-cov.txt");
		 if (file.exists())
                       file.delete();
                 else		   
		       file.createNewFile();
		 writer = new FileWriter("stmt-cov.txt",false);
	  }
	  catch (Exception ex) 
	  {
	          ex.printStackTrace();
	
	  }
        }	
	/**
	 *  Called when all tests have finished
	 * */
	public void testRunFinished(Result result) throws java.lang.Exception
	{
		writer.close();
	}
	
	public void testFinished(Description description) throws java.lang.Exception
	{
        StatementCoverageData.writeIntoFile(writer);
        }

	/**
	 *  Called when an atomic test is about to be started.
	 * */
	public void testStarted(Description description) throws java.lang.Exception
	{
		 writer.write("[TEST] "+description.getClassName()+":"+description.getMethodName() + "\n");
	}
}