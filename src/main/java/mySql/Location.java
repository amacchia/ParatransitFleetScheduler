package mySql;
import static java.lang.Math.sqrt;
/*
 * Location Class
 * This class holds the Longitude and Latitude coordinates
 * and various methods to make use of the coordinates.
 * 
 * @Kieran Walsh
 * 
 */
public class Location 
{
	//doubles to hold the coordinates and keep precision.
	private double latitude;
	private double longitude;
	private int id;
	public final boolean isOrigin;
	
	
	//Constructor
	public Location(int id, double latitude, double longitude, boolean isOrigin)
	{
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isOrigin = isOrigin;
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
	
	public int getID()
	{
		return id;
	}
	
	public double getLat()
	{
		return latitude;
	}
	
	public double getLong()
	{
		return longitude;
	}
	
	public String toString()
	{
		return "id: " + id + " lat: " + latitude + " long: " + longitude;
	}
}