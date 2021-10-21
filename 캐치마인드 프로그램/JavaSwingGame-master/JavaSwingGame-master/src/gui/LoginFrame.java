package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Client.MainClient;
import dao.UserDao;
import models.User;
import oracle.net.aso.c;
import utils.Protocol;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Color;

public class LoginFrame extends JFrame {

	private final static String TAG = "SigninFrame : ";

	public LoginFrame loginFrame = this;
	
	public JPanel pLogin;
	public JButton btID, btPW, btSign, btLogin;
	public JTextField tfID;
	public JPasswordField tfpw;
	public MainClient mainClient;
	public ImageIcon icon;
//	public ArrayList<String> userName = new ArrayList<>();

	// 생성자
	public LoginFrame() {
		back();
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);	
	}

	private void back() {
		icon = new ImageIcon("src/images/loginFrame.png");
		pLogin = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		
	}
	
	// 객체생성
	private void initObject() {
		
//		mainClient = new MainClient(loginFrame);
//		pLogin = new JPanel();
//		pLogin.setBackground(Color.WHITE);

//		btID = new JButton(new ImageIcon("src/images/tbID.png"));
//		btPW = new JButton(new ImageIcon("src/images/tbPw.png"));
		btSign = new JButton(new ImageIcon("src/images/tbSignin.png"));
		btLogin = new JButton(new ImageIcon("src/images/tbLogin.png"));

		tfID = new JTextField();
		tfpw = new JPasswordField();
	}

	// 데이터 초기화
	private void initData() {

	}

	// 디자인
	private void initDesign() {
		// 1. 기본세팅
		loginFrame.setTitle("Login");
		
		loginFrame.setBounds(100, 100, 382, 489);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		// 2. 패널세팅
		getContentPane().add(pLogin);
		pLogin.setLayout(null);

		// 3. 디자인
		tfID.setColumns(10);
		tfID.setBounds(38, 177, 285, 43);
		Border borderLine1 = BorderFactory.createLineBorder(Color.BLACK, 3);
		tfID.setBorder(borderLine1);
//		btID.setBounds(38, 145, 89, 27);
		tfpw.setBounds(38, 269, 285, 43);
		Border borderLine = BorderFactory.createLineBorder(Color.BLACK, 3);
		tfpw.setBorder(borderLine);
//		btPW.setBounds(38, 236, 100, 27);
		tfpw.setColumns(10);
		btSign.setBounds(49, 350, 124, 41);
		btLogin.setBounds(189, 350, 124, 41);

		// 4. 패널에 컴포넌트 추가
//		pLogin.add(btID);
		pLogin.add(tfID);
//		pLogin.add(btPW);
		pLogin.add(tfpw);
		pLogin.add(btSign);
		pLogin.add(btLogin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(111, 281, 120, -1);
		pLogin.add(separator);
	}

	// 리스너 등록
	private void initListener() {

		btSign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SigninFame(mainClient);
				loginFrame.setVisible(false);
			}
		});
		
		btLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// userdao에 셀렉트문으로 select from where username 이랑 qw가 같을경우 성공/ 리턴이 0개가되면 오류메시지
				UserDao userDao = UserDao.getInstance();
				int result = userDao.로그인(tfID.getText(), tfpw.getText());

				if (result == 1) {
					new GameRoomFrame(tfID.getText());
					loginFrame.setVisible(false);
					// 로그인 성공시 list 에 담아서 push
					String userName = tfID.getText() + ",";
//					System.out.println(TAG + "userN 확인 : " + userN);
//					userName.add(userN);
//					System.out.println(TAG + "userName 확인 : " + userName);
//					mainClient.userSend(userName);
				} else {
					JOptionPane.showMessageDialog(null, "로그인에 실패했습니다.");
					tfID.setText("");
					tfpw.setText("");
				}

			}
		});

	}
}
