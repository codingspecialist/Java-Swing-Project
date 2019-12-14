package starz502Client.frames;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import starz502Client.models.JoinModel;

public class JoinFrame extends JFrame {

	private BufferedReader reader;
	private PrintWriter writer;

	private JPanel contentPane;
	private JButton btnJoin;
	private JButton btnBack;
	private JTextField tfJId;
	private JPasswordField tfJPw;
	private JPasswordField tfJcf;
	private JLabel checkMsg;
	private String pws, pwchecks;
	private boolean checkMsg1;

//   public static void main(String[] args) {
//      EventQueue.invokeLater(new Runnable() {
//         public void run() {
//            try {
//               JoinFrame frame = new JoinFrame();
//               
//            } catch (Exception e) {
//               e.printStackTrace();
//            }
//         }
//      });
//   }

	public JoinFrame(BufferedReader r, PrintWriter w) {
		this.reader = r;
		this.writer = w;

		// x ��ư �۵� ���ϰ� �ϴ� ��
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		setLocationRelativeTo(null);
		setResizable(false);

		// ����̹���
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon img = new ImageIcon("images/join/join.png");
				Dimension d = getSize();
				g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		contentPane.setLayout(null);
		setContentPane(contentPane);

		ImageIcon join = new ImageIcon("images/join/btnJoin.png");
		btnJoin = new JButton(join);
		btnJoin.setBounds(624, 478, 87, 34);
		btnJoin.setContentAreaFilled(false);
		btnJoin.setBorderPainted(false);
		btnJoin.setFocusPainted(false);
		btnJoin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnJoin);

		ImageIcon back = new ImageIcon("images/join/btnBack.png");
		btnBack = new JButton(back);
		btnBack.setBounds(510, 478, 87, 34);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnBack);

		tfJId = new JTextField();
		tfJId.setColumns(10);
		tfJId.setBounds(510, 264, 201, 34);
		tfJId.setOpaque(false);
		tfJId.setBorder(null);
		contentPane.add(tfJId);

		tfJPw = new JPasswordField();
		tfJPw.setColumns(10);
		tfJPw.setBounds(510, 325, 201, 34);
		tfJPw.setOpaque(false);
		tfJPw.setBorder(null);
		contentPane.add(tfJPw);

		tfJcf = new JPasswordField();
		tfJcf.setColumns(10);
		tfJcf.setBounds(510, 388, 201, 34);
		tfJcf.setOpaque(false);
		tfJcf.setBorder(null);
		contentPane.add(tfJcf);

		checkMsg = new JLabel("\uBE44\uBC00\uBC88\uD638 \uBD88\uC77C\uCE58");
		checkMsg.setHorizontalAlignment(SwingConstants.RIGHT);
		checkMsg.setBackground(Color.WHITE);
		checkMsg.setFont(new Font("���� ���", Font.BOLD, 14));
		checkMsg.setForeground(Color.RED);
		checkMsg.setBounds(606, 432, 103, 34);
		contentPane.add(checkMsg);

		// �ڷΰ��� ��ư
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// ȸ������ ��ư
		btnJoin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//========================================================================

				/**
				 * 
				 * ID, PW ������ �������� ������
				 * 
				 * �����κ��� ���� �޴� ��� ���� "1"(String Ÿ����)�� ������ ȸ�����Կ� ������ �� => ȸ�����Կ� �����ߴٴ� �˸�â ����
				 * "0"(String Ÿ����)�� ������ ȸ�����Կ� ������ �� => ȸ�����Կ� �����ߴٴ� �˸�â ����
				 * 
				 */

				String pw = String.valueOf(tfJPw.getPassword());
				String cf = String.valueOf(tfJcf.getPassword());

				// ��й�ȣ�� ��й�ȣ Ȯ���� ��ġ ����ġ����
				if (pw.equals(cf)) {
					checkMsg1 = true;
				} else {
					checkMsg1 = false;
				}

				if (checkMsg1) {
					// 1. JoinModel ��ü �����
					JoinModel joinModel = new JoinModel();

					joinModel.setStz_username(tfJId.getText());
					joinModel.setStz_password(pw);

					// 2. �ش� ��ü�� GSON ���̺귯���� �̿��Ͽ� JSON���� �����
					Gson gson = new Gson();
					String json = gson.toJson(joinModel);

					// 3. ������� json�� ������ ������
					try {
						writer.println(json);
						writer.flush();

						// 4. �����κ��� ���� �ޱ�(ȸ������ �����ϸ� true, �����ϸ� false�� �޴´�.)
						String isJoinSuccess = reader.readLine();

						if (isJoinSuccess.equals("true")) {
							JOptionPane.showMessageDialog(null, "�����մϴ�!. ȸ�������� �Ϸ�Ǿ����ϴ�.");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�.");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "��й�ȣ ����ġ", JOptionPane.WARNING_MESSAGE);
				}

//========================================================================            
			}
		});

		setVisible(true);

		test();
	}

	public void test() {
		pws = String.valueOf(tfJPw.getPassword());
		pwchecks = String.valueOf(tfJcf.getPassword());
		tfJPw.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pws = String.valueOf(tfJPw.getPassword());
				if (pws.equals(pwchecks)) {
					checkMsg.setText("��й�ȣ ��ġ");
					checkMsg.setForeground(Color.GREEN);
				} else {
					checkMsg.setText("��й�ȣ ����ġ");
					checkMsg.setForeground(Color.RED);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		tfJcf.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pwchecks = String.valueOf(tfJcf.getPassword());
				if (pws.equals(pwchecks)) {
					checkMsg.setText("��й�ȣ ��ġ");
					checkMsg.setForeground(Color.GREEN);
				} else {
					checkMsg.setText("��й�ȣ ����ġ");
					checkMsg.setForeground(Color.RED);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

}