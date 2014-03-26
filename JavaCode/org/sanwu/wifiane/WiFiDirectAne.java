package org.sanwu.wifiane;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class WiFiDirectAne implements FREFunction {

	public Boolean isWiFiP2PEnable=false;
	public FREContext context;
	public Activity ac;
	private WifiP2pManager manager;
	private Channel channel;
	private final IntentFilter intentFilter=new IntentFilter();
	private BroadcastReceiver receiver=null;
	
	public MyPeerHandler myPeerhandler;
	public MyConnectionHandler myConnectionHandler;
	/**
	 * @param arg1 :  arg1[0] is functionType  about what`s you want to do
	 * like this:
	 * 0 initP2pManager
	 * 1 Search otherDevice
	 * 2 connect to other device .  And this time arg1[1] is the index of deviceList. must set it
	 * 3 disConnect 
	 * 4 create a p2p group . and you will be group owner.
	 * 5 remove you p2p group 
	 * */
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		context=(ExtendsionContext)arg0;
		ac=context.getActivity(); //get Main Activiey
		FREObject funTypeObj=arg1[0];
		int funType=-1;
		try {
			funType=funTypeObj.getAsInt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(funType==0){
			initP2p();
		}else if(funType==1)
		{
			fetchPeers();
		}else if(funType==2){
			FREObject indexObj=arg1[1];
			int index=-1;
			try{
				index=indexObj.getAsInt();
			}catch(Exception e){
				
			}
			connectPeer(index);
		}else if(funType==3){
			manager.cancelConnect(channel, new ActionListener() {
				@Override
				public void onSuccess() {
					context.dispatchStatusEventAsync("CANCEL_CONNECT", "true");
				}
				@Override
				public void onFailure(int reason) {
					context.dispatchStatusEventAsync("CANCEL_CONNECT", "false");
				}
			});
		}
		else if(funType==4){
			manager.createGroup(channel, new ActionListener() {
				@Override
				public void onSuccess() {
					context.dispatchStatusEventAsync("CREATE_GROUP", "true");
				}
				@Override
				public void onFailure(int reason) {
					context.dispatchStatusEventAsync("CREATE_GROUP", "false");
				}
			});
		}else if(funType==5){
			manager.removeGroup(channel, new ActionListener() {
				
				@Override
				public void onSuccess() {
					context.dispatchStatusEventAsync("REMOVE_GROUP", "true");	
				}
				@Override
				public void onFailure(int reason) {
					context.dispatchStatusEventAsync("REMOVE_GROUP", "false");
				}
			});
		}
		return null;
	}

	private void connectPeer(int index) {
		this.myPeerhandler.connectByIndex(index);
	}

	private void fetchPeers() {
		ActionListener listener =new ActionListener() {
			
			@Override
			public void onSuccess() {
				context.dispatchStatusEventAsync("DISCOVER_PEERS", "true");
				
			}
			
			@Override
			public void onFailure(int reason) {
				
				context.dispatchStatusEventAsync("DISCOVER_PEERS", "false");
			}
		};
		
		manager.discoverPeers(channel, listener);
		
		
	}

	private void initP2p() { //initP2pManager and BroadcastReceiver
		 manager = (WifiP2pManager) ac.getSystemService(Context.WIFI_P2P_SERVICE);
	     channel = manager.initialize(ac, ac.getMainLooper(), null);
		
	     myPeerhandler=new MyPeerHandler(context,manager,channel);
	     myConnectionHandler=new MyConnectionHandler(context, manager, channel);
	     
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        receiver=new WifiDirectBoardCastReceiver(manager,channel,this);
		ac.registerReceiver(receiver, intentFilter);
	}

}
