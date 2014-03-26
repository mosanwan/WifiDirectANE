package
{
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.ProgressEvent;
	import flash.events.ServerSocketConnectEvent;
	import flash.net.ServerSocket;
	import flash.net.Socket;
	import flash.text.TextFormat;
	
	import fl.controls.Button;
	import fl.controls.TextArea;
	import fl.controls.TextInput;
	import fl.events.ComponentEvent;
	
	import org.sanwu.wifidirect.WifiDirectAne;
	import org.sanwu.wifidirect.WifiP2pDevice;
	import org.sanwu.wifidirect.events.WifiDirectConnectEvent;
	import org.sanwu.wifidirect.events.WifiDirectConnectionInfoEvent;
	import org.sanwu.wifidirect.events.WifiDirectFetchOthersEvent;
	import org.sanwu.wifidirect.events.WifiDirectGroupEvent;
	import org.sanwu.wifidirect.events.WifiDirectP2pStateChangeEvent;
	import org.sanwu.wifidirect.events.WifiDirectPeersChangedEvent;
	
	
	
	public class WifiDirectTest extends Sprite
	{
		public var b1:Button;
		public var b2:Button;
		public var b3:Button;
		public var b4:Button;
		public var b5:Button;
		public var b6:Button;
		public var b7:Button;
		public var b8:Button;
		public var b9:Button;
		
		
		public var t1:TextInput;
		public var t2:TextArea;
		public var t3:TextArea;
		
		
		public var serverSocket:ServerSocket=new ServerSocket();
		public var clientSocket:Socket;
		public var port:int=8650;
		
		public var other:WifiP2pDevice;
		
		public var ownerAdress:String="";
		public var isOwner:Boolean;
		
		private var context:WifiDirectAne;
		private var isServer:Boolean=false;
		private var client:Socket;
		public function WifiDirectTest()
		{
			super();
			
			// 支持 autoOrient
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			initUI();
			initContext();
		}
		
		private function initContext():void
		{
			context=new WifiDirectAne();
			context.addEventListener(WifiDirectP2pStateChangeEvent.WIFI_P2P_STATE_CHANGED,onStateChange);
			context.addEventListener(WifiDirectFetchOthersEvent.FETCH_RESPONEDS,onFetchRes);
			context.addEventListener(WifiDirectConnectEvent.CONNECT_OTHER,onConnect);
			context.addEventListener(WifiDirectConnectEvent.DIS_CONNECT_OTHER,onDisConnect);
			context.addEventListener(WifiDirectGroupEvent.CREATE_GROUP,onCreateGroup);
			context.addEventListener(WifiDirectGroupEvent.REMOVE_GROUP,onRemoveGroup);
			context.addEventListener(WifiDirectPeersChangedEvent.WIFI_P2P_PEERS_CHANGE,peerChange);
			context.addEventListener(WifiDirectConnectionInfoEvent.CONNECTION_INFO,onConnectioninfo);
		}
		
		protected function onConnectioninfo(event:WifiDirectConnectionInfoEvent):void
		{
			
			ownerAdress=event.ownerAdress;
			ownerAdress=ownerAdress.substring(1);
			show("ConnectionInfoEvent  "+ownerAdress);
			isOwner=event.isMeOwner;
			if(isOwner)
			{
				show("I am is Owner");
				isOwner=true;
			}else{
				show("i am is Client");
				isOwner=false;
			}
			
		}
		protected function peerChange(event:WifiDirectPeersChangedEvent):void
		{
			var p:Vector.<WifiP2pDevice>=event.peers;
			for (var i:int = 0; i <p.length; i++) 
			{
				var d:WifiP2pDevice=p[i];
				show(d.deviceName+" "+d.deviceAdress+" "+d.deviceStatus);
				if(d.deviceStatus==WifiP2pDevice.CONNECTED)
				{
					
				}
			}
			
		}
		protected function onRemoveGroup(event:WifiDirectGroupEvent):void
		{
			show("REmoveGroup "+event.result);
		}
		
		protected function onCreateGroup(event:WifiDirectGroupEvent):void
		{
			show("CreateGroup "+event.result.toString());
		}
		
		protected function onDisConnect(event:WifiDirectConnectEvent):void
		{
			show("DisConnect "+event.result.toString());
		}
		
		protected function onConnect(event:WifiDirectConnectEvent):void
		{
			show("Connect"+event.result.toString());
			
		}
		
		protected function onFetchRes(event:WifiDirectFetchOthersEvent):void
		{
			show("开始搜索 = "+event.responds.toString());
		}
		
		protected function onStateChange(event:WifiDirectP2pStateChangeEvent):void
		{
			show("P2PENABLE = "+event.p2pEnable.toString());
		}
		
		private function initUI():void
		{
			initBtn(b1,"初始化",0,0,b1C);
			initBtn(b2,"搜索",416,0,b2C);
			initBtn(b3,"建组",0,120,b3C);
			initBtn(b4,"解组",416,120,b4C);
			initBtn(b5,"连接",0,250,b5C);
			initBtn(b6,"断开",416,250,b6C);
			initBtn(b7,"服务器",0,950,b7C);
			initBtn(b8,"客户端",416,950,b8C);
			initBtn(b9,"发送",250,1179,b9C);
			var format:TextFormat=new TextFormat(null,30);
			
			t2=new TextArea();
			t2.editable=false;
			t3=new TextArea();
			t2.setSize(680,580);this.addChild(t2);t2.move(20,370);
			t3.setSize(680,99);this.addChild(t3);t3.move(20,1060);
			t2.setStyle("textFormat",format);
			t3.setStyle("textFormat",format);
			
			if(ServerSocket.isSupported)
			{
				
				//show("支持ServiceSocket服务器");
			}else{
				//show("该设备不支持ServerSocket");
			}
			
		}
		private function b9C(e:Event):void
		{
			if(t3.text!=null)
			{
				if(isOwner)
				{
					client.writeUTFBytes(t3.text);
					client.flush();
					t3.text="";
				}else{
					clientSocket.writeUTFBytes(t3.text);
					clientSocket.flush();
					t3.text="";
				}
			}
			
		}private function b8C(e:Event):void
		{
			clientSocket=new Socket();
			clientSocket.addEventListener(Event.CONNECT,onServerConnected);
			clientSocket.addEventListener(ProgressEvent.SOCKET_DATA,onServerData);
			clientSocket.connect(ownerAdress,port);
			isServer=false;
			show("创建客户端并连接到 "+ownerAdress);
			
		}
		
		protected function onServerData(event:ProgressEvent):void
		{
			show(clientSocket.readUTFBytes(event.bytesLoaded));
		}
		
		protected function onServerConnected(event:Event):void
		{
			show("连接到服务器成功");
			clientSocket.writeUTFBytes("Hello i am Client");
			clientSocket.flush();
		}
		private function b7C(e:Event):void
		{
			if(ServerSocket.isSupported)
			{
				if(serverSocket.bound)
				{
					serverSocket.close();
					serverSocket=new ServerSocket();
				}
				serverSocket.bind(port,ownerAdress);
				serverSocket.addEventListener(ServerSocketConnectEvent.CONNECT,onClientConnect);
				serverSocket.listen();
				isServer=true;
				show("创建服务器");
			}else{
				show("该设备不支持ServerSocket");
			}
		}
		
		protected function onClientConnect(event:ServerSocketConnectEvent):void
		{
			var socket:Socket=event.socket;
			client=socket;
			socket.addEventListener(ProgressEvent.SOCKET_DATA,onClientData);
			show("发现客户端连接请求");
			
		}
		
		protected function onClientData(event:ProgressEvent):void
		{
			show("收到客户端消息 ");
			var socket:Socket=event.target as Socket;
			show(socket.readUTFBytes(event.bytesLoaded));
		}
		private function b6C(e:Event):void
		{
			context.disConnect();
			
		}
		private function b5C(e:Event):void
		{
			context.connect(0);
			
		}
		private function b4C(e:Event):void
		{
			context.removeGroup();
			
		}
		private function b3C(e:Event):void
		{
			context.createGroup();
			
		}
		
		private function b2C(e:Event):void
		{
			context.disCoverOthers();
			
		}
		
		private function b1C(e:Event):void
		{
			context.initP2pManager();
			
		}
		
		private function initBtn(btn:Button,label:String,px:int,py:int,callBack:Function):void
		{
			btn=new Button();
			btn.label=label;
			btn.setSize(300,100);
			btn.addEventListener(ComponentEvent.BUTTON_DOWN,callBack);
			btn.setStyle("textFormat",new TextFormat(null,24));
			this.addChild(btn);
			btn.x=px;
			btn.y=py;
		}
		private function show(s:Object):void
		{
			t2.appendText("\n"+s.toString());
			t2.verticalScrollPosition=t2.height;
		}
	}
}