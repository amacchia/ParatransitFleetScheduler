
/*
 * Contains information about the origin/destination,
 * route, distance, pick-up time
 * 
 * @Kieran Walsh
 * 
 */
public class Ride 
{
	private Location origin;
	private Location destination;
	private double distance;
	private long pickUpTime;
	
	//Constructor
	public Ride(Location origin, Location destination,
				double distance, long pickUpTime)
	{
		this.origin = origin;
		this.destination = destination;
		this.distance = distance;
		this.pickUpTime = pickUpTime;
	}
	
}
