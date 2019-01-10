package cscie97.asn2.housemate.model;

import java.io.*;
import java.util.*;

public class Room {
	
	private String house;
	private String name;
	private String floor;
	private String type;
	private int NumOfWin;
	
	
	public Room(String h, String n, String f, String t, int w){
		house = h;
		name = n;
		floor = f;
		type = t;
		NumOfWin = w;
		
		for (int x = 0; x < w; x++){
			clients.getInstance().addAppliance((h + n + "window"+ Integer.toString(x)), "window", n, h); // add new windows to applianceMap in client class
		}
	}
	
	/**
     * getHouse - returns the name of the House where the room is located
     * @return name
     */
	public String getHouse(){
		return house;
	}
	
	/**
     * getName - returns the rooms's unique identifier
     * @return name
     */
	public String getName(){
		return name;
	}
	
	/**
     * getFloor - returns the rooms's floor
     * @return floor
     */
	public String getFloor(){
		return floor;	
	}

	/**
     * getType - returns the rooms's type
     * @return type
     */
	public String getType(){
		return type;
	}
	
	/**
     * getWindowNum - returns the room's window number 
     * @return NumOfWin
     */
	public int getWindowNum(){
		return NumOfWin;	
	}
	
}
