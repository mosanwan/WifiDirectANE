package org.sanwu.mywifidirect;

import java.net.InetAddress;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;

public class MyConnectionInfoListener implements ConnectionInfoListener {

	private MainActivity activity;
	private WifiP2pManager manager;
	private Channel channel;
	public MyConnectionInfoListener(MainActivity ac,WifiP2pManager mgr,Channel ch){
		activity=ac;
		manager=mgr;
		channel=ch;
	}
	@Override
	public void onConnectionInfoAvailable(WifiP2pInfo info) {
		InetAddress groupOwnnerAddress=info.groupOwnerAddress;
		activity.show("hostAdress"+groupOwnnerAddress.getHostAddress());
		if(info.groupFormed&&info.isGroupOwner){
			activity.show("I am Owner");
		}else if(info.groupFormed){
			activity.show("I am not Owner");
		}
	}

}
