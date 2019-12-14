package musicMaker;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DrumNote extends JLabel{
	private int currentValue = 0;
	private int x,y;
	
	private ImageIcon notSelected = new ImageIcon("img/drumNoteNotSelected.png");
	private ImageIcon selected = new ImageIcon("img/drumNoteSelected.png");
	
	public DrumNote(int x,int y) {
		
		this.x = x;
		this.y = y;
		
		setIcon(notSelected);
		setBackground(Color.GRAY);
		setBounds(x, y, 60, 60);
		setSize(60,60);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(currentValue == 0) {				
					setIcon(selected);
					currentValue = 1;
				}else {
					setIcon(notSelected);
					currentValue = 0;
				}
			}
		});	
		
	}
	
	

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
		if(currentValue==1) {
			setIcon(selected);
		}else {
			setIcon(notSelected);
		}
	}



	public int getCurrentValue() {
		return currentValue;
	}
	
	
	

}
