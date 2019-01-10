/**
 * 
 */
package cscie97.asn4.housemate.entitlement;

/**privilege Class - This is the abstract class/foundation for the permission and role composite relationship
 * 
 */
public abstract class  privilege {
	
	public String unique_ID;
	public String name;
	public String description;	
	
	public privilege(String ID, String new_name, String descript){
		unique_ID = ID;
		name = new_name;
		description = descript;		
	}
}

