package starz502Client.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import starz502Client.models.ResultModel;

public class GameResultFrame extends JFrame implements Runnable{
	private BufferedReader reader;
	private PrintWriter writer;
	private String username;
	
	private ResultModel resultModel;
	
	private JPanel resultMainPanel, resultSubPanel;
	private JLabel resultWinLoseLabel, resultRankLabel, resultRatingPointLabel, resultExperienceText, resultExperienceNumber;
	private JProgressBar resultExperienceBar;
	private JButton btnExit, btnToLobby;

	public GameResultFrame(BufferedReader r, PrintWriter w, String username) {
		// ���� �����ӿ��� �Ѱ��ִ� reader, writer �ޱ�
		this.reader = r;
		this.writer = w;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(username);

		resultMainPanel = new JPanel();
		resultMainPanel = (JPanel) getContentPane();
		resultMainPanel.setLayout(null);
		resultMainPanel.setBackground(new Color(200, 190, 230));
		resultMainPanel.setBorder(new LineBorder(Color.BLACK, 5, false));

		resultSubPanel = new JPanel();
		resultSubPanel.setBorder(new LineBorder(new Color(0, 0, 0), 8));
		resultSubPanel.setLocation(150, 73);
		resultMainPanel.add(resultSubPanel);
		resultSubPanel.setSize(800, 600);
		resultSubPanel.setBackground(Color.WHITE);
		resultSubPanel.setLayout(null);

		resultWinLoseLabel = new JLabel("W I N");
		resultWinLoseLabel.setForeground(Color.RED);
		resultWinLoseLabel.setFont(new Font("Arial Black", Font.BOLD, 74));
		resultWinLoseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultWinLoseLabel.setBounds(0, 145, 800, 87);
		resultSubPanel.add(resultWinLoseLabel);

		resultRankLabel = new JLabel("RANK : 1st");
		resultRankLabel.setForeground(Color.ORANGE);
		resultRankLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
		resultRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultRankLabel.setBounds(0, 242, 800, 77);
		resultSubPanel.add(resultRankLabel);

		resultRatingPointLabel = new JLabel("RATING : 5500(+30)");
		resultRatingPointLabel.setForeground(Color.PINK);
		resultRatingPointLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
		resultRatingPointLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultRatingPointLabel.setBounds(0, 317, 800, 53);
		resultSubPanel.add(resultRatingPointLabel);

		resultExperienceText = new JLabel("EXP ");
		resultExperienceText.setForeground(Color.GREEN);
		resultExperienceText.setFont(new Font("Arial Black", Font.BOLD, 40));
		resultExperienceText.setHorizontalAlignment(SwingConstants.CENTER);
		resultExperienceText.setBounds(163, 380, 134, 36);
		resultSubPanel.add(resultExperienceText);

		resultExperienceBar = new JProgressBar();
		resultExperienceBar.setBounds(287, 380, 280, 36);
		//�����ǥ��
		resultExperienceBar.setStringPainted(true);
		resultSubPanel.add(resultExperienceBar);
		
		resultExperienceNumber = new JLabel("0/100");
		resultExperienceNumber.setFont(new Font("Arial Black", Font.BOLD, 14));
		resultExperienceNumber.setBounds(579, 388, 100, 15);
		resultSubPanel.add(resultExperienceNumber);
		
		ImageIcon exit = new ImageIcon("images/result/btnexit.png");
		btnExit = new JButton(exit);
		btnExit.setBounds(489, 480, 134, 65);
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(false);
		btnExit.setFocusPainted(false);
		btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		resultSubPanel.add(btnExit);

		ImageIcon lobby = new ImageIcon("images/result/btnlobby.png");
		btnToLobby = new JButton(lobby);
		btnToLobby.setBounds(635, 480, 134, 65);
		btnToLobby.setContentAreaFilled(false);
		btnToLobby.setBorderPainted(false);
		btnToLobby.setFocusPainted(false);
		btnToLobby.setCursor(new Cursor(Cursor.HAND_CURSOR));
		resultSubPanel.add(btnToLobby);
		


		// 1. ResultModel ��ü ����� username�� �־ ������
		resultModel = new ResultModel();
		resultModel.setStz_username(username);

		// 2. �ش� ��ü�� GSON ���̺귯���� �̿��Ͽ� JSON���� �����
		Gson gson = new Gson();
		String json = gson.toJson(resultModel);
		
		// 3. ������ ������
		writer.println(json);
		writer.flush();
		
		// 4. �����κ��� ���� �޴� ������ ����
		Thread t = new Thread(this);
		t.start();
		
		
		// ���� ��ư
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// �κ�� ���ư��� ��ư
		btnToLobby.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LobbyFrame(reader, writer, username);
				dispose();
			}
		});
		
		
		setVisible(true);
		
	}
	
	
	@Override
	public void run() {
		// �����κ��� ���� �޴� ������ ����
		while(true) {
			try {
				String jsonData = reader.readLine();
				
				char dataType = jsonData.toString().charAt(12);
				if(dataType=='5') {
					Gson gson = new Gson();
					resultModel = gson.fromJson(jsonData, ResultModel.class);
					
					if (resultModel.getRank() == 1) {
						// ��ũ 1�� win �������� lose
						resultWinLoseLabel.setText("W I N");
						resultRankLabel.setText("RANK : 1st");
						// 30 20 0 -10
						resultRatingPointLabel.setText("RATING : " + resultModel.getStz_rating() + "(+30)");
						resultExperienceBar.setValue(resultModel.getStz_exp());
						resultExperienceNumber.setText(resultModel.getStz_exp()+" / 100");

					} else if (resultModel.getRank() == 2) {
						// ��ũ 1�� win �������� lose
						resultWinLoseLabel.setText("L O S E");
						resultRankLabel.setText("RANK : 2nd");
						// 30 20 0 -10
						resultRatingPointLabel.setText("RATING : " + resultModel.getStz_rating() + "(+20)");
						resultExperienceBar.setValue(resultModel.getStz_exp());
						resultExperienceNumber.setText(resultModel.getStz_exp()+" / 100");

					} else if (resultModel.getRank() == 3) {
						// ��ũ 1�� win �������� lose
						resultWinLoseLabel.setText("L O S E");
						resultRankLabel.setText("RANK : 3rd");
						// 30 20 0 -10
						resultRatingPointLabel.setText("RATING : " + resultModel.getStz_rating() + "(+0)");
						resultExperienceBar.setValue(resultModel.getStz_exp());
						resultExperienceNumber.setText(resultModel.getStz_exp()+" / 100");
					} else {
						// ��ũ 1�� win �������� lose
						resultWinLoseLabel.setText("L O S E");
						resultRankLabel.setText("RANK : 4th");
						// 30 20 0 -10
						resultRatingPointLabel.setText("RATING : " + resultModel.getStz_rating() + "(-10)");
						resultExperienceBar.setValue(resultModel.getStz_exp());
						resultExperienceNumber.setText(resultModel.getStz_exp()+" / 100");
					}
					
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		
	}
	

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					new GameResultFrame(null, null, null);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
}
