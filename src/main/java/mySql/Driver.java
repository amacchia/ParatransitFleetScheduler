package mySql;
import java.util.ArrayList;

public class Driver 
{
	private int id;
	private ArrayList<Location> route;
	private Location curLocation;
	
	public Driver(int id, ArrayList<Location> route)
	{
		this.id = id;
		this.route = route;
		this.curLocation = new Location(-1, -1, 0, 0, null, true);
	}
	
	//return driver ID
	public int getID()
	{
		return id;
	}
	
	//return driver route.
	public ArrayList<Location> getRoute()
	{
		return route;
	}
	
	//add to the end of drivers route.
	public void updateRoute(ArrayList<Location> newRoute)
	{
		for(int i = 0; i < newRoute.size(); i++)
		{
			route.add(newRoute.get(i));
		}
		updateCurLocation();
	}
	
	//updates current location of driver for pathfinding algorithm.
	public void updateCurLocation()
	{
		curLocation = route.get(route.size()-1);
	}
	
	//return current location of driver.
	public Location getCurLocation()
	{
		return curLocation;
	}
	

}
