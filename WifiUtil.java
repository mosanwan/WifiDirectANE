package com.sanwu.helloandroid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.R.string;
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
	public static final int WIFI_AP_STATE_DISABLING=0;
	public static final int WIFI_AP_STATE_DISABLED=11;
	public static final int WIFI_AP_STATE_ENABLING=12;
	public static final int WIFI_AP_STATE_ENABLED=13;
	public static final int WIFI_AP_STATE_FAILED=14;
	
	
	private Context _context;
	public  WifiUtil(Context context){
		_context=context;
	}
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
	public  void setWifiAp(WifiManager wifiManager,WifiConfiguration netConfig,boolean openOrClose)
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
	public int getWifiApState(WifiManager wifiManager){
		try{
			Method method= wifiManager.getClass().getMethod("getWifiApState");
			int i = (Integer) method.invoke(wifiManager);
			return i;
		}catch(Exception e){
			return WIFI_AP_STATE_FAILED;
		}
	}
}
