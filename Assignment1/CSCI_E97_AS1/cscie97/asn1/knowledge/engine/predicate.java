package cscie97.asn1.knowledge.engine;
import java.io.*;
import java.util.*;


/**
 * The predicate class has two member variables - Identifier and createDate.
 * Each predicate has its unique identifier (Identifier) and time created (createDate) 
 * which unique identifies each node object
 */
public class predicate {
    private String identifier;
    private long createDate;
    
    
    /**
     * Default constructor - set createDate value is set when object is created
     */
    public predicate(){
        createDate = (System.currentTimeMillis() / 1000L);
    }
    
    /**
     * Constructor 2 - set identifier and createDate
     *  
     * @param s - is used to initialize the value of Identifier member variable
     */
    public predicate(String s){
        identifier = s;
        createDate = (System.currentTimeMillis() / 1000L);}
    
    

    /**
     * setIdentifier - changes the value of the node Identifier with String s
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
    }
    
    /**
     * getCreatedate - returns the value of createDate member variable
     * @return - createDate
     */
    public long getCreatedate(){
        return createDate;
    }

}
