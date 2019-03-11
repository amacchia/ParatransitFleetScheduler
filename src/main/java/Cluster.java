import mySql.Ride;
import java.util.ArrayList;
import java.lang.Math;

public class Cluster{

   public static ArrayList<ArrayList<Ride>> randomCluster(ArrayList<Ride> all_rides){
      ArrayList<ArrayList<Ride>> clusters = new ArrayList<ArrayList<Ride>>();
      ArrayList<Ride> cluster;
      for(int i = 0; i < (all_rides.size() / 3); i++){//Populate clusters with one element so none are empty
         cluster = new ArrayList<Ride>();
         cluster.add(all_rides.get(0));
         clusters.add(cluster);
         all_rides.remove(0);
      }
      for(int i = 0; i < all_rides.size(); i++){//Randomly assign Rides into clusters
         clusters.get((int) Math.random()*clusters.size()).add(all_rides.get(0));
         all_rides.remove(0);
      }
      return clusters;
   }

}