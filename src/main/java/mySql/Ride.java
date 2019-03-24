package mySql;

import java.sql.Date;
import java.sql.Timestamp;

/*
 * Contains information about the origin/destination,
 * route, distance, pick-up time
 * 
 * @Kieran Walsh
 * 
 */
public class Ride 
{
	private int id;
	private Location origin;
	private Location dest;
	private Date pickUpTime;
	
	//Constructor
	public Ride(int id, Location origin, Location dest, Date pickUpTime)
	{
		this.id = id;
		this.origin = origin;
		this.dest = dest;
		this.pickUpTime = pickUpTime;
	}
	
	public int getID() {
		return id;
	}
	
	public Location getOrig() {
		return origin;
	}
	
	public Location getDest() {
		return dest;
	}
	
	public Date getTime() {
		return pickUpTime;
	}
	
	public String toString()
	{
		String result = "";
		result += "origin " + origin + " destination " + dest;
		return result;
	}
}
