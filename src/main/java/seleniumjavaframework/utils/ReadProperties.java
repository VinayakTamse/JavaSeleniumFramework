package seleniumjavaframework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	
	public static FileInputStream file;
	public static FileOutputStream fileo;
	public static Properties props;
	
	public static Properties getPropertyValue(String filename)
	{
		try {
		file = new FileInputStream(filename);
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		props = new Properties();
		try {
			props.load(file);
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		}
		
		return props;
	}
	

	

}
