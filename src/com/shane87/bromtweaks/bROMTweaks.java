package com.shane87.bromtweaks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.*;

public class bROMTweaks extends Activity {
	String KTschedMinGranularity;
	String KTschedLatency;
	String KTschedWakeupGranularity;
	String KTschedFeatures;
	String VMdirtyBackgroundRatio;
	String VMdirtyRatio;
	String VMdirtyWriteback;
	String VMdirtyExpire;
	String VMoomKillAllocTask;
	String VMswappiness;
	String VMvfsCachePressure;
	String NETwmemMax;
	String NETrmemMax;
	String NET_TCPwmem;
	String NET_TCPrmem;
	String NET_TCPtimestamps;
	String NET_TCPtwReuse;
	String NET_TCPsack;
	String NET_TCPtwRecycle;
	String NET_TCPwindowScaling;
	String NET_TCPkeepAliveProbes;
	String NET_TCPkeepAliveIntvl;
	String NET_TCPfinTimeout;
	String NETrmemDefault;
	String NETwmemDefault;
	String SDreadAhead;
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
    		output = output.concat("echo \"Mounting all partitions noatime and nodiratime\" | tee -a $LOG_FILE;\n");
    		output = output.concat("PART=`mount | cut -d \" \" -f3`;\n");
    		output = output.concat("STL=`ls -d /sys/block/stl*`;\n");
    		output = output.concat("BML=`ls -d /sys/block/bml*`;\n");
    		output = output.concat("MMC=`ls -d /sys/block/mmc*`;\n\n");
    		output = output.concat("for k in $PART;\n");
    		output = output.concat("do\n");
    		output = output.concat("\tsync;\n");
    		output = output.concat("\tmount -o remount,noatime,nodiratime $k;\n");
    		output = output.concat("done;\n");
    		output = output.concat("echo \"Finished IO-noatime/nodiratime tweak application\" | tee -a $LOG_FILE;\n\n");
    		output = output.concat("echo \"Applying Vampirefo Kernel tweaks\" | tee -a $LOG_FILE;");
    		output = output.concat("echo 4000000 > /proc/sys/kernel/sched_min_granularity_ns;\n");
    		output = output.concat("echo 8000000 > /proc/sys/kernel/sched_latency_ns;\n");
    		output = output.concat("echo 1600000 > /proc/sys/kernel/sched_wakeup_granularity_ns;\n");
    		output = output.concat("echo 24319 > /proc/sys/kernel/sched_features;\n");
    		output = output.concat("echo \"Finished kernel tweak application\" | tee -a $LOG_FILE;\n\n");
    		output = output.concat("echo \"Applying VM tweaks\" | tee -a $LOG_FILE;\n");
    		output = output.concat("echo 20 > /proc/sys/vm/dirty_background_ratio;\n");
    		output = output.concat("echo 30 > /proc/sys/vm/dirty_ratio;\n");
    		output = output.concat("echo 800 > /proc/sys/vm/dirty_writeback_centisecs;\n");
    		output = output.concat("echo 800 > /proc/sys/vm/dirty_expire_centisecs;\n");
    		output = output.concat("echo 1 > /proc/sys/vm/oom_kill_allocating_task;\n");
    		output = output.concat("echo 20 > /proc/sys/vm/swappiness;\n");
    		output = output.concat("echo 2048 > /proc/sys/vm/min_free_kbytes;\n");
    		output = output.concat("echo 75 > /proc/sys/vm/vfs_cache_pressure;\n");
    		output = output.concat("echo 1 > /proc/sys/vm/laptop_mode;\n");
    		output = output.concat("echo \"Finished vm tweak application\" | tee -a $LOG_FILE;\n\n");
    		output = output.concat("echo \"Applying tcp ipv4 tweaks\" | tee -a $LOG_FILE;\n");
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