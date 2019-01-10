package cscie97.asn4.housemate.controller;


import cscie97.asn4.housemate.entitlement.entitler;
import cscie97.asn4.housemate.entitlement.visitor;
import cscie97.asn4.housemate.model.ModelService;
import cscie97.asn4.housemate.model.appliance;

import java.util.*;


/**
 * Controller Service
 * 		  - it is invoked by cmd_API, it checks KG for new commands from sensors and appliances and executes
 */
public class ControllerService {
	
	private static ControllerService Jarvis = new ControllerService(); //Singleton  Design implementation 
	private ControllerService(){};
	public static ControllerService getInstance(){
		return Jarvis;
	}
	
	public Map<Integer,request> requestMap = new HashMap<>(); //HashMap to hold all requests
	
	
	
	/**
	 * HMMS()
	 * 		  - shorter method for calling ModelService.getInstance();
	 */
	public static ModelService HMMS(){
		return ModelService.getInstance();
	}
	
	
	
	/**
     * check_world_status()
     * 		  - Check through all commands in KG 
     * 		  - It stores the command in requestMap
     * 		  - Invoke execute_rule to execute new command
     */
	public void check_world_status() throws Exception{
		boolean valid_cmd = false;
		String previous[] = {"", "",""};
	
			if (HMMS().queryMap.containsKey("?" + " " + "input" + " " + "?")){	// if commands exist in KG	
				
				for (String cmd : HMMS().queryMap.get("?" + " " + "input" + " " + "?")){ 	//iterate through commands
					
					String current[] = cmd.split(" ");
					
					if ( current[2].equals("done")){ 
						continue;														//Skip previous commands
					}		
					
					previous[0] = current[0];
					previous[1] = current[1];
					previous[2] = current[2];
					
					valid_cmd = true;							
					requestMap.put(cmd.hashCode(), new request("open", cmd));//store new commands
					
					try{
						 execute_rule(requestMap.get(cmd.hashCode()).command);			//execute command according to execute_rule()
						 requestMap.get(cmd.hashCode()).status = "closed";				// store command status as closed if exception is not thrown
							}
					catch (Exception e){
						System.out.println(e);
					}

					System.out.println(); 
					System.out.println();
				}
			}	
			
			if (valid_cmd)
				HMMS().updateData(previous[0], previous[1], "done"); //Mark new commands as done
		}
	
	
	

	/**
     * execute_rule()
     * 		  - Use switch statement to match the command
     * 		  - It confirms that the command comes from the expected device time
     * 		  - It executes the rule and returns to check_world_status()
     */
	public String execute_rule(String cmd) throws Exception{
		
		String elements[] = cmd.split(" ");
		String occupantName = null;
		String voicePrint = null;
		String status = "ControllerCommandException: (" + cmd + ") is an invalid command or the command doesnot match the device type";

		
		if (elements[2].contains("?")){
			String[]extractCmdOwner = elements[2].split("\\?"); // for cases where the owner name is added with "+"
			elements[2] = extractCmdOwner[0];
			voicePrint= extractCmdOwner[1];
			//System.out.println("cmd voice print is " + voicePrint);
			
		}
		
		System.out.println("************************"+elements[0] + " "+ elements[1] + " "+ elements[2] + "************************"); //display command prior to any rule execution
		
		
		if (elements[2].contains("+")){
			String[]extractCameraTarget = elements[2].split("\\+"); // for cases where the occupant name is added with "+"
			elements[2] = extractCameraTarget[0];
			occupantName= extractCameraTarget[1];
			//System.out.println("occupant name is " + occupantName);
		}
		
		
		String senderName = elements[0], senderRoom, senderHouse;
		String targetsFound, targetHouse = null, targetRooms = null, targetType = null;
		String deviceType;
	
		
		
		if (HMMS().applianceMap.containsKey(elements[0])){        				// if the input device is an appliance, locate room and house
			senderRoom  = HMMS().applianceMap.get(elements[0]).getRoom();
			senderHouse = HMMS().applianceMap.get(elements[0]).getHouse();
			deviceType =  HMMS().applianceMap.get(elements[0]).getType();
			
		}
		else{																	// if the input device is a sensor, locate room and house
			senderRoom  = HMMS().sensorMap.get(elements[0]).getRoom();
			senderHouse = HMMS().sensorMap.get(elements[0]).getHouse();
			deviceType =  HMMS().sensorMap.get(elements[0]).getType();
			
		}
	
		
		
		switch (elements[2])
		{
		
		case "open_door": 
			
			if (deviceType.equals("AVA")){
		
				for (appliance findDoors:HMMS().applianceMap.values()){
					if (findDoors.getType().equals("door")){ //locate all doors
						targetsFound = findDoors.getName();
						targetHouse = findDoors.getHouse();
						targetRooms = findDoors.getRoom();
						targetType = findDoors.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){   //locate doors in same room and house as sender
                   
							if (secure_HMMS_access(targetsFound, "status", "opened", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findDoors.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")) + " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}
							
						}
					}
				}
				status = "good";
			}
			break;
			
			
			
		case "close_door":

			if (deviceType.equals("AVA")){
				for (appliance findDoors:HMMS().applianceMap.values()){
					if (findDoors.getType().equals("door")){	//locate all doors
						targetsFound = findDoors.getName();
						targetHouse = findDoors.getHouse();
						targetRooms = findDoors.getRoom();
						targetType = findDoors.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){   //locate doors in same room and house as sender
							if (secure_HMMS_access(targetsFound, "status", "closed", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findDoors.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")) + " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}		
						}
					}
				}	
				status = "good";
			}
		break;	
		
		
		
		case "lights_off":

			if (deviceType.equals("AVA")){
				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){			//locate all lights
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						targetType = findLights.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){ // locate all lights in same room and house
							
							if (secure_HMMS_access(targetsFound, "status", "OFF", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")) + " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}
							
						}
					}
				}
				status = "good";
			}
		break;	
		
		
		
		case "lights_on":

			if (deviceType.equals("AVA")){
				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){			// locate all lights
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						targetType = findLights.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){ 	// locate all lights in same room and house
							if (secure_HMMS_access(targetsFound, "status", "ON", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")) + " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}
						}
					}
				}
				status = "good";
			}
		break;	
		
		
		case "lights_color_to_RED":

			if (deviceType.equals("AVA")){
				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){			// locate all lights
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						targetType = findLights.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){ 	// locate all lights in same room and house
							if (secure_HMMS_access(targetsFound, "status", "RED", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?"))+ " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}
						}
					}
				}
				status = "good";
			}
		break;	
		
		case "lights_color_to_GREEN":

			if (deviceType.equals("AVA")){
				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){			// locate all lights
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						targetType = findLights.getType();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){ 	// locate all lights in same room and house
							if (secure_HMMS_access(targetsFound, "status", "GREEN", voicePrint, senderHouse )){//verify user entitlement and update KG
								System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?"))+ " by " + entitler.getInstance().voiceFind(voicePrint));
							}
							else {
								System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any known users or matched user lacks required entitlement");
							}
						}
					}
				}
				status = "good";
			}
		break;
		
		
		
		case "occupant_detected":

			
			if (deviceType.equals("camera")){
				for (appliance findDevices:HMMS().applianceMap.values()){
					if (findDevices.getType().equals("light") || findDevices.getType().equals("thermostat")   ){//locate all thermostat and lights	
						targetsFound = findDevices.getName();
						targetHouse = findDevices.getHouse();
						targetRooms = findDevices.getRoom();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom) && findDevices.getType().equals("light") ){ // locate light in same room and house
							HMMS().updateData(targetsFound, "status", "ON");	// update KG and display
							System.out.println(findDevices.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
						}
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom) && findDevices.getType().equals("thermostat") ){//locate all thermostat in same room and house
							HMMS().updateData(targetsFound, "status", "High"); //update KG and display
							System.out.println(findDevices.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
						}		
						HMMS().updateData(occupantName, "location", senderRoom);
					}
				}
				status = "good";
			}
		break;	
		
		
		
		case "occupant_leaving":

			if (deviceType.equals("camera")){
				for (appliance findDevices:HMMS().applianceMap.values()){
					if (findDevices.getType().equals("light") || findDevices.getType().equals("thermostat")   ){			//locate all thermostat and lights		
						targetsFound = findDevices.getName();
						targetHouse = findDevices.getHouse();
						targetRooms = findDevices.getRoom();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom) && findDevices.getType().equals("light") ){// locate light in same room and house
							HMMS().updateData(targetsFound, "status", "OFF");	// update KG and display
							System.out.println(findDevices.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
						}
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom) && findDevices.getType().equals("thermostat") ){//locate all thermostat in same room and house
							HMMS().updateData(targetsFound, "status", "low");  //update KG and display
							System.out.println(findDevices.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
						}
						HMMS().updateData(occupantName, "location", "unknown");
					}
				}
				status = "good";
			}
		break;	
		
		
		
		case "occupant_is_active":
			
			if (deviceType.equals("camera")){
				
				HMMS().updateData(occupantName, "status", "active");  //update KG and display
				System.out.println(HMMS().queryMap.get(occupantName + " " + "status" + " " + "?"));
				status = "good";
			}
		break;	
		
		
		
		case "occupant_is_inactive":
			
			if (deviceType.equals("camera")){			
					
				HMMS().updateData(occupantName, "status", "inactive"); //update KG and display
				System.out.println(HMMS().queryMap.get(occupantName + " " + "status" + " " + "?"));
				status = "good";

				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){			//locate all lights
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						if (targetHouse.equals(senderHouse) && targetRooms.equals(senderRoom)){ // locate all lights in same room and house
							HMMS().updateData(targetsFound, "status", "DIM"); //update KG and display
							System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
						}
					}
				}
				
			}
		break;
		
		
		
		case "where_is":
			if (deviceType.equals("AVA")){
				if (!(entitler.getInstance().voiceFind(voicePrint).equals("Unknown_occupant"))){
					System.out.println(HMMS().queryMap.get(occupantName + " " + "location" + " " + "?") + " by " + entitler.getInstance().voiceFind(voicePrint)); //Display location of occupant
					
				}
				else {
					System.out.println("AcessDeniedException: " + voicePrint + " voice print does not match any registered users");
				}
				
				status = "good";	
			}
		break;
		
		
		
		case "time_to_cook_is_zero":
			if (deviceType.equals("oven")){
				HMMS().updateData(senderName, "status", "OFF"); //update KG and display
				System.out.println(HMMS().queryMap.get(senderName + " " + "status" + " " + "?"));
				status = "good";
			}
		break;
		
		
		
		case "beer_count_is":
			if (deviceType.equals("refrigerator")){
				if (Integer.parseInt(occupantName) < HMMS().occupantMap.get("mother").beer_minimum) // Check mother's beer count minimum and execute accordingly
					System.out.println("earl@walgreens_beer: "+ elements[0] + " needs 2 crates of beer. Deliver ASAP or might accidentally dial 911");
				else{
					System.out.println(Integer.parseInt(occupantName) + " beers is still enough according for mother. No extra beer needed");
				}
				status = "good";
			}
		break;
		
		
		
		case "mode_FIRE":
			
			if (deviceType.equals("smoke_detector")){
				
				for (appliance findLights:HMMS().applianceMap.values()){
					if (findLights.getType().equals("light")){	// locate all lights 
						targetsFound = findLights.getName();
						targetHouse = findLights.getHouse();
						targetRooms = findLights.getRoom();
						HMMS().updateData(targetsFound, "status", "ON");// turn on all lights and display message
						System.out.println(findLights.getRoom() + " " + (HMMS().queryMap.get(targetsFound + " " + "status" + " " + "?")));
					}
				status = "good";
				}
				System.out.println("Fire in the Kitchen, please leave the house immediately.");
				System.out.println("First floor occupants, please use the window if possible");
				System.out.println("Dialing 911.............................................");
				System.out.println("Hello Operator," + targetHouse + " at " + HMMS().houseMap.get(targetHouse).getAddress() + " is on fire!!!");	
			}
		break;
		
		
		default:
	
		}
		
		if (!(status.equals("good")))
				throw new Exception(status); // if command is unmatched or device type  does not match command
		
		return status;
	}
	
	
	/**
     * secure_HMMS_access()
     * 		  - Identify voice print's owner
     * 		  - Generate token for the voice print's owner 
     * 		  - Invoke secureUpdateData with command elements and corresponding user token
     */
	public boolean secure_HMMS_access(String resource_name, String action, String value, String voicePrint, String house ){
		
		visitor check = new visitor();
		boolean state = false;
		
		check.user_check = entitler.getInstance().voiceFind(voicePrint);
		check.user_house = house;
		int token = entitler.getInstance().voiceTokGen(check);
	
		if (HMMS().secureUpdateData(resource_name, action, value, token))
			state = true;
		
		
		return state;	
	}
}







	

