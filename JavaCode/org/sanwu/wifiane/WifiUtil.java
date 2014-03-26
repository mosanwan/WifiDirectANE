package org.sanwu.wifiane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class WifiUtil {
	
	private static WifiUtil _ins=null;
	public static WifiUtil getInstance(Context context)
	{
		if(_ins==null)_ins=new WifiUtil(context);
		return _ins;
	}
	/**正在关闭*/
	public static final int WIFI_AP_STATE_DISABLING=0;
	/**已经关闭*/
	public static final int WIFI_AP_STATE_DISABLED=11;
	/**正在开启*/
	public static final int WIFI_AP_STATE_ENABLING=12;
	/**已开启*/
	public static final int WIFI_AP_STATE_ENABLED=13;
	/**获取状态失败*/
	public static final int WIFI_AP_STATE_FAILED=14;
	
	
	private Context _context;
	private WifiManager wifiManager;
	public  WifiUtil(Context context){
		_context=context;
		wifiManager=(WifiManager)_context.getSystemService("wifi");
	}
	/**
	 * 创建一个WIFI热点
	 * @param ssid 名称
	 * @key 密码
	 * */
	public   WifiConfiguration createWifiApConfig(String ssid,String key){
		WifiConfiguration netConfig=new WifiConfiguration();
		netConfig.SSID=ssid;
		netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
		netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		netConfig.preSharedKey=key;
		return netConfig;
	}
	/**
	 * 打开或关闭一个WIFI热点
	 * @param netConfig 一个网络设置（如果是要关闭热点的话，可以设置为null）
	 * @param openOrClose 开启或关闭(true or false)
	 * */
	public  void setWifiAp(WifiConfiguration netConfig,boolean openOrClose)
	{
		
		Method method1 =null;
		try{
			method1=wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
			method1.invoke(wifiManager, netConfig,openOrClose);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
	}
	/**
	 * 根据ssid和Key来创建WIFI热点
	 * 关闭热点时 ssid 和key 随便写一个，然后 openOrclose设为False
	 * */
	public  void setWifiAp(String ssid,String key,boolean openOrClose)
	{
		WifiConfiguration netConfig=new WifiConfiguration();
		netConfig.SSID=ssid;
		netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
		netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		netConfig.preSharedKey=key;
		Method method1 =null;
		try{
			method1=wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
			if(openOrClose==true)
			{
				method1.invoke(wifiManager, netConfig,openOrClose);
			}else{
				method1.invoke(wifiManager, null,openOrClose);
			}
			
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
	}
	/**连接到指定的Wifi热点
	 * 
	 * */
	public void connectToWifiAp(String ssid,String key){
		if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
		{
			WifiConfiguration netConfig=new WifiConfiguration();
			netConfig.SSID=ssid;
			netConfig.preSharedKey=key;
			netConfig.status=WifiConfiguration.Status.ENABLED;
			netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			
			int nid=wifiManager.addNetwork(netConfig);
			wifiManager.enableNetwork(nid, true);
		}
	}
	/**获取WIFI热点状态*/
	public int getWifiApState(){
		try{
			Method method= wifiManager.getClass().getMethod("getWifiApState");
			int i = (Integer) method.invoke(wifiManager);
			return i;
		}catch(Exception e){
			return WIFI_AP_STATE_FAILED;
		}
	}
	/**获取WIFI状态*/
	public int getWIFIState(){
		int i =wifiManager.getWifiState();
		return i;
	}
	/**设置WIFI状态*/
	public void setWIFIState(Boolean b){
		wifiManager.setWifiEnabled(b);
	}
	public void scanf(){
		wifiManager.startScan();
	}
}