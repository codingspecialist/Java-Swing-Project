package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import dao.ComboDao;
import dao.DBConnection;
import models.Combo;
import models.Places;

@SuppressWarnings("serial")
public class SelectTheater1 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JList<Combo> liPlace;
	private JList<String> liTheater;
	private JButton btnBack;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	private String userId;

	public SelectTheater1(String userId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		liPlace.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				Combo place = liPlace.getSelectedValue();
				int placeId = place.getKey();
				new SelectDate(userId, placeId, "Theater");
				frame.dispose();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(userId);
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		conn = DBConnection.getConnection();
		ArrayList<Places> places = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement("SELECT * FROM PLACE");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Places place = new Places();
				place.setId(rs.getInt("ID"));
				place.setName(rs.getString("NAME"));
				place.setAddr(rs.getString("ADDR"));
				places.add(place);
			}

			backgroundPanel = new JPanel();
			frame.setContentPane(backgroundPanel);
			frame.setTitle("영화 예매 프로그램 ver1.0");

			CustomUI custom = new CustomUI(backgroundPanel);
			custom.setPanel();
			
			String place[] = new String[places.size()];
			for (int i = 0; i < place.length; i++) {
				place[i] = places.get(i).getName();
			}

			ComboDao cDao = ComboDao.getInstance();
			DefaultListModel<Combo> listModel = new DefaultListModel<>();
			Vector<Combo> comboPlaces = cDao.setCombo("place");
			
			for (Combo combo : comboPlaces) {
				listModel.addElement(combo);
			}
			
			liPlace = custom.setList("liPlace", listModel, 0);
			
			btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}