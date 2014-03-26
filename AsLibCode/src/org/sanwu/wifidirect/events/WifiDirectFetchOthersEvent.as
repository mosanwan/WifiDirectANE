package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	/**
	 * 
	 * @author T3
	 * 
	 */	
	public class WifiDirectFetchOthersEvent extends Event
	{
		public static const FETCH_RESPONEDS:String="Fetch_Responds";
		
		public var responds:Boolean;
		public function WifiDirectFetchOthersEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}