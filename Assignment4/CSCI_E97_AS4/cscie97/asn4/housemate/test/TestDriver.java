package cscie97.asn4.housemate.test;


import cscie97.asn4.housemate.model.importer;


public class TestDriver {

	public static void main(String[] args) throws Exception {
   
		importer file = new importer();
	       
	        try {
				file.importFile(args[0]);
			   }
			   
			catch (Exception e)
			   {
				   System.out.println(e);
			   }  
	}
}

