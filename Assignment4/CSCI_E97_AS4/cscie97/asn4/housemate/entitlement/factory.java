package cscie97.asn4.housemate.entitlement;



/**factory Class - This is the abstract class/foundation for the entitler_factory
 * 
 */
public abstract class factory {

	public abstract void createUser(String new_name, String ID);
	public abstract void createResource(String new_name, String known_role_ID, String new_resource);
	public abstract void createRole(String ID, String new_name, String descript);
	public abstract void createPerm(String ID, String new_name, String descript);
}
