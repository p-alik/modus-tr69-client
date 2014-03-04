package ch.ethz.iks.concierge.framework;

import java.io.File;

public class ShutDown {
	public static void shutdown(){
		Framework.shutdown(false);
	}
	public static void init(){		
		File startupFile = new File(System.getProperty("xargs","." + File.separatorChar + "init.xargs"));
		System.out.print(startupFile.getAbsolutePath());
	}
	 
}
