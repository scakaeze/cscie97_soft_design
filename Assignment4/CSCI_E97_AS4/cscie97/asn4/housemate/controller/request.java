package cscie97.asn4.housemate.controller;

public class request {

	public String status; // status of the command
	public String command; // the command string
	
	public request(String statusIN,String commandIN){
		status = statusIN;
		command = commandIN;
	}
}
