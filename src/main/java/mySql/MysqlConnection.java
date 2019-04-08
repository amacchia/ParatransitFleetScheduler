package mySql;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//import org.apache.commons.lang3.time.DateUtils;
//import java.util.Calendar;
//import java.util.Date;


/*
 * Connect to MySQL database and store information.
 * Each instance of this class should only hold one
 * set of data from the database.
 * For other datasets/tables in the database, 
 * simply create another instance for that data.
 * 
 */
public class MysqlConnection 
{
	private String url;
	private String db;
	private String driver;
	private String userName;
    private String password;
    private String input;
    private Connection con;
	private Statement stmt;
	private ResultSet rs;
	String[][] locationArray;
	String[][] passengerArray;
	String[][] driverArray;
	String[][] rideArray;
	String[][] routeArray;
	String[][] mergeArray;
	String[][] array;
	
	//Constructor
	public MysqlConnection(String input, String url, String db, String driver,
							String userName, String password)
	{
		this.url = url;
		this.db = db;
		this.driver = driver;
		this.userName = userName;
		this.password = password;
		this.input = input;
	}	

	
	public int routeID()
	{
		System.out.println("Establishing Connection with " + input + " in " + db
				+ "\n--------------------------------\n");
		int max = 0;
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT MAX(routeID) from " + input);
			while(rs.next())
			{
				max = rs.getInt("MAX(routeID)");
			}
			rs.close();
			con.close();
			return max;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return max;
	}
	
	public void writeRouteToDB(int routeID, int requestID, int driverID,
								int locationID, int orderInRoute)
	{
		
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			String query = "INSERT INTO route (routeID, requestID, driverID, locationID, orderInRoute)" +
					" VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, routeID);
			preparedStmt.setInt(2, requestID);
			preparedStmt.setInt(3, driverID);
			preparedStmt.setInt(4, locationID);
			preparedStmt.setInt(5, orderInRoute);
			
			preparedStmt.execute();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
			
	}
	

	
	public void connect()
	{
		System.out.println("Establishing Connection with " + input + " in " + db
				+ "\n--------------------------------\n");
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + input);	//selects which dataset to query.
			
			//Find number of columns in the table.
			rs.last();  		//When using time frame, we can delete
			int rows = rs.getRow();	//These 3 Lines 
			rs.beforeFirst();		//And uncomment the others.
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			
//			//Add dates to range in query
//		    Date now = new Date();
//		    Calendar cl = Calendar. getInstance();
//		    cl.add(Calendar.HOUR, 2);
//			Date limit = DateUtils.addHours(now, 2); //add two hours
			
			//Find the amount of rows that are within time range.
//			int rows = 0;
//			while(rs.next())
//			{
//				if( (rs.getTimestamp(2)).before(limit) && (rs.getTimestamp(2).after(now)) )
//				{
//					rows++;  //increment rows each time it finds time within range.
//				}
//			}
			
			//Give mergeArray the correct amount of rows/columns.
			array = new String[rows][col];
//			rs.beforeFirst(); //Go to start of ResultSet.
			
			//add data into mergeArray if meets conditions.
			int i = 0;
			while(rs.next())
			{
//				if( (rs.getTimestamp(2)).before(limit) && (rs.getTimestamp(2).after(now)) )
//				{
					for(int j = 0; j < col; j++)
					{
						array[i][j] = rs.getString(j+1);
					}
					i++;
//				}
			}
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);  
		}
			
	}
	
//	public void writeRouteTable(ArrayList<Location> loc, ArrayList<Integer> driverID)
//	{
//		String query = " INSERT INTO Route (RouteID, locationID, requestID, orderInRoute, driverID)"
//		        + " values (?, ?, ?, ?, ?)";
//		try 
//		{
//			
//			PreparedStatement prepStmt = con.prepareStatement(query);
//			prepStmt.setInt(1, x);
//			prepStmt.setInt(2, x);
//			prepStmt.setInt(3, x);
//			prepStmt.setInt(4, x);
//			prepStmt.setInt(5, x);
//		}
//		
//	}
	
	
	public String[][] getArray()
	{
		return array;
	}
	
	//access information in locationArrayy[][]
	public String[][] getLocationArray()
	{
		return locationArray;
	}
	
	//access Info in mergeArray[][]
	public String[][] getMergeArray()
	{
		return mergeArray;
	}
	
	//access information in passengerArray[][]
	public String[][] getPassengerArray()
	{
		return passengerArray;
	}
	
	//access information in driverArray[][]
	public String[][] getDriverArray()
	{
		return driverArray;
	}
	
	//access information in rideArray[][]
	public String[][] getRideArray()
	{
		return rideArray;
	}
	
	//access information in routeArray[][]
	public String[][] getRouteArray()
	{
		return routeArray;
	}
	
	//Connect to database and get information
//	private void connect()
//	{
//		System.out.println("Establishing Connection with " + input + " in " + db
//							+ "\n--------------------------------\n");
//		try
//		{	//Establish Connection
//			Class.forName(driver);
//			con = DriverManager.getConnection(url+db, userName, password);
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("select * from " + input);	//selects which dataset to query.
//			rs.last();				//sets rs to last row.
//			int rows = rs.getRow(); //reads current row, to instantiate correct size for 2D Arrays.
//			rs.beforeFirst();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int col = rsmd.getColumnCount();
//			
//			if(input.equals("location"))
//			{
//				locationArray = new String[rows][col];
//				int i = 0;
//				while(rs.next())
//				{
//					for(int j = 0; j < col; j++)
//					{
//						locationArray[i][j] = rs.getString(j+1);
//					}
//					i++;
//				}
//			}
//			else if(input.equals("driver"))
//			{
//				driverArray = new String[rows][col];
//				int i = 0;
//				while(rs.next())
//				{
//					for(int j = 0; j < col; j++)
//					{
//						driverArray[i][j] = rs.getString(j+1);
//					}
//					i++;
//				}
//			}
//			else if(input.equals("passenger"))
//			{
//				passengerArray = new String[rows][col];
//				int i = 0;
//				while(rs.next())
//				{
//					for(int j = 0; j < col; j++)
//					{
//						passengerArray[i][j] = rs.getString(j+1);
//					}
//					i++;
//				}
//			}
//			else if(input.equals("ride"))
//			{
//				rideArray = new String[rows][col];
//				int i = 0;
//				while(rs.next())
//				{
//					for(int j = 0; j < col; j++)
//					{
//						rideArray[i][j] = rs.getString(j+1);
//					}
//					i++;
//				}
//			}
//			else if(input.equals("route"))
//			{
//				routeArray = new String[rows][col];
//				int i = 0;
//				while(rs.next())
//				{
//					for(int j = 0; j < col; j++)
//					{
//						routeArray[i][j] = rs.getString(j+1);
//					}
//					i++;
//				}
//			}
//			con.close();
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);  
//		}
//	}
	
}