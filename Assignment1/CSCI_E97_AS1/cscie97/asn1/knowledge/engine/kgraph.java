package cscie97.asn1.knowledge.engine;


import java.io.*;
import java.util.*;



/**
 * The kgraph class (knowledge graph) is an in-memory graph which stores well formed triples and helps retrieve triple queries as well 
 *  Kgraph has the following associations
 *  - Node map - this which maps node identifiers (map keys) to their respective nodes(map objects)
 *  - Predicate Map - this which maps predicate identifiers (map keys) to their respective predicates(map objects)
 *  - Triple Map - this maps well formed triple identifiers (map keys) to their respective triples(map objects)
 *  - Query Map - this maps well formed query triple identifiers (map keys) to their a String concatenation of their origin triple identifiers(map objects)
 *  - It creates a private static member of itself called instance
 */
public class kgraph {
	
    
    private Map<String,node> nodeMap = new HashMap<>();
    private Map<String,predicate> predicateMap = new HashMap<>();
    private Map<String,triple> tripleMap = new HashMap<>();
    private Map<String,String> queryMapset = new HashMap<>();
    private static kgraph instance = new kgraph();
    
    

	/**
	 * It prevents new members of Kgraph from being created
	 */
    private kgraph(){
    }
    
    
    /**
	 * It provides public access to the object independent kgraph "instance"
	 */
    public static kgraph getInstance(){
        return instance;
    }
    
    
    
    /**getNode returns a node instance for the given node identifier.
   	 * If the node does not exist, it creates the new node and adds it to the nodeMap
   	 * 
   	 * @param nodeIdentifier
   	 * @return nodeIdentifier's node
   	 */
    public node getNode(String nodeIdentifier){
        
        if (nodeMap.containsKey(nodeIdentifier)){
            return nodeMap.get(nodeIdentifier);
        }   
        else{
            nodeMap.put(nodeIdentifier, new node(nodeIdentifier));
            return null;
        }
    };
    
  
    
    /**getPredicate returns a predicate instance for the given predicate identifier.
   	 * If the predicate does not exist, it creates the new predicate and adds it to the nodeMap
   	 * 
   	 * @param predIdentifier
   	 * @return predIdentifier' predicate
   	 */
    public predicate getPredicate(String predIdentifier){
        
        if (predicateMap.containsKey(predIdentifier)){
            return predicateMap.get(predIdentifier);
        }   
        else{
            predicateMap.put(predIdentifier, new predicate(predIdentifier));
            return null;
        }
    };
    
    
    
    
    
    /**
   	 * getTriple returns a triple instance for the given triple nodes and predicate.
   	 * If the triple does not exist, it creates the new triple and adds it to the tripleMap
   	 * 
   	 * @param s
   	 * @param p
   	 * @param o
   	 * @return triple
   	 */
    
    public triple getTriple(node s, predicate p, node o){
        triple testTriple = new triple(s, p, o);
        
        if (tripleMap.containsKey(testTriple.getIdentifier())){
            return tripleMap.get(testTriple.getIdentifier()) ;
        }
        
        else{
            tripleMap.put(testTriple.getIdentifier(), testTriple);
            return null;
        }
};
    
  


	/**getQuery checks if the query input elements point to an existing query in queryMapset 
	 * if it exists
	 * - it updates the mapped queryMapSet with the new matching origin triple by concatenating the triple identifier to other existing triple identifiers
	 * that point to the same query
	 * 
	 * if it does not exist
	 * - It creates a new Query Entry and adds its value as that of its origin triple identifier
	 * 
	 * @param s
	 * @param p
	 * @param o
	 * @param originalTriple
	 */
    public void getQuery(node s, predicate p, node o, triple originalTriple){
        triple bell = new triple(s, p, o);
        String tempValue;
        
        if (queryMapset.containsKey(bell.getIdentifier())){
            
            
            tempValue = queryMapset.get(bell.getIdentifier()); // if queryMapset triple already exists, store its value in tempValue
            queryMapset.remove(bell.getIdentifier());// delete the map entry
            tempValue = tempValue + " " + originalTriple.getIdentifier(); // concatenate the original triple's identifier to tempValue
            queryMapset.put(bell.getIdentifier(), tempValue); // create new knowMapset entry with queryMap identifier key and tempValue value   
        }
        
        else{
        	queryMapset.put(bell.getIdentifier(), originalTriple.getIdentifier());// simple entering a new queryMapset entry
        
        }
    }
    
    


/**importTriple accepts a triple subject(s), predicate(p) and object(o)
 * - it updates the node map, predicate map, triple map and query map(embedded with knowMapset update). 
 * i.e query triples are a permutation of all possible combinations between a subject-predicate-node set with "?"
 * 
 * @param s
 * @param p
 * @param o
 */ 
    public void importTriple(node s, predicate p, node o){
        node symbolo = new node("?");
        predicate symbolp = new predicate("?");
        
        triple tempTriple = new triple (s, p, o);
        
        getTriple(s, p, o); // update tripleMap
        getNode(s.getIdentier()); // update nodeMap with node s
        getPredicate(p.getIdentier());// update predicateMap with predicate p
        getNode(o.getIdentier());// update nodeMap with node o
            
         
        // below are all query triples and their queryMapset entries
        getQuery(s, p, symbolo, tempTriple);            //S P ?
        getQuery(s, symbolp, o, tempTriple);            //S ? O
        getQuery(s, symbolp, symbolo, tempTriple);      //S ? ?
        getQuery(symbolo, p, o, tempTriple);            //? P O
        getQuery(symbolo, symbolp, symbolo, tempTriple);//? ? ?
        getQuery(symbolo, p, symbolo, tempTriple);      //? P ?
        getQuery(symbolo, symbolp, o, tempTriple);      //? ? O
        
    } 
    
    
   

/**It accepts well formed querySet elements' subject, predicate and object
 * it prints all triples that match the querySet element(s) entry
 * 
 * @param subject
 * @param predicate
 * @param object
 */ 
    public void executeQuery(String subject, String predicate, String object){
    
        
        String temp = subject + predicate + object;
        
        String parts[] = (queryMapset.get(temp)).split(" ", tripleMap.size());
        
        
        for (int x = 0; x < parts.length; x++)
        {
            System.out.print((tripleMap.get(parts[x])).getSubIdent() );
            System.out.print( " " + (tripleMap.get(parts[x])).getPredIdent());
            System.out.print( " " + (tripleMap.get(parts[x])).getObjIdent()+ ".");
            System.out.println();
        }   
    } 
    
    
    
    
    /**it returns true if nodeMap contains a particular node. returns false otherwise
     * 
     * @param bell
     */
    public boolean containsNode(String bell){
    
      if (nodeMap.containsKey(bell))  {
          return true;
      }
      else {
          return false;
      }
    }
    
    
    /**it returns true if predicateMap contains a particular predicate. returns false otherwise
     * 
     * @param bell
     */
    public boolean containsPred(String bell){
    
      if (predicateMap.containsKey(bell))  {
          return true;
      }
      else {
          return false;
      }
    }
    
    
    /**it returns true if tripleMap contains a particular triple. returns false otherwise
     * 
     * @param bell
     */
    public boolean containsTriple(String bell){
    
      if (tripleMap.containsKey(bell))  {
          return true;
      }
      else {
          return false;
      }
    }
    
    /**it returns true if queryMapSet contains a particular triple. returns false otherwise
     * 
     * @param bell
     */
    public boolean containsQuery(String bell){
    
      if (queryMapset.containsKey(bell))  {
          return true;
      }
      else {
          return false;
      }
    }
    
    
    
    
}
         
