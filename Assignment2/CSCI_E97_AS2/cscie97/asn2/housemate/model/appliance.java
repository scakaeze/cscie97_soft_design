package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class appliance {
	

	private String name;
	private String type;
	private String room;
	private String house;
	
	public appliance (String n, String t, String r, String h){
		name = n;
		type = t;
		room = r;
		house = h;		
	}
	
	/**
     * getName - sets the appliance's name
     * @return name
     */
	public String getName(){
		return name;
	}
	
	/**
     * getType - sets the appliance's type
     * @return type
     */
	public String getType(){
		return type;
	}
	
	/**
     * getRoom - sets the appliance's room
     * @return room
     */
	public String getRoom(){
		return room;
	}
	
	/**
     * getHouse - sets the appliance's house
     * @return house
     */
	public String getHouse(){
		return house;
	}
}
