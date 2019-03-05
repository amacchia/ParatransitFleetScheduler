import mySql.MysqlConnection;

public class LocationTester 
{
	//Test Location Class methods.
	// NEW! Also test the MysqlConnection class.
	//Initialize Location as Location(Latitude, Longitude)
	/*
	 * Layout for the arrays are as follow:
	 * 
	 * locationArray[x][0] --> location_id
	 * locationArray[x][1] --> address
	 * locationArray[x][2] --> latitude
	 * locationArray[x][3] --> longitude
	 * locationArray[x][4] --> street_address
	 * locationArray[x][5] --> city
	 * locationArray[x][6] --> state
	 * locationArray[x][7] --> zipcode
	 * 
	 * passengerArray[x][0] --> email
	 * passengerArray[x][1] --> first_name
	 * passengerArray[x][2] --> last_name
	 * passengerArray[x][3] --> password
	 * passengerArray[x][4] --> isDriver
	 * passengerArray[x][5] --> passenger_id
	 * 
	 * driverArray[x][0] --> email
	 * driverArray[x][1] --> first_name
	 * driverArray[x][2] --> last_name
	 * driverArray[x][3] --> password
	 * 
	 * rideArray[x][0] --> ride_id  
	 * rideArray[x][1] --> passenger_id
	 * rideArray[x][2] --> pickup_id
	 * rideArray[x][3] --> dropoff_id
	 * rideArray[x][4] --> pick_up_time
	 * 
	 * routeArray[x][0] --> route_id
	 * routeArray[x][1] --> driver_email
	 * routeArray[x][2] --> location_id
	 */
	public static void main(String[] args)
	{
		MysqlConnection locationCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "root");
		
		String[][] locationArray = locationCon.getLocationArray();
		
		Location x = new Location(Double.parseDouble(locationArray[0][2]), Double.parseDouble(locationArray[0][3]));
		Location y = new Location(Double.parseDouble(locationArray[1][2]), Double.parseDouble(locationArray[1][3]));
		
		System.out.println("Distance between locationArray[0][] and locationArray[1][]: " + x.compareTo(y) + " degrees");
	}
}
