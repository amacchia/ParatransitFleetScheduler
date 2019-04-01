package pathfinder;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mySql.Location;

public class SerializeRoute implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void serialize(ArrayList<Location> route, int i)
	{
		//create date to search directory(or make if it does not exist.
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date now = new Date();
		String date = simpleDateFormat.format(now);
		
		String PATH = "./results/";
		String datePath = PATH.concat(date);
		File directory = new File(datePath);
		if(!directory.exists())
		{
			directory.mkdir();
		}
		String filename = datePath + "/route" + i;
		try
		{
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(route);
			out.close();
			file.close();
			System.out.println("Object has been serialized");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
}
