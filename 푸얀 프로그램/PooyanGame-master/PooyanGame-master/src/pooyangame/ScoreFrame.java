package pooyangame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScoreFrame extends JFrame{
	
	private static final String TAG = "ScoreFrame:";
	private PooyanApp pooyanApp;
	
	public JLabel laScore;
	//public FileWriter fout = null;
	
	public ScoreFrame(PooyanApp pooyanApp) {
		this.pooyanApp= pooyanApp;
		setTitle("Score");
		setSize(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		laScore = new JLabel();
		laScore.setSize(200, 50);
		System.out.println(pooyanApp.score);
		laScore.setText(pooyanApp.score+"Á¡");
		laScore.setLocation(20, 10);
		laScore.setFont(new Font("Serif", Font.BOLD, 30));
		laScore.setForeground(Color.BLACK);
		

		add(laScore);
		
		setVisible(true);
		
	}
	
}
