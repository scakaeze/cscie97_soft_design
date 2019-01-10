package cscie97.asn4.housemate.model;

import java.util.*;


public class API {
	

	/**
     * define - accepts a command v
     * 		  - It determines what house_model class instance  should be created
     *        - It creates the instance using attributes specified by command V
     * 
     * @param command
     */
	public String define(String command) throws Exception{
		String element[] = command.split(" ");
		String temp = "";
		String status = "";
		
		switch (element[1])
		{
		
		case "house": 
			for (int x = 4; x<element.length; x++ ){
				temp = temp + element[x] + " ";
			}
			
			if (!(ModelService.getInstance().houseExists(element[2])))
			{		
				ModelService.getInstance().addHouse(element[2], temp);
				status = "";
			}
			
			else{
				status = "ModelServiceException: The house already exists or (" + command + ") is invalid command";
				throw new Exception(status);
				}
			
			break;
			
			
		case "room":
			
			if (!(ModelService.getInstance().roomExists(element[2], element[7])))
			{
			ModelService.getInstance().addRoom(element[7], element[2], element[3], element[5], Integer.parseInt(element[9]));
			status = "";
			}
			else{
				status = "ModelServiceException: The room already exists or (" + command + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
		
			
		case "occupant":
			
			if (!(ModelService.getInstance().occupantExists(element[2])))
			{
			ModelService.getInstance().addOccupant(element[2], element[4]);
			status = "";
			}
			
			else{
				status = "ModelServiceException: The occupant already exists or (" + command + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
			
		case "sensor":
			if (!(ModelService.getInstance().sensorExists(element[2], element[6]))){
				ModelService.getInstance().addSensor(element[2], element[4], element[7], element[6]);
				status = "";
			}
			
			else{
				status = "ModelServiceException: The sensor already exists or (" + command + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
		
		case "appliance":
			if (!(ModelService.getInstance().applianceExists(element[2], element[6]))){
				ModelService.getInstance().addAppliance(element[2], element[4], element[7], element[6]);
				status = "";
			}
			
			else{
				status = "ModelServiceException: The appliance already exists or (" + command + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
		default:
			status = "ModelServiceException: (" + command + ") is an invalid command";
			throw new Exception(status);
			}
		
		return status;
	}
	
	
	/**
     * set - accepts command
     * 	   - It determines what sensor/appliance value has set or updated
     *     - It updates the sensor/appliance value
     * 
     * @param command
     */
	public String set(String command) throws Exception{
		String element[] = command.split(" ");
		String temp = "";
		
		switch (element[1])
		{
		
		case "sensor": 
			if (ModelService.getInstance().sensorExists(element[4], element[2])){	
				ModelService.getInstance().updateData(element[4], element[6], element[8]);
			}
			else{ 
				temp = "ModelServiceException: sensor " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			break;
			
		case "appliance": 
			if (ModelService.getInstance().applianceExists(element[4], element[2])){
				ModelService.getInstance().updateData(element[4], element[6], element[8]);
			}
			else{ 
				temp = "ModelServiceException: appliance " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			break;
			
		}
		return temp;
	}
	
	
	/**
     * get - return model details specified by command
     * @param command
     */
	public String get(String command) throws Exception{
		String element[] = command.split(" ");
		String temp = "";
		
		switch (element[1])
		{
		
		case "sensor": 
			
			if (ModelService.getInstance().sensorExists(element[4], element[2]) && element.length == 7){
				
				for (String check : ModelService.getInstance().queryMap.get(element[4]+" "+ element[6] + " "+ "?")) {
			        System.out.println(check);
			     }
				
			}
			else if ( ModelService.getInstance().sensorExists(element[4], element[2]) && element.length == 5){
				
				//clients.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?");
				
				for (String check : ModelService.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?")) {
			        System.out.println(check);
			     }
			}
			
			else{
				temp = "ModelServiceException: sensor " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			
			break;
			
		
		case "appliance":
			
			if (ModelService.getInstance().applianceExists(element[4], element[2]) && element.length == 7){
				
				for (String check : ModelService.getInstance().queryMap.get(element[4]+" "+ element[6] + " "+ "?")) {
			        System.out.println(check);
			     }
				
			}
			else if ( ModelService.getInstance().applianceExists(element[4], element[2]) && element.length == 5){
				
				for (String check : ModelService.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?")) {
			        System.out.println(check);
			     }
			}
			
			else{
				temp = "ModelServiceException: appliance " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			
			break;	
			
		case "configuration": 
			
			if ( element.length == 4 && ModelService.getInstance().houseExists(element[3])){
				System.out.print("House "+ element[3]+ " is located at ");
				System.out.print(ModelService.getInstance().houseMap.get(element[3]).getAddress() + "\n");
			}
			
			else if (element.length == 5 && ModelService.getInstance().roomExists(element[4], element[3])){
				System.out.print("Room "+ element[4]+ " is located in house ");
				System.out.print(ModelService.getInstance().roomMap.get(element[4]).getHouse() + " on floor ");
				System.out.print(ModelService.getInstance().roomMap.get(element[4]).getFloor() + ". It is also a ");
				System.out.print(ModelService.getInstance().roomMap.get(element[4]).getType() + " with ");
				System.out.print(ModelService.getInstance().roomMap.get(element[4]).getWindowNum() + " windows\n");	
			}	
			else if (command.equals("show configuration")){
				configAll();
			}
			break;
			
			
		default:
			temp = "ModelServiceException:: The command is wrong";
			throw new Exception(temp);
		}
		return temp;	
	}
	
	
	/**
     * configAll - return all house configurations
     * @param v
     */
	public void configAll(){
		Set<String> homeNames = ModelService.getInstance().houseMap.keySet();
		
		for (String check : homeNames) {
			System.out.print("House "+ check+ " is located at ");
			System.out.print(ModelService.getInstance().houseMap.get(check).getAddress() + "\n");
	     }
	}

	
}











