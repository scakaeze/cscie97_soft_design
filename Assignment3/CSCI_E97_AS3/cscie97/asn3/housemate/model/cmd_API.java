package cscie97.asn3.housemate.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import cscie97.asn3.housemate.controller.ControllerService;

//DEFINE CLASS
public class cmd_API {
	
	/**
     * cmdSort - evaluates "command" and determines which  API command (define, set or get) should process the command
     *         - Then it sends command "s" to the corresponding API command
     * @param command
     */
	
	public void cmdSort(String command) throws Exception{
		
		API sortee = new API();
		String status;
	
		String element[] = command.split(" ");
		
		switch (element[0])
		{
		
		case "define": 
			
			try{
			sortee.define(command);
			}
			catch (Exception e){
				System.out.println(e);
			}
			break;
			
		case "set": 
			
			try{
				sortee.set(command);
				}
				catch (Exception e){
					System.out.println(e);
				}
			break;
			
		case "show":
			
			try{
				sortee.get(command);
				}
				catch (Exception e){
					System.out.println(e);
				}
			break;
			
		default:
			status = "CommandException: (" + command + ") is an invalid command";
			throw new Exception(status);
			
		}		
	}

	
	
	/**
     * importFile - it accepts a file containing lines of command and reads each command delimited by "\n"
     * 		      - It then send each line to cmdSort() for further processing
     * 
     * @param v
     */
	public void importFile(String filename) throws Exception {
        
	       File input = new File(filename);
	       String line;
	       String status;
	       
	       
	       //The import exception implementation
	       if (!input.exists()){
	           
	    	   status = "ImportException: Import file does not exist";
				throw new Exception(status);
	       }
	       
	       Scanner read = new Scanner(input); // new scanner object to access file contents
	       read.useDelimiter(Pattern.quote("\n"));// period is set as triple list delimiters
	       
	        
	       while (read.hasNext()){
	       
	    	   line = read.next().trim();
	    	   
	    	   //line = line.toLowerCase();
	    	   
	    	   if(line.length() < 2)
	    		   continue;
	    	   
	    	   try{
	    		   cmdSort(line);
	    		   ControllerService.getInstance().check_world_status();
					}
					catch (Exception e){
						System.out.println(e);
					}
	    	 
	      }
	       read.close(); // File is closed after knowledge map is fully updated
	        
	    }
}
