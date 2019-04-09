package pathfinder;
import mySql.Location;
import java.util.ArrayList;


public class Branch {
	public final byte capacity;
	public final double distance;
	private short[] path;
	private short[] remaining;
	
	public Branch(byte c, double d, short[] path, int nextLocation, short[] remaining) {
		capacity = c;
		distance = d;
		this.path = new short[path.length + 1];
		for(int i = 0; i < path.length; i++) {
			this.path[i] = path[i];
		}
		this.path[path.length] = (short) nextLocation;
		this.remaining = new short[remaining.length];
		for(int i = 0; i < remaining.length; i++) {
			this.remaining[i] = remaining[i];
		}
		this.remaining[nextLocation] = 1;
	}
	
	public short[] getPath() {
		return path;
	}
	
	public short[] getRemaining() {
		return remaining;
	}
}
