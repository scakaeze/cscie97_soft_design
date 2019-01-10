package cscie97.asn1.knowledge.engine;
import java.io.*;
import java.util.*;



/**
 * The node class has two member variables - Identifier and createDate.
 * Each node has its unique identifier (Identifier) and time created (createDate) 
 * which unique identifies each node object
 */
public class node {

	 
    private String identifier; // constains a unique identifier string
    private long createDate; //contains the triple object Unix time stamp
    
    
    /**
     * Default constructor - set createDate value is set when object is created
     */
    public node(){
        createDate = (System.currentTimeMillis() / 1000L);
    }
    
    
    
    /**
     * Constructor 2 - set identifier and createDate
     *  
     * @param s - is used to initialize the value of Identifier member variable
     */
    public node(String s){
        identifier = s;
        createDate = (System.currentTimeMillis() / 1000L);}
    
    
    /**
     * setIdentifier  changes the value of the node Identifier with String s
     *  
     * @param s - is used to change the value of Identifier member variable
     */
    public void setIdentifier(String s){
        identifier = s;
    }
    
    
    /**
     * getIdentifier - returns the value of identifier member variable
     * @return identifier 
     */
    public String getIdentier(){
        return identifier;
    };
    
    
    /**
     * getCreatedate - returns the value of createDate member variable
     * @return - createDate
     */
    public long getCreatedate(){
        return createDate;
    }
   
}
