package cscie97.asn1.knowledge.engine;


import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


public class importer {
    
	
	
	/**
	 * The ImportTripleFile method 
	 *  - opens a file of name
	 *  - checks if file exists (Throws Exception if file cannot be located)
	 *  - reads a line
	 *  - trims the line
	 *  - If line contains less than 3 characters, it means it is an empty line with
	 *  - Breaks the line into 3 strings (subject, predicate and object) using space delimiting
	 *  - it creates new subject node, predicate pred and object node with the three corresponding strings
	 *  - These Strings are then sent to the knowledge graph importTriple method to update the knowledge graph
	 *  @param filename  contains the input file name
	 */
    public void importTripleFile(String filename) throws IOException {
        
       File input = new File(filename);
       String line;
       
       
       //The import exception implementation
       if (!input.exists()){
           System.out.println(" ImportException: Import file does not exist");
           System.exit(0);
       }
       
       
       Scanner read = new Scanner(input); // new scanner object to access file contents
       read.useDelimiter(Pattern.quote("."));// period is set as triple list delimiters
       
        
       while (read.hasNext()){
       
    	   line = read.next().trim();
    	   line = line.replace("\n", "");
    
        
    	   if (line.length() < 1){
    		   continue;
    	   }
        
    	   String splitLine[] = (line).split(" ", 3); //line is split to three strings
        
        
    	   node s = new node(splitLine[0].replaceAll("\\s+",""));          // new subject node is created as all white spaces are removed
    	   predicate p = new predicate(splitLine[1].replaceAll("\\s+",""));//new pred predicate is created as all white spaces are removed
    	   node o = new node(splitLine[2].replaceAll("\\s+",""));          // new object node is created as all white spaces are removed
        
    	   (kgraph.getInstance()).importTriple(s, p, o);
        
      }
       
       read.close(); // File is closed after knowledge map is fully updated
        
    }
    
   
}
