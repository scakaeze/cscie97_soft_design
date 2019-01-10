package cscie97.asn4.housemate.model;



public class sensor {

	private String name;
	private String type;
	private String room;
	private String house;
	
	public sensor (String n, String t, String r, String h){
		name = n;
		type = t;
		room = r;
		house = h;	
	}
	
	/**
     * getName - sets the sensor's name
     * @return name
     */
	public String getName(){
		return name;
	}
	
	/**
     * getRoom - sets the sensor's type
     * @return type
     */
	public String getType(){
		return type;
	}
	
	/**
     * getRoom - sets the sensor's room
     * @return room
     */
	public String getRoom(){
		return room;
	}
	
	/**
     * getHouse - sets the sensor's house
     * @return house
     */
	public String getHouse(){
		return house;
	}
	
}

