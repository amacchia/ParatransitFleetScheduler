package mySql;
import mySql.Ride;
import java.util.ArrayList;
import java.lang.Math;

public class Cluster{

   public static ArrayList<ArrayList<Ride>> randomCluster(ArrayList<Ride> all_rides){
      ArrayList<ArrayList<Ride>> clusters = new ArrayList<ArrayList<Ride>>();
      ArrayList<Ride> cluster;
      int size = all_rides.size() / 3;
      for(int i = 0; i < size; i++){//Populate clusters with one element so none are empty
         cluster = new ArrayList<Ride>();
         cluster.add(all_rides.get(0));
         clusters.add(cluster);
         all_rides.remove(0);
      }
      for(int i = 0; i < all_rides.size(); i++){//Randomly assign Rides into clusters
         clusters.get((int) (Math.random()*clusters.size())).add(all_rides.get(0));
         all_rides.remove(0);
      }
      return clusters;
   }
   
   public static ArrayList<ArrayList<Ride>> kmeans(int k, ArrayList<Ride> all_rides){
      if(k > all_rides.size()){
         k = all_rides.size();
      }
      ArrayList<ArrayList<Ride>> clusters = new ArrayList<ArrayList<Ride>>();
      ArrayList<Ride> temp_copy = (ArrayList<Ride>) all_rides.clone();//Create a shallow copy to randomly pick and remove from
      int randomInt;
      for(int i = 0; i < k; i++){
         randomInt = (int) (Math.random()*temp_copy.size());
         clusters.add(new ArrayList<Ride>());
         clusters.get(i).add(temp_copy.get(randomInt));//Randomly select a centroid
         temp_copy.remove(randomInt);//Remove to prevent identical centroids
      }
      for(Ride ride: all_rides){
         clusters.get((int) (Math.abs(closestIndex(clusters, ride))) - 1).add(ride);//Assign each ride to the closest centroid
         //Value is likely negative, since clusters are near-empty, so calculate the absolute value
      }
      int iterations = 0, correct_index;
      boolean changedClusters = true;
      double[] rideValues = new double[4];
      ArrayList<Ride> cluster;
      Ride r;
      ArrayList<ArrayList<Ride>> temp_cluster;
      while(changedClusters && (iterations < 40)){//Guarantee convergence with a good approximation to ensure fast computational time
         changedClusters = false;
         iterations++;
         //Recompute centroids
         for(int i = 0; i < clusters.size(); i++){
            rideValues[0] = 0;
            rideValues[1] = 0;
            rideValues[2] = 0;
            rideValues[3] = 0;
            cluster = clusters.get(i);
            for(int j = 1; j < cluster.size(); j++){
               r = cluster.get(j);
               rideValues[0] += r.getOrig().getLat();
               rideValues[1] += r.getOrig().getLong();
               rideValues[2] += r.getDest().getLat();
               rideValues[3] += r.getDest().getLong();
            }
            //create Ride object and set first element of cluster to it
            cluster.set(0, new Ride(-1, new Location(1, rideValues[0]/(cluster.size()-1), rideValues[1]/(cluster.size()-1), true), 
            new Location(1, rideValues[2]/(cluster.size()-1), rideValues[3]/(cluster.size()-1), false), null));
         }
         //initialize new clusters with 0th-element centroids
         temp_cluster = new ArrayList<ArrayList<Ride>>();
         for(int i = 0; i < clusters.size(); i++){
            temp_cluster.add(new ArrayList<Ride>());
            temp_cluster.get(i).add(clusters.get(i).get(0));//Add first element of the list- the centroid
         }
         //Reassign all rides to nearest centroids, recording changes
         for(int i = 0; i < clusters.size(); i++){
            cluster = clusters.get(i);
            for(int j = 1; j < cluster.size(); j++){
               correct_index = closestIndex(clusters, cluster.get(j));
               if(correct_index < 0){
                  changedClusters = true;//Negative values indicate the Ride is being moved to a different cluster
                  correct_index = (correct_index * -1) - 1;
                  temp_cluster.get(correct_index).add(cluster.get(j));
               }else{
                  correct_index--;
                  temp_cluster.get(correct_index).add(cluster.get(j));
               }
            }
         }
         //set clusters equal to the now-processed temp variable for next iteration
         clusters = temp_cluster;
      }
      //remove centroids from final list of clusters
      for(ArrayList<Ride> clust: clusters){
         clust.remove(0);
      }
      return clusters;
   }
   
   public static int closestIndex(ArrayList<ArrayList<Ride>> centroids, Ride r){
      //Returns the cluster which a ride should be assigned- positive if already located there and negative otherwise
      //returns 1 higher to account for 0
      int minimumDistLocation = 0;
      double minimumDist = r.compareTo(centroids.get(0).get(0));
      double currentDist;
      for(int i = 1; i < centroids.size(); i++){
         currentDist = r.compareTo(centroids.get(i).get(0));
         if(currentDist < minimumDist){//Update closest centroid in this case
            minimumDist = currentDist;
            minimumDistLocation = i;
         }
      }
      boolean first = true;//skip centroid in calculation
      for(Ride ride: centroids.get(minimumDistLocation)){
         if(first){
            first = false;
            continue;
         }else{
            if(ride.getID() == r.getID()){
               return minimumDistLocation + 1;//Already located in that centroid's cluster
            }
         }
      }
      return -1 * (minimumDistLocation + 1);//Not found in the centroid cluster that's closest
   }

}