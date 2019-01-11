package checker1;


import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;

public class SocketClient {
	private String address="127.0.0.1"; //"127.0.0.1"  192.168.43.244
	private int port = 8765;


	 void init(String ip, int port) {
		this.port = port;
		this.address = ip;

	}

	public SocketClient(String content,String IP,int PORT) {
		
		init(IP,PORT);	
		Socket client = new Socket();
		InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
		try {
			client.connect(isa, 10000);
			BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
			out.write(content.getBytes());
			out.flush();
			out.close();
			out = null;
			System.out.println("Send From Client");

			client.close();
			client = null;

		} catch (java.io.IOException e) {
			System.out.println("Socket連線有問題 !");
			System.out.println("IOException :" + e.toString());
		}
	}
	public SocketClient() {

	}

	public static void main(String args[]) {
//		new SocketClient();
	}
}
