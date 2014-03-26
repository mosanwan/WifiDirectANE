package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	
	public class WifiDirectConnectionInfoEvent extends Event
	{
		public static const CONNECTION_INFO:String="Connection_info";
		public var ownerAdress:String;
		public var isMeOwner:Boolean=false;
		public function WifiDirectConnectionInfoEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}