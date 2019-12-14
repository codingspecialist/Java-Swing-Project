package musicMaker;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dbconn.UserDB;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername, tfPassword;
	

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 241);
		setResizable(false);//크기고정
		setLocationRelativeTo(null);//정중앙에 실행된다.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblUsername.setBounds(51, 23, 83, 42);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblPassword.setBounds(51, 73, 83, 42);
		contentPane.add(lblPassword);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(146, 44, 116, 21);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(146, 86, 116, 21);
		contentPane.add(tfPassword);
		
		JButton btnLogin = new JButton("\uB85C\uADF8\uC778");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setBounds(165, 138, 97, 23);
		contentPane.add(btnLogin);
		
		JButton btnJoin = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btnJoin.setBounds(51, 138, 97, 23);
		contentPane.add(btnJoin);
		setVisible(true);
		
		
	
		//로그인 액션
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDB userDB = UserDB.getInstance();
				String username = tfUsername.getText();
				String password = tfPassword.getText();
				int result = userDB.findByUsernameAndPassword(username,password);
				
				if(result == 1) {
					JOptionPane.showMessageDialog(null, "Success Login");
					new MusicMakerApp(username);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Fail Login");
				}
			}
	});
		
		//회원가입 액션
		btnJoin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new JoinFrame();
			
			}
		});
	}
}
