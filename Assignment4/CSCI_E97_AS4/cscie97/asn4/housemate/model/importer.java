package cscie97.asn4.housemate.model;


import cscie97.asn4.housemate.controller.ControllerService;
import cscie97.asn4.housemate.entitlement.entitler;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;



public class importer {
	
	private int world_time = 0;
	private int tokenLimit = 20;
	
	
	/**
     * update_admin_token   - updates the current admin token life time
     * 						- if the token life time exceeds "tokenLimit", token status is marked as invalid
     * @param command
     */
	public void update_admin_token(){
		int token = entitler.getInstance().current_token;
		
		if (token != 34 && entitler.getInstance().allTokenMap.containsKey(token)){
			entitler.getInstance().allTokenMap.get(token).token_life_time++;
			if ((entitler.getInstance().allTokenMap.get(token).token_life_time) > tokenLimit){
				entitler.getInstance().allTokenMap.get(token).state = false;
				System.out.println("Logout Bot: Current admin token has expired");
				System.out.println();
				System.out.println();
			}
		}		
	}
	
	
	

	/**
     * importFile - it accepts a file containing lines of command and reads each command delimited by "\n"
     * 		      - It then send each line to cmdSort() for further processing
     * 
     * @param filename
     */
	public void importFile(String filename) throws Exception {
        
		
	       File input = new File(filename);
	       String line;
	       String status;
	       
	       //The import exception implementation
	       if (!input.exists()){
	          status = "HouseMateImportException: Import file does not exist";
				throw new Exception(status);
	       }
	       
	       Scanner read = new Scanner(input); // new scanner object to access file contents
	       read.useDelimiter(Pattern.quote("\n"));// period is set as triple list delimiters
	        
	       while (read.hasNext()){
	    	   line = read.next().trim();
	    	   String logTemp[] = line.split(" ");
	       
	    	   
	    	   if(line.length() < 2) //If current line is empty
	    		   continue;
	    	   
	    	   
	    	   update_admin_token(); //update current adm,in token
	    	   world_time++;//increment workd time
	    	   
	    	   
	   			if (logTemp[0].equals("login")){ //if it is a login command, login and skip remaining instructions
	    		   entitler.getInstance().login(logTemp[1], logTemp[2], logTemp[3]);
	    		   continue;
	    	   }
	   		
	   		
	    	   if (logTemp[0].equals("logout")){//if it is a logout command, logout and skip remaining instructions
	    		   entitler.getInstance().logout();
	    		   continue;
	    	   }
	   		
	    	   
	    	   if (!(entitler.getInstance().checkAdminToken())){//if no admin is logged in, notify user and skip remaining instructions
	    		   System.out.println("AccessDeniedException: No logged admin. Entering sleep mode");
	    		   continue;
	    	   } 
	   		
	   		
	    	  
	    	  
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
	
	
	
	
	
	/**
     * cmdSort - evaluates "command" and determines which API command should process the command
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
			
			
		case "define_permission": // create permissions
			
			entitler.getInstance().zeus.createPerm(element[1], element[2], element[3]);	
			break;
			
			
		case "define_role":	//create roles
			
			entitler.getInstance().zeus.createRole(element[1], element[2], element[3]);	
			break;
			
		
		case "create_resource_role": // create resources and assign "element[2]" to it
			
			entitler.getInstance().zeus.createResource(element[1], element[2], element[3]);
			break;
		
			
		case "create_user"://create users
			
			if (ModelService.getInstance().occupantExists(element[2])){
				entitler.getInstance().zeus.createUser(element[2], element[1]);	
			}
			else {
				System.out.println("AuthenticationException: The occupant corresponding to "+ element[2]+" does not exist");
			}
			
			break;
			
			
		case "add_entitlement_to_role"://adds permission or entitlement to role
			
			if (entitler.getInstance().allRoleMap.containsKey(element[1]) && entitler.getInstance().allPermMap.containsKey(element[2])){
				entitler.getInstance().allRoleMap.get(element[1]).perm_Map.put(element[2],entitler.getInstance().allPermMap.get(element[2]));	
			}
			
			break;
		
			
		case "add_resource_role_to_user": //add role to user
			
			if (entitler.getInstance().allUsersMap.containsKey(element[1]) && entitler.getInstance().allResourceMap.containsKey(element[2])){
				
				entitler.getInstance().allUsersMap.get(element[1]).resource_Map.put(element[2], entitler.getInstance().allResourceMap.get(element[2]));
			}
			
			break;
			
			
		case "add_user_credential":  // update user credentials
			
			if (entitler.getInstance().allUsersMap.containsKey(element[1])){
				
				if (element[2].equals("password")){
				entitler.getInstance().allUsersMap.get(element[1]).login_password = element[3].hashCode();
				
				}
				
				
				else if (element[2].equals("voice_print")){
					entitler.getInstance().allUsersMap.get(element[1]).voicePrint = element[3];
					
				}
				
				else {
					entitler.getInstance().allUsersMap.get(element[1]).login_name = element[3];
				}
			}
			
			break;
			
		
			
		default:
			status = "HouseMateServiceException: (" + command + ") is an invalid command";
			throw new Exception(status);
			
		}
	}	
}

