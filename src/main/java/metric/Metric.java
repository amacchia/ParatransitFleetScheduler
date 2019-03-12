package metric;

import java.util.ArrayList;

import mySql.Location;

public class Metric {
	
	public static double computeScore(ArrayList<ArrayList<Location>> routes) {
		double[] avgDistances = new double[routes.size()];
		int indexForAvgDistances = 0;
		for (ArrayList<Location> list : routes) {
			double avgDistance = calculateAvgDistance(list);
			avgDistances[indexForAvgDistances] = avgDistance;
			indexForAvgDistances++;
		}
		
		double sum = 0;
		for (int i = 0; i < avgDistances.length; i++) {
			sum += avgDistances[i];
		}
		return sum / routes.size(); // Avg distance of all routes
	}
	
	
	private static double calculateAvgDistance(ArrayList<Location> route) {
		double distanceSum = 0;
		for (int i = 0; i < route.size() - 1; i++) {
			Location firstLocation = route.get(i);
			Location secondLocation = route.get(i + 1);
			distanceSum += firstLocation.compareTo(secondLocation);
		}
		System.out.println("Dist Sum: " + distanceSum);
		return distanceSum / (route.size() - 1); // Minus 1 becasue there are N Locations and N-1 Distances
	}
}