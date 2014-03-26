package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	/**
	 * when you connect or disConnect a peer. will dispatch this event
	 * @author T3
	 * 
	 */	
	public class WifiDirectConnectEvent extends Event
	{
		public static const CONNECT_OTHER:String="connect_to_other";
		public static const DIS_CONNECT_OTHER:String="disConnectOther";
		
		public var result:Boolean;
		
		public function WifiDirectConnectEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}