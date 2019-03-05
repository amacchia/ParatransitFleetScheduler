package mySql;
import java.sql.*;
import java.util.Scanner;

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
	
	//Constructor
	public MysqlConnection(String url, String db, String driver,
							String userName, String password)
	{
		this.url = url;
		this.db = db;
		this.driver = driver;
		this.userName = userName;
		this.password = password;
		
		Scanner sc = new Scanner(System.in);  // Reading from System.in
		System.out.println("Which dataset would you like to connect to? (case sensitive)");
		input = sc.next();
		sc.close();
		
		connect(); //on instantiation automatically connect to database.
	}
	
	//Connect to database and get information
	private void connect()
	{
		System.out.println("Establishing Connection with " + input + " in " + db
							+ "\n--------------------------------");
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + input);
			rs.last();				//sets rs to last row.
			int rows = rs.getRow(); //reads current row, to instantiate correct size for 2D Arrays.
			rs.beforeFirst();		//set rs back to beginning 
			if(input.equals("location"))
			{
				locationArray = new String[rows][8];
				int i = 0;
				while(rs.next())
				{				
					locationArray[i][0] = rs.getString(1);
					locationArray[i][1] = rs.getString(2);
					locationArray[i][2] = rs.getString(3);
					locationArray[i][3] = rs.getString(4);
					locationArray[i][4] = rs.getString(5);
					locationArray[i][5] = rs.getString(6);
					locationArray[i][6] = rs.getString(7);
					locationArray[i][7] = rs.getString(8);
					i++;
				}
			}
			else if(input.equals("driver"))
			{
				driverArray = new String[rows][4];
				int i = 0;
				while(rs.next())
				{
					driverArray[i][0] = rs.getString(1);
					driverArray[i][1] = rs.getString(2);
					driverArray[i][2] = rs.getString(3);
					driverArray[i][3] = rs.getString(4);
					i++;
				}
			}
			else if(input.equals("passenger"))
			{
				passengerArray = new String[rows][6];
				int i = 0;
				while(rs.next())
				{
					passengerArray[i][0] = rs.getString(1);
					passengerArray[i][1] = rs.getString(2);
					passengerArray[i][2] = rs.getString(3);
					passengerArray[i][3] = rs.getString(4);
					passengerArray[i][4] = rs.getString(5);
					passengerArray[i][5] = rs.getString(6);
					i++;
				}
			}
			else if(input.equals("ride"))
			{
				rideArray = new String[rows][5];
				int i = 0;
				while(rs.next())
				{
					rideArray[i][0] = rs.getString(1);
					rideArray[i][1] = rs.getString(2);
					rideArray[i][2] = rs.getString(3);
					rideArray[i][3] = rs.getString(4);
					rideArray[i][4] = rs.getString(5);
					i++;
				}
			}
			else if(input.equals("route"))
			{
				routeArray = new String[rows][4];
				int i = 0;
				while(rs.next())
				{
					routeArray[i][0] = rs.getString(1);
					routeArray[i][1] = rs.getString(2);
					routeArray[i][2] = rs.getString(3);
					routeArray[i][3] = rs.getString(4);
				}
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);  
		}
	}
	
	//access information in locationArrayy[][]
	public String[][] getLocationArray()
	{
		return locationArray;
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
}