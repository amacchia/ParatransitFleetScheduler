package mySql;
import java.sql.Timestamp;
import java.util.*;
import org.springframework.boot.origin.Origin;
import mySql.MysqlConnection;
import mySql.Cluster;

public class LocationTester 
{
	//Test Class methods. (Not just Location.java, despite the name).
	//Initialize Location as Location(int id, double Latitude, double Longitude)
	//Initialize  Ride as Ride(int id, int pickUpID, int dropOffID, Timestamp pickUpTime)
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
	static String[][] locationArray;
	static String[][] rideArray;
	
	
	public static ArrayList<Ride> populateRides() 
	{
		ArrayList<Ride> rideList = new ArrayList<Ride>();
		for(int i = 0; i < rideArray.length; i++)
		{
			rideList.add(new Ride(Integer.parseInt(rideArray[i][0]),
						new Location(i+1, getOrigLat(i), getOrigLong(i)),
						new Location(i+1, getDestLat(i), getDestLong(i)),
						null));
//			rideList.add(new Ride(Integer.parseInt(rideArray[i][0]), 
//						new Location(i+1, Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][2]), Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][3])),
//						new Location(i+1, Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][3])-1][3]), Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][3])),
//						null));
		}
		return rideList;
	}
	
	private static double getOrigLat(int i)
	{
		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][2]);
	}
	private static double getOrigLong(int i)
	{
		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][3]);
	}
	private static double getDestLat(int i)
	{
		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][3])-1][2]);
	}
	private static double getDestLong(int i)
	{
		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][3])-1][3]);
	}
	public static void main(String[] args)
	{
		MysqlConnection locationCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "root");
		
		locationArray = locationCon.getLocationArray();
//		int locationxID = Integer.parseInt(locationArray[0][0]);
//		int locationyID = Integer.parseInt(locationArray[1][0]);
//		
//		Location x = new Location(locationxID, Double.parseDouble(locationArray[0][2]), Double.parseDouble(locationArray[0][3]));
//		Location y = new Location(locationyID, Double.parseDouble(locationArray[1][2]), Double.parseDouble(locationArray[1][3]));
//		double distance = x.compareTo(y);
//		
//		System.out.println("Distance between locationArray[0][] and locationArray[1][]: " + distance + " degrees");
		
		
		
		//Test Ride.java
		System.out.println("\n\n");
		MysqlConnection rideCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "root");
		
		rideArray = rideCon.getRideArray();
//		int rideID = Integer.parseInt(rideArray[0][0]);
//		
//		
//		Ride ride1 = new Ride(rideID, );
//		System.out.println(ride1.getTime()); //Test Timestamp has correct format
//		
//		//find which Location Objects links to 'ride1' pickUp and dropOff id's
//		Location ride1Loc = getOriginLoc(rideArray[0][2]);
//		System.out.println(ride1Loc.getID());
		
		ArrayList<Ride> rides = populateRides();
		for(int i = 0; i < rides.size(); i++) {
			System.out.println(rides.get(i));
		}
		
		ArrayList<ArrayList<Ride>> clusters = Cluster.randomCluster(rides);
		for(int i = 0; i < clusters.size(); i++)
		{
			System.out.println("Cluster Number: " + i);
			for(int j = 0; j < clusters.get(i).size(); j++)
			{
				System.out.println(clusters.get(i).get(j) +"\n");
			}
		}
	}
	
//	private static Location getOriginLoc(String pickUp)
//	{
//		for(int i = 0; i < locationArray.length; i++)
//		{
//			if(locationArray[i][0] == pickUp) 
//			{
//				return new Location(Integer.parseInt(locationArray[i][0]), Double.parseDouble(locationArray[i][2]), Double.parseDouble(locationArray[i][3]));
//			}
//		}
//		return null;
//	}
}
