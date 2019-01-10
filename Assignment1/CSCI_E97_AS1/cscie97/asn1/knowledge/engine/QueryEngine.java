package cscie97.asn1.knowledge.engine;

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

/**
 *
 * @author Damien
 */
public class QueryEngine {
    
    
   
    /** QueryFile 
     * - accepts a filename 
     * - checks if file exists
     * - reads each line delimited by "."
     * - trims each line 
     * - and sends each line executeQuery function
     * 
     *  @param file
	 */
    public void QueryFile(String file) throws IOException {
    
        File input = new File(file);
        String queryLine;
       
       
       if (!input.exists()){
           System.out.println("QueryEngineException: Query File does not exist");
           System.exit(0);
       }
       
       Scanner read = new Scanner(input);
       
       read.useDelimiter(Pattern.quote("."));
       
       while (read.hasNext()){
       
        
    	   queryLine = read.next().trim();
    	   queryLine = (queryLine.replace("\n", ""));
    	   
    	   if (queryLine.length()< 1){
               continue; 
            }
        
    	   executeQuery(queryLine);
    	   System.out.println();
       }
       read.close();
    
    }
    
    
    
    
    /** executeQuery
	 * - gets a string line parameter
	 * - trims the line
	 * - breaks line into 3 strings using " " delimiting
	 * - Checks for any bad subject, predicate, object, sentence arrangement, incomplete line, returns queries without "?"
	 * - sends well formed query elements to executeQuery in kgraph executeQuery method
     * 
     *  @param queryLine
	 */
    public void executeQuery(String queryLine){
        
        String querySymbol = "?";
        
        String queryElements[] = (queryLine.trim()).split(" ", 3);
        
        
        
         if (queryLine.length()< 1){
           System.out.println("QueryEngineException: Null triple "); 
           return; 
        }
         
               
        if ((queryElements.length < 3)){
           System.out.println("QueryEngineException: (" + queryLine +") is an incomplete query set"); 
           return; 
        }
        
       
        System.out.println(queryLine + ".");
        queryElements[0] = queryElements[0].replace(" ", "");
        queryElements[1] = queryElements[1].replace(" ", "");
        queryElements[2] = queryElements[2].replace(" ", "");
        
        
        //returns triple that already exists in tripleMap
        if ((kgraph.getInstance()).containsTriple(queryElements[0] + queryElements[1] + queryElements[2])){ 
            System.out.println(queryElements[0] +" "+queryElements[1] + " " + queryElements[2] + ".");  
            return;
        }  
        
        // invalid subject node
        if (  !(kgraph.getInstance().containsNode(queryElements[0])) && !(queryElements[0].equals(querySymbol))  ){ 
        
            System.out.println("QueryEngineException: (" + queryLine +") is not a well formed query set.");
            System.out.println(" Clue - Check subject");
            
            return;
        }
        
        // invalid predicate predicate
        if (  !(kgraph.getInstance().containsPred(queryElements[1])) && !(queryElements[1].equals(querySymbol))  ){ 
        
            System.out.println("QueryEngineException: (" + queryLine +") is not a well formed query set");
            System.out.println(" Clue - Check predicate");
            return;
        }
        
        // invalid object node or more than 3 strings
        if (  !(kgraph.getInstance().containsNode(queryElements[2])) && !(queryElements[2].equals(querySymbol))  ){ 
        
            System.out.println("QueryEngineException: (" + queryLine +") is not a well formed query set.");
            System.out.println("Clue - check object or line more than three String elements");
            return;
        }
        
        //valid query elements but not well formed
        if (!(kgraph.getInstance().containsQuery(queryElements[0] + queryElements[1] + queryElements[2]))){
            System.out.println("QueryEngineException: (" + queryLine +") is not a well formed query set.");
            System.out.println("Clue - subject, predicate or node arrangement");
            return;
        }
       
        
        (kgraph.getInstance()).executeQuery(queryElements[0], queryElements[1], queryElements[2]);
    }
    
}
