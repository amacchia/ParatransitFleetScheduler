package mySql;
import java.util.*;
import org.springframework.boot.origin.Origin;
import mySql.MysqlConnection;
import pathfinder.Methods;
import mySql.Cluster;
import metric.Metric;

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
	static String[][] mergeArray;
	

	public static void main(String[] args)
	{
		//Make connection to database and the location dataset.
//		MysqlConnection locationCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
//				"com.mysql.cj.jdbc.Driver", "root", "root");
		
		//create locationArray from information gathered from connection.
//		locationArray = locationCon.getLocationArray();
		
		// Connect to database again, but go to ride dataset
//		MysqlConnection rideCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
//				"com.mysql.cj.jdbc.Driver", "root", "root");
		//create rideArray from information gathered from connection.
//		rideArray = rideCon.getRideArray();
		
				
		MysqlConnection mergeCon = new MysqlConnection("jdbc:mysql://localhost:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "root");
		mergeArray = mergeCon.getMergeArray();
		
		
		//populate an ArrayList of rides.
		ArrayList<Ride> rides = populateRides();
		for(int i = 0; i < rides.size(); i++) {
			System.out.println(rides.get(i));
		}
		
      ArrayList<ArrayList<Ride>> clusters1 = Cluster.randomCluster(rides);
      System.out.println(rides.size());  //check size of rides array.
      ArrayList<ArrayList<Ride>> clusters2 = Cluster.randomCluster(rides);
      System.out.println(rides.size());
      ArrayList<ArrayList<Ride>> clusters3 = Cluster.randomCluster(rides);
      System.out.println(rides.size());
      double score1 = Metric.computeScore(toLocationArray(clusters1));
      double score2 =  Metric.computeScore(toLocationArray(clusters2));
      double score3 =  Metric.computeScore(toLocationArray(clusters3));
      System.out.println("Score1, Score2, and Score3 for the clusters: "+ score1 + " " + score2 + " " + score3);
      ArrayList<ArrayList<Ride>> bestclusters;
      if(score1 > score2){
         if(score1 > score3){
            bestclusters = clusters1;
         }else{
            bestclusters = clusters3;
         }
      }else{
         if(score2 > score3){
            bestclusters = clusters2;
         }else{
            bestclusters = clusters3;
         }
      }
		for(int i = 0; i < bestclusters.size(); i++)
		{
			System.out.println("Cluster Number: " + i);
			for(int j = 0; j < bestclusters.get(i).size(); j++)
			{
				System.out.println(bestclusters.get(i).get(j) +"\n");
			}
		}
		ArrayList<ArrayList<Location>> bestLocations = toLocationArray(bestclusters);
		for(int i = 0; i < bestLocations.size(); i++)
		{
			Methods.pathfind(new Location(-1, 0, 0, true), bestLocations.get(i), new ArrayList<Location>(),
					new ArrayList<Location>(), 2, 0);
		}
	}
	
	public static ArrayList <Ride> populateRides()
	{
		ArrayList<Ride> rideList = new ArrayList<Ride>();
		for(int i = 0; i < mergeArray.length; i++)
		{
			rideList.add(new Ride(Integer.parseInt(mergeArray[i][0]),
					new Location(i+1, getOrigLat(i), getOrigLong(i), true),
					new Location(i+1, getDestLat(i), getDestLong(i), false),
					null));
					
		}
		return rideList;
	}
	
//	//Makes an ArrayList of rides. Using connect()
//	public static ArrayList<Ride> populateRides() 
//	{
//		ArrayList<Ride> rideList = new ArrayList<Ride>();
//		for(int i = 0; i < rideArray.length; i++)
//		{
//			rideList.add(new Ride(Integer.parseInt(rideArray[i][0]),
//						new Location(i+1, getOrigLat(i), getOrigLong(i), true),
//						new Location(i+1, getDestLat(i), getDestLong(i), false),
//						null));
//		}
//		return rideList;
//	}
	
   
   public static ArrayList<ArrayList<Location>> toLocationArray(ArrayList<ArrayList<Ride>> rideClusters){
      ArrayList<ArrayList<Location>> locationClusters = new ArrayList<ArrayList<Location>>();
      System.out.println(rideClusters.size());
      for(int i = 0; i < rideClusters.size(); i++){
         locationClusters.add(new ArrayList<Location>());
         for(int j = 0; j < rideClusters.get(i).size(); j++){
            locationClusters.get(i).add(rideClusters.get(i).get(j).getOrig());
            locationClusters.get(i).add(rideClusters.get(i).get(j).getDest());

         }
      }
      return locationClusters;
   }
   
   /*
    * Uses indices from rideArray and locationArray to return
    * the Latitude of the origin.
    * 
    */
//	private static double getOrigLat(int i)
//	{
//		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][2]);
//	}
   
	//using connectMerge()
	private static double getOrigLat(int i)
	{
		return Double.parseDouble(mergeArray[i][2]);
	}
	
	/*
	 * Uses indices from rideArray and locationArray to return
     * the Longitude of the origin.
	 */
//	private static double getOrigLong(int i)
//	{
//		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][2])-1][3]);
//	}
	
	//using connectMerge()
	private static double getOrigLong(int i)
	{
		return Double.parseDouble(mergeArray[i][3]);
	}
	
	/*
	 * Uses indices from rideArray and locationArray to return
     * the Latitude of the destination.
	 */
//	private static double getDestLat(int i)
//	{
//		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][3])-1][2]);
//	}
//	
//	/*
//	 * Uses indices from rideArray and locationArray to return
//     * the Longitude of the destination.
//	 */
//	private static double getDestLong(int i)
//	{
//		return Double.parseDouble(locationArray[Integer.parseInt(rideArray[i][3])-1][3]);
//	}
	
	//using connectMerge().
	private static double getDestLat(int i)
	{
		return Double.parseDouble(mergeArray[i][5]);
	}
	//using connectMerge().
	private static double getDestLong(int i)
	{
		return Double.parseDouble(mergeArray[i][6]);
	}
	
}
