
package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    public static float USDtoEGP;
    public static float EGPtoUSD;
    /**Gets the currency transfer rates from the configuration.properties file (file name has to be sent)*/
    public void readProperties(String fileName){
        
        InputStream input = null;
	Properties prop = new Properties();

	try {


            input = new FileInputStream(fileName);

                
		// load a properties file
		prop.load(input);

		// get the property value and print it out
		USDtoEGP=Float.parseFloat( prop.getProperty("conversion.usd.egp"));
		EGPtoUSD=Float.parseFloat( prop.getProperty("conversion.egp.usd"));
                
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    }
}
