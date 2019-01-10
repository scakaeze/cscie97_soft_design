package cscie97.asn3.housemate.test;

import cscie97.asn3.housemate.model.ModelService;
import cscie97.asn3.housemate.model.cmd_API;
import cscie97.asn3.housemate.model.occupant;
import cscie97.asn3.housemate.controller.ControllerService;
import cscie97.asn3.housemate.controller.request;


//DEFINE CLASS
public class TestDriver {

	public static void main(String[] args) throws Exception {
        
        
		cmd_API file = new cmd_API();
	       
	        try {
				file.importFile(args[0]);
			   }
			   
			catch (Exception e)
			   {
				   System.out.println(e);
			   }     
	}
}
