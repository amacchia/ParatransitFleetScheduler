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
	 * reqDetArray[x][10] --> destinationAddress
	 * 
	 */
	static String[][] reqDetArray;	//Holds details from the morris_ridedetails view
	static int[] driversToday;		//contains to ID's of the drivers that are driving the current day.
	static int routeID = -1; 		//start at -1 so if there is an error, it can be easily spotted in the database.
	public static void main(String[] args)
	{
		MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		routeID = con.routeID() + 1;		//This connection is to find the max value routeID currently
											//in the database, so when we write to the database later,
											//we do not have two routes with the same ID.
		
		con = new MysqlConnection("morris_requestdetails", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		con.connect();
		reqDetArray = con.getArray();		//This connection is to get the information for all the requests
											//and their information that have been put into the database.
		
		con = new MysqlConnection("driverschedule", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		driversToday = con.getDrivers();	//This connection is to see which drivers are available 
		System.out.println("");				//today so they can be assigned routes after they've 
											//been generated.
		
		
		//populate an ArrayList of rides.
		ArrayList<Ride> rides = populateRides();
		System.out.println("List of Rides:");
		for(int i = 0; i < rides.size(); i++) {
			System.out.println(rides.get(i));  //Print all the rides out to check they were all added.
		}
		
		System.out.println(""); 		//Cleaner console prints.
		
      ArrayList<ArrayList<Ride>> clusters1 = Cluster.kmeans(driversToday.length, rides); //makes a cluster for each driver.
      ArrayList<ArrayList<Ride>> clusters2 = Cluster.kmeans(driversToday.length, rides);
      ArrayList<ArrayList<Ride>> clusters3 = Cluster.kmeans(driversToday.length, rides);

      double score1 = Metric.computeScore(toLocationArray(clusters1));
      double score2 =  Metric.computeScore(toLocationArray(clusters2));
      double score3 =  Metric.computeScore(toLocationArray(clusters3));
      System.out.println("Score1, Score2, and Score3 for the clusters: "+ score1 + " " + score2 + " " + score3 +"\n");
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
			iterRoutes(route, driversToday[i]);  	//write the routes that were just generated 
													//into the route table in the database
		}
		System.out.println("Write to Route table completed."); 	//Print statement to know when all routes
																//have been written to the database.
	}
	
	
	//Uses information that was read from the database, 
	//to create Ride and Location objects. Then 
	//returns an ArrayList<Rides>
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
   private static void iterRoutes(ArrayList<Location> routes, int driverID)
   {
	   MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
	   int orderInRoute = 1;  			//keeps track of the order of rides.
	   Iterator<Location> iterate = routes.iterator();
	   while (iterate.hasNext())
		{
		   Location temp = iterate.next();
		   con.writeRouteToDB(routeID, temp.getReqID(), driverID, temp.getDbID(), orderInRoute); //write to the database route table.
		   orderInRoute++;					//increase ride order.
		}
	   routeID++;  //When method is over, increase the routeID for next time it is run.
   }
   
   
   //Get the ID of the Ride/Request.
   private static int getReqID(int i)
   {
	   return Integer.parseInt(reqDetArray[i][0]);
   }
   
   //Get ID of the User that requested the ride.
   private static int getUserID(int i)
   {
	   return Integer.parseInt(reqDetArray[i][1]);
   }
   
    //get the Location ID of the Origin. (Not the ride ID)
	private static int getOrigID(int i)
	{
		return Integer.parseInt(reqDetArray[i][2]);
	}
	
	//get the Location ID of the Destination. (Not the ride ID)
	private static int getDestID(int i)
	{
		return Integer.parseInt(reqDetArray[i][3]);
	}
	
	//get the date and time the ride is requested for.
	private static Timestamp getReqDate(int i)
	{
		return Timestamp.valueOf(reqDetArray[i][4]);
	}
	
	//Get Latitude degree of the origin.
	private static double getOrigLat(int i)
	{
		return Double.parseDouble(reqDetArray[i][5]);
	}
	
	//get the longitude degree of the origin.
	public static double getOrigLong(int i)
	{
		return Double.parseDouble(reqDetArray[i][6]);
	}
	
	//Get the String address of the origin.
	private static String getOrigAddress(int i)
	{
		return reqDetArray[i][7];
	}
	
	//Get Latitude degree of the destination.
	private static double getDestLat(int i)
	{
		return Double.parseDouble(reqDetArray[i][8]);
	}
	
	//Get Longitude degree of the  destination.
	private static double getDestLong(int i)
	{
		return Double.parseDouble(reqDetArray[i][9]);
	}
	
	//get the String address of the destination.
	private static String getDestAddress(int i)
	{
		return reqDetArray[i][10];
	}
	
}
