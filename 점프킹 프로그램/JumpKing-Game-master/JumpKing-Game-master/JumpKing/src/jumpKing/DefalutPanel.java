package jumpKing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import lombok.Data;

// 디폴트패널 클래스입니다.
// 패널의 기본값이 저장되어있습니다.
@Data
class DefalutPanel extends JPanel{

private DefalutPanel defalutPanel = this;
private JPanel mainPanel;
private JPanel panel;
private JLabel laName;
private JTextArea jtaContent;
private JButton btnOk;
private JumpKingApp jumpkingApp;

	public DefalutPanel(JumpKingApp jumpKingApp) {		
		this.jumpkingApp = jumpKingApp;
		init(); 
		setting();
		batch();	
		listener();
		
	}
	public void init() {
		btnOk = new JButton("확인");
		mainPanel = new JPanel(); 
		panel = new JPanel();
		 laName = new JLabel("이름");
		 jtaContent = new JTextArea();
		 
	}
	
	void setting() {
		setLayout(null);
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 700, 300);
		mainPanel.setBackground(Color.black);
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 15, 680, 175);
		panel.setLayout(null);
		laName.setOpaque(true);
		laName.setBackground(Color.WHITE);
		laName.setBounds(12, 10, 660, 29);
		btnOk.setBounds(600, 120, 60, 40);
		btnOk.setForeground(Color.white);
		btnOk.setBackground(new Color(255,56,92));
		
		jtaContent.setBackground(Color.WHITE);
		jtaContent.setFont(new Font("Serif",Font.BOLD,20));
		jtaContent.setBounds(12, 49, 660, 116);
		
	}
	
	void batch() {
	add(mainPanel);
	mainPanel.add(panel);
	panel.add(btnOk);
	panel.add(jtaContent);
	panel.add(laName);
	}
	
	void listener() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jumpkingApp.setFocusable(true);
				setVisible(false);				
			}
		});
	}
	
	
	}