package mySql;
import java.util.ArrayList;

public class Driver 
{
	private int id;
	private ArrayList<Location> route;
	
	public Driver(int id, ArrayList<Location> route)
	{
		this.id = id;
		this.route = route;
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
		for(int i = 0; i < newRoute.size(); i++)
		{
			route.add(newRoute.get(i));
		}
	}
	

}
