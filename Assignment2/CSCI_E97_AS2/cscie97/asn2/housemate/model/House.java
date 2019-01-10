package cscie97.asn2.housemate.model;

public class House {

	private String name;
	private String address;
	private String auth_token;
	
	//House constructor
	public House(String n,String addr){
		name = n;
		address = addr;
	}
	
	/**
     * getName - returns the name of the any House Object
     * @return name
     */
	public String getName(){
		return name;
	}
	
	/**
     * getAddress - returns the name of the any House Address
     * @return address
     */
	public String getAddress(){
		return address;
	}
}
