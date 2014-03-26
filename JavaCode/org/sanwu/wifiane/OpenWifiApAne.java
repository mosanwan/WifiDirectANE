package org.sanwu.wifiane;

import android.app.Activity;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class OpenWifiApAne  implements FREFunction {

//	private  int OPEN_WIFI=0;
//	private  int CLOSE_WIFI=1;
//	private   int CREATE_WIFI_AP=2;
//	private   int CLOSE_WIFI_AP=3;
//	private   int GET_WIFI_AP_STATE=4;
//	private   int GET_WIFI_STATE=5;
//	private 	  int CONNECT_TO_WIFI_AP=6;
	//private final String TEST="test";
	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		ExtendsionContext context=(ExtendsionContext)arg0;
		Activity a=context.getActivity();
		FREObject funObj=arg1[0];
		int funType=-1;
		FREObject result;
		
		FREObject ssidObj;
		FREObject keyObj;
		
		try {
			funType=funObj.getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		switch (funType) {
		case 0:
			WifiUtil.getInstance(a).setWIFIState(true);
			break;
		case 1:
			WifiUtil.getInstance(a).setWIFIState(false);
			break;
		case 2:
			ssidObj=arg1[1];
			keyObj=arg1[2];
			try {
				WifiUtil.getInstance(a).setWifiAp(ssidObj.getAsString(), keyObj.getAsString(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			WifiUtil.getInstance(a).setWifiAp("close", "12345678", false);
			break;
		case 4:
			try {
				result=FREObject.newObject(WifiUtil.getInstance(a).getWifiApState());
				return result;
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				result=FREObject.newObject(WifiUtil.getInstance(a).getWIFIState());
				return result;
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			break;
		case 6:
			ssidObj=arg1[1];
			keyObj=arg1[2];
			try {
				WifiUtil.getInstance(a).connectToWifiAp(ssidObj.getAsString(), keyObj.getAsString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:	
			break;
		}
		
		
		return null;
	}
}
