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
		
		System.out.println("Which dataset would you like to connect to? (case sensitive)");
		Scanner sc = new Scanner(System.in);  // Reading from System.in
		input = sc.next();
		
		connect(); //on instantiation automatically connect to database.
	}
	
	//Connect to database and get information
	private void connect()
	{
		System.out.println("Establishing Connection with " + input + " in " + db
							+ "\n--------------------------------\n");
		try
		{	//Establish Connection
			Class.forName(driver);
			con = DriverManager.getConnection(url+db, userName, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + input);	//selects which dataset to query.
			rs.last();				//sets rs to last row.
			int rows = rs.getRow(); //reads current row, to instantiate correct size for 2D Arrays.
			rs.beforeFirst();
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			System.out.println(col);			//could use to set column size with a variable
												//instead of hard coding the amount of columns.
			if(input.equals("location"))
			{
				locationArray = new String[rows][col];
				int i = 0;
				while(rs.next())
				{
					for(int j = 0; j < col; j++)
					{
						locationArray[i][j] = rs.getString(j+1);
					}
					i++;
				}
			}
			else if(input.equals("driver"))
			{
				driverArray = new String[rows][col];
				int i = 0;
				while(rs.next())
				{
					for(int j = 0; j < col; j++)
					{
						driverArray[i][j] = rs.getString(j+1);
					}
					i++;
				}
			}
			else if(input.equals("passenger"))
			{
				passengerArray = new String[rows][col];
				int i = 0;
				while(rs.next())
				{
					for(int j = 0; j < col; j++)
					{
						passengerArray[i][j] = rs.getString(j+1);
					}
					i++;
				}
			}
			else if(input.equals("ride"))
			{
				rideArray = new String[rows][col];
				int i = 0;
				while(rs.next())
				{
					for(int j = 0; j < col; j++)
					{
						rideArray[i][j] = rs.getString(j+1);
					}
					i++;
				}
			}
			else if(input.equals("route"))
			{
				routeArray = new String[rows][col];
				int i = 0;
				while(rs.next())
				{
					for(int j = 0; j < col; j++)
					{
						routeArray[i][j] = rs.getString(j+1);
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