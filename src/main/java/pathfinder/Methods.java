package pathfinder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import mySql.Location;

import java.lang.Math;

public class Methods {
	
	
	public static void pathfind(Location starter, ArrayList<Location> points, ArrayList<Location> route, 
			 ArrayList<Location> reserves, int cap, int pass)
	{
			if(points.isEmpty())										//Putting this first, as it's the exit of the recusion. If the points list
				{
					String result = printroute(route);										//has no new points to go to, time to print the route.
					System.out.println(result);
					return;
				}
			ArrayList<Location> rte = route;							//Stores route as it's being developed, array for easy printing later
			ArrayList<Location> pts = points;							//Stores available locations for next in route, linked for easy removal
	//		Iterator<Location> iterator = pts.iterator();				//used to convert linked list pts into arraylist maths for distance later
	//		ArrayList<Location> maths = new ArrayList<Location>();		
	//		while (iterator.hasNext())													
	//		{	
	//			maths.add(iterator.next());								//conversion of pts into maths.
	//		}
			ArrayList<Location> reserve = reserves;					//Reserve list of destinations not able to be gone to. like destinations before thier corresponding origin has been met, or origins while the car is full
			Location next = minDistance(starter, pts);
			int idx = pts.indexOf(next);								//index of the next potential destination in the route



			if(next.isOrigin)
			{
				if(pass < cap)
				{
					rte.add(next);								//add point to next slot of route, to print later
					pts.remove(idx);							//removes the current location from the next iteration, preventing revisiting points
					pass++;
					Iterator<Location> iterate = reserve.iterator();

					while (iterate.hasNext())
					{
							pts.add(iterate.next());			//re-adds previously removed points back into avaiable points.
					}
					reserve = new ArrayList<Location>();		//clears out reserves after they are added
					pathfind(next, pts, rte, reserve, cap, pass);		//Recursively calls method now with added passenger, and one less point available
					return;
				}
				else											//Car is at capacity, can't go to this location
				{
					reserve.add(next);							//adds current point to reserve list, to be added after destination is reached
					pts.remove(idx);							//removes point from current pool of valid next destinations
					pathfind(next, pts, rte, reserve, cap, pass);
					return;
				}
			}
			else
			{
				Iterator<Location> iterates = pts.iterator();

				while (iterates.hasNext())
					{
						Location test = iterates.next();
						if((test.getID() == next.getID()) && test.isOrigin)			//getname on both to match them as origin-dest pair, is origin on next to see if it's not the destination
						{
							reserve.add(next);							//adds current point to reserve list, to be added after destination is reached
							pts.remove(idx);							//removes point from current pool of valid next destinations
							pathfind(next, pts, rte, reserve, cap, pass);
							return;
						}						
					}
				rte.add(next);								//if above pathfind wasn't hit, the destination is valid to go to
				pts.remove(idx);							//removes the current location from the next iteration, preventing revisiting points
				Iterator<Location> iterate = reserve.iterator();

				while (iterate.hasNext())
				{
						pts.add(iterate.next());			//re-adds previously removed points back into avaiable points.
				}
				reserve = new ArrayList<Location>();		//clears out reserves after they are added
				pass--;
				pathfind(next, pts, rte, reserve, cap, pass);
				return;
			}


	}

	public static String printroute(ArrayList<Location> finals)
	{
		String result = "start";
		Iterator<Location> iterate = finals.iterator();

		while (iterate.hasNext())
		{
			Location test = iterate.next();
			if(test.isOrigin)
			{
				result = result + ", "+ test.getID() + "O";
			}
			else
				result = result + ", "+ test.getID() + "D";
		}

		return result;
	}



	public static Location minDistance(Location start, ArrayList<Location> points)
	{
		double min = 10000000;
		ArrayList<Location> mini = new ArrayList<Location>();
		Iterator<Location> iterator = points.iterator();
		//int pointsidx = 0;		dont need to keep track of the size				//starts at 0 because start location isn't in list
		int resultidx = 0;

		while (iterator.hasNext())													//should iterate only though inputed values of the arraylist, as it can have less 
		{																			//values in it than it's full size is. trying to cut out on null exceptions. hopefully
			Location test = iterator.next();
			double dist = distance(start.getLat(), start.getLong(), 
									test.getLat(), test.getLong());					//gets the distance between the current point and the possible next point
			mini.add(points.get(mini.size()));		//pointsidx));										//supposed to add only locations besides the place you are currently at
			//pointsidx++;															//increases index pointer
			if(dist < min)															//if the distance between these points is the smallest so far
			{	
				min = dist;															//set it as the new minimum
				resultidx = mini.size() - 1;									//set the result index pointer to points index - 1, as it will have 1 less item in it compared to the input			
			}																		//otherwise, it doesn't update the result pointer, and moves onto the next comparison
		}

		return points.get(resultidx);													//should return the point that gave the shortest distance between it and the start position
	}


	private static double distance(double latitude1, double longitude1,
			double latitude2, double longitude2)
	{
		double distance;
		double x = latitude2 - latitude1;
		double y = longitude2 - longitude1;
		distance = Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
		return distance;
	}
}
