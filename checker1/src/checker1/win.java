package checker1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class win extends Frame {
	Checker app;
	board b;
	Button btn;
	TextField txt_ip;
	TextField txt_port;
	List list_color;
	TextArea sockettext = new TextArea();
	Label label_ip;
	Label label_port;
	Label label_choose;
	Button btnStart;
	Button btnChoose;
	String mcolor = "RED";
	HashMap mapcolor = new HashMap();
	int mycolor = 2;
	

	void init() {
		mapcolor.put("YELLOW", 2);
		mapcolor.put("RED", 3);
		mapcolor.put("GREEN", 4);

		txt_ip = new TextField(8);
		txt_ip.setText("127.0.0.1"); // 預設內容
		txt_port = new TextField(4);
		txt_port.setText("8765");

		list_color = new List();
		list_color.add("YELLOW");
		list_color.add("RED");
		list_color.add("GREEN");
		list_color.select(0);
		list_color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mcolor = e.getActionCommand();
				mycolor = (int) mapcolor.get(mcolor);
				System.out.println(mycolor);

			}
		});
		btnStart = new Button();
		btnStart.setSize(100, 50);
		btnStart.setLabel("start server");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b.ip = txt_ip.getText();
				b.port = Integer.parseInt(txt_port.getText());
				app.start();
				btnStart.setEnabled(false);
				btnChoose.setEnabled(true);

			}
		});


		btnChoose = new Button();
		btnChoose.setSize(100, 50);
		btnChoose.setLabel("choose");
		btnChoose.setEnabled(false);
		btnChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 設定自己的顏色
				b.myColor = mycolor;
				// 選擇後不能在按這個list
				list_color.setEnabled(false);
				btnChoose.setEnabled(false);
				if (list_color.getItemCount() == 3) // 查看list是3個還是2個 如果是3表示對方還沒選
				{
					// 告訴別人你的顏色
					// 2 yellow 3 red 4 green
					// 前面的數字表示你是選擇顏色的還是後 如果是3表示你先選
					new SocketClient("3," + Integer.toString(mycolor),b.ip,b.port);

				} else if (list_color.getItemCount() == 2) // 如果是2個 表示對方已經先選了顏色 現在按了button連線後就可以開始
				{
					// 因為候選顏色 所以你後攻
					b.isYourTurn = 0;
					b.makeTwoColor(b.otherColor);
					// 前面的數字如果是2表示你是後選顏色
					new SocketClient("2," + Integer.toString(mycolor),b.ip,b.port);
				}
			}
		});

		label_ip = new Label();
		label_ip.setText("ip:");
		label_port = new Label();
		label_port.setText("client port:");
		label_choose = new Label();
		label_choose.setText("choose color (Please double click)");
	}

	public win(Checker app) throws HeadlessException {
		this.app = app;
		init();
		b = new board();
		b.setBounds(0, 0, 1000, 700);

		setLayout(new BorderLayout());
		add(label_ip);
		add(txt_ip);
		add(label_port);
		add(txt_port);
		add(btnStart);
		add(label_choose);
		add(list_color);
		add(btnChoose);
		add(b);

	}
}