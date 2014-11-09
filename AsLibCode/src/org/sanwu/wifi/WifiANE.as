package org.sanwu.wifi
{
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;

	public class WifiANE extends EventDispatcher
	{
		private const   OPEN_WIFI:int=0;
		private const   CLOSE_WIFI:int=1;
		private const   CREATE_WIFI_AP:int=2;
		private const   CLOSE_WIFI_AP:int=3;
		private const   GET_WIFI_AP_STATE:int=4;
		private const   GET_WIFI_STATE:int=5;
		private const   CONNECT_TO_WIFI_AP:int=6;
		
		private var context:ExtensionContext;
		public function WifiANE()
		{
			context=ExtensionContext.createExtensionContext("org.sanwu.wifiane.Extendsion","");
			context.addEventListener(StatusEvent.STATUS,onStatus);
		}
		public function initP2P():void
		{
			context.call("WIFIDIRECT",0);
		}
		public function fetchPeers():void{
			context.call("WIFIDIRECT",1);
		}
		protected function onStatus(event:StatusEvent):void
		{
			dispatchEvent(event);
		}
		public function test():void
		{
			trace(context.call("WIFIANE"));
		}
		public function openWifiAP(ssid:String,key:String):void
		{
			trace(context.call("WIFIANE",CREATE_WIFI_AP,ssid,key));
		}
		public function closeWifiAp():void
		{
			trace(context.call("WIFIANE",CLOSE_WIFI_AP));
		}
		public function openWifi():void
		{
			trace(context.call("WIFIANE",OPEN_WIFI));
		}
		public function closeWifi():void
		{
			trace(context.call("WIFIANE",CLOSE_WIFI));
		}
		public function getWifiState():int
		{
			var result:int=int(context.call("WIFIANE",GET_WIFI_STATE));
			return result;
		}
		public function getWifiApState():int
		{
			var result:int=int(context.call("WIFIANE",GET_WIFI_AP_STATE));
			return result;
		}
		public function connectToWifiAp(ssid:String,key:String):void
		{
			trace(context.call("WIFIANE",CONNECT_TO_WIFI_AP,ssid,key));
			//hello game
		}
	}
}