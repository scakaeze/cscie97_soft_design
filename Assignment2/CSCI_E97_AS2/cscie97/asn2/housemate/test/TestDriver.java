package cscie97.asn2.housemate.test;

import cscie97.asn2.housemate.model.CMDAPI;

public class TestDriver {

	public static void main(String[] args) throws Exception {
        
        
	       CMDAPI file = new CMDAPI();
	       
	       	
	       	try {
				file.importFile(args[0]);
			   }
			   
			catch (Exception e)
			   {
				   System.out.println(e);
			   }
	       	
	    	try {
				file.importFile("no file");
			   }
			   
			catch (Exception e)
			   {
				   System.out.println(e);
			   }
	    	
	        System.gc();
	}
}
