package com.shane87.bromtweaks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;

public class bROMTweaks extends Activity {
	boolean IOnoATime;
	String KTschedMinGranularity;
	String KTschedLatency;
	String KTschedWakeupGranularity;
	String KTschedFeatures;
	String VMdirtyBackgroundRatio;
	String VMdirtyRatio;
	String VMdirtyWriteback;
	String VMdirtyExpire;
	boolean VMoomKillAllocTask;
	String VMswappiness;
	String VMminFreeKbytes;
	String VMvfsCachePressure;
	boolean VMlaptopMode;
	String NETwmemMax;
	String NETrmemMax;
	String[] NETrmem;
	String[] NETwmem;
	boolean NET_TCPtimestamps;
	boolean NET_TCPtwReuse;
	boolean NET_TCPsack;
	boolean NET_TCPtwRecycle;
	boolean NET_TCPwindowScaling;
	String NET_TCPkeepAliveProbes;
	String NET_TCPkeepAliveIntvl;
	String NET_TCPfinTimeout;
	String NETrmemDefault;
	String NETwmemDefault;
	String SDreadAhead;
	
	protected static final int REFRESH = 0;
	protected static final int FAILED = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AsyncTask<Void, Void, Integer> init = new initializer().execute((Void[]) null);
        
        int ret = -1;
        
        try
        {
        	ret = init.get();
        }catch(Exception Ignored){}
        
        switch(ret)
        {
        case REFRESH:
        {
        	
        }
        case FAILED:
        {
        	Toast.makeText(getApplicationContext(), "Initialization Failed!", Toast.LENGTH_LONG);
        	this.finish();
        }
        }
    }
    
    private class initializer extends AsyncTask<Void, Void, Integer>
    {

		@Override
		protected Integer doInBackground(Void... params)
		{
			Integer retVal = FAILED;
			
			buildBaseScript();
			
			if(!ShellInterface.isSuAvailable())
			{
				retVal = FAILED;
			}
			else
			{
				String tmp = ShellInterface.getProcessOutput("mount");
				if(tmp.contains("noatime"))
				{
					IOnoATime = true;
				}
				else
					IOnoATime = false;
				
				KTschedMinGranularity = ShellInterface.getProcessOutput("cat /proc/sys/kernel/sched_min_granularity_ns");
				KTschedLatency = ShellInterface.getProcessOutput("cat /proc/sys/kernel/sched_latency_ns");
				KTschedWakeupGranularity = ShellInterface.getProcessOutput("cat /proc/sys/kernel/sched_wakeup_granularity_ns");
				KTschedFeatures = ShellInterface.getProcessOutput("cat /proc/sys/kernel/sched_features");
				
				VMdirtyBackgroundRatio = ShellInterface.getProcessOutput("cat /proc/sys/vm/dirty_background_ratio");
				VMdirtyRatio = ShellInterface.getProcessOutput("cat /proc/sys/vm/dirty_ratio");
				VMdirtyWriteback = ShellInterface.getProcessOutput("cat /proc/sys/vm/dirty_writeback_centisecs");
				VMdirtyExpire = ShellInterface.getProcessOutput("cat /proc/sys/vm/dirty_expire_centisecs");
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/vm/oom_kill_allocating_task");
				if(tmp.contains("1"))
					VMoomKillAllocTask = true;
				else
					VMoomKillAllocTask = false;
				VMswappiness = ShellInterface.getProcessOutput("cat /proc/sys/vm/swappiness");
				VMminFreeKbytes = ShellInterface.getProcessOutput("cat /proc/sys/vm/min_free_kbytes");
				VMvfsCachePressure = ShellInterface.getProcessOutput("cat /proc/sys/vm/vfs_cache_pressure");
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/vm/laptop_mode");
				if(tmp.contains("1"))
					VMlaptopMode = true;
				else
					VMlaptopMode = false;
				
				NETwmemMax = ShellInterface.getProcessOutput("cat /proc/sys/net/core/wmem_max");
				NETrmemMax = ShellInterface.getProcessOutput("cat /proc/sys/net/core/rmem_max");
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_wmem");
				NETwmem = tmp.split(" ");
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_rmem");
				NETrmem = tmp.split(" ");
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_timestamps");
				if(tmp.contains("1"))
					NET_TCPtimestamps = true;
				else
					NET_TCPtimestamps = false;
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_tw_reuse");
				if(tmp.contains("1"))
					NET_TCPtwReuse = true;
				else
					NET_TCPtwReuse = false;
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_sack");
				if(tmp.contains("1"))
					NET_TCPsack = true;
				else
					NET_TCPsack = false;
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_tw_recycle");
				if(tmp.contains("1"))
					NET_TCPtwRecycle = true;
				else
					NET_TCPtwRecycle = false;
				tmp = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_window_scaling");
				if(tmp.contains("1"))
					NET_TCPwindowScaling = true;
				else
					NET_TCPwindowScaling = false;
				NET_TCPkeepAliveProbes = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_keepalive_probes");
				NET_TCPkeepAliveIntvl = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_keepalive_intvl");
				NET_TCPfinTimeout = ShellInterface.getProcessOutput("cat /proc/sys/net/ipv4/tcp_fin_timeout");
				NETrmemDefault = ShellInterface.getProcessOutput("cat /proc/sys/net/core/rmem_default");
				NETwmemDefault = ShellInterface.getProcessOutput("cat /proc/sys/net/core/wmem_default");
				
				SDreadAhead = null;
				SDreadAhead = ShellInterface.getProcessOutput("cat /sys/devices/virtual/bdi/179:0/read_ahead_kb");
				
				retVal = REFRESH;
			}
			
			return retVal;
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
	    		output = output.concat("echo 404480 > /proc/sys/net/core/wmem_max;\n");
	    		output = output.concat("echo 404480 > /proc/sys/net/core/rmem_max;\n");
	    		output = output.concat("echo 4096 16384 404480 > /proc/sys/net/ipv4/tcp_wmem;\n");
	    		output = output.concat("echo 4096 87380 404480 > /proc/sys/net/ipv4/tcp_rmem;\n");
	    		output = output.concat("echo 0 > /proc/sys/net/ipv4/tcp_timestamps;\n");
	    		output = output.concat("echo 1 > /proc/sys/net/ipv4/tcp_tw_reuse;\n");
	    		output = output.concat("echo 1 > /proc/sys/net/ipv4/tcp_sack;\n");
	    		output = output.concat("echo 1 > /proc/sys/net/ipv4/tcp_tw_recycle;\n");
	    		output = output.concat("echo 1 > /proc/sys/net/ipv4/tcp_window_scaling;\n");
	    		output = output.concat("echo 5 > /proc/sys/net/ipv4/tcp_keepalive_probes;\n");
	    		output = output.concat("echo 30 > /proc/sys/net/ipv4/tcp_keepalive_intvl;\n");
	    		output = output.concat("echo 30 > /proc/sys/net/ipv4/tcp_fin_timeout;\n");
	    		output = output.concat("echo 404480 > /proc/sys/net/core/rmem_default;\n");
	    		output = output.concat("echo 404480 > /proc/sys/net/core/wmem_default;\n");
	    		output = output.concat("echo \"Finished tcp ipv4 tweak application\" | tee -a $LOG_FILE;\n\n");
	    		output = output.concat("echo \"Applying SD Card read-ahead tweak\" | tee -a $LOG_FILE;\n");
	    		output = output.concat("if [ -e /sys/devices/virtual/bdi/179:0/read_ahead_kb ];then\n");
	    		output = output.concat("\techo 1024 > /sys/devices/virtual/bdi/179:0/read_ahead_kb;\n");
	    		output = output.concat("fi;\n");
	    		output = output.concat("echo \"SD Card read-ahead tweak applied\" | tee -a $LOG_FILE;\n\n");
	    		output = output.concat("echo \"bROM tweak script finished successfully at $( date +\"%m-%d-%Y %H:%M:%S\" )\" | tee -a $LOG_FILE;\n");
	    		OutputStreamWriter out = new OutputStreamWriter(openFileOutput("bROMTweaks.sh", 0));
	    		out.write(output);
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
}