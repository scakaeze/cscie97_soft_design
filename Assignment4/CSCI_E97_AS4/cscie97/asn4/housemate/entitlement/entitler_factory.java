package cscie97.asn4.housemate.entitlement;


public class entitler_factory extends factory{

	
	
	/**
	 * createUser() - create new user and store user in allUsersMap in entitler singleton class
	 *
	 */
	@Override
	public void createUser(String new_name, String ID) {
		
		user person = new user (new_name, ID);	
		entitler.getInstance().allUsersMap.put( ID, person);		
	}

	
	/**
	 * createResource() - create new resource and store resource in allResourceMap in entitler singleton class
	 *
	 */
	@Override
	public void createResource(String new_name, String known_role_ID, String new_resource) {
	
		resource perResource = new resource (new_name, known_role_ID, new_resource);
		entitler.getInstance().allResourceMap.put(new_name, perResource);	
	}


	
	
	/**
	 * createRole() - create new role and store role in allRoleMap in entitler singleton class
	 *
	 */
	@Override
	public void createRole(String ID, String new_name, String descript) {
		
		role position = new role (ID, new_name, descript);
		entitler.getInstance().allRoleMap.put(ID, position);	
	}

	
	
	/**
	 * createPerm() - create new permission and store permission in allRoleMap in entitler singleton class
	 *
	 */
	@Override
	public void createPerm(String ID, String new_name, String descript) {
		
		permission right = new permission(ID,  new_name, descript);
		entitler.getInstance().allPermMap.put(ID, right);		
	}

}
