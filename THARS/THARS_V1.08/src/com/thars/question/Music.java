package com.thars.question;

public class Music {

	private static int MscOn = 1;  
	
    public static int getMscStatus() {  
        return MscOn;  
    }  
      
    public static void setMscStatus(int Msc_On) {  
        Music.MscOn = Msc_On;  
    }  
}
