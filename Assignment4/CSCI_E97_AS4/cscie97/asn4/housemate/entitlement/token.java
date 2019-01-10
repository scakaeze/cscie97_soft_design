package cscie97.asn4.housemate.entitlement;

import java.util.HashSet;
import java.util.Set;


/**
 * token Class - contains properties that define every House Mate token
 *
 */
public class token {
	
	public String user;
	public int unique_ID;
	public String resource; //typically corresponding to house where the token is valid
	public boolean state; // indicates if token is valid(true) or invalid(false)
	public Set<String> user_permissions = new HashSet<>(); // contains all permission unique IDs entitled to "user"
	public int token_life_time = 0;
	
}
