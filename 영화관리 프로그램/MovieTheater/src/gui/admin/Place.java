package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.PlaceDao;
import models.Places;
import util.Utils;

@SuppressWarnings("serial")
public class Place extends JDialog {

	private JPanel contentPane;
	private JTextField tfName, tfAddr;
	private JButton btnInsUpd, btnDelCan;
	private JLabel lbTitle, lbPlace, lbAddr;

	public Place(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 363, 260);

		init();

		PlaceDao dao = PlaceDao.getInstance();
		Places place;
		Utils util = new Utils();
		int executeCd = 1;

		if (status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");

		} else if (status == 2) {
			place = dao.selectOne(id);
			if (place == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfName.setText(place.getName());
				tfAddr.setText(place.getAddr());
				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}

		} else if (status == 3) {
			place = dao.selectOne(id);
			if (place == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfName.setText(place.getName());
				tfAddr.setText(place.getAddr());
				tfName.setEditable(false);
				tfAddr.setEditable(false);
				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}

		btnInsUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;

				String name = tfName.getText();
				String addr = tfAddr.getText();

				if (statusText.equals("입력")) {
					result = dao.insert(name, addr);
					if (result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER3:데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else if (result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER4:데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.placeTable.setModel(main.setPlaceTable(null));
						util.hiddenTableColumn(main.placeTable, 0);
						dispose();
					}

				} else if (statusText.equals("수정")) {
					if (status == 2) {
						result = dao.update(id, name, addr);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER5:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER6:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.placeTable.setModel(main.setPlaceTable(null));
							util.hiddenTableColumn(main.placeTable, 0);
							dispose();
						}
					} else if (status == 3) {
						new Place(main, 2, id);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER7:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		btnDelCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnDelCan.getText();

				if (statusText.equals("삭제")) {
					int result = 0;
					int check = JOptionPane.showConfirmDialog(contentPane, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER8:데이터를 삭제할 수 없습니다.\n해당 지점에서 상영중인 영화가 있을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER9:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.placeTable.setModel(main.setPlaceTable(null));
							util.hiddenTableColumn(main.placeTable, 0);
							dispose();
						}
					}
				} else if (statusText.equals("취소")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER10:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		setVisible(true);

		if (executeCd == -1) {
			dispose();
		}
	}

	private void init() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("영화관 관리 프로그램 ver1.0");

		lbTitle = new JLabel("지점 관리");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 137, 40);
		contentPane.add(lbTitle);

		lbPlace = new JLabel("지점명");
		lbPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPlace.setBounds(37, 50, 80, 20);
		contentPane.add(lbPlace);

		tfName = new JTextField();
		tfName.setBounds(129, 50, 168, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);

		lbAddr = new JLabel("주소");
		lbAddr.setHorizontalAlignment(SwingConstants.RIGHT);
		lbAddr.setBounds(37, 80, 80, 20);
		contentPane.add(lbAddr);

		tfAddr = new JTextField();
		tfAddr.setColumns(10);
		tfAddr.setBounds(129, 80, 168, 20);
		contentPane.add(tfAddr);

		btnInsUpd = new JButton();
		btnInsUpd.setBounds(95, 155, 76, 28);
		contentPane.add(btnInsUpd);

		btnDelCan = new JButton();
		btnDelCan.setBounds(195, 155, 76, 28);
		contentPane.add(btnDelCan);

	}
}