package mySql;
import static java.lang.Math.sqrt;
import java.io.*;
/*
 * Location Class
 * This class holds the Longitude and Latitude coordinates
 * and various methods to make use of the coordinates.
 * 
 * @Kieran Walsh
 * 
 * 
 */
public class Location
{

	//doubles to hold the coordinates and keep precision.
	private double latitude;
	private double longitude;
	private int reqID;
	private int dbID;
	public boolean isOrigin;
	private String address;
	
	
	//Constructor with address
	public Location(int reqID, int dbID, double latitude, double longitude,
					String address, boolean isOrigin)
	{
		this.reqID = reqID;
		this.dbID = dbID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isOrigin = isOrigin;
		this.address = address;
	}
	
	//Constructor no address
	public Location(int reqID, double latitude, double longitude, boolean isOrigin)
	{
		this.reqID = reqID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isOrigin = isOrigin;
	}
	
	//Constructor only lat/long
	public Location(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/* Helper method to comapreTo() method to 
	 * calculate distance between two sets of coordinates.
	 */
	private double distance(double latitude1, double longitude1,
							double latitude2, double longitude2)
	{
		double distance;
		double x = latitude2 - latitude1;
		double y = longitude2 - longitude1;
		distance = sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
		return distance;
	}
	
	/* Compares two sets of coordinates to
	 * find the distance between them.
	 */
	public double compareTo(Location coords)
	{
		return distance(longitude, latitude, coords.longitude, coords.latitude);
	}
	
	public int getReqID()
	{
		return reqID;
	}
	
	public int getDbID()
	{
		return dbID;
	}
	
	public double getLat()
	{
		return latitude;
	}
	
	public double getLong()
	{
		return longitude;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String toString()
	{
		return "id: " + dbID + " Address: " + address;
	}
}