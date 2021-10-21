package pooyangame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TitleFrame extends JFrame{
	
	private JLabel laBackground;
	
	public TitleFrame() {
		laBackground = new JLabel(new ImageIcon("images/titleFrame.png"));
		setTitle("Pooyan");
		setSize(620, 630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setContentPane(laBackground);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					new PooyanApp();
					dispose();
				}
			}
			
		});
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new TitleFrame();

	}
}
