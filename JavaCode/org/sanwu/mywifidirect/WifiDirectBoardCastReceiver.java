package org.sanwu.mywifidirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

public class WifiDirectBoardCastReceiver extends BroadcastReceiver {

	private WifiP2pManager manager;
	private Channel channel;
	private MainActivity activity;
	public WifiDirectBoardCastReceiver(WifiP2pManager _manager,Channel _channel,MainActivity _activity){
		super();
		this.manager=_manager;
		this.channel=_channel;
		this.activity=_activity;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		String action=intent.getAction();
		if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
			activity.show("p2p state change action");
			int state=intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
				activity.setIsWifiP2pEnabled(true);
			}else{
				activity.setIsWifiP2pEnabled(false);
			}
		}else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
			activity.show("p2p peer change action");
			if(manager!=null){
				manager.requestPeers(channel, (PeerListListener)activity.myPeerhandler);
			}else{
				activity.show("manager is null");
			}
		}else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){
			activity.show("p2p peer connection change action");
			NetworkInfo networkInfo=(NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			
			if(networkInfo.isConnected()){
				activity.show("连接上了");
				manager.requestConnectionInfo(channel, activity.myConnectionListener);
			}else{
				activity.show("没有连接上");
			}
			
		}else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){
			activity.show("p2p this device change action");
			 WifiP2pDevice thisDevice=((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
	          activity.show("ThisDevice Name="+thisDevice.deviceName);
		}

	}

}
