package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import map.CookieMap;

public class WaitingRoom extends JFrame {

	private JPanel contentPane;
	private JTextField chatTf;
	public String chat;
	private JTextArea ta;
	private String username;
	
	public WaitingRoom() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create the frame.
	 */
	public WaitingRoom(String username) {
		this.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 915, 497);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		ImageIcon image4 = new ImageIcon("images/map3.png");
		ImageIcon image = new ImageIcon("images/map1.png");
		ImageIcon image2 = new ImageIcon("images/map2.png");
		Image icon = image2.getImage();
		Image icon2 = image.getImage();
		icon = icon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		icon2 = icon2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon image3 = new ImageIcon(icon);
		ImageIcon image5 = new ImageIcon(icon2);

		JPanel select1 = new JPanel();
		select1.setForeground(SystemColor.desktop);
		select1.setBounds(45, 48, 346, 123);
		contentPane.add(select1);
		select1.setBackground(new Color(0, 191, 255));
		select1.setLayout(null);
		
		select1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				select1.setBackground(Color.BLUE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				select1.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				select1.setBackground(new Color(0, 191, 255));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				select1.setBackground(Color.BLUE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				new CookieMap(username);
			}
		});

		JLabel map1 = new JLabel(image5);
		map1.setBounds(12, 10, 102, 104);
		select1.add(map1);

		JLabel mapName = new JLabel("\uCFE0\uD0A4 \uB9F5");
		mapName.setForeground(Color.YELLOW);
		mapName.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		mapName.setBackground(Color.BLUE);
		mapName.setBounds(126, 10, 175, 42);
		select1.add(mapName);

		JLabel conditon = new JLabel("Waiting..");
		conditon.setForeground(Color.RED);
		conditon.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		conditon.setBounds(126, 62, 92, 35);
		select1.add(conditon);

		JLabel userNum = new JLabel("1/8");
		userNum.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		userNum.setBounds(249, 62, 71, 35);
		select1.add(userNum);

		JPanel select2 = new JPanel();
		select2.setLayout(null);
		select2.setForeground(Color.BLACK);
		select2.setBackground(new Color(0, 191, 255));
		select2.setBounds(476, 48, 346, 123);
		contentPane.add(select2);
		select2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				select2.setBackground(Color.BLUE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				select2.setBackground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				select2.setBackground(new Color(0, 191, 255));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				select2.setBackground(Color.BLUE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		JLabel map2 = new JLabel(image3);
		map2.setBounds(12, 10, 102, 104);
		select2.add(map2);

		JLabel mamName2 = new JLabel("\uD574\uC801 \uB9F5");
		mamName2.setForeground(Color.YELLOW);
		mamName2.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		mamName2.setBackground(Color.BLUE);
		mamName2.setBounds(148, 10, 175, 42);
		select2.add(mamName2);

		JLabel condition2 = new JLabel("Waiting..");
		condition2.setForeground(Color.RED);
		condition2.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		condition2.setBounds(148, 62, 92, 35);
		select2.add(condition2);

		JLabel userNum2 = new JLabel("1/8");
		userNum2.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		userNum2.setBounds(252, 62, 71, 35);
		select2.add(userNum2);

		chatTf = new JTextField();
		chatTf.setBackground(new Color(0, 191, 255));
		chatTf.setBounds(40, 390, 600, 35);
		contentPane.add(chatTf);
		chatTf.setColumns(10);

		JPanel listPanel = new JPanel();
		listPanel.setBackground(new Color(0, 191, 255));
		listPanel.setBounds(704, 218, 164, 207);
		contentPane.add(listPanel);
		listPanel.setLayout(null);

		JList list = new JList();
		list.setBounds(153, 194, -142, -189);
		listPanel.add(list);

		ta = new JTextArea();
		ta.setBounds(45, 218, 600, 161);
		ta.setBackground(new Color(0, 191, 255));
		contentPane.add(ta);
		ta.enable(false);

		ChatThread ct = new ChatThread();
		ct.send(username);
		ct.start();

		chatTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					// Server·Î Àü¼Û
					String msg = chatTf.getText();
					chatTf.setText("");
					
					ct.send(msg);
				}
			}
		});

	}

	class ChatThread extends Thread {

		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;

		public ChatThread() {
			// ¼­¹ö ¿¬°á
			try {
				socket = new Socket("localhost", 5000);
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void run() {
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					ta.append(line + "\n");
					repaint();
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// UI¾²·¹µå »ç¿ë
		public void send(String msg) {
			writer.println(msg);
		}
	}

}