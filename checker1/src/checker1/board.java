package checker1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class board extends Canvas {

	int[][] A = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 4, 0, 0, 0, 0 },
			{ 0, 0, 0, 3, 3, 1, 1, 1, 1, 1, 1, 4, 4, 0, 0, 0, 0 },
			{ 0, 0, 3, 3, 3, 1, 1, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0 },
			{ 0, 3, 3, 3, 3, 1, 1, 1, 1, 4, 4, 4, 4, 0, 0, 0, 0 },
			{ 3, 3, 3, 3, 3, 1, 1, 1, 4, 4, 4, 4, 4, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	int[][][] goal = {
			{ { 16, 4 }, { 15, 4 }, { 15, 5 }, { 14, 4 }, { 14, 5 }, { 14, 6 }, { 13, 4 }, { 13, 5 }, { 13, 6 },
					{ 13, 7 }, { 12, 4 }, { 12, 5 }, { 12, 6 }, { 12, 7 }, { 12, 8 } },
			{ { 8, 12 }, { 7, 12 }, { 7, 13 }, { 6, 12 }, { 6, 13 }, { 6, 14 }, { 5, 12 }, { 5, 13 }, { 5, 14 },
					{ 5, 15 }, { 4, 12 }, { 4, 13 }, { 4, 14 }, { 4, 15 }, { 4, 16 } },
			{ { 8, 4 }, { 7, 4 }, { 7, 5 }, { 6, 4 }, { 6, 5 }, { 6, 6 }, { 5, 4 }, { 5, 5 }, { 5, 6 }, { 5, 7 },
					{ 4, 4 }, { 4, 5 }, { 4, 6 }, { 4, 7 }, { 4, 8 } } };
	double l = 40;
	double dx = l * Math.cos(60 * Math.PI / 180);
	double dy = l * Math.sin(60 * Math.PI / 180);
	int r = 5;
	Point p0 = new Point();
	int mark = 0;
	int i_old, j_old;
	board b;
	int myColor = 2;// 預設為黃色2
	int otherColor =0; // 預設為0
	int isYourTurn = 3; // 0表示對方回合 1表示自己回合 3表示尚未開始  4表示遊戲結束 

	int[][] move_jump = { { -2, 0 }, { 2, 0 }, { 0, -2 }, { 0, 2 }, { -2, 2 }, { 2, -2 } };
	int[][] move_step = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { 1, -1 } };
	Image image_yourTurn;
	Image image_otherTurn;
	Image image_notYet;
	Image image_yourColor;
	Image image_otherColor;
	Image image_end;
	
	String ip = "127.0.0.1";
	int port = 8765;

	void clearmark() {
		int i, j;
		for (i = 0; i < 17; i++)
			for (j = 0; j < 17; j++) {
				if (A[i][j] > 4)
					A[i][j] = 1;
			}
		repaint();
	}

	void possiblemove(int i, int j) {
		int k, l;
		for (int m = 0; m < 6; m++) {
			k = i + move_jump[m][0];
			l = j + move_jump[m][1];
			if (k >= 0 && l >= 0 && k < 17 && l < 17)
				if (ismovable(i, j, k, l)) {
					A[k][l] = 5;
					possiblemove(k, l);
				}
			k = i + move_step[m][0];
			l = j + move_step[m][1];
			if (k >= 0 && l >= 0 && k < 17 && l < 17)
				if (ismovable(i, j, k, l) && A[i][j] != 5) {
					A[k][l] = 5;
				}
		}
		repaint();
	}

	boolean ismovable(int i, int j, int k, int l) {
		if (A[i][j] > 1 && A[k][l] == 1) {
			for (int m = 0; m < 6; m++) {
				if (k == i + move_step[m][0] && l == j + move_step[m][1])
					return true;
				else if ((k == i + move_jump[m][0] && l == j + move_jump[m][1])
						&& A[i + move_step[m][0]][j + move_step[m][1]] > 1)
					return true;
			}
		}
		return false;
	}

	public board() {

		p0.x = 150;
		p0.y = 40;
		b = this;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int i = (int) Math.round((x - p0.x - (y - p0.y) * dx / dy) / 2 / dx);
				int j = (int) Math.round((y - p0.y) / dy);
				// check does it start
				if (isYourTurn == 3) {
					clearmark();
					JOptionPane.showMessageDialog(null, "havn't started yet");
				}
				// check is that your color
				else if (A[i_old][j_old] != myColor) {
					clearmark();
					JOptionPane.showMessageDialog(null, "you can't move this");
				}
				// check is it your turn
				else if (isYourTurn != 1) {
					clearmark();
					JOptionPane.showMessageDialog(null, "not your turn");
				}

				else if(isYourTurn ==1){ 
					if (i == i_old && j == j_old) {
						clearmark();
					} else {
						if (A[i][j] == 5) {
							A[i][j] = mark;
							A[i_old][j_old] = 1;
							b.repaint();
							String s;
							s = new String("" + myColor + "," + 1 + "," + i_old + "," + j_old + "," + i + "," + j);
							new SocketClient(s,b.ip,b.port);
							isWin(myColor);
						}
						clearmark();

					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// if someone win
				if (isYourTurn == 4) {
					JOptionPane.showMessageDialog(null, "Please restart the game");
				} else {
					int x = e.getX();
					int y = e.getY();
					int i = (int) Math.round((x - p0.x - (y - p0.y) * dx / dy) / 2 / dx);
					int j = (int) Math.round((y - p0.y) / dy);
					mark = A[i][j];
					i_old = i;
					j_old = j;
					possiblemove(i, j);
					b.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		final int N = 17;
		int i, j;
		cell c = new cell();
		int x, y;

		// draw all the lines
		drawTheLine(g);

		// show who's turn
		whoseTurn(g, isYourTurn);

		// show each other's color
		showColor(g, myColor, otherColor);

		// draw the star
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++) {
				if (A[i][j] > 0) {
					x = (int) ((2 * i + j) * dx);
					y = (int) (j * dy);
					c.set(p0.x + x, p0.y + y, A[i][j] - 1);
					c.draw(g);
					g.drawString("(" + i + "," + j + ")", p0.x + x, p0.y + y + r + r + r);
				}
			}

	}

//---------------------------
	boolean set(int i, int j, int player) {
		if (A[i][j] < 1) {
			System.out.println("wrong position");
			return false;
		} else
			A[i][j] = player;
		return true;
	}

	boolean jump(int i, int j, int m, int n) {
		A[m][n] = A[i][j];
		A[i][j] = 1;
		repaint();
		isWin(otherColor);
		return true;
	}

	int getX(int i, int j) {
		int x;
		x = p0.x + (int) ((2 * i + j) * dx);
		return x;
	}

	int getY(int j) {
		int y;
		y = p0.y + (int) (j * dy);
		return y;
	}

	void Line(Graphics g, int x, int y, int xx, int yy) {
		int x1, x2, y1, y2;
		x1 = getX(x, y);
		y1 = getY(y);
		x2 = getX(xx, yy);
		y2 = getY(yy);
		g.setColor(Color.GRAY);
		g.drawLine(x1, y1, x2, y2);
	}

	void drawTheLine(Graphics g) {
		// the one way line
		for (int r = 0; r < 12; r++) {
			// enter the first (x,y) and the last (x,y)
			// Line(Graphics g,x1,y1,x2,y2)
			Line(g, r, 12, 12, r);
		}
		for (int r = 5; r < 8; r++) {
			Line(g, 4, r, r, 4);
		}
		for (int r = 13; r < 17; r++) {
			Line(g, r, 4, 12, (r - 8));
			Line(g, 4, r, (r - 8), 12);
		}
		// the another line
		for (int r = 1; r < 13; r++) {
			Line(g, r, (12 - r), r, 12);
		}
		for (int r = 13; r < 16; r++) {
			Line(g, r, 4, r, (20 - r));
		}
		for (int r = 4; r < 8; r++) {
			Line(g, r, 4, r, (12 - r));
			Line(g, r, 12, r, (20 - r));
		}
		// the other lines
		for (int r = 1; r < 13; r++) {
			Line(g, (12 - r), r, 12, r);
		}
		for (int r = 4; r < 8; r++) {
			Line(g, 4, r, (12 - r), r);
			Line(g, 12, r, (20 - r), r);
		}
		for (int r = 13; r < 16; r++) {
			Line(g, 4, r, (20 - r), r);
		}
	}

	void whoseTurn(Graphics g, int yourTurn) {
		try {
			image_yourTurn = ImageIO.read(new File("yourTurn.png"));
			image_otherTurn = ImageIO.read(new File("otherTurn.png"));
			image_notYet = ImageIO.read(new File("notYet.png"));
			image_end = ImageIO.read(new File("end.png"));
		} catch (Exception ex) {
			System.out.println("can't fine the image");
		}

		if (yourTurn == 1)
			g.drawImage(image_yourTurn, 150, 10, null);
		else if (yourTurn == 0)
			g.drawImage(image_otherTurn, 150, 10, null);
		else if (yourTurn == 4)
			g.drawImage(image_end, 150, 10, null);
		else
			g.drawImage(image_notYet, 150, 10, null);

	}

	public void makeTwoColor(int color) {
		// int color means the opponent's color
		int noPlayer = 1; // 預設沒有任何顏色
		int judge = myColor + color;
		otherColor = color;
		System.out.println(judge);
		// 2 yellow 3 red 4 green
		if (judge == 5) // mean green doesn't be chosen
			noPlayer = 4;
		else if (judge == 6) // mean red doesn't be chosen
			noPlayer = 3;
		else if (judge == 7) // mean yellow doesn't be chosen
			noPlayer = 2;

		for (int i = 0; i < 17; i++)
			for (int j = 0; j < 17; j++)
				if (A[i][j] == noPlayer)
					A[i][j] = 1;

		repaint();
	}

	void isWin(int color) {
		int r = color - 2;
		int count = 0;
		for (int i = 0; i < 15; i++) {
			if (A[goal[r][i][0]][goal[r][i][1]] != color) {
				break;
			} else
				count++;
		}
		if (count == 15) {
			// change the pic
			isYourTurn = 4;
			repaint();
			
			if (color == myColor)
				JOptionPane.showMessageDialog(null, "You win!");
			else if (color == otherColor)
				JOptionPane.showMessageDialog(null, "You lose");
		} else
		{
			if(color==myColor)		
				isYourTurn = 0;// 表示你下玩 換對手
			else
				isYourTurn = 1;
		}
			

	}

	void showColor(Graphics g, int my, int other) {
		String[] yourFileName = { "", "", "yourYellow.png", "yourRed.png", "yourGreen.png" };
		String[] otherFileName = { "", "", "otherYellow.png", "otherRed.png", "otherGreen.png" };
		try {
			image_yourColor = ImageIO.read(new File(yourFileName[my]));
			image_otherColor = ImageIO.read(new File(otherFileName[other]));

		} catch (Exception ex) {
			System.out.println("can't fine the color image");
		}
		if (my + other >= 5) {
			g.drawImage(image_yourColor, 200, 70, null);
			g.drawImage(image_otherColor, 200, 120, null);

		}

	}

}

class cell {
	Point p = new Point();
	int r = 10;
	int player = 0;
	Color yellow = new Color(240, 227, 113);
	Color red = new Color(244, 113, 113);
	Color green = new Color(143, 200, 128);
	Color gray = new Color(135, 135, 135);
//	final Color[] c = { Color.WHITE, Color.YELLOW, Color.RED, Color.GREEN, Color.GRAY };
	final Color[] c = { Color.WHITE, yellow, red, green, gray };

	void set(int x, int y, int player) {
		p.x = x;
		p.y = y;
		this.player = player;
	}

	void draw(Graphics g) {
		g.setColor(c[player]);
		g.fillOval(p.x - r, p.y - r, r + r, r + r);
		g.setColor(Color.BLACK);
		g.drawOval(p.x - r, p.y - r, r + r, r + r);
	}

}
