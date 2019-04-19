package CSVGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import mySql.MysqlConnection;
import java.sql.*;
import java.sql.Date;

public class csvGenerator {
	

	
	public static void main(String[] args) {
			
		Connection con;
		Statement stmt;
		ResultSet rs;
		MysqlConnection det = new MysqlConnection("morris_location", "jdbc:mysql://3.81.8.187:3306/", "rideshare",
				"com.mysql.cj.jdbc.Driver", "root", "senproj19");
		int minLocationID = 0;
		int maxLocationID = 0;
		int maxUserID = 0;
		
		System.out.println("Establishing Connection with " + det.getInput() + " in " + det.getDb()
				+ "\n--------------------------------\n");
	
		try
		{	
			//Establish Connection
			Class.forName(det.getDriver());
			con = DriverManager.getConnection(det.getUrl() + det.getDb(), det.getUserName(), det.getPassword());
			stmt = con.createStatement();
			
			//First Query for Max & Min LocationIDs
			rs = stmt.executeQuery("SELECT MIN(locationID), MAX(locationID) from " + det.getInput());
			while(rs.next())
			{			
				minLocationID = rs.getInt("MIN(locationID)");
				maxLocationID = rs.getInt("MAX(locationID)");
			}
			System.out.println("Range of LocationIDs: " + minLocationID + " - " + maxLocationID );
			
			rs.close();
			
			//Second Query for Range of UserIDs
			rs = stmt.executeQuery("SELECT MAX(userID) FROM rideshare.user");
			while(rs.next())
			{
				maxUserID = rs.getInt("MAX(userID)");
			}
			System.out.println("Range of UserID's: 1 - " + maxUserID);
			rs.close();
			
			con.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		//Get all of the necessary input
		Scanner sc = new Scanner(System.in);
		System.out.println("How many requests would you like to generate?");
		int numOfRequests = Integer.parseInt(sc.nextLine());
		
		
		//Date either current day or parameter
		System.out.println("What is the date for the requests? (yyyy-mm-dd)");
		String date = sc.nextLine();
		
		
		//Instantiate and populate a list of specified request times between 9am and 5pm.
		ArrayList<String> requestTimes = new ArrayList<String>();
		requestTimes.add("09:00:00");
		requestTimes.add("09:30:00");
		requestTimes.add("10:00:00");
		requestTimes.add("10:30:00");
		requestTimes.add("11:00:00");
		requestTimes.add("11:30:00");
		requestTimes.add("12:30:00");
		requestTimes.add("12:00:00");
		requestTimes.add("13:30:00");
		requestTimes.add("13:00:00");
		requestTimes.add("14:30:00");
		requestTimes.add("14:30:00");
		requestTimes.add("15:00:00");
		requestTimes.add("15:30:00");
		requestTimes.add("16:00:00");
		requestTimes.add("16:30:00");
		requestTimes.add("17:00:00");
		
		
		//May just be hardcoded in the final design on the webserver.
		System.out.println("Choose a name for the output file. Example: C:/Users/raypi/test.csv");
		String fileName = "BadFileName.txt";
		try {
			fileName = sc.nextLine();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		sc.close();
		
		//Will be used to make sure that a route dosen't go from a point to the same point
		int originID;
		int destinationID;
		
		//This will be used to generate random userIDs.
		Random rand = new Random();
		int randUserID;
		int randIndex;
		String time;
		
		try (PrintWriter writer = new PrintWriter(new File(fileName))) {

			//Generate the first row of the CSV
		    StringBuilder sb = new StringBuilder();
		     
		    sb.append("userID,");
		    sb.append("originID,");
		    sb.append("destinationID,");
		    sb.append("requestDate");
		    sb.append('\n');
		    
		    //Loop to generate the rest of the csv. 
		    while(numOfRequests > 0)
		    {
		    	//Generate the userID.
		    	randUserID = rand.nextInt(maxUserID) + 1;
		     	
		    	//Generate the originID and destinationID. until they're different.
		    	//WARNING: If the max and min are equal, this is an infinite loop. 
		    	do {
		    		//Generate the originID
		    		originID = rand.nextInt(maxLocationID + 1 - minLocationID) + minLocationID;
		    		//Generate the destinationID
		    		destinationID = rand.nextInt(maxLocationID + 1 - minLocationID) + minLocationID;
		    	}
		    	while (originID == destinationID); //Request cannot be To/From Same location
		    	
		    	//Create random index to select a request time
		    	randIndex = rand.nextInt(requestTimes.size() - 1);
		    	time = requestTimes.get(randIndex);
		    	
		    	//Append the values to the CSV
		    	sb.append(randUserID + ",");
		    	sb.append(originID + ",");
		    	sb.append(destinationID + ",");
		    	sb.append(date + " " + time); //(yyyy-mm-dd hh:mm:ss) Date Format
		    	sb.append("\n");
		    	
		    	//Decrement request count
		    	numOfRequests--;
		    }

		    writer.write(sb.toString());

		    System.out.println("Done!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	
	}
}
