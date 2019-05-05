package mySql;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import mySql.MysqlConnection;
import pathfinder.BestFirstBranchBound;
import mySql.Cluster;

public class LocationTester 
{
	static String[][] reqDetArray;	//Holds details from the morris_ridedetails view
	static int[] driversToday;		//contains to ID's of the drivers that are driving the current day.
	static int routeID; 			//Used when writing routes to the database.
	static ArrayList<ArrayList<ArrayList<Ride>>> best_clusters;
	public static void main(String[] args) throws ParseException
	{
		//String startTime = new Date().toString();	//Used to print runtime of application
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		String Stringdate = inputFormat.format(new Date());	//create String for todays date.
		if(args.length>0)
		{
			Stringdate = args[0];	//if there is a parameter, use that date instead of current date.
		}
		
		MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		routeID = con.routeID() + 1;		//This connection is to find the max value routeID currently
											//in the database, so when we write to the database later,
											//we do not have two routes with the same ID.
		
		System.out.println("Check routeID: " + routeID);	//Here for testing.
		
		con = new MysqlConnection("requestdetails", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		con.connect(Stringdate);
		reqDetArray = con.getArray();		//This connection is to get the information for all the requests
											//and their information that have been put into the database.
		
		con = new MysqlConnection("driverschedule", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		driversToday = con.getDrivers(Stringdate);	//This connection is to see which drivers are available 
		
		
		System.out.println(driversToday.length + " Drivers Today");	//Here for testing.
		for(int i = 0; i < driversToday.length; i++)		//Here for testing.
		{													//Here for testing.
			System.out.println("ID: " + driversToday[i]);			//Here for testing.
		}													//Here for testing.
		System.out.println(""); 							//Here for testing.
		
		
		ArrayList<Driver> drivers = new ArrayList<Driver>();	//create an ArrayList to store the available drivers.
		for(int i = 0; i < driversToday.length;i++)
		{
			Driver temp = new Driver(driversToday[i], new ArrayList<Location>());	//create instances for the drivers
			drivers.add(temp);	//add those drivers into the ArrayList<Driver> drivers
		}
		
		ArrayList<Ride> rides = populateRides();	//make an ArrayList of all rides for chosen day.
		
		System.out.println("Number of rides in ArrayList<Rides> rides: " + rides.size());	//Here for testing.
		for(int i = 0; i < rides.size(); i++)												//Here for testing.
		{																					//Here for testing.
			System.out.println(rides.get(i));												//Here for testing.
		}																					//Here for testing.
		System.out.println(""); 															//Here for testing.
		
		ArrayList<ArrayList<Ride>> ridesByTime = timeSort(rides);	//Index rides by their request time.
		
		int total = 0;
		System.out.println("\nMake sure rides are in proper Time Slots:");		//Here for testing.
		for(int i = 0; i < ridesByTime.size(); i++)								//Here for testing.
		{																		//Here for testing.
			System.out.println("****Time: " + ridesByTime.get(i).get(0).getTime());	//Here for testing.
			for(int j = 0; j < ridesByTime.get(i).size(); j++)					//Here for testing.
			{																	//Here for testing.
				total++;														//Here for testing.
				System.out.print("ID: " + ridesByTime.get(i).get(j).getID());	//Here for testing.
				System.out.println("	" + ridesByTime.get(i).get(j).getTime());//Here for testing.
			}																	//Here for testing.
		}																		//Here for testing.
		System.out.println("Total Time Slotted Rides: " + total); 				//Here for testing.
		total = 0;																//Here for testing.
		
		
		//Contains all the rides after being clustered with kmeans.
		ArrayList<ArrayList<ArrayList<Ride>>> best_clusters = makeClusterArray(ridesByTime);
		for(int i = 0; i < best_clusters.size(); i++)		//remove any empty index that was created.
		{
			for(int j = best_clusters.get(i).size()-1; j >=0; j--)
			{
				if(best_clusters.get(i).get(j).isEmpty())
				{
					best_clusters.get(i).remove(j);
				}
			}
		}
		
		
		System.out.println("\nClusters:"); 									//Here for testing.
		for(int i = 0; i < best_clusters.size(); i ++)						//Here for testing.
		{																	//Here for testing.
			for(int j = 0; j < best_clusters.get(i).size();j++)				//Here for testing.
			{																//Here for testing.
				for(int k = 0; k < best_clusters.get(i).get(j).size();k++)	//Here for testing.
				{															//Here for testing.
					System.out.println("Ride ID: " + best_clusters.get(i).get(j).get(k).getID());
					total++;												//Here for testing.
				}															//Here for testing.
			}																//Here for testing.
		}																	//Here for testing.
		System.out.println("Rides in Clusters: " + total); 					//Here for testing.
		total =0;															//Here for testing.

		//Converts the clusters of rides to Location objects (origins and destinations).
		ArrayList<ArrayList<ArrayList<Location>>> bestLocations = toLocationArray(best_clusters);
		
		
		System.out.println("\nLocations:"); 								//Here for testing.
		for(int i = 0; i < bestLocations.size(); i ++)						//Here for testing.
		{																	//Here for testing.
			for(int j = 0; j < bestLocations.get(i).size();j++)				//Here for testing.
			{																//Here for testing.
				for(int k = 0; k < bestLocations.get(i).get(j).size();k++)	//Here for testing.
				{															//Here for testing.
					System.out.println("Location ID: " + bestLocations.get(i).get(j).get(k).getDbID());
					total++;												//Here for testing.
				}															//Here for testing.
			}																//Here for testing.
		}																	//Here for testing.
		System.out.println("Locations in Clusters: " + total + "\n"); 		//Here for testing.
		total = 0;															//Here for testing.
		
		
		int index = 0;
		ArrayList<Location> route;
		int CAPACITY = 10;	//car passenger capacity.
		for(int i = 0; i <bestLocations.size();i++)
		{
			for(int j = 0; j < bestLocations.get(i).size();j++)
			{
				if(index == drivers.size())
				{
					//created routes get assigned to drivers, one after the other
					//once all drivers have been used, reset the index back to
					//the beginning.
					index = 0;
				}
				route = BestFirstBranchBound.DFsearch(CAPACITY, drivers.get(index).getCurLocation(), bestLocations.get(i).get(j));
				
				//add generated route to the drivers route ArrayList<Location>
				iterRoutes(route, drivers.get(index));
				index++;	//use next driver.
			}
		}
		
		
		for(int i=0; i < drivers.size();i++)
		{
			System.out.println("Driver " + drivers.get(i).getID() + " has " + drivers.get(i).getRoute().size() + " locations");	//Here for testing.
			total = total + drivers.get(i).getRoute().size();		//Here for testing.
			writeRoutes(drivers.get(i));	//write to database
			routeID++;	//increase routeID for the next route.
		}
		System.out.println(total/2 + " Rides were written to the database");	//Here for testing.
		System.out.println("All Routes in database.");	//Print statement to know the application has ended.
		
		
//		String endTime = new Date().toString();
//		System.out.println("Start time: " + startTime);
//		System.out.println("End Time: " + endTime);		
	}
	
	
	
	/*
	 * Takes the ArrayList of rides and Sorts them into an ArrayList of
	 * an ArrayList of Rides based on time.  
	 * 
	 */
	public static ArrayList<ArrayList<Ride>> timeSort(ArrayList<Ride> rides)
	{
		ArrayList<ArrayList<Ride>> timeSlot = new ArrayList<ArrayList<Ride>>();
		int index = 0;
		
		//For all rides in ArrayList<Ride> rides
		for(Ride r: rides)
		{
			//if empty add an index and the first ride
			if(timeSlot.size() == 0)
			{
				ArrayList<Ride> temp = new ArrayList<Ride>();
				temp.add(r);
				timeSlot.add(temp);
				index++;
			}
			
			//check if the request time is not equal to the last input,
			//add a new index and add the ride into the new time slot.
			else if( !(r.getTime()).equals((timeSlot.get(timeSlot.size()-1).get(index-1).getTime())) )
			{
				timeSlot.add(new ArrayList<Ride>());
				timeSlot.get(timeSlot.size()-1).add(r);
			}
			
			//add the ride to the current time slot, as it has the same time.
			else
			{
				timeSlot.get(timeSlot.size()-1).add(r);
			}
		}
		return timeSlot;
	}
	
	/*
	 * Stores the clusters into an ArrayList<ArrayList<ArrayList<Ride>>>.
	 * The clusters are based on the times of rides and uses kmeans.
	 * 
	 */
	
	public static ArrayList<ArrayList<ArrayList<Ride>>> makeClusterArray(ArrayList<ArrayList<Ride>> allRides)
	{
		ArrayList<ArrayList<ArrayList<Ride>>> clusters = new ArrayList<ArrayList<ArrayList<Ride>>>();
		for(int j = 0; j < allRides.size(); j++)
		{
			clusters.add(Cluster.kmeans(driversToday.length, allRides.get(j)));
		}
		return clusters;
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
   
	/*
	 * Gets the rides from the ride clusters that were created, and
	 * puts them into an ArrayList<ArrayList<ArrayList<Location>>>
	 * This way we have the origin and destinations objects to put into 
	 * the pathfind method.
	 */
   public static ArrayList<ArrayList<ArrayList<Location>>> toLocationArray(ArrayList<ArrayList<ArrayList<Ride>>> rideClusters){
	   
      ArrayList<ArrayList<ArrayList<Location>>> locationClusters = new ArrayList<ArrayList<ArrayList<Location>>>();
      for(int i = 0; i < rideClusters.size(); i++)
      {
    	  locationClusters.add(new ArrayList<ArrayList<Location>>());
    	  for(int j = 0; j < rideClusters.get(i).size(); j++)
    	  {
    		  locationClusters.get(i).add(new ArrayList<Location>());
    		  for(int k = 0; k < rideClusters.get(i).get(j).size(); k++)
    		  {
    			  //get the origin and destination.
    			  locationClusters.get(i).get(j).add(rideClusters.get(i).get(j).get(k).getOrig());
    			  locationClusters.get(i).get(j).add(rideClusters.get(i).get(j).get(k).getDest());
    			  
    		  }
    	  }
    	  
      }
      return locationClusters;
   }
   
   /*
    * Iterate through the routes from pathfind.
    * add the route to the Drivers ArrayList<Location> routes.
    */
   private static void iterRoutes(ArrayList<Location> routes, Driver drivers)
   {
	   drivers.updateRoute(routes);
   }
   
   /*
    * connects to the database and writes to the Route table.
    */
   private static void writeRoutes(Driver driver)
   {
	   MysqlConnection con = new MysqlConnection("route", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
	   ArrayList<Location> routes = driver.getRoute();
	   Iterator<Location> iterate = routes.iterator();
	   int orderInRoute = 1;
	   while (iterate.hasNext())
	   {
		   Location temp = iterate.next();
		   con.writeRouteToDB(routeID, temp.getReqID(), driver.getID(), temp.getDbID(), orderInRoute);
		   orderInRoute++;
	   }
   }
   
   
   //Get the ID of the Ride/Request.
   private static int getReqID(int i)
   {
	   return Integer.parseInt(reqDetArray[i][0]);
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
