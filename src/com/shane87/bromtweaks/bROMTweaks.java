package com.shane87.bromtweaks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.*;

public class bROMTweaks extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if(buildBaseScript())
        {
        	//add code to let user know
        }
        else
        {
        	//add code to let user know
        }
    }
    
    private boolean buildBaseScript()
    {
    	try
    	{
    		String output = new String("");
    		output = output.concat("#!/system/bin/sh\n");
    		output = output.concat("#bROM tweak script\n");
    		output = output.concat("#b_randon14\n\n");
    		output = output.concat("sleep 120\n\n");
    		output = output.concat("LOG_FILE=/data/tweaks.log\n");
    		output = output.concat("\tif [ -e $LOG_FILE ]; then\n");
    		output = output.concat("\t\trm $LOG_FILE;\n");
    		output = output.concat("\tfi\n\n");
    		output = output.concat("echo \"Starting bROM Tweak Script $( date +\"%m-%d-%Y %H:%M:%S\" )\" | tee -a $LOG_FILE;\n\n");
    		output = output.concat("echo \"Mounting all partitions noatime and nodiratime\" | tee -a $LOG_FILE;");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		output = output.concat("");
    		OutputStreamWriter out = new OutputStreamWriter(openFileOutput("bROMTweaks.sh", 0));
    		out.write("test");
    		out.close();
    	}
    	catch(java.io.IOException e)
    	{
    		Toast.makeText(getApplicationContext(), "Could not create bROMTweaks.sh!!", Toast.LENGTH_LONG);
    		return false;
    	}
    	
    	ShellInterface.runCommand("chmod 777 /data/data/com.shane87.bromtweaks/files/bROMTweaks.sh");
    	return true;
    }
}