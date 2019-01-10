package cscie97.asn4.housemate.model;




public class occupant {

	private String name;
	private String house;
	private String type;
	public int beer_minimum;
	
	public occupant(String n, String t ){
		name = n;
		type = t;	
		
		if (type.equals("adult")){
			beer_minimum = 5;
			}
		else{
			beer_minimum = 0;
		}
		
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


