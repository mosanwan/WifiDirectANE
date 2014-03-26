package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	
	import org.sanwu.wifidirect.WifiP2pDevice;
	/**
	 * 
	 * @author T3
	 * 
	 */	
	public class WifiDirectPeersChangedEvent extends Event
	{
		public static const WIFI_P2P_PEERS_CHANGE:String="p2pPeersChangedEvent";
		public var peers:Vector.<WifiP2pDevice>;
		public function WifiDirectPeersChangedEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			peers=new Vector.<WifiP2pDevice>();
			super(type, bubbles, cancelable);
		}
	}
}