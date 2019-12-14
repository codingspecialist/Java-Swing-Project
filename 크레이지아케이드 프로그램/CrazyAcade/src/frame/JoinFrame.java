package frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;
import models.Member;
import java.awt.Color;
import java.awt.SystemColor;

public class JoinFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblJoin;
	private JButton joinCompleteBtn;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JTextField tfName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinFrame frame = new JoinFrame();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JoinFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(576, 396);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblJoin = new JLabel("\uD06C\uC544 \uAC8C\uC784\uC544\uC774\uB514 \uB9CC\uB4E4\uAE30");
		Font f1 = new Font("����", Font.BOLD, 20); //�ü� ���� ����
		lblJoin.setFont(f1); 
		lblJoin.setBounds(28, 27, 280, 35);
		contentPane.add(lblJoin);
		
		JLabel lblUsername = new JLabel("\uBE44\uBC00\uBC88\uD638");
		lblUsername.setBackground(Color.BLUE);
		lblUsername.setBounds(17, 159, 121, 28);
		contentPane.add(lblUsername);
		
		JLabel label = new JLabel("\uAC8C\uC784\uC544\uC774\uB514");
		label.setBackground(Color.BLUE);
		label.setBounds(17, 113, 121, 20);
		contentPane.add(label);
		
		JLabel lblName = new JLabel("\uC774\uB984");
		lblName.setBounds(17, 210, 121, 20);
		contentPane.add(lblName);

		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		tfUsername.setBounds(159, 106, 186, 35);
		contentPane.add(tfUsername);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(159, 156, 186, 35);
		contentPane.add(tfPassword);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(159, 203, 186, 35);
		contentPane.add(tfName);
		
		
		joinCompleteBtn = new JButton("ȸ�����ԿϷ�");
		joinCompleteBtn.setBounds(206, 363, 139, 29);
		contentPane.add(joinCompleteBtn);
		
		JButton joinCompleteBtn = new JButton("\uD655\uC778");
		joinCompleteBtn.setForeground(Color.WHITE);
		joinCompleteBtn.setBackground(Color.BLUE);
		joinCompleteBtn.setBounds(28, 275, 125, 29);
		contentPane.add(joinCompleteBtn);
		
		JButton canclebtn = new JButton("\uCDE8\uC18C");
		canclebtn.setForeground(Color.WHITE);
		canclebtn.setBackground(Color.BLUE);
		canclebtn.setBounds(202, 275, 125, 29);
		contentPane.add(canclebtn);
		
		setVisible(true);
		//ȸ�����ԿϷ� �׼�
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ȸ������ ����(DB�� insert)
				Member member = new Member();
				member.setUsername(tfUsername.getText());
				member.setPassword(tfPassword.getText());
				member.setName(tfName.getText());
				
				MemberDao dao = MemberDao.getInstance();
				int result = dao.save(member);
				
				if(result==1) {
					JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�."); //�˸�â
					dispose();//�� â ���°�				
				}
				else {
					JOptionPane.showMessageDialog(null, "ȸ�������� �����Ͽ����ϴ�."); //�˸�â
				}
			}
		});
		
		
		canclebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}
}