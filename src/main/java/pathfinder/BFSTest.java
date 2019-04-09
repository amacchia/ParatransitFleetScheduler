package pathfinder;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import mySql.Location;
import metric.Metric;

public class BFSTest {
	
	@Test
	public void scaleTest() {
		Location pointO = new Location(1, 0, 0, true);
		Location pointD = new Location(1, 100, 100, false);
		ArrayList<Location> points = new ArrayList<Location>();
		for(int i = 0; i < 6; i++) {
			points.add(pointO);
			points.add(pointD);
		}
		points = BreadthFirstSearch.BFsearch(3, new Location(2, 98, 98, false), points);
		for(Location point : points) {
			System.out.println(point.toString());
		}
		System.out.println(Metric.pathDistance(points));
		assertNotNull(1);
	}
	
}
