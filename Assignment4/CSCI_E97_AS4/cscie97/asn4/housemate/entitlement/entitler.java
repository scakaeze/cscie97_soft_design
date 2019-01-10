package cscie97.asn4.housemate.entitlement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * entitler Class - manages all entitlement and security issues in the House Mate Service
 */
public class entitler {
	
	public Map<String,user> allUsersMap = new HashMap<>(); // stores all created users
	public Map<String,resource> allResourceMap = new HashMap<>(); //stores all created resources
	public Map<String,role> allRoleMap = new HashMap<>(); //stores all created roles
	public Map<String,permission> allPermMap = new HashMap<>();//stores all created permissions
	public Map<Integer,token> allTokenMap = new HashMap<>();//stores all created tokens
	
	private static entitler bouncer = new entitler(); //entitler Singleton design. Check Singleton design requirement
	private entitler(){};
	public static entitler getInstance(){
		return bouncer;
	}
	
	Random rand_int_gen = new Random(); // random integer generator
	public factory zeus = new entitler_factory(); // this is the entitlement domain class creator .Check Abstract Factory design requirement
	public int current_token = rand_int_gen.nextInt(1000000000) + 10000; // contains current System admin token. Initializes to random number
	public boolean admin_token = true; // variable which can disable the central admin funtions. No implementation
	
	
	
	

	/**
	 * adminTokgen() - accepts a visitor argument and creates an administrative token( tokens used to keep House Mate Service system online)
	 * 
	 * @param query
	 */
	public int adminTokGen(visitor query){
		
		token nextToken  = new token();
		
		nextToken.resource = query.user_house;
		nextToken.user = query.user_check;
		nextToken.unique_ID = rand_int_gen.nextInt(1000000000) + 10000;
		nextToken.state = true;
		nextToken.user_permissions = query.getPermissions(nextToken.user, nextToken.resource);
		allTokenMap.put(nextToken.unique_ID, nextToken);
		current_token = nextToken.unique_ID;
	
	return nextToken.unique_ID;
	}
	
	
	
	/**
	 * voiceTokgen() - accepts a visitor argument and creates an voice token needed to complete an AVA request from any occupant
	 * 
	 * @param query
	 */
	public int voiceTokGen(visitor query){
		
		token nextToken  = new token();
		
		nextToken.resource = query.user_house;
		nextToken.user = query.user_check;
		nextToken.unique_ID = rand_int_gen.nextInt(1000000000) + 10000;
		nextToken.state = true;
		
		nextToken.user_permissions = query.getPermissions(nextToken.user, nextToken.resource);
	
		allTokenMap.put(nextToken.unique_ID, nextToken);
	
	return nextToken.unique_ID;
	}
	
	
	
	/**
	 * voiceFind() - accepts a voice print and returns the associated user. else return unknown applicant
	 * 
	 * @param voicePrint_ID
	 */
	public String voiceFind(String voicePrint_ID){
		
		for (user f: allUsersMap.values()){
			if (f.voicePrint.equals(voicePrint_ID)){
				return f.unique_ID;
			}
		}
		return "Unknown_occupant";
	}
	
	
	
	/**
	 * login() - accepts a username and password from a House Mate Service admin login
	 * 
	 * @param username
	 * @param password
	 * @param house
	 */
	public boolean login(String username, String password, String house){
		
		if (username.equals("admin") && password.equals("password")){
			current_token = 32;
			System.out.println("Login bot: Central Admin's login was Successful");
			System.out.println();
			return true;
		}
		
		for (user f : allUsersMap.values()){
			 if (f.login_name.equals(username) &&  f.login_password == password.hashCode()){
				 visitor bol = new visitor();
				 bol.user_check = f.unique_ID;
			     bol.user_house = house; 
			     adminTokGen(bol);
			     System.out.println("Login Bot: User " + f.unique_ID + "'s Login was Successful");
			     System.out.println();
			     return true;
			 }
		 }
		System.out.println("AccessDeniedException: Please enter accurate Admin Username and matching Password");
	     return false;
	}
	
	
	/**
	 * login() - sets the current admin login token state to "invalid"
	 * 
	 */
	public void logout(){
		int token  = current_token;
		
		if ( current_token == 32 ){
			current_token = rand_int_gen.nextInt(1000000000) + 10000;	
			System.out.println("Logout Bot: Logging out Central User");
			System.out.println();
			return;
		}
		
		if (allTokenMap.containsKey(token)){
			allTokenMap.get(token).state = false;
			System.out.println("Logout Bot: Logging out last User: " + allTokenMap.get(token).user);
			System.out.println();
			return;
		}
	}
	
	
	
	/**
	 * checkAdminToken() - sets the current admin login token state to "invalid"
	 * 
	 */
	public boolean checkAdminToken(){
		
		int token = entitler.getInstance().current_token;
		
		if (entitler.getInstance().current_token == 32 && entitler.getInstance().admin_token){
			return true;
			}
		
		if (entitler.getInstance().allTokenMap.containsKey(token)){
			
			if (entitler.getInstance().allTokenMap.get(token).user_permissions.contains("user_admin") && entitler.getInstance().allTokenMap.get(token).state){
				return true;
			}
			else{
				return false;	
			}
		}
		
		System.out.println("AcessDeniedException:Current token does not have admin privileges");
		return false;
	}
	
}


