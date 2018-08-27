
package bank.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static float USDtoEGP;
    private static float EGPtoUSD;
    private static int   nParseThreads;
    private static int   nApplyThreads;
    private static boolean initialized=false;
    /**Gets the currency transfer rates from the configuration.properties file (file name has to be sent)*/
    public static void readProperties(String fileName){
        initialized=true;
        InputStream input = null;
	Properties prop = new Properties();

	try {


            input = new FileInputStream(fileName);

                
		// load a properties file
		prop.load(input);

		// get the property value and print it out
		USDtoEGP=Float.parseFloat( prop.getProperty("conversion.usd.egp"));
		EGPtoUSD=Float.parseFloat( prop.getProperty("conversion.egp.usd"));
                nParseThreads=Integer.parseInt(prop.getProperty("threads.parse.number"));
                nApplyThreads=Integer.parseInt(prop.getProperty("threads.apply.number"));

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

    public static float getUSDtoEGP() {
        return USDtoEGP;
    }

    public static float getEGPtoUSD() {
        return EGPtoUSD;
    }

    public static int getnParseThreads() {
        return nParseThreads;
    }

    public static int getnApplyThreads() {
        return nApplyThreads;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setUSDtoEGP(float USDtoEGP) {
        PropertiesManager.USDtoEGP = USDtoEGP;
    }

    public static void setEGPtoUSD(float EGPtoUSD) {
        PropertiesManager.EGPtoUSD = EGPtoUSD;
    }

    public static void setnParseThreads(int nParseThreads) {
        PropertiesManager.nParseThreads = nParseThreads;
    }

    public static void setnApplyThreads(int nApplyThreads) {
        PropertiesManager.nApplyThreads = nApplyThreads;
    }

    public static void setInitialized(boolean initialized) {
        PropertiesManager.initialized = initialized;
    }
    
}
