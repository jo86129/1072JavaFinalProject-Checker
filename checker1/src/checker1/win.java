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
		txt_ip.setText("127.0.0.1"); // �w�]���e
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
				// �]�w�ۤv���C��
				b.myColor = mycolor;
				// ��ܫᤣ��b���o��list
				list_color.setEnabled(false);
				btnChoose.setEnabled(false);
				if (list_color.getItemCount() == 3) // �d��list�O3���٬O2�� �p�G�O3��ܹ���٨S��
				{
					// �i�D�O�H�A���C��
					// 2 yellow 3 red 4 green
					// �e�����Ʀr��ܧA�O����C�⪺�٬O�� �p�G�O3��ܧA����
					new SocketClient("3," + Integer.toString(mycolor),b.ip,b.port);

				} else if (list_color.getItemCount() == 2) // �p�G�O2�� ��ܹ��w�g����F�C�� �{�b���Fbutton�s�u��N�i�H�}�l
				{
					// �]���Կ��C�� �ҥH�A���
					b.isYourTurn = 0;
					b.makeTwoColor(b.otherColor);
					// �e�����Ʀr�p�G�O2��ܧA�O����C��
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