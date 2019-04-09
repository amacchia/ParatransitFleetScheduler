package mySql;
import java.sql.Timestamp;
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
	 * locationArray[x][0] --> locationID
	 * locationArray[x][1] --> latitude
	 * locationArray[x][2] --> longitude
	 * locationArray[x][3] --> streetAddress
	 * locationArray[x][4] --> city
	 * locationArray[x][5] --> state
	 * locationArray[x][6] --> zipcode
	 * 
	 * driverArray[x][0] --> driveID
	 * driverArray[x][1] --> email
	 * driverArray[x][2] --> firstName
	 * driverArray[x][3] --> lastName
	 * driverArray[x][4] --> password
	 * 
	 * requestArray[x][0] --> requestID 
	 * requestArray[x][1] --> userID
	 * requestArray[x][2] --> originID
	 * requestArray[x][3] --> destinationID
	 * requestArray[x][4] --> requestDate
	 * requestArray[x][5] --> requestTimestamp
	 * 
	 * routeArray[x][0] --> routeID
	 * routeArray[x][1] --> requestID
	 * routeArray[x][2] --> driverID
	 * routeArray[x][3] --> locationID
	 * routeArray[x][4] --> orderInRoute
	 * 
	 * reqDetArray[x][0] --> requestID
	 * reqDetArray[x][1] --> userID
	 * reqDetArray[x][2] --> originID
	 * reqDetArray[x][3] --> destinationID
	 * reqDetArray[x][4] --> requestID
	 * reqDetArray[x][5] --> originLatitude
	 * reqDetArray[x][6] --> originLongitude
	 * reqDetArray[x][7] --> originAddress
	 * reqDetArray[x][8] --> destinationLatitude
	 * reqDetArray[x][9] --> destinationLongitude
	 * reqDetArray[x][0] --> destinationAddress
	 * 
	 */
	static String[][] routeArray;
	static String[][] reqDetArray;
	static String[][] mergeArray;	
	static int routeID = -1; 		//start at -1 so if there is an error, it can be easily spotted in the database.
	public static void main(String[] args)
	{
		MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		routeID = con.routeID() + 1;
		System.out.println(routeID +"\n");
		
		con = new MysqlConnection("morris_requestdetails", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		con.connect();
		reqDetArray = con.getArray();
		
		
		//populate an ArrayList of rides.
		ArrayList<Ride> rides = populateRides();
		for(int i = 0; i < rides.size(); i++) {
			System.out.println(rides.get(i));
		}
		
      ArrayList<ArrayList<Ride>> clusters1 = Cluster.kmeans(3, rides);
      ArrayList<ArrayList<Ride>> clusters2 = Cluster.kmeans(3, rides);
      ArrayList<ArrayList<Ride>> clusters3 = Cluster.kmeans(3, rides);
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
			ArrayList<Location> route = Methods.pathfind(new Location(-1, -1, 0, 0, null, true), bestLocations.get(i), new ArrayList<Location>(),
					new ArrayList<Location>(), 2, 0);
			iterRoutes(route);
		}
		System.out.println("Write to Route table completed.");
	}
	
	public static ArrayList <Ride> populateRides()
	{
		ArrayList<Ride> rideList = new ArrayList<Ride>();
		for(int i = 0; i < reqDetArray.length; i++)
		{
			rideList.add(new Ride(getReqID(i),
					new Location(getReqID(i), getOrigID(i), getOrigLat(i), getOrigLong(i), getOrigAddress(i), true),
					new Location(getReqID(i), getDestID(i), getDestLat(i), getDestLong(i), getDestAddress(i), false),
					getReqDate(i)));
					
		}
		return rideList;
	}	
	
   
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
    * Iterate through the routes from pathfind.
    * While iterating through, write to the route table 
    * in the database.
    */
   private static void iterRoutes(ArrayList<Location> routes)
   {
	   MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
	   int index = 1;
	   Iterator<Location> iterate = routes.iterator();
	   while (iterate.hasNext())
		{
		   Location temp = iterate.next();
		   con.writeRouteToDB(routeID, temp.getReqID(), 1, temp.getDbID(), index);
		   index++;
		}
	   routeID++;
   }
   
   
   //Get the ID of the Ride
   private static int getReqID(int i)
   {
	   return Integer.parseInt(reqDetArray[i][0]);
   }
   //
   private static int getUserID(int i)
   {
	   return Integer.parseInt(reqDetArray[i][1]);
   }
   
    //get the Location ID of the Origin.  (Not the ride ID)
	//Using connectMerge()
	private static int getOrigID(int i)
	{
		return Integer.parseInt(reqDetArray[i][2]);
	}
	//using connectMerge()
	private static int getDestID(int i)
	{
		return Integer.parseInt(reqDetArray[i][3]);
	}
	
	//using connectMerge()
	private static Timestamp getReqDate(int i)
	{
		return Timestamp.valueOf(reqDetArray[i][4]);
	}
	
	//Get String address of the origin.
	//using connectMerge()
	private static double getOrigLat(int i)
	{
		return Double.parseDouble(reqDetArray[i][5]);
	}
	
	//get the Location ID of the Origin.  (Not the ride ID)
	//Using connectMerge()
	public static double getOrigLong(int i)
	{
		return Double.parseDouble(reqDetArray[i][6]);
	}
	
	//using connectMerge().
	private static String getOrigAddress(int i)
	{
		return reqDetArray[i][7];
	}
	
	//using connectMerge().
	private static double getDestLat(int i)
	{
		return Double.parseDouble(reqDetArray[i][8]);
	}
	
	//Get String address of the destination.
	//Using connectMerge()
	private static double getDestLong(int i)
	{
		return Double.parseDouble(reqDetArray[i][9]);
	}
	
	private static String getDestAddress(int i)
	{
		return reqDetArray[i][10];
	}
	
}
