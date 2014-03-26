package org.sanwu.wifidirect
{
	public class WifiP2pDevice
	{
		public static const AVAILABLE:int=3;
		public static const CONNECTED:int=0;
		
		public static const FAILED:int=2;
		public static const INVITED:int=1;
		public static const UNAVALIBLE:int=4;
		
		public var deviceName:String;
		public var deviceAdress:String;
		public var deviceStatus:int;
		public function WifiP2pDevice()
		{
		}
	}
}