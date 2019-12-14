package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DBConnection;

@SuppressWarnings("serial")
public class Join extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId, txtPassword, txtcheck, txtbirth, txtmobile;
	private JButton btnJoinComplete, btnPrev;
	private JCheckBox cbAgree;

	private static Connection conn;
	private static PreparedStatement pstmt;

	private static final String SQL = "INSERT INTO USERS (ID, USER_ID, PASSWORD, BIRTH_DATE, PHONE, PRIVACY_FG, ADMIN_FG, INS_DT, DEL_FG) VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, 'N', SYSDATE,'N')";
	private static final String SQL2 = "SELECT * FROM USERS WHERE USER_ID = ?";

	public Join() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		btnJoinComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userid = txtUserId.getText();
				if (userid.length() < 8) {
					JOptionPane.showMessageDialog(null, "아이디는 8글자 이상 입력해주세요");
					txtUserId.setText("");
				}
				
				String password = txtPassword.getText();
				if (password.length() < 8) {
					JOptionPane.showMessageDialog(null, "비밀번호는 8글자 이상 입력해주세요");
					txtPassword.setText("");
				}
				
				String passwordCheck = txtcheck.getText();
				if (!(password.equals(passwordCheck))) {
					JOptionPane.showMessageDialog(null, "비밀번호가 동일하지 않습니다.");
					txtPassword.setText("");
					txtcheck.setText("");
				}

				String regExp = "^[0-9]+$";
				String birth = txtbirth.getText();
				if (birth.length() != 8) {
					JOptionPane.showMessageDialog(null, "생년월일은 8글자로 입력해주세요 ex)19940815");
					txtbirth.setText("");
				} else if (!(birth.matches(regExp))) {
					JOptionPane.showMessageDialog(null, "생년월일은 숫자만 입력할 수 있습니다");
					txtbirth.setText("");
				}
				
				String mobile = txtmobile.getText();
				if (!(mobile.length() == 10 || mobile.length() == 11)) {
					JOptionPane.showMessageDialog(null, "전화번호는 10-11자리만 가능합니다");
					txtmobile.setText("");
				} else if (!(mobile.matches(regExp))) {
					JOptionPane.showMessageDialog(null, "전화번호는 숫자만 입력할 수 있습니다");
					txtmobile.setText("");
				}
				
				String privacyFg = "N";
				if (cbAgree.isSelected() == true) {
					privacyFg = "Y";
				} else {
					JOptionPane.showMessageDialog(null, "개인정보 수집 및 이용 약관에 동의해주세요");
				}
				
				conn = DBConnection.getConnection();
				int checkId = 0;
				try {
					pstmt = conn.prepareStatement(SQL2);
					pstmt.setString(1, userid);
					checkId = pstmt.executeUpdate();
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if(checkId == 1) {
					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.\n다른 아이디를 이용해주세요.");
				}

				if (userid.length() >= 8 && password.length() >= 8 && password.equals(passwordCheck)
						&& birth.length() == 8 && (mobile.length() == 10 || mobile.length() == 11)
						&& cbAgree.isSelected() == true && birth.matches(regExp) && mobile.matches(regExp)
						&& checkId == 0) {

					conn = DBConnection.getConnection();
					int returnCnt = 0;
					try {
						pstmt = conn.prepareStatement(SQL);
						pstmt.setString(1, userid);
						pstmt.setString(2, password);
						pstmt.setString(3, birth);
						pstmt.setString(4, mobile);
						pstmt.setString(5, privacyFg);

						returnCnt = pstmt.executeUpdate();
						conn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					if(returnCnt == 1) {
						JOptionPane.showMessageDialog(null, "회원가입 완료");
						new Login();
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "회원가입 실패, 다시 시도해 주세요.");
					}
				}
			}
		});
		
		cbAgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String consent = "[필수] 개인정보 수집 및 이용 동의\r\n" + "\r\n" + "아래의 목적으로 개인정보를 수집 및 이용합니다\r\n" + "\r\n"
						+ "1. 목적 : 개인 식별, 프로그램 기능 사용\r\n" + "2. 항목 : 아이디, 휴대폰 번호, 생년월일\r\n"
						+ "3. 보유 기간 : 회원 탈퇴 후 3개월까지 보유\r\n" + "4. 개인정보의 수집 및 이용에 대한 동의를 거부할 수 있으나, 기능 사용이 제한됩니다.";
				int result = JOptionPane.showConfirmDialog(null, consent, "Confirm", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					cbAgree.setSelected(true);
				} else if (result == JOptionPane.NO_OPTION) {
					cbAgree.setSelected(false);
				}
			}
		});

		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
				frame.dispose();
			}
		});

		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("영화 예매 프로그램 ver1.0");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		txtUserId = custom.setTextField("txtUserId", "ID", 35, 180, 350, 40);
		txtPassword = custom.setPasswordField("txtPassword", "PASSWORD", 35, 240, 350, 40);
		txtcheck = custom.setPasswordField("txtcheck", "PASSWORD", 35, 300, 350, 40);
		txtbirth = custom.setTextField("txtbirth", "BIRTH", 35, 360, 350, 40);
		txtmobile = custom.setTextField("txtmobile", "MOBILE", 35, 420, 350, 40);
		cbAgree = custom.setCheckBox("cbAgree", " ARE YOU AGREE?", 35, 485);

		btnJoinComplete = custom.setBtnBlue("btnBlue", "회원가입완료", 550);
		btnPrev = custom.setBtnWhite("btnWhite", "이전으로", 605);
	}
}