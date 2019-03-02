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
	
//	private int id;  //Uncomment if we need to give a location an ID to link with passenger.
	
	//Constructor
	public Location(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	//Constructor with ID
	//Uncomment if we need ID. Delete if not.
//	public Location(int id, double latitude, double longitude)
//	{
//		this.id = id;
//		this.latitude = latitude;
//		this.longitude = longitude;
//	}
	
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
}
