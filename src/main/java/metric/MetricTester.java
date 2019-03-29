package metric;

import java.util.ArrayList;

import mySql.Location;

public class MetricTester {

	public static void main(String[] args) {
		ArrayList<ArrayList<Location>> routes = new ArrayList<>();
		ArrayList<ArrayList<Location>> routes2 = new ArrayList<>();
		
		ArrayList<Location> r1 = new ArrayList<>();
		ArrayList<Location> r2 = new ArrayList<>();
		ArrayList<Location> r3 = new ArrayList<>();
		
		Location l1 = new Location(32.37744722, 86.30083333);
		Location l2 = new Location(34.74675833, 92.28861111);
		Location l3 = new Location(38.57657222, 121.49333333);
		Location l4 = new Location(41.76413611, 72.68277778);
		Location l5 = new Location(30.43811111, 84.28166667);
		Location l6 = new Location(43.61769722, 116.19972222);
		Location l7 = new Location(39.04800833, 95.67805556);
		Location l8 = new Location(30.45707222, 91.1875);
		Location l9 = new Location(39.164075, 118.76638889);
		Location l10 = new Location(38.33638889, 81.61222222);
		
		r1.add(l1);
		r1.add(l2);
		r1.add(l10);
		r1.add(l9);
		
		r2.add(l3);
		r2.add(l4);
		r2.add(l8);
		r2.add(l7);
		
		r3.add(l5);
		r3.add(l6);
		
		routes.add(r1);
		routes.add(r2);
		routes.add(r3);
		
		
		Location lT1 = new Location(1, 5.0, 3.0, true);
		Location lT2 = new Location(10.0, 15.0);
		Location lT3 = new Location(2.0, 8.0);
		Location lT4 = new Location(20.0, 70.0);
		ArrayList<Location> r4 = new ArrayList<>();
		ArrayList<Location> r5 = new ArrayList<>();
		r4.add(lT1);
		r4.add(lT2);
		r5.add(lT3);
		r5.add(lT4);
		routes2.add(r4);
		routes2.add(r5);
		
		double metricScore = Metric.computeScore(routes);
		double metricScore2 = Metric.computeScore(routes2);
		System.out.println(metricScore);
		System.out.println(metricScore2);
	}

}
