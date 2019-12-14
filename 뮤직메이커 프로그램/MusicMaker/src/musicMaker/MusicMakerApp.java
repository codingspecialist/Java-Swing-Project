package musicMaker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

import dbconn.FileDB;
import model.MusicFile;
import model.Track;
import java.awt.SystemColor;

public class MusicMakerApp extends JFrame {

	protected static final String String = null;
	private JPanel contentPane;
	private JTable table;
	private String username;
	private playThread pt;
	private JTable listTable;
	private JPanel gui;
	private Converter converter;
	private JPanel panelMain;
	private DefaultTableModel tableModel;

	class playThread extends Thread {
		
		private void playMusic() {
			Pattern p1 = new Pattern(converter.trackToString());
			Rhythm r = new Rhythm();
			String drum = converter.drumTrackToString();
			String dt[] = drum.split("@");
			
			for (int i = 0; i < dt.length; i++) {
				r.addLayer(dt[i]);
			}
			
			Pattern p2 = new Pattern(r.getPattern().repeat(2).setTempo(converter.getMusicFile().getBpm()));
						
			p1.setTempo(converter.getMusicFile().getBpm());
			
			
			Player p = new Player();
			p.play(p1,p2);
			
		}	
		
		public void run() {
				try {
					playMusic();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
	}
	

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicMakerApp frame = new MusicMakerApp();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param password
	 * @param username
	 */

	public MusicMakerApp() {
		this(null);
	}

	public MusicMakerApp(String username) {

		panelMain = new JPanel();
		getContentPane().add(panelMain);
		
		this.username = username;
		setTitle("MusicMaker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		setResizable(false);// �겕湲곌퀬�젙
		// setMinimumSize(new Dimension(900, 500)); // 理쒖냼�겕湲�

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setFont(new Font("留묒� 怨좊뵓", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mnNewFileItem = new JMenuItem("New File");
		mnNewMenu.add(mnNewFileItem);
		mnNewMenu.addSeparator();
		mnNewFileItem.addActionListener(new ActionListener() {
			//새 파일
			@Override
			public void actionPerformed(ActionEvent e) {
				
					new NewFileFrame(username,tableModel);
					
				
			}
		});

		JMenuItem mnSaveItem = new JMenuItem("Save");
		mnNewMenu.add(mnSaveItem);
		mnNewMenu.addSeparator();
		
		JMenuItem mnDeleteItem = new JMenuItem("Delete");
		mnNewMenu.add(mnDeleteItem);
		mnNewMenu.addSeparator();
		
		mnDeleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDB fileDB = FileDB.getInstance();
				int row = listTable.getSelectedRow();
				String selectLow = (String) listTable.getValueAt(row, 0);				
				int result = fileDB.delete(username, selectLow);
				if(result==1) {
					JOptionPane.showMessageDialog(null, "Successfully Delete");
					tableModel.removeRow(row);
				}
			}
		});
		
		
		mnSaveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				 FileDB fileDB = FileDB.getInstance();
	             int result = fileDB.save(converter.getMusicFile());

	             if (result == 1) {
	                JOptionPane.showMessageDialog(null, "Successfully Saved");
	                
	             } else {
	                JOptionPane.showMessageDialog(null, "Fail Save");
	                
	             }
			}
		});

		JMenuItem mnLogoutItem = new JMenuItem("Logout");
		mnNewMenu.add(mnLogoutItem);
		mnNewMenu.addSeparator();

		mnLogoutItem.addActionListener(new ActionListener() {
			// 硫붾돱 諛� 濡쒓렇�븘�썐 �빖�뱾�윭
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();

			}
		});

		JMenuItem mnExitItem = new JMenuItem("Exit");
		mnNewMenu.add(mnExitItem);
		
		JButton btnTest = new JButton("test");
		//menuBar.add(btnTest);
		

		mnExitItem.addActionListener(new ActionListener() {
			// 硫붾돱 諛� 醫낅즺 �빖�뱾�윭
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelTop = new JPanel();
		panelTop.setBackground(SystemColor.controlDkShadow);
		panelTop.setBounds(220, 0, 1374, 46);
		contentPane.add(panelTop);
		panelTop.setLayout(null);

		JButton btnPlay = new JButton("\u25B6");
		btnPlay.setBackground(Color.DARK_GRAY);
		btnPlay.setForeground(new Color(154, 205, 50));
		btnPlay.setFont(new Font("援대┝", Font.PLAIN, 12));
		btnPlay.setBounds(0, 0, 46, 46);
		btnPlay.addActionListener(new ActionListener() {

			// �옱�깮踰꾪듉 �빖�뱾�윭
			@Override
			public void actionPerformed(ActionEvent e) {
				pt = new playThread();

				pt.start();

			}
		});

		panelTop.add(btnPlay);

		JButton btnStop = new JButton("\u25A0");
		btnStop.setBackground(Color.DARK_GRAY);
		btnStop.setForeground(new Color(154, 205, 50));
		btnStop.setBounds(49, 0, 46, 46);
		btnStop.addActionListener(new ActionListener() {

			// �젙吏� 踰꾪듉 �빖�뱾�윭
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pt.wait();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
		panelTop.add(btnStop);

		JSpinner spinnerBPM = new JSpinner();
		spinnerBPM.setModel(new SpinnerNumberModel(new Integer(120), null, null, new Integer(1)));
		spinnerBPM.setFont(new Font("Arial", Font.BOLD, 24));
		spinnerBPM.setForeground(Color.WHITE);
		spinnerBPM.setBackground(Color.DARK_GRAY);
		spinnerBPM.setBounds(147, 0, 132, 46);
		panelTop.add(spinnerBPM);
		spinnerBPM.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int tem = Integer.parseInt(spinnerBPM.getValue().toString());
				System.out.println(tem);
				converter.setBPM(tem);
				converter.getMusicFile().setBpm(tem);
			}
		});
		
		JComboBox cmbTrack = new JComboBox();
		cmbTrack.setFont(new Font("Arial", Font.BOLD, 17));
		cmbTrack.setBounds(367, 0, 105, 46);
		panelTop.add(cmbTrack);
		
		JButton btnAddTrack = new JButton("AddTrack");
		btnAddTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					converter.addTrackAndSaveToMusicFile();
					cmbTrack.removeAllItems();
					cmbTrack.addItem("---");
					for (Track t : converter.getTrack()) {
						cmbTrack.addItem(t.getTrackNum());
					}
					cmbTrack.addItem("Drum");						
				
			
			}
		});
		btnAddTrack.setFont(new Font("Arial", Font.BOLD, 14));
		btnAddTrack.setForeground(Color.WHITE);
		btnAddTrack.setBackground(Color.ORANGE);
		btnAddTrack.setBounds(635, -1, 105, 46);
		panelTop.add(btnAddTrack);
		
		JButton btnRemovetrack = new JButton("RemoveTrack");
		btnRemovetrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(cmbTrack.getSelectedItem().toString().equals("---"))) {
					remove(panelMain);
					int delNum = Integer.parseInt(cmbTrack.getSelectedItem().toString());
					converter.removeTrackAndSaveToMusicFile(delNum);
					cmbTrack.removeAllItems();
					cmbTrack.addItem("---");
					for (Track t : converter.getTrack()) {
						cmbTrack.addItem(t.getTrackNum());
					}
					cmbTrack.addItem("Drum");							
				}				
						
			}
		});
		btnRemovetrack.setForeground(Color.WHITE);
		btnRemovetrack.setFont(new Font("Arial", Font.BOLD, 14));
		btnRemovetrack.setBackground(Color.ORANGE);
		btnRemovetrack.setBounds(752, -1, 141, 46);
		panelTop.add(btnRemovetrack);
		
		JLabel lblBpm = new JLabel("BPM");
		lblBpm.setFont(new Font("Arial", Font.BOLD, 12));
		lblBpm.setForeground(Color.WHITE);
		lblBpm.setBounds(107, 12, 39, 21);
		panelTop.add(lblBpm);
		
		JLabel lblTrack = new JLabel("Track");
		lblTrack.setForeground(Color.WHITE);
		lblTrack.setFont(new Font("Arial", Font.BOLD, 12));
		lblTrack.setBounds(329, 12, 39, 21);
		panelTop.add(lblTrack);
		
		JButton btnLoadTrack = new JButton("LoadTrack");
		btnLoadTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cmbTrack.getSelectedItem().equals("Drum")) {
					remove(panelMain);
					panelMain = new DrumPanel(converter);
					getContentPane().add(panelMain);		
				}else if((cmbTrack.getSelectedItem().equals("---"))) {
					remove(panelMain);		
				}else {		
					remove(panelMain);
					panelMain = new TrackPanel(converter, Integer.parseInt(cmbTrack.getSelectedItem().toString()));
					getContentPane().add(panelMain);			
				}
			}
		});
		btnLoadTrack.setForeground(Color.WHITE);
		btnLoadTrack.setFont(new Font("Arial", Font.BOLD, 14));
		btnLoadTrack.setBackground(Color.ORANGE);
		btnLoadTrack.setBounds(482, -1, 141, 46);
		panelTop.add(btnLoadTrack);


//		JPanel panelMain = new TrackPanel(converter);
//		contentPane.add(panelMain);
//		setVisible(true);

		JPanel panelFile = new JPanel();
		panelFile.setLayout(new BorderLayout());
		panelFile.setBackground(Color.DARK_GRAY);
		panelFile.setBounds(0, 0, 208, 850);
		contentPane.add(panelFile);

		JLabel nameLabel = new JLabel("-" + username);
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("援대┝", Font.BOLD | Font.ITALIC, 18));

		nameLabel.setBounds(12, 34, 109, 25);
		panelFile.add(nameLabel, BorderLayout.NORTH);

		// �뀒�씠釉붿뿉 �뙆�씪�씠由� 肉뚮━湲�

		// 而щ읆�씠由�
		Vector<String> fn = ColumnName.getColumnName();
		// �뜲�씠�꽣 諛쏆븘�삤湲�
		FileDB fileDB = FileDB.getInstance();
		ArrayList<String> fileName = fileDB.loadFile(username);

		tableModel = new DefaultTableModel(fn, 0);

		for (int i = 0; i < fileName.size(); i++) {
			Vector<Object> row = new Vector<Object>();
			row.addElement(fileName.get(i));
			tableModel.addRow(row);
		}


		JScrollPane scrollPane = new JScrollPane();
		panelFile.add(scrollPane, BorderLayout.CENTER);

		listTable = new JTable(tableModel);
		scrollPane.setViewportView(listTable);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			// ���옣�맂 �뙆�씪紐낆뿉 留욎떠 �젙蹂대뱾 遺덈윭�삤湲�
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbTrack.removeAllItems();
				int row = listTable.getSelectedRow();
				String selectLow = (String) listTable.getValueAt(row, 0);
				FileDB fileDB = FileDB.getInstance();
				MusicFile musicFile = fileDB.loadTrack(selectLow);
				converter = new Converter(musicFile);
				converter.getMusicFile().setUsername(username);
				converter.getMusicFile().setFilename(selectLow);
				converter.getMusicFile().setUserfilename(username + "_" + selectLow);
				
				spinnerBPM.setValue(converter.getMusicFile().getBpm());
				cmbTrack.addItem("---");
				for (Track t : converter.getTrack()) {
					cmbTrack.addItem(t.getTrackNum());
				}
				cmbTrack.addItem("Drum");

			}
		});
		panelFile.add(btnLoad, BorderLayout.SOUTH);
		

btnTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MusicFile f = new  MusicFile();
				f.setBpm(140);
				f.setFilename("test");
				f.setMadi(8);
				f.setNote("V0 I[Electric_Bass_Finger] G2w . . . . . . . G2w . . . . . . . G2w . . . . . . . G2w . . . . . . . C3w . . . . . . . C3w . . . . . . . D3w . . . . . . . D3w . . . . . . .  V1 I[Slap_Bass_1] G3q . Ri G3i A#3i G3i Ri G3i Ri G3i Ri G3i A#3i G3i F3q . G3q . Ri G3i A#3i G3i Ri G3i Ri G3i Ri G3i A#3i G3i F3q . C4q . Ri C4i E4i C4i Ri C4i Ri C4i Ri C4i E4i C4i A#3q . D4q . Ri D4i F#4i D4i Ri D4i Ri D4i Ri D3i Ri Ri Ri Ri");
				f.setDrumnote("O.O.O.O.O.O.O.O.O.O.O.O.O.O.O.O.@................................@................................@................................@................................");
				converter = new Converter(f);
				spinnerBPM.setValue(converter.getMusicFile().getBpm());
				cmbTrack.addItem("---");
				for (Track t : converter.getTrack()) {
					cmbTrack.addItem(t.getTrackNum());
				}
				cmbTrack.addItem("Drum");
			}
		});


		setVisible(true);
	}
}