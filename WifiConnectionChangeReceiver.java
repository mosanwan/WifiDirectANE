package com.sanwu.helloandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiConnectionChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("me", "ÍøÂç×´Ì¬±ä¸ü");
		WifiManager wifiManager =(WifiManager)context.getSystemService("wifi");
		Log.e("me", "WifiState="+wifiManager.getWifiState());
	}

}
