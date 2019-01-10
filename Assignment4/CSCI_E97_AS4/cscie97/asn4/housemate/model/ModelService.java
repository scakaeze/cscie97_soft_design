package cscie97.asn4.housemate.model;



import java.util.*;
import cscie97.asn4.housemate.entitlement.entitler;



public class ModelService {

	private static ModelService current = new ModelService();              // Object independent singleton class
	 
	public Map<String,house> houseMap = new HashMap<>();         // Collection of mapped houses
	public Map<String,room> roomMap = new HashMap<>();           // Collection of mapped rooms
	public Map<String,occupant> occupantMap = new HashMap<>();   // Collection of mapped occupants
	public Map<String,sensor> sensorMap = new HashMap<>();       // Collection of mapped sensors
	public Map<String,appliance> applianceMap = new HashMap<>(); // Collection of mapped appliance
	public Map<String,String> entryMap = new HashMap<>();        // Collection of mapped entries
	public Map<String,Set<String>> queryMap = new HashMap<>();   // Collection of mapped queries in knowledge map  
	
	
	// Private constructor for the Clients class for Singleton design
	private ModelService(){}
	
	// Single instance return function for the Clients Class
	public static ModelService getInstance(){
		return current;
	}
	
	
	/**
     * addHouse - adds new house object to houseMap
     * @param name
     * @param addr
     */
	public void addHouse(String name, String addr ){	
		ModelService.getInstance().houseMap.put(name, new house(name, addr));
	}
	
	
	/**
     * addRoom - adds new room object to roomMap
     * @param n   - name
     * @param h   - house
     * @param f   - floor
     * @param t   - type
     * @param w   - windows
     */
	public void addRoom(String h, String n, String f, String t, int w){
		ModelService.getInstance().roomMap.put(n, new room(h , n, f, t, w));
	}
	
	
	/**
     * addOccupant - adds new occupant object to occupantMap
     * @param n   - name     
     * @param t   - type
     */
	public void addOccupant(String n, String t){
		ModelService.getInstance().occupantMap.put(n, new occupant(n , t));
	}
	
	
	/**
     * addSensor - adds new sensor object to sensorMap
     * @param n   - name     
     * @param t   - type
     * @param r   - room
     * @param h   - house
     */
	public void addSensor(String n, String t, String r, String h){
		ModelService.getInstance().sensorMap.put(n, new sensor(n, t, r, h ));
	}
	

	/**
     * addAppliance - adds new appliance object to applianceMap
     * @param n   - name     
     * @param t   - type
     * @param r   - room
     * @param h   - house
     */
	public void addAppliance(String n, String t, String r, String h){
		ModelService.getInstance().applianceMap.put(n, new appliance(n, t, r, h ));
	}
	
	
	/**
     * houseExists - Checks if house already exists in houseMap
     * @param n   - name     
     * @return true/false
     */
	public boolean houseExists(String name){
		if (houseMap.containsKey(name))
		{
			return true;
		}
		else{return false;}
	}
	
	

	/**
     * roomExists - Checks if room already exists in roomMap
     * @param n   - name     
     * @param h   - house
     * @return true/false
     */
	public boolean roomExists(String name, String h){
		
		if (roomMap.containsKey(name) && roomMap.get(name).getHouse().equals(h))
		{
			return true;
		}
		
		else{
			return false;
			}
	}
	
	
	/**
     * occupantExists - Checks if occupant already exists in occupantMap
     * @param n   - name     
     * @return true/false
     */
	public boolean occupantExists(String name){
		
		if (occupantMap.containsKey(name))
		{
			return true;
		}
		
		else{
			return false;
			}
	}
	
	
	/**
     * sensorExists - Checks if sensor already exists in sensorMap
     * @param n   - name     
     * @param h   - house
     * @return true/false
     */
	public boolean sensorExists(String n, String h ){
		
		if (sensorMap.containsKey(n) && sensorMap.get(n).getHouse().equals(h))
		{
			return true;
		}
		
		else{
			return false;
			}
	}
	
	
	/**
     * applianceExists - Checks if appliance already exists in applianceMap
     * @param n   - name     
     * @param h   - house
     * @return true/false
     */
	public boolean applianceExists(String n, String h ){
		
		if (applianceMap.containsKey(n) && applianceMap.get(n).getHouse().equals(h))
		{
			return true;
		}
		
		else{
			return false;
			}
	}
	
	
	/**
     * relo_occupant - Checks if occupant exists in occupantMap and relocates the existing occupant to a house
     * @param n   - name     
     * @param h   - house
     * @return true/false
     */
	public boolean relo_occupant(String n, String h ){
		
		if (  ModelService.getInstance().occupantMap.containsKey(n) && ModelService.getInstance().houseMap.containsKey(h)){
			ModelService.getInstance().occupantMap.get(n).setHouse(h);
			return true;	
		}
		
		else{
			return false;
			}
	}
	
	
	/**
     * newEntry - This updates the queryMap with a new entry
     * 
     *  		String original is the original String text which generated the query comprised of Strings s, p and o.
     * 			s, p or o might be "?". This enables a future queryMap entry to return the original string. 
     * 
     * @param n   - name     
     * @param h   - house
     * @return true/false
     */
	
	public void newEntry(String s, String p, String o, String original){
		String querykey = s + " " + p + " " + o;
		
		if (  ModelService.getInstance().queryMap.containsKey(querykey)  ){    //checks if queryMap key  s+p+o exists
			ModelService.getInstance().queryMap.get(querykey).add(original);   // if it does, it adds original to its mapped set of Strings
		}
		
		else{
			Set<String> input = new HashSet<>();
			input.add(original);
			ModelService.getInstance().queryMap.put(querykey, input); // If queryMap SET object does not exist, it creates a new mapping
		}	
    }
	
	
	
	/**
     * importData - updates entryMap with key "s+p+?" mapped to String "s+p+o". 
     *            - This makes it easy to change with "oven1 temperature HOT" to "oven1 temperature COLD"
     *            
     *            - It also updates queryMap with all possible "?" and s, p and o combinations.
     *            - This enables all future queries
     * @param s - subject    
     * @param p - predicate
     * @param o - object
     */
	public void importData(String s, String p, String o){
		
		String query = "?";
		String bond = s + " " + p + " " + query;
		String temp = s + " " + p + " " + o;
		entryMap.put(bond,temp);		      
		
		newEntry(s, p, query, temp);              //S P ?
		newEntry(s, query, o, temp);              //S ? O
		newEntry(s, query, query, temp);          //S ? ?
		newEntry(query, p, o, temp);              //? P O
		newEntry(query, query, query, temp);      //? ? ?
		newEntry(query, p, query, temp);          //? P ?
		newEntry(query, query, o, temp);          //? ? O      
    } 
	
	
	/**
     * deleteEntry - This deletes a existing queryMap entry 
     * 			   - It deletes "original" from the SET mapped by s+p+o
     *
     * @param s - subject    
     * @param p - predicate
     * @param o - object
     * @param original
     */
	public void deleteEntry(String s, String p, String o, String original){
		String temp = s + " " + p + " " + o;
		queryMap.get(temp).remove(original);
		
		if (queryMap.get(temp).isEmpty())      // if an entryMap SET is empty after original is deleted, the mapped SET is also removed from queryMap
			queryMap.remove(temp);	
	}
	
	
	
	/**
     * deleteData - This deletes all existing queryMap entries of any String s+p+o
     * 
     * @param s - subject    
     * @param p - predicate
     * @param o - object
     */
	public void deleteData(String s, String p, String o){
		
		String original = s + " " + p + " " + o;
		String query = "?";
				
		
		deleteEntry(s, p, query, original);              //S P ?
		deleteEntry(s, query, o, original);              //S ? O
		deleteEntry(s, query, query, original);          //S ? ?
		deleteEntry(query, p, o, original);              //? P O
		deleteEntry(query, query, query, original);      //? ? ?
		deleteEntry(query, p, query, original);          //? P ?
		deleteEntry(query, query, o, original);          //? ? O     
    } 
	
	

	/**
     * updateData - This updates queryMap with new s+p+o values
     *            - For example, "smoke_detector1 power ON" is updated with "smoke_detector1 power OFF"
     * 
     * @param s - subject    
     * @param p - predicate
     * @param o - object
     */
	public void updateData(String s, String p, String o){
		
		String query = "?";
		String bond = s + " " + p + " " + query;
		String temp;
		
		if (entryMap.containsKey(bond)){  //checks if any previous entry matching s+p+? has been entered into the knowledge graph
			temp = entryMap.get(bond);      // if true, it returns the previous entry to "temp"
			entryMap.remove(bond);          // then it deletes the previous entry from entryMap
			
			String splitLine[] = (temp).split(" ", 3);
			deleteData(splitLine[0], splitLine[1], splitLine[2]); // It deletes all queries generated the previous entry from queryMap 
			
			importData(s, p, o);		                          // and updates queryMap with s+p+o (new entry)
		}
		
		else{
			importData(s, p, o);                               // if false. It creates a new queryMap entry
		}
	}
	
	
	/**
     * secureUpdateData - verifies that "token" has permission required to modify "subject"
     *            - if verified, it calls updateDat(s,p,o) 
     * 
     * @param s - subject    
     * @param p - predicate
     * @param o - object
     * @param token - integer
     */
	public boolean secureUpdateData(String s, String p, String o, int token){
		
		String obtain_type = applianceMap.get(s).getType();
		String expected_permission = "control_" + obtain_type;
		if (entitler.getInstance().allTokenMap.get(token).user_permissions.contains(expected_permission)){
			updateData( s, p, o);
			return true;
		}	
		
		return false;
	}
	
	
}
