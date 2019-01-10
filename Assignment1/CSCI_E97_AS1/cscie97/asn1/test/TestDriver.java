package cscie97.asn1.test;

import java.io.*;

import cscie97.asn1.knowledge.engine.QueryEngine;
import cscie97.asn1.knowledge.engine.importer;

public class TestDriver {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        
        importer file = new importer ();
        file.importTripleFile(args[0]);
        
        QueryEngine gol = new QueryEngine();
        gol.QueryFile(args[1]);
    	
        System.gc();
    	
    }
    
	}


