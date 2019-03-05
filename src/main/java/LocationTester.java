
public class LocationTester 
{
	//Test Location Class methods.
	//Initialize Location as Location(Latitude, Longitude)
	public static void main(String[] args)
	{
		Location x = new Location(40.769, -73.9549);
		Location y = new Location(40.7223, -73.9887);
		
		System.out.println(x.compareTo(y));
	}
}
