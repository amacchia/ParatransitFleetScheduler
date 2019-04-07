package pathfinder;
import mySql.Location;
import java.util.ArrayList;
import metric.Metric;

public class LocalSearch{
   
   public static ArrayList<Location> search(Location origin, ArrayList<Location> all_points, int heuristic_depth){//Origin is the Driver's Location and all_points is an ordered representation of rides- origins followed immediately by their corresponding destination
      //Naively explore a single path, save it, and compute its distance metric
      ArrayList<Location> path = (ArrayList<Location>) all_points.clone();
      double minDistance = Metric.pathDistance(path);
      double currentDistance;
      Location currentLocation;
      //Each index of the tree represents a different layer and unexplored branches are stored there
      ArrayList<ArrayList<Location>> tree = new ArrayList<ArrayList<Location>>(all_points.size());
      //Initialize first layer of the tree and helper data structures
      ArrayList<Location> canVisit = new ArrayList<Location>((all_points.size() / 2) + 1);
      ArrayList<Location> futureVisit = new ArrayList<Location>((all_points.size() / 2) + 1);
      Location[] lookahead = new Location[heuristic_depth + 1];
      for(Location point : all_points){
         if(point.isOrigin){
            canVisit.add(point);
         }else{
            futureVisit.add(point);
         }
      }
      tree.set(0, (ArrayList<Location>) canVisit.clone());
      int layer = 0;
      while(tree.get(0).size() > 0){//While there are branches left to explore
         if((tree.get(layer).size() == 0) || layer == tree.size()){//All branches checked at current level OR end of tree
            layer--;
            //TODO prune upper layer
            continue;
         }
      
      }
      return path;
   }
   
   private static double greedyHeuristic(ArrayList<Location> canVisit, ArrayList<Location> futureVisit, Location[] lookahead){
      return -1;
   }

}