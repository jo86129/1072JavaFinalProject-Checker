package checker1;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Checker extends Thread {
	boolean OutServer = false;
	private ServerSocket server;
	int ServerPort = 8765;
	win w;
	String strbuf = new String();
	String[] getColor = new String[] { " ", " ", "YELLOW", "RED", "GREEN" };


	public void run() {
		Socket socket;
		java.io.BufferedInputStream in;
		strbuf = "Server started!";
		w.sockettext.setText(strbuf);
		System.out.println("Server started!");

		ArrayList<Integer> command = new ArrayList();
		while (!OutServer) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("connected : InetAddress = " + socket.getInetAddress());
				strbuf += "connected : InetAddress = " + socket.getInetAddress()+"\n";
				w.sockettext.setText(strbuf);
				// TimeOut時間
				socket.setSoTimeout(10000);

				in = new java.io.BufferedInputStream(socket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = in.read(b)) > 0) {
					data += new String(b, 0, length);
					strbuf += data;
					w.sockettext.setText(strbuf);
				}
				//區分訊息是是甚麼
				if (data.length() !=3) {
					String[] A = data.split(",");
					command.clear();
					for (String token : A) {
						command.add(Integer.parseInt(token));
					}
					//表示攻守交換
					w.b.isYourTurn=1;
					System.out.println("value:" + data);
					w.b.jump(command.get(2), command.get(3), command.get(command.size() - 2),
							command.get(command.size() - 1));

					
					
				} 
				//如果資訊長度為3表示是在設定顏色
				else if(data.length() ==3) {
					
					String[] A = data.split(",");
					command.clear();
					for (String token : A) {
						command.add(Integer.parseInt(token));
					}
					if (command.get(0) == 3) {
						// the other one choose the color
						// 2 yellow 3 red 4 green
						w.b.otherColor=command.get(1);
						//如果對方選擇黃色(2) 那自己的預設變成紅色(3)
						if(command.get(1)==2)
							w.mycolor=3;
						w.list_color.remove(getColor[command.get(1)]); // 讓自己那一選項消失
						
					}
					//means you chose the color first and you can start first
					else if (command.get(0) == 2) {
						
						//switch the pic and means start
						w.b.isYourTurn=1;
						//make the board two color
						w.b.makeTwoColor(command.get(1));
						
					}
				}
				
				
				// -----------------------------------------
				DataOutputStream out;
				out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF("test"); // have to insert the string
				out.close();
				out = null;
				// -----------------------------------------
				in.close();
				in = null;
				socket.close();

			} catch (java.io.IOException e) {
				System.out.println("Socketconnect error !");
				System.out.println("IOException :" + e.toString());
			}
		}

	}

	public Checker() {
		try {
			server = new ServerSocket(ServerPort);

		} catch (java.io.IOException e) {
			System.out.println("Socket startup error!");
			System.out.println("IOException :" + e.toString());
		}
	}

	public static void main(String[] args) {
		Checker c = new Checker();
		c.w = new win(c);
		c.w.setSize(1000, 1000);
		c.w.setLayout(new FlowLayout());

		c.w.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}

		});

		c.w.setVisible(true);

	}

}