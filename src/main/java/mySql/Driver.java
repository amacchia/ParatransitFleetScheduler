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
	
	public int getID()
	{
		return id;
	}
	
	public ArrayList<Location> getRoute()
	{
		return route;
	}
	
	public void updateRoute(ArrayList<Location> newRoute)
	{
		//System.out.println("Driver " + this.id + " adding:");
		for(int i = 0; i < newRoute.size(); i++)
		{
			route.add(newRoute.get(i));
			//System.out.println(newRoute.get(i));
		}
		//System.out.println("");
	}
	
	public void updateCurLocation()
	{
		curLocation = route.get(route.size()-1);
	}
	
	public Location getCurLocation()
	{
		return curLocation;
	}
	

}
