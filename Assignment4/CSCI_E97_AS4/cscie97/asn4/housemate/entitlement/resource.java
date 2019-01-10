package cscie97.asn4.housemate.entitlement;

import java.util.HashMap;
import java.util.Map;

import cscie97.asn4.housemate.model.ModelService;

public class resource {

	public String name;
	public String role; // unique ID of role attached to each resource instance
	public String resource_house; // house for which this resource was created for
	public Map<String,role> role_Map = new HashMap<>();
	
	public resource(String new_name, String known_role_ID, String new_resource){
		
		name = new_name;
		role = known_role_ID;
		resource_house = new_resource;
		
		if (ModelService.getInstance().houseExists(resource_house)){ // if house corresponding to "new_resource" actually exists
		
			if (entitler.getInstance().allRoleMap.containsKey(role)){ // if role "known_role_ID" actually exists
				role_Map.put(resource_house, entitler.getInstance().allRoleMap.get(role)); // add "known_role_ID" to the new resource
			}
			
			else {
				System.out.println("AuthenticationException: The role "+ role +" does not exist");
			}
		}
		else {
			System.out.println("AuthenticationException: The resource "+ resource_house +" does not exist");
		}
		
	}
	
}
