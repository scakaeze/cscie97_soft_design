package cscie97.asn4.housemate.entitlement;

import java.util.HashMap;
import java.util.Map;


/**
 * user Class - contains entitlement, login, logout, voice print and allowed permission properties per existing user
 *
 */
public class user {
	
	
	public String name;
	public String unique_ID;
	public String login_name;
	public int login_password;
	public String voicePrint;
	
	public Map<String,resource> resource_Map = new HashMap<>();
	
	public user( String new_name, String ID){
		unique_ID = ID;
		name  = new_name;
		
	}
	
}
