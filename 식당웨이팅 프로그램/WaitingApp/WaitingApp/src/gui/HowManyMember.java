package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import dao.CustomerDaoCustomer;
import dao.MyCount;
import java.awt.Color;
import java.awt.SystemColor;

public class HowManyMember extends JFrame implements ActionListener {
	CustomerDaoCustomer dao = CustomerDaoCustomer.getInstance();
	private JPanel contentPane;
	private JTextField textField;
	
	String peopleNum;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HowManyMember frame = new HowManyMember();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	
	public HowManyMember() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 354);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setForeground(SystemColor.text);
		textPane.setBackground(SystemColor.textHighlight);
		textPane.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 24));
		textPane.setText("\uC778\uC6D0");
		textPane.setBounds(98, 10, 54, 45);
		contentPane.add(textPane);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 24));
		textField.setBounds(12, 59, 226, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton b1 = new JButton("1");
		b1.setBackground(Color.WHITE);
		b1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b1.setBounds(10, 114, 68, 45);
		contentPane.add(b1);
		b1.addActionListener(this);
		
		JButton b2 = new JButton("2");
		b2.setBackground(Color.WHITE);
		b2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b2.setBounds(90, 114, 68, 45);
		contentPane.add(b2);
		b2.addActionListener(this);
		
		JButton b3 = new JButton("3");
		b3.setBackground(Color.WHITE);
		b3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b3.setBounds(170, 114, 68, 45);
		contentPane.add(b3);
		b3.addActionListener(this);
		
		JButton b4 = new JButton("4");
		b4.setBackground(Color.WHITE);
		b4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b4.setBounds(10, 163, 68, 45);
		contentPane.add(b4);
		b4.addActionListener(this);
		
		JButton b5 = new JButton("5");
		b5.setBackground(Color.WHITE);
		b5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b5.setBounds(90, 163, 68, 45);
		contentPane.add(b5);
		b5.addActionListener(this);
		
		JButton b6 = new JButton("6");
		b6.setBackground(Color.WHITE);
		b6.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b6.setBounds(170, 163, 68, 45);
		contentPane.add(b6);
		b6.addActionListener(this);
		
		JButton b7 = new JButton("7");
		b7.setBackground(Color.WHITE);
		b7.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b7.setBounds(10, 212, 68, 45);
		contentPane.add(b7);
		b7.addActionListener(this);
		
		JButton b8 = new JButton("8");
		b8.setBackground(Color.WHITE);
		b8.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b8.setBounds(90, 212, 68, 45);
		contentPane.add(b8);
		b8.addActionListener(this);
		
		JButton b9 = new JButton("9");
		b9.setBackground(Color.WHITE);
		b9.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b9.setBounds(170, 212, 68, 45);
		contentPane.add(b9);
		b9.addActionListener(this);
		
		JButton bback = new JButton("\u25C0");
		bback.setBackground(Color.WHITE);
		bback.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		bback.setBounds(10, 260, 68, 45);
		contentPane.add(bback);
		bback.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					peopleNum=peopleNum.substring(0,peopleNum.length()-1);
					textField.setText(peopleNum);
				}catch (Exception e2) {

				}
				
			}
		});
		
		JButton b0 = new JButton("0");
		b0.setBackground(Color.WHITE);
		b0.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b0.setBounds(90, 260, 68, 45);
		contentPane.add(b0);
		b0.addActionListener(this);
		
		JButton bok = new JButton("\uC785\uB825");
		bok.setForeground(Color.WHITE);
		bok.setBackground(new Color(0, 100, 0));
		bok.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		bok.setBounds(170, 260, 68, 45);
		contentPane.add(bok);
		bok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(peopleNum == null) {
					
				}else {
					
					try {
						System.out.println(peopleNum);
						int people = Integer.parseInt(peopleNum);
						
						dao.insert(CustomerMainFrame.result, people);
						CustomerMainFrame.wait.setText(dao.select()+"");
						
//						ManagerMainFrame frame = new ManagerMainFrame();
//						setVisible(false);
//						new ManagerMainFrame().setVisible(true);

						
						JOptionPane.showMessageDialog(null, "¿¹¾àÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
						
						
						dispose();
					} catch (Exception e2) {
						
					}

				}
				
			}
		});
		setVisible(true);
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		peopleNum = textField.getText();
		peopleNum += e.getActionCommand();
		textField.setText(peopleNum);
	}
	
	
}
