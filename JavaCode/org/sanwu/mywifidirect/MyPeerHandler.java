package org.sanwu.mywifidirect;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.ListAdapter;

public class MyPeerHandler extends ListFragment implements PeerListListener {

	private List<WifiP2pDevice> peers =new ArrayList<WifiP2pDevice>();
	private MainActivity activity;
	private WifiP2pManager manager;
	private Channel chanel;
	public MyPeerHandler(MainActivity _ac, WifiP2pManager mgr,Channel c){
		activity=_ac;
		manager=mgr;
		chanel=c;
		activity.show("register MypeerHandler");
	}
	public int connectByIndex(int i){
		if(i>peers.size()-1){
			return 0;
		}
		
		WifiP2pDevice device=peers.get(i);
		
		WifiP2pConfig config=new WifiP2pConfig();
		config.deviceAddress=device.deviceAddress;
		config.wps.setup=WpsInfo.PBC;
		activity.show("ConnectDeviceName"+device.deviceName+"ConnectAdress"+config.deviceAddress);
	
		
		ActionListener listener=new ActionListener() {
			
			@Override
			public void onSuccess() {
				activity.show("设备连接成功");
			}
			@Override
			public void onFailure(int reason) {
				activity.show("连接设备失败");
			}
		};
		manager.connect(chanel, config,listener); //连接
		return -1;
	}
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peersList) {
		activity.show("Handler peersList");
		peers.clear();
		peers.addAll(peersList.getDeviceList());
		if(peers.size()==0){
			activity.show("没有发现设备");
		}else{
			activity.show("发现"+peers.size()+"个设备");
			for(int i=0;i<peers.size();i++){
				WifiP2pDevice device=peers.get(i);
				activity.show("FindDeviceName:"+device.deviceName+"\nFindDeviceAdress:"+device.deviceAddress+"\nFindDeviceStatus"+device.status);
			}
		}
	}
	

}
