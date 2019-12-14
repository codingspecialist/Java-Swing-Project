package musicMaker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TrackNoteLabel extends JLabel  {
	
	private int x,y,currentVal,noteIndex,noteLen;
	private ImageIcon defaultImg=new ImageIcon("img/pinoNoteBg1.png");
	private ImageIcon beginImg=new ImageIcon("img/pinoNoteBgBegin.png");
	private ImageIcon midImg=new ImageIcon("img/pinoNoteBgMid.png");
	private ImageIcon endImg=new ImageIcon("img/pinoNoteBgEnd.png");
	private ImageIcon oneBakjaImg=new ImageIcon("img/pinoNoteBgOneBakja.png");
	
	
	public TrackNoteLabel(int x,int y) {
		this.x = x;
		this.y = y;
		setIcon(defaultImg);
		currentVal = 0;
		noteLen=0;
		setBounds(x, y, 15, 20);	
	}
	
	public int getNoteIndex() {
		return noteIndex;
	}
	
	public int getNoteLen() {
		return noteLen;
	}
		
	public void changeToDefaulttImg() {
		currentVal = 0;
		noteIndex = 0;
		noteLen=0;
		setIcon(defaultImg);
	}
	
	public void changeToBeginImg(int noteLen) {
		currentVal = 1;
		noteIndex = 1;
		this.noteLen=noteLen;
		setIcon(beginImg);
	}
	
	public void changeToMidImg() {
		currentVal = 1;
		noteIndex = 2;
		noteLen=0;
		setIcon(midImg);
	}
	
	public void changeToEndImg() {
		currentVal = 1;
		noteIndex = 3;
		noteLen=0;
		setIcon(endImg);
	}
	
	public void changeToOneBakjaImg() {
		currentVal = 1;
		noteIndex = 4;
		noteLen=1;
		setIcon(oneBakjaImg);
	}
		

}
