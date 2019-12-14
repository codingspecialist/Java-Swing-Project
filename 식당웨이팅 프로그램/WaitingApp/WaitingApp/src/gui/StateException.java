package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class StateException extends JFrame {

	private JPanel contentPane;

    //상태 발생에서 바를 선택하지 않았을 때 바를 선택하라는 패널 
	public StateException() {
		setAlwaysOnTop(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 300, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("\uD14C\uC774\uBE14 \uBC14\uB97C \uC120\uD0DD\uD574 \uC8FC\uC138\uC694.");
		label.setLabelFor(label);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		contentPane.add(label, BorderLayout.CENTER);
		dispose();
	}

}
