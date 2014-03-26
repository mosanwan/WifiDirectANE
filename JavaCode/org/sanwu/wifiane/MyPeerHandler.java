package org.sanwu.wifiane;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

import com.adobe.fre.FREContext;

public class MyPeerHandler  implements PeerListListener {

	private FREContext context;
	private List<WifiP2pDevice> peers =new ArrayList<WifiP2pDevice>();
	private WifiP2pManager manager;
	private Channel chanel;
	public MyPeerHandler(FREContext _context, WifiP2pManager mgr,Channel c){
		context=_context;
		manager=mgr;
		chanel=c;
	}
	public int connectByIndex(int i){
		if(i>peers.size()-1){
			return 0;
		}
		
		WifiP2pDevice device=peers.get(i);
		
		WifiP2pConfig config=new WifiP2pConfig();
		config.deviceAddress=device.deviceAddress;
		config.wps.setup=WpsInfo.PBC;
		
		ActionListener listener=new ActionListener() {
			
			@Override
			public void onSuccess() {
				context.dispatchStatusEventAsync("CONNECT_TO_PEER", "true");
			}
			@Override
			public void onFailure(int reason) {
				context.dispatchStatusEventAsync("CONNECT_TO_PEER", "false");
			}
		};
		manager.connect(chanel, config,listener); //
		return -1;
	}
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peersList) {

		peers.clear();
		peers.addAll(peersList.getDeviceList());
		String details="";
		if(peers.size()==0){
		}else{
			for(int i=0;i<peers.size();i++){
				WifiP2pDevice device=peers.get(i);
				details+=device.deviceName+"<||>"+device.deviceAddress+"<||>"+device.status+"<||>";
			}
		}
		context.dispatchStatusEventAsync("WIFI_P2P_PEERS_CHANGED_ACTION", details);
	}
	

}
