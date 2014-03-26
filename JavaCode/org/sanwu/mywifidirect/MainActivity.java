package org.sanwu.mywifidirect;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements ChannelListener{

	private static final int SERVER_PORT = 9652;
	
	private String tag="MyWifiDirect";
	private WifiP2pManager manager;
	private boolean isWifiP2pEnabled=false;
	private boolean retryChannel=false;
	private final IntentFilter intentFilter=new IntentFilter();
	private Channel channel;
	private BroadcastReceiver receiver=null;
	
	public MyPeerHandler myPeerhandler;
	public MyConnectionInfoListener myConnectionListener;
	
	private EditText tv;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	private Button b6;
	
	
	public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
		show( "WifiP2p"+isWifiP2pEnabled);
        this.isWifiP2pEnabled = isWifiP2pEnabled;
        if(isWifiP2pEnabled)
        {
        	
        	startRegistration();
        }
    }
	private void startRegistration() {
		Map record=new HashMap();
		record.put("listenport", String.valueOf(SERVER_PORT));
		record.put("buddyname", "John"+(int)(Math.random()*1000));
		record.put("available", "visible");
		
//		WifiP2pDnsSdServiceInfo serverInfo=WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);
//		ActionListener listener =new ActionListener() {
//			
//			@Override
//			public void onSuccess() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onFailure(int reason) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//		show("addLocalServer");
//		manager.addLocalService(channel, serverInfo, listener);
		
	}
	private void scanPeers() {
		if(isWifiP2pEnabled){
			show("开始搜索Peer");
			manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
				@Override
				public void onSuccess() {
					show("正在搜索...");
				}
				@Override
				public void onFailure(int arg0) {
					show("搜索失败");
				}
			});
		}else{
			show("WIFI P2P 不可用");
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);
		b5=(Button)findViewById(R.id.button5);
		b6=(Button)findViewById(R.id.button6);
		OnClickListener onB1Click=new OnClickListener() { //Fetch按钮
			
			@Override
			public void onClick(View v) {
				scanPeers();
			}
		};
		OnClickListener onB2Click=new OnClickListener() { //Connect按钮
			
			@Override
			public void onClick(View v) {
				myPeerhandler.connectByIndex(0);
			}
		};
		OnClickListener onB3Click=new OnClickListener() {//Close按钮
			@Override
			public void onClick(View v) {
				manager.cancelConnect(channel, new ActionListener() {
					
					@Override
					public void onSuccess() {
						show("断开链接成功");
					}
					
					@Override
					public void onFailure(int reason) {
						show("断开链接失败");
					}
				});
			}
		};
		OnClickListener onB4Click=new OnClickListener() { //Clear按钮
			@Override
			public void onClick(View v) {
				tv.setText("");
			}
		};
		OnClickListener onB5Click=new OnClickListener() { //CreateGroup按钮
			
			@Override
			public void onClick(View v) {
				manager.createGroup(channel, new ActionListener() {
					
					@Override
					public void onSuccess() {
						show("创建Group成功");
					}
					
					@Override
					public void onFailure(int reason) {
						show("创建Group失败");
					}
				});
			}
		};
		OnClickListener onB6Click=new OnClickListener() { //CloseGroup按钮
			
			@Override
			public void onClick(View v) {
				manager.removeGroup(channel, new ActionListener() {
					
					@Override
					public void onSuccess() {
						show("CloseGroup成功");
						
					}
					
					@Override
					public void onFailure(int reason) {
						show("CloseGroup失败");
					}
				});
			}
		};
		b1.setOnClickListener(onB1Click);
		b2.setOnClickListener(onB2Click);
		b3.setOnClickListener(onB3Click);
		b4.setOnClickListener(onB4Click);
		b5.setOnClickListener(onB5Click);
		b6.setOnClickListener(onB6Click);
		

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        
        myPeerhandler=new MyPeerHandler(this,manager,channel);
        myConnectionListener=new MyConnectionInfoListener(this, manager, channel);
        
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        receiver=new WifiDirectBoardCastReceiver(manager,channel,this);
		registerReceiver(receiver, intentFilter);
	}
	@Override
	public void onResume(){
		super.onResume();
		
	}
	@Override
	public void onPause(){
		super.onPause();
		unregisterReceiver(receiver);
	}
	public void resetData(){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public  void show(String str){
		tv.append("\n"+str);
	}
	@Override
	public void onChannelDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
