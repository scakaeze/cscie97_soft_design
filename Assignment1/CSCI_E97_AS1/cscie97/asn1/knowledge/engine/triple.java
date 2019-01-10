package cscie97.asn1.knowledge.engine;
import java.io.*;
import java.util.*;



/**
 * The Triple class creates objects(triples), which represent a sentence of three words.
 * The first and third words are nodes. 
 * The second word is a predicate.
 * Each triple has an Identifier string that contains the concatenation of the 
 * subject predicate and object identifiers.
 * Each triple has a createDate variable 
 */
public class triple {
    
    
    private node subject = new node(); // memory is assigned to new node - subject 
    private predicate pred = new predicate();//memory is assigned to new predicate - pred
    private node object = new node();// memory is assigned to new node - object 
    
    private String identifier;// contains the triple object identifier
    private long createDate;// contains the triple object Unix time stamp
    
    /**
     * This is the default triple constructor. 
     * It initialized the triple object with createDate variable initialized
     */
    public triple(){
        createDate = (System.currentTimeMillis() / 1000L);
    }
    
    
    /**
     * This is the second constructor. 
     * It initialized the triple object members.
     * @param s - initialize subject 
     * @param p - initialize predicate
     * @param o - initialize object
     * Identifier and createDate are also created during object initialization
     */
    public triple(String s, String p, String o){
       subject.setIdentifier(s);
       pred.setIdentifier(p);
       object.setIdentifier(o);
       
       identifier = subject.getIdentier() + pred.getIdentier() + object.getIdentier(); 
       createDate = (System.currentTimeMillis() / 1000L);  
    }
    
    
    /**
     * This is the third constructor. 
     * It initialized the triple object members.
     * @param s - initialize subject 
     * @param p - initialize predicate
     * @param o - initialize object
     * Identifier and createDate are also created during object initialization
     */
    public triple(node s, predicate p, node o){
        subject.setIdentifier(s.getIdentier());
        pred.setIdentifier(p.getIdentier());
        object.setIdentifier(o.getIdentier());
        
        identifier = subject.getIdentier() + pred.getIdentier() + object.getIdentier(); 
        createDate = (System.currentTimeMillis() / 1000L);  
    }
    
    
    
    /**
     * getIdentifier returns the triple's identifier value
     * @return identifier
     */
    public String getIdentifier(){
        return identifier;
    
    }
    

    /**
     * getCreatedate returns the triple's createDate value
     * @return createDate
     */
    public long getCreatedate(){
        return createDate;
    }

    
    
    /**
     * getSubIdent returns the triple's subject identifier
     * @return subject.getIdentier()
     */
    public String getSubIdent(){
        return subject.getIdentier();
    }
    
    
    
    
    /**
     * getPredIden returns the triple's predicate identifier
     * @return pred.getIdentier()
     */
    public String getPredIdent(){
        return pred.getIdentier();
    }
    
    
    /**
     * getObjIdent returns the triple's object identifier
     * @return pred.getIdentier()
     */
    public String getObjIdent(){
        return object.getIdentier();
    }
    
}