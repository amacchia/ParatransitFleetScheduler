package pathfinder;

import java.util.ArrayList;

import mySql.Location;

public class TestForBestFirstBranchBound {
	public static void main(String[] args)
	{
		ArrayList<Location> testArrayList = new ArrayList<Location>();
		Location origin = new Location(1, 1, 81, 10, "addressOne", true);
		testArrayList.add(origin);
		testArrayList.add(new Location(2, 2, 87, 65, "addressTwo", false));
		testArrayList.add(new Location(3, 3, 72, 75, "addressThree", false));
		testArrayList.add(new Location(4, 4, 85, 6, "addressFour", false));
		testArrayList.add(new Location(5, 5, 57, 28, "addressFive", false));
		testArrayList.add(new Location(6, 6, 31, 59, "addressSix", false));
		testArrayList.add(new Location(7, 7, 100, 47, "addressSeven", false));
		testArrayList.add(new Location(8, 8, 60, 89, "addressEight", false));
		testArrayList.add(new Location(9, 9, 36, 19, "addressNine", false));
		testArrayList.add(new Location(10, 10, 76, 11, "addressTen", false));
		
		ArrayList<Location> returnedArray = BestFirstBranchBound.DFsearch(10, origin, testArrayList);
		System.out.println(returnedArray.toString());
	}

}
