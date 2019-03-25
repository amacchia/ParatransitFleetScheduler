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
   
   public double compareTo(Ride other){//4-dimensional Euclidean distance
      return Math.sqrt(Math.pow((this.origin.getLat() - other.origin.getLat()), 2)+ Math.pow((this.origin.getLong() - other.origin.getLong()), 2) 
      + Math.pow((this.dest.getLat() - other.dest.getLat()), 2) + Math.pow((this.dest.getLong() - other.dest.getLong()), 2));
   }
}
