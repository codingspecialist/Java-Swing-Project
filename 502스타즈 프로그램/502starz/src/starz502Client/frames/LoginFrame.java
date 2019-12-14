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
		
		// 이전 프레임에서 넘겨주는 reader, writer 받기
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
		
		//로그인 버튼
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//==========================================================================
				
				// 서버에게 id, pw 데이터를 GSON라이브러리 이용해서 JSON으로 보내야 함
				// starz502Client.models 패키지에 있는 LoginModel 클래스의 객체를 만들고 데이터(id, pw)를 담은 후
				// 해당 객체를 JSON 형태로 보내면 됨.
				
				//1. LoginModel 객체 만들기 (LoginModel 클래스 참고하면서 볼 것)
				LoginModel loginModel = new LoginModel();
				
				loginModel.setStz_username(tfLId.getText());
				String pw = String.valueOf(tfLPw.getPassword()); // JPasswordField 객체는 getText()로 값을 가져올 수 없음
				loginModel.setStz_password(pw);
				
				//2. 해당 객체를 GSON 라이브러리를 이용하여 JSON으로 만들기
				Gson gson = new Gson();
				String json = gson.toJson(loginModel);
				
				//3. 만들어진 json을 서버로 보내기
				writer.println(json);
				writer.flush();
				
				//4. 서버로부터 응답 받기 (로그인 성공하면 true, 실패하면 false를 받을것임)
				try {
					String isLoginSuccess = reader.readLine();
					
					if(isLoginSuccess.equals("true")) { // 로그인에 성공하면 다음 프레임으로 넘어감
						// 로비 프레임으로 넘어갈 때 username 가지고 가야함 : 서버로부터 받은 랭킹 정보 중에서 자신의 정보만 특정하기 위해서
						new LobbyFrame(reader, writer, loginModel.getStz_username()); 
						dispose();
					}else { // 로그인에 실패하면 실패했다고 메세지 띄우기
						JOptionPane.showMessageDialog(null, "로그인 실패!!!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
//==========================================================================				
			}
		});
		
		//회원가입 버튼
		btnJoin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new JoinFrame(reader, writer);
			}
		});

		setVisible(true);

	}
}
