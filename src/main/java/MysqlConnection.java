package mySql;
import java.sql.*;

public class MysqlConnection 
{
	
	public static void main(String args[])
	{
		/*
		 * Just a test to see how data can be stored.  Using 2D array for now
		 * Data could be stored by keeping table format.
		 * As long as we know the type of information stored in []['this'] index,
		 * we can extract the information out of it.
		 * All data in ['this'][] index would be for that specific instance.
		 * 
		 * Example: locationArray[0][2] would give the latitude info
		 * 			for the first data point in the table.
		 * 			locationArray[1][3] would give the longitude info
		 * 			for the second data point in the table.

		 * 
		 */
		String[][] locationArray = new String[16][8]; //hard coded numbers for now.
		String[][] passengerArray = new String[15][6];
		String[][] driverArray = new String[16][8];
		String[][] rideArray = new String[10][5];
		String[][] routeArray = new String[10][3];
		
		final String url = "jdbc:mysql://localhost:3306/";
		final String db = "rideshare";
		final String driver = "com.mysql.cj.jdbc.Driver";
		final String userName = "root";
	    final String password = "";
		try
		{	//Establish Connection with ridershare.location table
			System.out.println("Establish Connection with Location Table.\n"
					+ "---------------------------");
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url+db, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from location");
//			rs.last();			//Can use this code to make 2D Arrays correct size.
//			int rows = rs.getRow();
//			String[][] locationArray = new String[rows][8];
			int i = 0;
			while(rs.next())
			{
				System.out.println(rs.getInt(1) + ",	" + rs.getString(2)+",	" + rs.getDouble(3)
								+ ",	" + rs.getDouble(4) + ",	" + rs.getString(5) + ",	" + 
								rs.getString(6) + ",	" + rs.getString(7) + ",	" + rs.getString(8));
				
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
				con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);  
		}
		
		try
		{	//Establish Connection with ridershare.passenger table
			System.out.println("\n\n\nEstablish Connection with Driver Table.\n"
					+ "-----------------------------");
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url+db, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from passenger");
			int i = 0;
			while(rs.next())
			{
				System.out.println(rs.getString(1)+",	"+rs.getString(2)+",	"+rs.getString(3)
								+ ",	" + rs.getString(4) + ",	" + rs.getInt(5) + ",	" + 
								rs.getInt(6)); 
				passengerArray[i][0] = rs.getString(1);
				passengerArray[i][1] = rs.getString(2);
				passengerArray[i][2] = rs.getString(3);
				passengerArray[i][3] = rs.getString(4);
				passengerArray[i][4] = rs.getString(5);
				i++;
			}
				con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);  
		}
		
		try
		{	//Establish Connection with ridershare.driver table (Currently Empty)
			System.out.println("\n\n\nEstablish Connection with Driver Table(Currently Empty).\n"
					+ "-----------------------------");
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url+db, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from driver");
			int i =0;
			while(rs.next())
			{
				System.out.println(rs.getString(1)+",	"+rs.getString(2)+",	"+rs.getString(3)
								+ ",	" + rs.getString(4));
				driverArray[i][0] = rs.getString(1);
				driverArray[i][1] = rs.getString(2);
				driverArray[i][2] = rs.getString(3);
				i++;
			}
				con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);  
		}
		
		try
		{	//Establish Connection with ridershare.ride table
			System.out.println("\n\n\nEstablish Connection with Ride Table.\n"
					+ "-----------------------------");
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url+db, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ride");
			int i = 0;
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+",	"+ rs.getInt(2)+",	"+ rs.getInt(3)
								+ ",	" + rs.getInt(4) + ",	" + rs.getString(5));
				rideArray[i][0] = rs.getString(1);
				rideArray[i][1] = rs.getString(2);
				rideArray[i][2] = rs.getString(3);
				rideArray[i][3] = rs.getString(4);
				rideArray[i][4] = rs.getString(5);
				i++;
			}
				con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);  
		}
		
		try
		{	//Establish Connection with ridershare.route table (Currently Empty)
			System.out.println("\n\n\nEstablish Connection with Route Table(Currently Empty).\n"
					+ "-----------------------------");
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url+db, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from route");
			int i = 0;
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+",	"+ rs.getString(2)+",	"+ rs.getInt(3));
				routeArray[i][0] = rs.getString(1);
				routeArray[i][1] = rs.getString(2);
				routeArray[i][2] = rs.getString(3);
				i++;
			}
				con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);  
		}
		
		//Print out the Arrays to ensure data was properly input.
		try
		{
			System.out.println("\n\n\nPrinting Location Array\n--------------------");
			for(int i = 0; i<locationArray.length;i++)
			{
				for(int j = 0; j<locationArray[0].length;j++)
				{
					System.out.print(locationArray[i][j]);
					if(j < locationArray[0].length-1)	//comma after every column
					{								//except last column.
						System.out.print(",	");
					}
				}
				System.out.println();
			}
			
			System.out.println("\n\n\nPrinting Passenger Array\n--------------------");
			for(int i = 0; i<passengerArray.length;i++)
			{
				for(int j = 0; j<passengerArray[0].length;j++)
				{
					System.out.print(passengerArray[i][j]);
					if(j < passengerArray[0].length-1)	//comma after every column
					{								//except last column.
						System.out.print(",	");
					}
				}
				System.out.println();
			}
			
			System.out.println("\n\n\nPrinting Driver Array\n--------------------");
			for(int i = 0; i<driverArray.length;i++)
			{
				for(int j = 0; j<driverArray[0].length;j++)
				{
					System.out.print(driverArray[i][j]);
					if(j < driverArray[0].length-1)	//comma after every column
					{								//except last column.
						System.out.print(",	");
					}
				}
				System.out.println();
			}
			
			System.out.println("\n\n\nPrinting Ride Array\n--------------------");
			for(int i = 0; i<rideArray.length;i++)
			{
				for(int j = 0; j<rideArray[0].length;j++)
				{
					System.out.print(rideArray[i][j]);
					if(j < rideArray[0].length-1)	//comma after every column
					{								//except last column.
						System.out.print(",	");
					}
				}
				System.out.println();
			}
			
			System.out.println("\n\n\nPrinting Route Array\n--------------------");
			for(int i = 0; i<routeArray.length;i++)
			{
				for(int j = 0; j<routeArray[0].length;j++)
				{
					System.out.print((routeArray[i][j]));
					if(j < routeArray[0].length-1)	//comma after every column
					{								//except last column.
						System.out.print(",	");
					}
				}
				System.out.println();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}