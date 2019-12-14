package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import dao.ComboDao;
import dao.TheaterDao;
import models.Combo;
import models.Theaters;
import util.Utils;

@SuppressWarnings("serial")
public class Theater extends JDialog{

	private JPanel contentPane;
	private JLabel lbTitle, lbPlace, lbName, lbType;
	private JTextField txtName;
	private JComboBox<Combo> comboPlace, comboType; 
	private JButton btnInsUpd, btnDelCan;
	
	public Theater(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 240, 250);
		
		init();

		int executeCd = 1;
		
		ComboDao cDao = ComboDao.getInstance();
		Vector<Combo> comboPlaces = cDao.setCombo("place");
		if(comboPlaces == null) {
			Combo comboNull = new Combo(0, "없음");
			comboPlace.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER1:지점이 존재하지 않아 상영관을 등록할 수 없습니다.\n지점을 먼저 등록해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			executeCd = -1;
		} else {
			for (Combo combo : comboPlaces) {
				comboPlace.addItem(combo);
			}
		}
		
		Vector<Combo> comboSeats = cDao.setCombo("seat");
		if(comboSeats == null) {
			Combo comboNull = new Combo(0, "없음");
			comboType.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER2:좌석구분이 존재하지 않아 상영관을 등록할 수 없습니다.\n좌석구분을 먼저 등록해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			executeCd = -1;
		} else {
			for (Combo combo : comboSeats) {
				comboType.addItem(combo);
			}
		}
		
		TheaterDao dao = TheaterDao.getInstance();
		Theaters theater;
		Utils util = new Utils();
		
		if(status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if(status == 2) {
			theater = dao.selectOne(id);
			if(theater == null) {
				JOptionPane.showMessageDialog(this, "ER3:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtName.setText(theater.getName());
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, theater.getPlaceId()));
				comboType.setSelectedIndex(util.getComboIndex(comboType, theater.getSeatId()));
				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if(status == 3) {
			theater = dao.selectOne(id);
			if(theater == null) {
				JOptionPane.showMessageDialog(this, "ER4:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtName.setText(theater.getName());
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, theater.getPlaceId()));
				comboType.setSelectedIndex(util.getComboIndex(comboType, theater.getSeatId()));
				txtName.setEditable(false);
				comboPlace.setEnabled(false);
				comboType.setEnabled(false);
				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}
		
		btnInsUpd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;
				
				Combo place = (Combo) comboPlace.getSelectedItem();
				int placeId = place.getKey();
				String name = txtName.getText();
				Combo type = (Combo) comboType.getSelectedItem();
				int seatId = type.getKey(); 
				
				if(statusText.equals("입력")) {
					result = dao.insert(placeId, name, seatId);
					if(result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER5:데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else if(result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER6:데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.theaterTable.setModel(main.setTheaterTable(null));
						util.hiddenTableColumn(main.theaterTable, 0);
						dispose();
					}
				} else if (statusText.equals("수정")) {
					if(status == 2) {
						result = dao.update(id, placeId, name, seatId);
						if(result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER7:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER8:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.theaterTable.setModel(main.setTheaterTable(null));
							util.hiddenTableColumn(main.theaterTable, 0);
							dispose();
						}
					} else if (status == 3) {
						new Theater(main, 2, id);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER9:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		
		btnDelCan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String statusText = btnDelCan.getText();
				if(statusText.equals("삭제")) {
					int result = 0;
					int check = JOptionPane.showConfirmDialog(contentPane, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(check == JOptionPane.YES_OPTION) {
						result = dao.delete(id);
						if(result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER10:데이터를 삭제할 수 없습니다.\n해당 관에서 상영중인 영화가 있을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER11:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.theaterTable.setModel(main.setTheaterTable(null));
							util.hiddenTableColumn(main.theaterTable, 0);
							dispose();
						}
					}
				} else if(statusText.equals("취소")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER12:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		
		setVisible(true);
		
		if(executeCd == -1) {
			dispose();
		}
	}
	
	private void init() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("영화관 관리 프로그램 ver1.0");
		
		lbTitle = new JLabel("상영관 정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 135, 40);
		contentPane.add(lbTitle);
		
		lbPlace = new JLabel("지점");
		lbPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPlace.setBounds(30, 50, 65, 20);
		contentPane.add(lbPlace);
		
		comboPlace = new JComboBox<Combo>();
		comboPlace.setBounds(110, 50, 80, 20);
		contentPane.add(comboPlace);
		
		lbName = new JLabel("상영관명");
		lbName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbName.setBounds(30, 80, 65, 20);
		contentPane.add(lbName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(110, 80, 80, 20);
		contentPane.add(txtName);
		
		lbType = new JLabel("좌석구분");
		lbType.setHorizontalAlignment(SwingConstants.RIGHT);
		lbType.setBounds(30, 110, 65, 20);
		contentPane.add(lbType);
		
		comboType = new JComboBox<Combo>();
		comboType.setBounds(110, 110, 80, 20);
		contentPane.add(comboType);
		
		btnInsUpd = new JButton("입력/수정");
		btnInsUpd.setBounds(40, 160, 65, 30);
		contentPane.add(btnInsUpd);
		
		btnDelCan = new JButton("삭제/취소");
		btnDelCan.setBounds(120, 160, 65, 30);
		contentPane.add(btnDelCan);
	}
}