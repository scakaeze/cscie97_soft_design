package cscie97.asn2.housemate.model;

import java.io.IOException;
import java.util.Set;


public class API {
	
	

	/**
     * define - accepts a command v
     * 		  - It determines what house_model class instance  should be created
     *        - It creates the instance using attributes specified by command V
     * 
     * @param v
     */
	public String define(String v) throws Exception{
		String element[] = v.split(" ");
		String temp = "";
		String status = "";
		
		switch (element[1])
		{
		
		case "house": 
			for (int x = 4; x<element.length; x++ ){
				temp = temp + element[x] + " ";
			}
			
			if (!(clients.getInstance().houseExists(element[2])))
			{		
				clients.getInstance().addHouse(element[2], temp);
				status = "";
			}
			
			else{
				status = "CommandException: The house already exists or (" + v + ") is invalid command";
				throw new Exception(status);
				}
			
			break;
			
			
		case "room":
			
			if (!(clients.getInstance().roomExists(element[2], element[7])))
			{
			clients.getInstance().addRoom(element[7], element[2], element[3], element[5], Integer.parseInt(element[9]));
			status = "";
			}
			else{
				status = "CommandException: The room already exists or (" + v + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
		
			
		case "occupant":
			
			if (!(clients.getInstance().occupantExists(element[2])))
			{
			clients.getInstance().addOccupant(element[2], element[4]);
			status = "";
			}
			
			else{
				status = "CommandException: The occupant already exists or (" + v + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
			
		case "sensor":
			if (!(clients.getInstance().sensorExists(element[2], element[6]))){
				clients.getInstance().addSensor(element[2], element[4], element[7], element[6]);
				status = "";
			}
			
			else{
				status = "CommandException: The sensor already exists or (" + v + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
		
		case "appliance":
			if (!(clients.getInstance().applianceExists(element[2], element[6]))){
				clients.getInstance().addAppliance(element[2], element[4], element[7], element[6]);
				status = "";
			}
			
			else{
				status = "CommandException: The appliance already exists or (" + v + ") is an invalid command";
				throw new Exception(status);
				}
			
			break;
			
		default:
			status = "CommandException: (" + v + ") is an invalid command";
			throw new Exception(status);
			}
		
		return status;
	}
	
	
	/**
     * set - accepts a command v
     * 	   - It determines what sensor/appliance value has set or updated
     *     - It updates the sensor/appliance value
     * 
     * @param v
     */
	public String set(String v) throws Exception{
		String element[] = v.split(" ");
		String temp = "";
		
		switch (element[1])
		{
		
		case "sensor": 
			if (clients.getInstance().sensorExists(element[4], element[2])){	
				clients.getInstance().updateData(element[4], element[6], element[8]);
			}
			else{ 
				temp = "CommandException: sensor " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			break;
			
		case "appliance": 
			if (clients.getInstance().applianceExists(element[4], element[2])){
				clients.getInstance().updateData(element[4], element[6], element[8]);
			}
			else{ 
				temp = "CommandException: appliance " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			break;
			
		}
		return temp;
	}
	
	
	/**
     * get - return model details specified by command v
     * @param v
     */
	public String get(String v) throws Exception{
		String element[] = v.split(" ");
		String temp = "";
		
		switch (element[1])
		{
		
		case "sensor": 
			
			if (clients.getInstance().sensorExists(element[4], element[2]) && element.length == 7){
				
				for (String check : clients.getInstance().queryMap.get(element[4]+" "+ element[6] + " "+ "?")) {
			        System.out.println(check);
			     }
				
			}
			else if ( clients.getInstance().sensorExists(element[4], element[2]) && element.length == 5){
				
				//clients.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?");
				
				for (String check : clients.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?")) {
			        System.out.println(check);
			     }
			}
			
			else{
				temp = "CommandException: sensor " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			
			break;
			
		
		case "appliance":
			
			if (clients.getInstance().applianceExists(element[4], element[2]) && element.length == 7){
				
				for (String check : clients.getInstance().queryMap.get(element[4]+" "+ element[6] + " "+ "?")) {
			        System.out.println(check);
			     }
				
			}
			else if ( clients.getInstance().applianceExists(element[4], element[2]) && element.length == 5){
				
				for (String check : clients.getInstance().queryMap.get(element[4]+" "+ "?" + " "+ "?")) {
			        System.out.println(check);
			     }
			}
			
			else{
				temp = "CommandException: appliance " + element[4] + " does not exist";
				throw new Exception(temp);
			}
			
			break;	
			
		case "configuration": 
			
			if ( element.length == 4 && clients.getInstance().houseExists(element[3])){
				System.out.print("House "+ element[3]+ " is located at ");
				System.out.print(clients.getInstance().houseMap.get(element[3]).getAddress() + "\n");
			}
			
			else if (element.length == 5 && clients.getInstance().roomExists(element[4], element[3])){
				System.out.print("Room "+ element[4]+ " is located in house ");
				System.out.print(clients.getInstance().roomMap.get(element[4]).getHouse() + " on floor ");
				System.out.print(clients.getInstance().roomMap.get(element[4]).getFloor() + ". It is also a ");
				System.out.print(clients.getInstance().roomMap.get(element[4]).getType() + " with ");
				System.out.print(clients.getInstance().roomMap.get(element[4]).getWindowNum() + " windows\n");	
			}	
			else if (v.equals("show configuration")){
				configAll();
			}
			break;
			
			
		default:
			temp = "CommandException: The command is wrong";
			throw new Exception(temp);
		}
		return temp;	
	}
	
	
	/**
     * configAll - return all house configurations
     * @param v
     */
	public void configAll(){
		Set<String> homeNames = clients.getInstance().houseMap.keySet();
		
		for (String check : homeNames) {
			System.out.print("House "+ check+ " is located at ");
			System.out.print(clients.getInstance().houseMap.get(check).getAddress() + "\n");
	     }
	}

	
}










