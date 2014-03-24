package com.sanwu.helloandroid;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class OpenWifiApAne  implements FREFunction {

	WifiManager wifiManager;
	Handler handler;
	Handler closeWifiHandler;
	CloseWifiThread cwt;
	StartWifiApThead swat;
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		ExtendsionContext context=(ExtendsionContext)arg0;
		Activity a=context.getActivity();
		wifiManager=(WifiManager)a.getSystemService("wifi");
		startWifiAp() ;
		return null;
	}
	private void startWifiAp() {
		handler=new Handler();
		if(wifiManager.isWifiEnabled())
		{
			
			closeWifiHandler=new Handler(){
				@Override
				public void handleMessage(Message msg){
					startWifiAp();
					super.handleMessage(msg);
				}
			};
			cwt=new CloseWifiThread();
			Thread thread = new Thread(cwt);
			thread.start();
			
		}else{
			startWifiApTh();
		}
		
	}
	class CloseWifiThread implements Runnable{
		public CloseWifiThread()
		{
			super();
		}
		@Override
		public void run()
		{
			int state = wifiManager.getWifiState();
			if(state==WifiManager.WIFI_STATE_ENABLED)
			{
				wifiManager.setWifiEnabled(false);
				closeWifiHandler.postDelayed(cwt, 1000);
			}else if(state==WifiManager.WIFI_STATE_DISABLING){
				closeWifiHandler.postDelayed(cwt,1000);
			}else if(state==WifiManager.WIFI_STATE_DISABLED){
				
				closeWifiHandler.sendEmptyMessage(0);
			}
		}
	}
	class StartWifiApThead implements Runnable{
		public StartWifiApThead(){
			super();
		}
		@Override
		public void run()
		{
//			WifiUtil mWifiUtil=new WifiUtil();
//			int state =mWifiUtil.getWifiApState(wifiManager);
//			//show("wifi_disabled"+WifiUtil.WIFI_AP_STATE_ENABLED);
//			
//			if(state==WifiUtil.WIFI_AP_STATE_DISABLED){
//				//mWifiUtil.startWifiAp(wifiManager);
//				handler.postDelayed(swat, 1000);
//			}else if(state==WifiUtil.WIFI_AP_STATE_ENABLING
//					||state==WifiUtil.WIFI_AP_STATE_FAILED){
//				handler.postDelayed(swat,1000);
//			}else if(state==WifiUtil.WIFI_AP_STATE_ENABLED){
//			}
		}
	}
	private void startWifiApTh()
	{
		swat=new StartWifiApThead();
		Thread thead=new Thread(swat);
		thead.start();
		Log.v("me", "hello world");
	}

}
