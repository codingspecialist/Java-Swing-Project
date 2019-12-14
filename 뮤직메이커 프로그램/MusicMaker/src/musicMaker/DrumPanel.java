package musicMaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class DrumPanel extends JPanel {
	private Converter converter;
	private ArrayList<ArrayList<DrumNote>> note;
	private String noteModel[] = { "O", "S", "^", "+", "X" };
	private JPanel drumNotePanel;
	

	public DrumPanel(Converter converter) {
		this.converter = converter;
		setBackground(Color.DARK_GRAY);
		setBounds(220, 56, 1374, 800);
		setLayout(null);
		
		JLabel saveLbl = new JLabel("Save");
		saveLbl.setFont(new Font("Arial", Font.BOLD, 22));
		saveLbl.setHorizontalAlignment(SwingConstants.CENTER);
		saveLbl.setForeground(Color.WHITE);
		saveLbl.setBackground(Color.ORANGE);
		saveLbl.setBounds(0, 717, 200, 83);
		saveLbl.setOpaque(true);
		add(saveLbl);
		saveLbl.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				converter.getMusicFile().setDrumnote(guiToString());
				converter.musicFileToDrumTrack();
				JOptionPane.showMessageDialog(null, "Apply File | save: [File]-[Save])");
			}
		});
		
		

		JLabel drumImg = new JLabel(new ImageIcon("img/drum.png"));
		drumImg.setSize(200, 800);
		add(drumImg);

		drumNotePanel = new JPanel();
		drumNotePanel.setBackground(Color.DARK_GRAY);
		drumNotePanel.setBounds(0, 0, 2000, 800);
		drumNotePanel.setPreferredSize(new Dimension(2000, 800));
		drumNotePanel.setLayout(null);

		initDrumNote(32);
		stringToNote(converter.getMusicFile().getDrumnote());

		JScrollPane scrollPane = new JScrollPane(drumNotePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(200, 0, 1174, 800);
		add(scrollPane);
		

	}

	private void initDrumNote(int sizeOfNote) {
		note = new ArrayList<ArrayList<DrumNote>>();
		for (int i = 0; i < 5; i++) {
			note.add(new ArrayList<DrumNote>());
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < sizeOfNote; j++) {
				note.get(i).add(new DrumNote(60 * j, 60 * i));
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < sizeOfNote; j++) {
				drumNotePanel.add(note.get(i).get(j));
			}
		}
	}
	
	
	private void stringToNote(String notes) {
		String step1[] = notes.split("@");
		ArrayList<ArrayList<String>> step2 = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < step1.length; i++) {
			step2.add(new ArrayList<String>());
			for (int j = 0; j < step1[i].length(); j++) {
				step2.get(i).add(step1[i].charAt(j)+"");
			}
		}
		for (int i = 0; i < step2.size(); i++) {
			for (int j = 0; j < step2.get(i).size(); j++) {
				if(step2.get(i).get(j).equals(".")) {
					note.get(i).get(j).setCurrentValue(0);
				}else {
					note.get(i).get(j).setCurrentValue(1);
				}
			}
		}
		
	}
	
	

	public String guiToString() {
		String tem = "";
		for (int i = 0; i < 5; i++) {
			for (DrumNote n : note.get(i)) {
				if(n.getCurrentValue()==1) {
					tem = tem + noteModel[i];
				}else {
					tem = tem + ".";
				}
			}
			if(!(i==4)) {
				tem = tem + "@";
			}
		}
		System.out.println(tem);
		return tem;
	}
}
