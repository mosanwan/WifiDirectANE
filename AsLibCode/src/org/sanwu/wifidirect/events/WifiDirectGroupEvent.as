package org.sanwu.wifidirect.events
{
	import flash.events.Event;
	/**
	 *when you create or remove a group. this event will dispatch 
	 * @author T3
	 * 
	 */	
	public class WifiDirectGroupEvent extends Event
	{
		public static const CREATE_GROUP:String="create_group";
		public static const REMOVE_GROUP:String="remove_group";
		
		public var result:Boolean;
		public function WifiDirectGroupEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}