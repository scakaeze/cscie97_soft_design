package cscie97.asn4.housemate.entitlement;

import java.util.HashMap;
import java.util.Map;


/**
 * role Class - extends privilege abstract class and includes Hashmap for storing roles
 *
 */
public class role extends privilege{
	
	public Map<String,permission> perm_Map = new HashMap<>();
	
	public role(String ID, String new_name, String descript){
		super(ID, new_name, descript);	
	}
}
