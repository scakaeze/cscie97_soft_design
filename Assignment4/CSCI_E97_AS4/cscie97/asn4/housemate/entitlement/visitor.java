package cscie97.asn4.housemate.entitlement;

import java.util.HashSet;
import java.util.Set;

public class visitor {
	
	public String user_check;
	public String user_house;
	
	
	public Set<String> getPermissions(String user_ID, String house){
		
		Set<String> entitlements = new HashSet<>();	
		if (entitler.getInstance().allUsersMap.containsKey(user_ID)){
			for (resource f : entitler.getInstance().allUsersMap.get(user_ID).resource_Map.values()){
				
				if (f.resource_house.equals(house)){
        			for (role g : f.role_Map.values()){
        			
        				for (permission h : g.perm_Map.values()){
        					entitlements.add(h.unique_ID);
        					//System.out.println(h.unique_ID);/////////////////////////////////////////////////
        				}
        				//System.out.println("all permissions added to token"); /////////////////////////////////////////////////
        			}
        			break;
				}
			}
		}		
		return entitlements;
	}	
}














