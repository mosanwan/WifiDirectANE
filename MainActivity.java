package com.sanwu.helloandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	WifiManager wifiManager;
	Handler handler;
	Handler closeWifiHandler;
	CloseWifiThread cwt;
	StartWifiApThead swat;
	TextView tv;
	
	private WifiP2pManager p2pManager;
	private boolean isWifiP2pEnabled=false;
	private boolean retryChannel=false;
	private final IntentFilter intentFilter=new IntentFilter();
	private BroadcastReceiver receiver=null;
	private Channel channel;
	
	public void setIsWifiP2pEnable(boolean isWifiP2pEnable){
		this.isWifiP2pEnabled=isWifiP2pEnable;
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.e("me", "start");
		wifiManager=(WifiManager)this.getSystemService("wifi");
		tv=(TextView)findViewById(R.id.debugText);
		startWifiAp();
		
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		p2pManager=(WifiP2pManager) this.getSystemService(Context.WIFI_P2P_SERVICE);
		channel=p2pManager.initialize(this, getMainLooper(), null);
		
	}

	private void startWifiAp() {
		handler=new Handler();
		if(wifiManager.isWifiEnabled())
		{
			show("Wifi is Enbale");
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
			show("Wifi is not Enable");
			startWifiApTh();
		}
		
	}

	private void show(String s) {
		tv.append("\n"+s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			show("Wifi = "+state);
			if(state==WifiManager.WIFI_STATE_ENABLED)
			{
				show("Wifi开启，准备关闭");
				wifiManager.setWifiEnabled(false);
				closeWifiHandler.postDelayed(cwt, 1000);
			}else if(state==WifiManager.WIFI_STATE_DISABLING){
				show("正在关闭Wifi");
				closeWifiHandler.postDelayed(cwt,1000);
			}else if(state==WifiManager.WIFI_STATE_DISABLED){
				show("已关闭Wifi");
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
//			show("StartWifiApThread");
//			//WifiUtil mWifiUtil=new WifiUtil();
//			int state =mWifiUtil.getWifiApState(wifiManager);
//			show("state="+state);
//			
//			if(state==WifiUtil.WIFI_AP_STATE_DISABLED){
//				//mWifiUtil.startWifiAp(wifiManager);
//				handler.postDelayed(swat, 1000);	
//			}else if(state==WifiUtil.WIFI_AP_STATE_ENABLING||state == WifiUtil.WIFI_AP_STATE_FAILED){
//				handler.postDelayed(swat, 1000);
//			}
//				else if(state==WifiUtil.WIFI_AP_STATE_ENABLED)
//			{
//				show("WIfi热点已经启动");	
//			}
		}
	}
	private void startWifiApTh()
	{
		WifiConfiguration config=WifiUtil.getInstance(this).createWifiApConfig("MyWifiAp", "12345678");
		WifiUtil.getInstance(this).setWifiAp(wifiManager, config, false);
//		swat=new StartWifiApThead();
//		Thread thead=new Thread(swat);
//		thead.start();
//		Log.v("me", "hello world");
	}

}

