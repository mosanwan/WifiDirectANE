package org.sanwu.wifidirect
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	
	import org.sanwu.wifidirect.events.WifiDirectConnectEvent;
	import org.sanwu.wifidirect.events.WifiDirectConnectionInfoEvent;
	import org.sanwu.wifidirect.events.WifiDirectFetchOthersEvent;
	import org.sanwu.wifidirect.events.WifiDirectGroupEvent;
	import org.sanwu.wifidirect.events.WifiDirectP2pStateChangeEvent;
	import org.sanwu.wifidirect.events.WifiDirectPeersChangedEvent;
	
	public class WifiDirectAne extends EventDispatcher
	{
		public static const initP2pManager:int=0; //init P2pManager
		public static const fetchOthers:int=1;
		
		private var context:ExtensionContext;
		public function WifiDirectAne(target:IEventDispatcher=null)
		{
			super(target);
			context=ExtensionContext.createExtensionContext("org.sanwu.wifiane.Extendsion","");
			context.addEventListener(StatusEvent.STATUS,onStatus);
		}
		/**init p2p manager*/
		public function initP2pManager():void
		{
			context.call("WIFIDIRECT",0);
		}
		public function disCoverOthers():void
		{
			context.call("WIFIDIRECT",1);
		}
		public function connect(index:int):void
		{
			context.call("WIFIDIRECT",2,index);
		}
		public function disConnect():void
		{
			context.call("WIFIDIRECT",3);
		}
		public function createGroup():void
		{
			context.call("WIFIDIRECT",4);
		}
		public function removeGroup():void
		{
			context.call("WIFIDIRECT",5);
		}
		
		protected function onStatus(event:StatusEvent):void
		{
			
			switch(event.code)
			{
				case "WIFI_P2P_STATE_CHANGED_ACTION":
					var p2pStateChangeEvent:WifiDirectP2pStateChangeEvent=new WifiDirectP2pStateChangeEvent(WifiDirectP2pStateChangeEvent.WIFI_P2P_STATE_CHANGED);
					if(event.level=="true")
					{
						p2pStateChangeEvent.p2pEnable=true;
					}else{
						p2pStateChangeEvent.p2pEnable=false;
					}
					dispatchEvent(p2pStateChangeEvent);
					break;
				case "DISCOVER_PEERS":
					var fechEvent:WifiDirectFetchOthersEvent=new WifiDirectFetchOthersEvent(WifiDirectFetchOthersEvent.FETCH_RESPONEDS);
					if(event.level=="true")
					{
						fechEvent.responds=true;
					}else{
						fechEvent.responds=false;
					}
					dispatchEvent(fechEvent);
					break;
				case "WIFI_P2P_PEERS_CHANGED_ACTION":
					handlerPeersChanged(event);
					break;
				case "CONNECT_TO_PEER":
					var connectEvent:WifiDirectConnectEvent=new WifiDirectConnectEvent(WifiDirectConnectEvent.CONNECT_OTHER);
					if(event.level=="true")
					{
						connectEvent.result=true;
					}else{
						connectEvent.result=false;
					}
					dispatchEvent(connectEvent);
					break;
				case "CANCEL_CONNECT":
					var disconnectEvent:WifiDirectConnectEvent=new WifiDirectConnectEvent(WifiDirectConnectEvent.DIS_CONNECT_OTHER);
					if(event.level=="true")
					{
						disconnectEvent.result=true;
					}else{
						disconnectEvent.result=false;
					}
					dispatchEvent(disconnectEvent);
					break;
				case "CREATE_GROUP":
					var createGroupEvent:WifiDirectGroupEvent=new WifiDirectGroupEvent(WifiDirectGroupEvent.CREATE_GROUP);
					if(event.level=="true")
					{
						createGroupEvent.result=true;
					}else{
						createGroupEvent.result=false;
					}
					dispatchEvent(disconnectEvent);
					break;
				case "REMOVE_GROUP":
					var removeGroupEvent:WifiDirectGroupEvent=new WifiDirectGroupEvent(WifiDirectGroupEvent.REMOVE_GROUP);
					if(event.level=="true")
					{
						removeGroupEvent.result=true;
					}else{
						removeGroupEvent.result=false;
					}
					dispatchEvent(removeGroupEvent);
					break;
				case "CONNECTION_INFO_AVAILABLE":
					handlerConnectionInfo(event);
					break;
			}
		}
		
		private function handlerConnectionInfo(event:StatusEvent):void
		{
			var evt:WifiDirectConnectionInfoEvent=new WifiDirectConnectionInfoEvent(WifiDirectConnectionInfoEvent.CONNECTION_INFO);
			var str:String=event.level;
			var strArr:Array=str.split("<||>");
			evt.ownerAdress=strArr[0] as String;
			if(strArr[1]=="true")
			{
				evt.isMeOwner=true;
			}else{
				evt.isMeOwner=false;
			}
			dispatchEvent(evt);
		}
		
		private function handlerPeersChanged(event:StatusEvent):void
		{
			var evt:WifiDirectPeersChangedEvent=new WifiDirectPeersChangedEvent(WifiDirectPeersChangedEvent.WIFI_P2P_PEERS_CHANGE);
			var str:String=event.level;
			if(str==""){
				dispatchEvent(evt);
				return;
			}
			var strArr:Array=str.split("<||>");
			var num:int=strArr.length/3;
			for (var i:int = 0; i < num; i++) 
			{
				var device:WifiP2pDevice=new WifiP2pDevice();
				device.deviceName=strArr[i*3];
				device.deviceAdress=strArr[i*3+1];
				device.deviceStatus=int(strArr[i*3+2]);
				evt.peers.push(device);
			}
			dispatchEvent(evt);
		}
	}
}