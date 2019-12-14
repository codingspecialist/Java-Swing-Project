package starz502Client.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitleFrame extends JFrame {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private JPanel panel;
	private JLabel lbSs;
	private JButton btnStart;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TitleFrame();
				} catch (Exception e) {
					e.printStackTrace();	
				}
			}
		});
	}

	public TitleFrame() {
		initialize();
		setVisible(true);
	}

	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);		
		setLocationRelativeTo(null); // 창 중앙에뜨기
		setResizable(false); // 창크기 변경 x
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon img = new ImageIcon("images/title/title.jpg");
				Dimension d = getSize();
				g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		ImageIcon start = new ImageIcon("images/title/btnStart.png");

		btnStart = new JButton(start); //JButton의 Border(외곽선)을 없애준다.
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBounds(420, 524, start.getIconWidth(), start.getIconHeight());
		btnStart.setFocusPainted(false);
		btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(btnStart);

		lbSs = new JLabel("");
		lbSs.setForeground(Color.RED);
		lbSs.setHorizontalAlignment(SwingConstants.CENTER);
		lbSs.setBounds(490, 500, 100, 15);
		panel.add(lbSs);

		// start 버튼
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lbSs.setText("서버에 접속중...");
				Thread t1 = new Thread(new ServerThread());
				t1.start();
			}
		});
	}

	class ServerThread implements Runnable {
		@Override
		public void run() {
			// 서버 연결 스레드
			try {
				socket = new Socket("localhost", 5000);//192.168.0.20
				System.out.println("서버접속 성공");
//==========================================================================				
				// 서버 연결에 성공하면 서버와 연결된 reader, writer 객체 생성함
				// reader, writer는 TitleFrame 에서는 필요 없지만 다른 프레임에서 필요하기 때문에 생성자를 통해서 매개변수로 넘겨줄 것임.
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());
//==========================================================================
				Thread.sleep(1500);
				
				// 서버와 연결된 reader, writer 객체를 매개변수로 다음 프레임에 넘겨줌
				new LoginFrame(reader, writer);
				dispose();

			} catch (Exception e2) {
				// 서버접속 실패
				lbSs.setText("접속실패");
			} finally {

			}

		}
	}
}