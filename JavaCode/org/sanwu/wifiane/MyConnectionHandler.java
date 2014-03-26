package org.sanwu.wifiane;

import java.net.InetAddress;


import com.adobe.fre.FREContext;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;

public class MyConnectionHandler implements ConnectionInfoListener {

	private FREContext context;
	private WifiP2pManager manager;
	private Channel channel;
	public MyConnectionHandler(FREContext con,WifiP2pManager mgr,Channel ch){
		context=con;
		manager=mgr;
		channel=ch;
	}
	@Override
	public void onConnectionInfoAvailable(WifiP2pInfo info) {
		
		InetAddress groupOwnnerAddress=info.groupOwnerAddress;
		if(info.groupFormed&&info.isGroupOwner){
			context.dispatchStatusEventAsync("CONNECTION_INFO_AVAILABLE", groupOwnnerAddress+"<||>true");
		}else if(info.groupFormed){
			context.dispatchStatusEventAsync("CONNECTION_INFO_AVAILABLE", groupOwnnerAddress+"<||>false");
		}

	}

}
