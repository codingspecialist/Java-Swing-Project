package starz502Client.frames;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;

import starz502Client.models.LoginModel;

public class LoginFrame extends JFrame {

	private BufferedReader reader;
	private PrintWriter writer;
	
	private JPanel contentPane;
	private JTextField tfLId;
	private JPasswordField tfLPw;
	private JButton btnLogin;
	private JButton btnJoin;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public LoginFrame(BufferedReader r, PrintWriter w) {
		
		// ���� �����ӿ��� �Ѱ��ִ� reader, writer �ޱ�
		this.reader = r;
		this.writer = w;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				 ImageIcon img = new ImageIcon("images/login/login.png");
			     Dimension d = getSize();
			     g.drawImage(img.getImage(), 0, 0,d.width,d.height, null);
			}
		};
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		ImageIcon login = new ImageIcon("images/login/btnlogin.png");
		btnLogin = new JButton(login);
		btnLogin.setBounds(584, 429, 87, 34);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnLogin);
		
		ImageIcon join = new ImageIcon("images/login/btnjoin.png");
		btnJoin = new JButton(join);
		btnJoin.setBounds(485, 429, 87, 34);
		btnJoin.setContentAreaFilled(false);
		btnJoin.setBorderPainted(false);
		btnJoin.setFocusPainted(false);
		btnJoin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnJoin);
		
		tfLId = new JTextField();
		tfLId.setBounds(490, 322, 175, 33);
		tfLId.setColumns(10);
		tfLId.setOpaque(false);
		tfLId.setBorder(null);
		contentPane.add(tfLId);
		
		tfLPw = new JPasswordField();
		tfLPw.setColumns(10);
		tfLPw.setBounds(490, 383, 175, 33);
		tfLPw.setOpaque(false);
		tfLPw.setBorder(null);
		contentPane.add(tfLPw);
		
		//�α��� ��ư
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//==========================================================================
				
				// �������� id, pw �����͸� GSON���̺귯�� �̿��ؼ� JSON���� ������ ��
				// starz502Client.models ��Ű���� �ִ� LoginModel Ŭ������ ��ü�� ����� ������(id, pw)�� ���� ��
				// �ش� ��ü�� JSON ���·� ������ ��.
				
				//1. LoginModel ��ü ����� (LoginModel Ŭ���� �����ϸ鼭 �� ��)
				LoginModel loginModel = new LoginModel();
				
				loginModel.setStz_username(tfLId.getText());
				String pw = String.valueOf(tfLPw.getPassword()); // JPasswordField ��ü�� getText()�� ���� ������ �� ����
				loginModel.setStz_password(pw);
				
				//2. �ش� ��ü�� GSON ���̺귯���� �̿��Ͽ� JSON���� �����
				Gson gson = new Gson();
				String json = gson.toJson(loginModel);
				
				//3. ������� json�� ������ ������
				writer.println(json);
				writer.flush();
				
				//4. �����κ��� ���� �ޱ� (�α��� �����ϸ� true, �����ϸ� false�� ��������)
				try {
					String isLoginSuccess = reader.readLine();
					
					if(isLoginSuccess.equals("true")) { // �α��ο� �����ϸ� ���� ���������� �Ѿ
						// �κ� ���������� �Ѿ �� username ������ ������ : �����κ��� ���� ��ŷ ���� �߿��� �ڽ��� ������ Ư���ϱ� ���ؼ�
						new LobbyFrame(reader, writer, loginModel.getStz_username()); 
						dispose();
					}else { // �α��ο� �����ϸ� �����ߴٰ� �޼��� ����
						JOptionPane.showMessageDialog(null, "�α��� ����!!!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
//==========================================================================				
			}
		});
		
		//ȸ������ ��ư
		btnJoin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new JoinFrame(reader, writer);
			}
		});

		setVisible(true);

	}
}
