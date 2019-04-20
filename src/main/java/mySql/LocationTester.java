package mySql;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.boot.origin.Origin;
import mySql.MysqlConnection;
import pathfinder.DepthFirstSearch;
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
	 * reqDetArray[x][4] --> requestDate
	 * reqDetArray[x][5] --> originLatitude
	 * reqDetArray[x][6] --> originLongitude
	 * reqDetArray[x][7] --> originAddress
	 * reqDetArray[x][8] --> destinationLatitude
	 * reqDetArray[x][9] --> destinationLongitude
	 * reqDetArray[x][10] --> destinationAddress
	 * 
	 */
	static double[] scores;
	static String[][] reqDetArray;	//Holds details from the morris_ridedetails view
	static int[] driversToday;		//contains to ID's of the drivers that are driving the current day.
	static int routeID = -1; 		//start at -1 so if there is an error, it can be easily spotted in the database.
	static ArrayList<ArrayList<ArrayList<Ride>>> best_clusters;
	public static void main(String[] args) throws ParseException
	{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		String Stringdate = inputFormat.format(new Date());
		if(args.length>0)
		{
			Stringdate = args[0];

		}
		
		MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		routeID = con.routeID() + 1;		//This connection is to find the max value routeID currently
											//in the database, so when we write to the database later,
											//we do not have two routes with the same ID.
		
		con = new MysqlConnection("morris_requestdetails", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		con.connect(Stringdate);
		reqDetArray = con.getArray();		//This connection is to get the information for all the requests
											//and their information that have been put into the database.
		
		con = new MysqlConnection("driverschedule", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		driversToday = con.getDrivers(Stringdate);	//This connection is to see which drivers are available 
		System.out.println("");				//today so they can be assigned routes after they've 
											//been generated.
		
		//populate an ArrayList of rides.
		ArrayList<Ride> rides = populateRides();
		ArrayList<ArrayList<Ride>> ridesByTime = timeSort(rides);
		System.out.println("List of Rides:");
		for(int i = 0; i < ridesByTime.size(); i++) 
		{
			String time = ridesByTime.get(i).get(0).getTime().toString();
			time = time.substring(11, time.length());
			System.out.println("Rides for " + time);
			for(int j = 0; j < ridesByTime.get(i).size(); j++)
			{
				System.out.println(ridesByTime.get(i).get(j).toString());
			}
		}		
		System.out.println(""); 		//Separate for cleaner console prints.
		
		int numRepeat = 3;				//Repeats making the same clusters for each time to find the best one.
		ArrayList<ArrayList<ArrayList<Ride>>> best_clusters = makeClusterArray(ridesByTime, numRepeat);
		int p = 1;
		for(int i = 0; i < best_clusters.size(); i++)
		{
			for(int j = 0; j < best_clusters.get(i).size(); j++)
			{
				for(int k = 0; k < best_clusters.get(i).get(j).size(); k++)
				{
					System.out.println("Cluster " + p + ": " + best_clusters.get(i).get(j).get(k).getID());
				}
			}
			p++;
		}
		
		ArrayList<ArrayList<Location>> bestLocations = toLocationArray(best_clusters);
		for(int i = 0; i < bestLocations.size(); i++)
		{
			System.out.println("New Index");
			for(int j = 0; j < bestLocations.get(i).size(); j++)
			{
				System.out.println("Best Location IDs: " + bestLocations.get(i).get(j).getDbID());
			}
		}
		ArrayList<Location> route;
		int CAPACITY = 3;
		for(int i = 0; i <bestLocations.size();i++)
		{
			route = DepthFirstSearch.DFsearch(CAPACITY, new Location(-1, -1, 0, 0, null, true), bestLocations.get(i));
			iterRoutes(route, driversToday[i]);
		}
		System.out.println("Write to route table complete.");
	}
	
	
	
	/*
	 * Takes the ArrayList of rides and Sorts them into an ArrayList of
	 * an ArrayList of Rides based on time.  
	 * Ex: all 8am rides will be in the same index, all 8:15 rides will be in
	 * the same index, etc.
	 */
	public static ArrayList<ArrayList<Ride>> timeSort(ArrayList<Ride> rides)
	{
		ArrayList<ArrayList<Ride>> timeSlot = new ArrayList<ArrayList<Ride>>();
		int index = 0;
		for(Ride r: rides)
		{
			if(timeSlot.size() == 0)
			{
				ArrayList<Ride> temp = new ArrayList<Ride>();
				temp.add(r);
				timeSlot.add(temp);
				index++;
			}
			
			else if( !(r.getTime()).equals((timeSlot.get(timeSlot.size()-1).get(index-1).getTime())) )
			{
				timeSlot.add(new ArrayList<Ride>());
				timeSlot.get(timeSlot.size()-1).add(r);
			}
			else
			{
				timeSlot.get(timeSlot.size()-1).add(r);
			}
		}
		return timeSlot;
	}
	
	/*
	 * Stores the clusters into an ArrayList<ArrayList<ArrayList<Ride>>>.
	 * The clusters are based on the times of rides.  
	 * This way be only cluster rides that have a the same pick up time.  
	 * We wouldn't want an 8AM ride getting clustered with a 5PM ride.
	 * 
	 */
	
	public static ArrayList<ArrayList<ArrayList<Ride>>> makeClusterArray(ArrayList<ArrayList<Ride>> allRides, int numRepeat)
	{
		ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters = new ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>>();
		for(int i = 0; i < numRepeat; i++)
		{
			clusters.add(new ArrayList<ArrayList<ArrayList<Ride>>>());
			for(int j = 0; j < allRides.size(); j++)
			{
				clusters.get(i).add(Cluster.kmeans(driversToday.length, allRides.get(j)));
			}
			
			
		}
		scores = makeScoreArray(clusters);
		best_clusters = bestClusters(scores, clusters);
		return best_clusters;
	}
	
	
	/*
	 * computes the scores of the clusters.
	 */
	public static double[] makeScoreArray(ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters)
	{
		double[] scores = new double[clusters.size()];
		for(int i = 0; i < clusters.size(); i++)
		{
			scores[i] = Metric.computeScore(toLocationArray(clusters.get(i)));
		}		
		return scores;
	}
	
	/*
	 * Compares the scores of each cluster
	 */
	public static ArrayList<ArrayList<ArrayList<Ride>>> bestClusters(double[] scores2,
										ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters)
	{	
		double max = scores2[0];
		int maxIndex = 0;
		for(int i = 1; i<scores2.length; i++)
		{
			if(scores2[i] > max)
			{
				max = scores2[i];
				maxIndex = i;	
			}
		}
		return clusters.get(maxIndex);
	}
	
	/*
	 * Uses information that was read from the database,
	 * to create Ride and Location objects. Then 
	 * returns an ArrayList<Rides>
	 */
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
   
   public static ArrayList<ArrayList<Location>> toLocationArray(ArrayList<ArrayList<ArrayList<Ride>>> rideClusters){
      ArrayList<ArrayList<Location>> locationClusters = new ArrayList<ArrayList<Location>>();
      
      for(int i = 0; i < rideClusters.size(); i++)
      {
    	  locationClusters.add(new ArrayList<>());
    	  for(int j = 0; j < rideClusters.get(i).size(); j++)
    	  {
    		  for(int k = 0; k < rideClusters.get(i).get(j).size(); k++)
    		  {
    			  locationClusters.get(i).add(rideClusters.get(i).get(j).get(k).getOrig());
    			  locationClusters.get(i).add(rideClusters.get(i).get(j).get(k).getDest());
    			  
    		  }
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
		   System.out.println("RouteID: " + routeID);
		   System.out.println("ReqID: " + temp.getReqID());
		   System.out.println("driver ID: " + driverID);
		   System.out.println("DB ID: " + temp.getDbID());
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
