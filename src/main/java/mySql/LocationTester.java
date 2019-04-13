package mySql;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * reqDetArray[x][4] --> requestDate
	 * reqDetArray[x][5] --> originLatitude
	 * reqDetArray[x][6] --> originLongitude
	 * reqDetArray[x][7] --> originAddress
	 * reqDetArray[x][8] --> destinationLatitude
	 * reqDetArray[x][9] --> destinationLongitude
	 * reqDetArray[x][10] --> destinationAddress
	 * 
	 */
	static ArrayList<ArrayList<Ride>> ridesByTime;			//Holds all of the rides, each column is an hour of the day(in order).
	static String[][] reqDetArray;	//Holds details from the morris_ridedetails view
	static int[] driversToday;		//contains to ID's of the drivers that are driving the current day.
	static int routeID = -1; 		//start at -1 so if there is an error, it can be easily spotted in the database.
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
		ridesByTime = timeSort(rides);
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
		ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters = makeClusterArray(ridesByTime, numRepeat);
		System.out.println("Cluster: " + clusters.size());
		int p = 1;
		for(int i = 0; i < clusters.size(); i++)
		{
			for(int j = 0; j < clusters.get(i).size(); j++)
			{
				System.out.println("\nCluster " + p + " size: " + clusters.get(i).get(j).size());
				for(int k = 0; k < clusters.get(i).get(j).size(); k++)
				{
					for(int x = 0; x < clusters.get(i).get(j).get(k).size(); x++)
					{
						System.out.println("Cluster " + p + ": " + clusters.get(i).get(j).get(k).get(x).getID());
					}
				}
			}
			p++;
		}
		
		double[][] scores = makeScoreArray(clusters); //double[row][col] column is a time slot, row contains recalculated scored for that time slot.
		System.out.println("scores[0].length: " + scores[0].length);
		System.out.println("scoreslength: " + scores.length);
		for(int i = 0; i < scores[0].length; i++)
		{
			for (int j = 0; j < scores.length;j++)
			{
				System.out.println(scores[j][i]);
			}
			
		}
		ArrayList<ArrayList<ArrayList<Ride>>> best_clusters = bestClusters(scores, clusters);
		
		

		
//      System.out.println("Score1, Score2, and Score3 for the clusters: "+ score1 + " " + score2 + " " + score3 +"\n");
//      ArrayList<ArrayList<Ride>> bestclusters;
//      if(score1 > score2){
//         if(score1 > score3){
//            bestclusters = clusters1;
//         }else{
//            bestclusters = clusters3;
//         }
//      }else{
//         if(score2 > score3){
//            bestclusters = clusters2;
//         }else{
//            bestclusters = clusters3;
//         }
//      }
		for(int i = 0; i < best_clusters.size(); i++)
		{
			System.out.println("Cluster Number: " + (i+1));
			for(int j = 0; j < best_clusters.get(i).size(); j++)
			{
				System.out.println(best_clusters.get(i).get(j) +"\n");
			}
		}
//		ArrayList<ArrayList<Location>> bestLocations = toLocationArray(bestclusters);
//		for(int i = 0; i < bestLocations.size(); i++)
//		{
//			ArrayList<Location> route = Methods.pathfind(new Location(-1, -1, 0, 0, null, true), bestLocations.get(i), new ArrayList<Location>(),
//					new ArrayList<Location>(), 2, 0);
//			iterRoutes(route, driversToday[i]);  	//write the routes that were just generated 
//													//into the route table in the database
//		}
//		System.out.println("Write to Route table completed."); 	//Print statement to know when all routes
																//have been written to the database.
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
	
	public static ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> makeClusterArray(ArrayList<ArrayList<Ride>> allRides, int numRepeat)
	{
		ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters = new ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>>();
		for(int i = 0; i < allRides.size(); i++)
		{
			clusters.add(new ArrayList<ArrayList<ArrayList<Ride>>>());
			for(int j = 0; j < numRepeat; j++)
			{
				clusters.get(i).add(Cluster.kmeans(driversToday.length, allRides.get(i)));
			}
		}
		return clusters;
	}
	
	
	/*
	 * computes the scores of the clusters.
	 */
	public static double[][] makeScoreArray(ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters)
	{
		double[][] scores = new double[clusters.get(0).size()][clusters.size()];
		for(int i = 0; i < clusters.get(0).size(); i++)
		{
			for(int j = 0; j<clusters.size(); j++)
			{
				scores[i][j] = Metric.computeScore(toLocationArray(clusters.get(j)));
			}
		}
		return scores;
	}
	
	/*
	 * Compares the scores of each cluster
	 */
	public static ArrayList<ArrayList<ArrayList<Ride>>> bestClusters(double[][] scores,
										ArrayList<ArrayList<ArrayList<ArrayList<Ride>>>> clusters)
	{
		ArrayList<ArrayList<ArrayList<Ride>>> best_clusters = new ArrayList<ArrayList<ArrayList<Ride>>>();		
		for(int i = 0; i<scores[0].length; i++)
		{
			double max = 0;
			int maxIndexi = 0;
			int maxIndexj =0;
			for(int j = 0; j < scores.length; j++)
			{
				if(j != scores.length-1)
				{
					
					double x = scores[j][i];
					double y = scores[j+1][i];
					if( (x > y) && (x > max) )
					{
						max = x;
						maxIndexi = i;
						maxIndexj = j;
					}
					if( (y > x) && (y > max) )
					{
						max = (y);
						maxIndexi = i;
						maxIndexj= j;
					}
					if( x == y)
					{
						if(x >= max)
						{
							max = x;
							maxIndexi = i;
							maxIndexj = j;
						}
					}
				}
			}
			System.out.println("Max: " + max);
			best_clusters.add(clusters.get(maxIndexi).get(maxIndexj));
			max = 0;
			maxIndexi = 0;
			maxIndexj = 0;
			
		}
		return best_clusters;
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
//      for(int i = 0; i < rideClusters.size(); i++){
//         locationClusters.add(new ArrayList<Location>());
//         for(int j = 0; j < rideClusters.get(i).size(); j++){
//        	 locationClusters.get(i).add(rideClusters.get(i).get(j).getOrig());
//        	 locationClusters.get(i).add(rideClusters.get(i).get(j).getDest());
//         }
//      }
      
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
      for(int x = 0; x < locationClusters.size();x++)
      {
    	  System.out.println("New index****");
    	  for(int y=0; y< locationClusters.get(x).size();y++)
    	  {
    		  System.out.println(locationClusters.get(x).get(y).getDbID());
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
