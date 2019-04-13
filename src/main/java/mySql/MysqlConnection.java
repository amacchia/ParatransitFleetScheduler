package mySql;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
	String[][] array;
	int[] driversToday;
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
	
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
	

	/*
	 * Finds the max value of the routeID in the database.
	 * We do not want it writing routeID's with the same number to the database.
	 * We must find the highest value and then increment by 1 before we write
	 * into the database.
	 */
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
			rs = stmt.executeQuery("SELECT MAX(routeID) FROM " + input);
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
	
	
	/*
	 * Write to the route table in the database.
	 */
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
	
	
	/*
	 * Method uses a ResultSet and a String of the current day.
	 * Checks the ResultSet to find matches of the current day.
	 * If a match is found, the amount of rows in incremented.
	 * 
	 * The purpose of this method is to keep the the int[] of 
	 * drivers to a specific and exact size.  The size of the array 
	 * will be used later.
	 */
	private int howManyDrivers(ResultSet rs, String day)
	{
		int rows = 0;
		
		try 
		{
			while(rs.next())
			{
				if( (rs.getString(2)).equalsIgnoreCase(day) && (rs.getString(2).equalsIgnoreCase(day)))
				{
					rows++;  //increment rows each time it finds time within range.
				}
			}
			rs.beforeFirst();		//set the ResultSet back to the start.
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rows;
		
	}
	
	
	/*
	 * This method connects to the driverschedule table to find
	 * the selected days available drivers.  It will return an int[] of
	 * driver ID's.  These drivers will be assigned routes.
	 */
	public int[] getDrivers(String date) throws ParseException
	{
		Date date1 = inputFormat.parse(date);
		String day = dayFormat.format(date1);
		
		System.out.println("Establishing Connection with " + input + " in " + db
				+ "\n--------------------------------\n");
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " + input);
			
			driversToday = new int[howManyDrivers(rs, day)];
			System.out.println("Todays (" + day + ")  Drivers (ID's):");
			int i = 0;
			while(rs.next())
			{
				if(day.equalsIgnoreCase(rs.getString(2)))
				{
					int driverID = rs.getInt(1);
					System.out.println(driverID);
					driversToday[i] = driverID;
					i++;
				}
			}
			con.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return driversToday;
		
	}
	

	/*
	 * Can connect to an table/view in the database.
	 */
	public void connect(String date)
	{
		System.out.println("Establishing Connection with " + input + " in " + db
				+ "\n--------------------------------\n");
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " + input + " ORDER BY requestDate");	//selects which dataset to query.
			
			//Find number of columns in the table.
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			
			//Find the amount of rows that are within time range.
			int rows = 0;
			while(rs.next())
			{
				Date dbDate = rs.getDate(5, cal);
				String dbDateStr = inputFormat.format(dbDate);
				if( dbDateStr.equals(date) )
				{
					rows++;  //increment rows each time it finds time within range.
				}
			}
			
			//Give array the correct amount of rows/columns.
			array = new String[rows][col];
			rs.beforeFirst(); //Go to start of ResultSet.
			
			//add data into array if meets conditions.
			int i = 0;
			while(rs.next())
			{
				Date dbDate = rs.getDate(5, cal);
				String dbDateStr = inputFormat.format(dbDate);
				if( dbDateStr.equals(date) )
				{
					for(int j = 0; j < col; j++)
					{
						array[i][j] = rs.getString(j+1);
					}
					i++;
				}
			}
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);  
		}		
	}
	
	
	public String[][] getArray()
	{
		return array;
	}
	
}