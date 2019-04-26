package pathfinder;
import mySql.Location;
import java.util.ArrayList;
import java.util.Stack;
import java.util.PriorityQueue;

public class BestFirstBranchBound {
	
	private static final double performanceRatio = 1;
	
	public static ArrayList<Location> DFsearch(int capacity, Location origin, ArrayList<Location> points){
		Stack<Branch> branch_stack = new Stack<Branch>();
		short[] path = {};
		short[] remaining = new short[points.size()];
		short[] bestPath = null;
		double bestDistance = Double.MAX_VALUE;
		for(int i = 0; i < remaining.length; i++) {
			remaining[i] = 0;
		}
		//Initialize the queue with our first branches
		Location point;
		for(int i = 0; i < points.size(); i++) {
			point = points.get(i);
			if(point.isOrigin) {
				branch_stack.push(new Branch((byte) 1, origin.compareTo(point), path, i, remaining));
			}
		}
		//Iteratively process the queue
		Branch current_branch;
		PriorityQueue<Branch> current_branches = new PriorityQueue<Branch>();
		Branch[] branch_array = {};
		while(!branch_stack.empty()) {
			current_branch = branch_stack.pop();//Take the RIGHTMOST one for memory efficiency
			path = current_branch.getPath();
			//If leaf, compare to best path seen thus far
			if(path.length == points.size()) {
				if(current_branch.distance < bestDistance) {//Save information if this is the best one seen
					bestDistance = current_branch.distance;
					bestPath = path;
					/*for(short i : bestPath) {
						System.out.print(i + " ");
					}
					System.out.println();*/
				}
			}else {
				current_branches.clear();
				remaining = current_branch.getRemaining();
				//Consider all remaining origins, as long as we still have capacity
				if(current_branch.capacity < capacity) {
					for(int i = 0; i < remaining.length; i += 2) {
						if((remaining[i] == 0) && (((current_branch.distance + points.get(i).compareTo(points.get(path[path.length-1]))) / (path.length+1)) * points.size()) < bestDistance*performanceRatio) {//TODO update distance using distance matrix
							current_branches.add(new Branch((byte) (current_branch.capacity + ((byte) 1)), current_branch.distance + points.get(i).compareTo(points.get(path[path.length-1])),
									path, i, remaining));
						}
					}
				}
				//Drop off at destinations only if we've visited the origins
				for(int i = 1; i < remaining.length; i += 2) {
					if((remaining[i] == 0) && (remaining[i-1] == 1) && (((current_branch.distance + points.get(i).compareTo(points.get(path[path.length-1]))) / (path.length+1)) * points.size()) < bestDistance*performanceRatio) {//TODO update distance using distance matrix
						current_branches.add(new Branch((byte) (current_branch.capacity - ((byte) 1)), current_branch.distance + points.get(i).compareTo(points.get(path[path.length-1])),
								path, i, remaining));
					}
				}

				branch_array = new Branch[current_branches.size()];
				for(int i = 0; i < branch_array.length; i++) {
					branch_stack.push(current_branches.poll());
				}
			}
		}
		ArrayList<Location> optimalPath = new ArrayList<Location>(points.size() + 1);
		//optimalPath.add(origin);
		for(int i = 0; i < bestPath.length; i++) {
			optimalPath.add(points.get(bestPath[i]));
		}
		return optimalPath;
	}
	
	/*public static void main(String[] args) {
		
		PriorityQueue<Integer> test = new PriorityQueue<Integer>();
		test.add(10);
		test.add(0);
		test.add(25);
		Integer[] intarray = {};
		intarray = test.toArray(intarray);
		for(Integer i : intarray) {
			System.out.println(i);
		}
	}*/

}
