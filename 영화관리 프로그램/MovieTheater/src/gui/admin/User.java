package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDao;
import models.Users;

@SuppressWarnings("serial")
public class User extends JDialog {

	private JPanel contentPane;
	private JLabel lbTitle, lbUserId, lbBirth, lbPhone, lbPrivacyFg, lbAdminFg, lbInsDt, lbDelFg, lbDelDt;
	private JTextField txtUserId, txtBirth, txtPhone, txtPrivacyFg, txtAdminFg, txtInsDt, txtDelFg, txtDelDt;
	private JButton btnInsUpd, btnDelCan;

	public User(Main mainFrame, int status, String userId) {
		Main main = mainFrame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 450);

		init();

		int executeCd = 1;

		UserDao dao = UserDao.getInstance();
		Users user;

		if (status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if (status == 2) {
			user = dao.selectOne(userId);
			if (user == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtUserId.setText(user.getUserId());
				txtBirth.setText(user.getBirthDate() + "");
				txtPhone.setText(user.getPhone());
				txtPrivacyFg.setText(user.getPrivacyFg());
				txtAdminFg.setText(user.getAdminFg());
				txtInsDt.setText(user.getInsDt());
				txtDelFg.setText(user.getDelDt());
				txtDelDt.setText(user.getDelDt());
			}
		} else if (status == 3) {
			user = dao.selectOne(userId);
			if (user == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtUserId.setText(user.getUserId());
				txtUserId.setEditable(false);
				txtBirth.setText(user.getBirthDate() + "");
				txtBirth.setEditable(false);
				txtPhone.setText(user.getPhone());
				txtPhone.setEditable(false);
				txtPrivacyFg.setText(user.getPrivacyFg());
				txtPrivacyFg.setEditable(false);
				txtAdminFg.setText(user.getAdminFg());
				txtAdminFg.setEditable(false);
				txtInsDt.setText(user.getInsDt());
				txtInsDt.setEditable(false);
				txtDelFg.setText(user.getDelFg());
				txtDelFg.setEditable(false);
				txtDelDt.setText(user.getDelDt());
				txtDelDt.setEditable(false);
			}
		}

		btnInsUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = dao.updatePassword(userId);
				if (result == -1) {
					JOptionPane.showMessageDialog(contentPane, "ER3:비밀번호를 초기화 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else if (result == 0) {
					JOptionPane.showMessageDialog(contentPane, "ER4:비밀번호를 초기화 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(contentPane, "비밀번호 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
					main.userTable.setModel(main.setUserTable(null));
					dispose();
				}
			}
		});

		btnDelCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int check = JOptionPane.showConfirmDialog(contentPane, "탈퇴하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (check == JOptionPane.YES_OPTION) {
					int result = dao.updateDel(userId);
					if (result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER5:탈퇴처리를 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else if (result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER6:탈퇴처리를 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "회원탈퇴가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.userTable.setModel(main.setUserTable(null));
						dispose();
					}
				}
			}
		});

		setVisible(true);

		if (executeCd == -1) {
			dispose();
		}
	}

	private void init() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("영화관 관리 프로그램 ver1.0");

		lbTitle = new JLabel("사용자 관리");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 137, 40);
		contentPane.add(lbTitle);

		lbUserId = new JLabel("아이디");
		lbUserId.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUserId.setBounds(30, 60, 100, 20);
		contentPane.add(lbUserId);

		txtUserId = new JTextField();
		txtUserId.setBounds(140, 60, 120, 20);
		contentPane.add(txtUserId);

		lbBirth = new JLabel("생년월일");
		lbBirth.setHorizontalAlignment(SwingConstants.RIGHT);
		lbBirth.setBounds(30, 90, 100, 20);
		contentPane.add(lbBirth);

		txtBirth = new JTextField();
		txtBirth.setBounds(140, 90, 120, 20);
		contentPane.add(txtBirth);

		lbPhone = new JLabel("전화번호");
		lbPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPhone.setBounds(30, 120, 100, 20);
		contentPane.add(lbPhone);

		txtPhone = new JTextField();
		txtPhone.setBounds(140, 120, 120, 20);
		contentPane.add(txtPhone);

		lbPrivacyFg = new JLabel("개인정보 동의");
		lbPrivacyFg.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPrivacyFg.setBounds(30, 150, 100, 20);
		contentPane.add(lbPrivacyFg);

		txtPrivacyFg = new JTextField();
		txtPrivacyFg.setBounds(140, 150, 120, 20);
		contentPane.add(txtPrivacyFg);

		lbAdminFg = new JLabel("관리자 여부");
		lbAdminFg.setHorizontalAlignment(SwingConstants.RIGHT);
		lbAdminFg.setBounds(30, 180, 100, 20);
		contentPane.add(lbAdminFg);

		txtAdminFg = new JTextField();
		txtAdminFg.setBounds(140, 180, 120, 20);
		contentPane.add(txtAdminFg);

		lbInsDt = new JLabel("가입일자");
		lbInsDt.setHorizontalAlignment(SwingConstants.RIGHT);
		lbInsDt.setBounds(30, 210, 100, 20);
		contentPane.add(lbInsDt);

		txtInsDt = new JTextField();
		txtInsDt.setBounds(140, 210, 120, 20);
		contentPane.add(txtInsDt);

		lbDelFg = new JLabel("삭제여부");
		lbDelFg.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDelFg.setBounds(30, 240, 100, 20);
		contentPane.add(lbDelFg);

		txtDelFg = new JTextField();
		txtDelFg.setBounds(140, 240, 120, 20);
		contentPane.add(txtDelFg);

		lbDelDt = new JLabel("삭제일자");
		lbDelDt.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDelDt.setBounds(30, 270, 100, 20);
		contentPane.add(lbDelDt);

		txtDelDt = new JTextField();

		txtDelDt.setBounds(140, 270, 120, 20);
		contentPane.add(txtDelDt);

		btnInsUpd = new JButton("비밀번호 초기화");
		btnInsUpd.setBounds(23, 320, 130, 30);
		contentPane.add(btnInsUpd);

		btnDelCan = new JButton("회원탈퇴");
		btnDelCan.setBounds(163, 320, 100, 30);
		contentPane.add(btnDelCan);
	}

}