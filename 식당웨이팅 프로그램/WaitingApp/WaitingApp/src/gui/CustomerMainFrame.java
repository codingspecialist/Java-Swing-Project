package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.MyCount;
import dao.CustomerDaoCustomer;
import dao.SelectThread;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;


public class CustomerMainFrame extends JFrame  implements ActionListener{

	CustomerDaoCustomer dao = CustomerDaoCustomer.getInstance();

	private JPanel contentPane;
	private JTextField textField;
	public static JTextField wait;
	static String result;
	JButton bok;
//	private JTextField peopleNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMainFrame frame = new CustomerMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
		
		
	public CustomerMainFrame() throws Exception {
		
		ImageIcon icon = new ImageIcon("http://t1.daumcdn.net/liveboard/linkagelab/a83c6dd975a347229061bfceaa847aaf.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		SelectThread st = new SelectThread();
		
		JTextArea txtrAsdf = new JTextArea();
		txtrAsdf.setForeground(Color.WHITE);
		txtrAsdf.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		txtrAsdf.setBackground(Color.DARK_GRAY);
		txtrAsdf.setText("\uD734\uB300\uD3F0 \uBC88\uD638\uB97C \uC785\uB825\uD558\uC2DC\uBA74");
		txtrAsdf.setBounds(38, 47, 218, 29);
		contentPane.add(txtrAsdf);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setText("\uBC88\uD638\uD45C\uC640 \uC21C\uBC88\uC744");
		textArea.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setBounds(38, 83, 218, 29);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setForeground(Color.WHITE);
		textArea_1.setText("\uC54C\uB9BC\uD1A1\uC73C\uB85C \uC54C\uB824\uB4DC\uB9BD\uB2C8\uB2E4.");
		textArea_1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		textArea_1.setBackground(Color.DARK_GRAY);
		textArea_1.setBounds(38, 117, 231, 29);
		contentPane.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setForeground(Color.YELLOW);
		textArea_2.setText("\uBB38\uC790\uAC00 \uC218\uC2E0\uD6C4 \uC785\uC7A5\uD558\uC2DC\uBA74 \uB429\uB2C8\uB2E4.");
		textArea_2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		textArea_2.setBackground(Color.DARK_GRAY);
		textArea_2.setBounds(38, 199, 289, 29);
		contentPane.add(textArea_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 784, 461);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setForeground(Color.BLACK);
		textArea_4.setBackground(Color.WHITE);
		textArea_4.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 13));
		textArea_4.setText("\uD734\uB300\uD3F0 \uBC88\uD638\uB97C \uC785\uB825\uD574\uC8FC\uC138\uC694");
		textArea_4.setBounds(509, 38, 172, 22);
		panel.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setBackground(Color.WHITE);
		textArea_5.setForeground(Color.BLACK);
		textArea_5.setText("\u203B\uC11C\uBE44\uC2A4 \uC774\uC6A9\uC57D\uAD00\uACFC \uAC1C\uC778\uC815\uBCF4 \uCDE8\uAE09\uBC29\uCE68\uC5D0 \uB3D9\uC758\uD569\uB2C8\uB2E4.");
		textArea_5.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 8));
		textArea_5.setBounds(495, 62, 204, 15);
		panel.add(textArea_5);
		
		wait = new JTextField();
		wait.setBounds(42, 346, 100, 92);
		panel.add(wait);
		wait.setBackground(new Color(51, 51, 204));
		wait.setForeground(Color.WHITE);
		wait.setHorizontalAlignment(JTextField.RIGHT);
		wait.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 48));
		
		wait.setText(dao.select()+"");
		wait.setColumns(10);
		;
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setText("\uD300");
		textArea_6.setForeground(Color.YELLOW);
		textArea_6.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 24));
		textArea_6.setBackground(Color.DARK_GRAY);
		textArea_6.setBounds(142, 380, 41, 43);
		panel.add(textArea_6);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(43, 318, 100, 124);
		panel.add(textArea_3);
		textArea_3.setText("\uD604\uC7AC \uC6E8\uC774\uD305");
		textArea_3.setForeground(Color.YELLOW);
		textArea_3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		textArea_3.setBackground(Color.DARK_GRAY);
		
		JButton b1 = new JButton("1");
		b1.setBackground(Color.WHITE);
		b1.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b1.setBounds(487, 196, 72, 52);
		panel.add(b1);
		
		JButton b2 = new JButton("2");
		b2.setBackground(Color.WHITE);
		b2.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b2.setBounds(560, 196, 72, 52);
		panel.add(b2);
		
		JButton b3 = new JButton("3");
		b3.setBackground(Color.WHITE);
		b3.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b3.setBounds(633, 196, 72, 52);
		panel.add(b3);
		
		JButton b6 = new JButton("6");
		b6.setBackground(Color.WHITE);
		b6.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b6.setBounds(633, 250, 72, 52);
		panel.add(b6);
		
		JButton b5 = new JButton("5");
		b5.setBackground(Color.WHITE);
		b5.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b5.setBounds(560, 250, 72, 52);
		panel.add(b5);
		
		JButton b4 = new JButton("4");
		b4.setBackground(Color.WHITE);
		b4.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b4.setBounds(487, 250, 72, 52);
		panel.add(b4);
		
		JButton b7 = new JButton("7");
		b7.setBackground(Color.WHITE);
		b7.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b7.setBounds(487, 304, 72, 52);
		panel.add(b7);
		
		JButton b8 = new JButton("8");
		b8.setBackground(Color.WHITE);
		b8.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b8.setBounds(560, 304, 72, 52);
		panel.add(b8);
		
		JButton b9 = new JButton("9");
		b9.setBackground(Color.WHITE);
		b9.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b9.setBounds(633, 304, 72, 52);
		panel.add(b9);
		
		bok = new JButton("OK");
		bok.setForeground(Color.WHITE);
		bok.setBackground(new Color(0, 128, 0));
		bok.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		bok.setBounds(633, 357, 72, 52);
		panel.add(bok);
		
		JButton b0 = new JButton("0");
		b0.setBackground(Color.WHITE);
		b0.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		b0.setBounds(560, 357, 72, 52);
		panel.add(b0);
		
		JButton bback = new JButton("\u25C0");
		bback.setBackground(Color.WHITE);
		bback.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		bback.setBounds(487, 357, 72, 52);
		panel.add(bback);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds(487, 78, 218, 117);
		panel.add(textField);
		textField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 24));
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(465, 24, 260, 414);
		panel.add(panel_1);
		bback.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
						try {
				result = result.substring(0,result.length()-1);
				textField.setText(result);
						} catch (Exception e2) {
							
						}
				
			}
		});
		b0.addActionListener(this);
		bok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(e.getSource() == bok) {
					if(result == null) {
						System.out.println("¿¬¶ôÃ³Àû¾î¶ó");
						JOptionPane.showMessageDialog(null, "¿¬¶ôÃ³¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
					}else {
						try {

							new HowManyMember();
						} catch (Exception e2) {
							System.out.println("½ÇÆÐ");
							// TODO: handle exception
						}
						

					}
				}
				
			}
		});
		b9.addActionListener(this);
		b8.addActionListener(this);
		b7.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b3.addActionListener(this);
		b2.addActionListener(this);
		b1.addActionListener(this);
		

		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		result = textField.getText();
		result += e.getActionCommand();
		textField.setText(result);
		
	}
}
