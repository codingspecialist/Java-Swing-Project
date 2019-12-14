package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;

public class LoginFrame extends JFrame {
	
	//해당 프레임에 있는 컴포넌트
	private JLabel contentPane, lblNewLabel;
	private JTextField tfUsername, tfPassword;
	private JButton btnJoin, btnLogin;

	public LoginFrame() {
		
		//UI생성
		renderUI();

		//이벤트
		eventInit();
	}
	
	public void renderUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);

		setSize(1200, 840);
		setLocationRelativeTo(null);

		contentPane = new JLabel(new ImageIcon("images/login.jpg"));
		// contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfUsername = new JTextField();
		tfUsername.setText("sui");
		tfUsername.setHorizontalAlignment(SwingConstants.CENTER);
		tfUsername.setBounds(551, 626, 263, 35);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);

		tfPassword = new JTextField();
		tfPassword.setText("123");
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		tfPassword.setColumns(10);
		tfPassword.setBounds(551, 670, 263, 35);
		contentPane.add(tfPassword);

		btnLogin = new JButton(new ImageIcon("images/log.png"));
		btnLogin.setBounds(627, 731, 182, 54);
		contentPane.add(btnLogin);

		btnJoin = new JButton(new ImageIcon("images/join.png"));
		btnJoin.setBounds(385, 731, 182, 54);
		contentPane.add(btnJoin);

		lblNewLabel = new JLabel(new ImageIcon("images/insert.png"));
		lblNewLabel.setBackground(Color.ORANGE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(312, 529, 568, 273);
		contentPane.add(lblNewLabel);
		setVisible(true);
	}
	
	public void eventInit() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// DB에 아이디와 비밀번호가 있는지 확인
				String username = tfUsername.getText();
				String password = tfPassword.getText();
				MemberDao dao = MemberDao.getInstance();
				int result = dao.findByUsernameAndPassword(username, password);
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "로그인을 성공하였습니다.");
					
					//대기화면 UI 프레임 생성 (메인쓰레드)
					new WaitingRoom(username);

					//화면 닫기
					dispose();
				}
				// 없으면 로그인 실패 메시지
				else {
					JOptionPane.showMessageDialog(null, "로그인이 실패하였습니다.");
				}
			}
		});

		// 회원가입 액션
		btnJoin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입 프레임으로 이동
				JoinFrame jf = new JoinFrame();
			}
		});
	}
	

	// main 메소드 프레임 실행
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
