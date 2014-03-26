package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	/**
	 * WIFI_P2P Enabled Event
	 * show you is wifip2p can use? 
	 * @author T3
	 * 
	 */	
	public class WifiDirectP2pStateChangeEvent extends Event
	{
		public static const WIFI_P2P_STATE_CHANGED:String="WIFI_P2P_STATE_CHANGED_ACTION";
		
		public var p2pEnable:Boolean=false;
		public function WifiDirectP2pStateChangeEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}