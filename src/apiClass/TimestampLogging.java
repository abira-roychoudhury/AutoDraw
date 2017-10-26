package apiClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import modal.Constants;

public class TimestampLogging {
	
	String logFilePath,logs;
	
	/*
	 * Constructor 
	 * */
	public TimestampLogging()
	{
		File logFile = new File(Constants.logFile);
		try {
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		  }	
		logFilePath = logFile.getPath();
		logs = "";
	}
	
	/* DESCRIPTION : Maintains the log of the time taken for a required function to be executed
	 * INPUT : String FunctionName, Date start the start time of the function and Date end the end time of the function
	 * OUTPUT : int diff the time taken for the function to execute
	 * */
	public int fileLog(String functionName, Date start, Date end){
		int diff = (int) (end.getTime() - start.getTime());
		logs = logs +"\n"+ functionName +"    "+diff;
		return diff;
	}
	
	/* DESCRIPTION : Appends the image fileName and fileType to the log
	 * INPUT : String fileName and String fileType
	 * OUTPUT : 
	 * */
	public void fileDesc(String fileName, String fileType){
		logs = logs + "\n\n"+fileName+"     "+fileType;		
	}
	
	/* DESCRIPTION : Writes the entire log into the file 
	 * INPUT : 
	 * OUTPUT : 
	 * */
    public void fileWrite(){
    	try {
		    Files.write(Paths.get(logFilePath), logs.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
