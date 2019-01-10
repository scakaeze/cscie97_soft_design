package cscie97.asn2.housemate.model;

import java.util.HashMap;
import java.util.Map;

public class occupant {

	private String name;
	private String house;
	private String type;
	private Map<String, String> authkeys = new HashMap<>();
	
	public occupant(String n, String t ){
		name = n;
		type = t;			
	}
	
	/**
     * setHouse - sets the occupant location
     * @param h
     */
	public void setHouse(String h){
		house = h;	
	}
	

	/**
     * getName - sets the occupant's name
     * @return name
     */
	public String getName(){
		return name;
	}
	
	/**
     * getLocation - sets the occupant's location
     * @return house
     */
	public String getlocation(){
		return house;
	}
	/**
     * getLocation - sets the occupant's type
     * @return type
     */
	public String getType(){
		return type;
	}
	
}

