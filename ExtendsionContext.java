package com.sanwu.helloandroid;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class ExtendsionContext extends FREContext {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String,FREFunction> functionMap=new HashMap<String, FREFunction>();
		functionMap.put("openWifiAp", new OpenWifiApAne());
		return functionMap;
	}

}
