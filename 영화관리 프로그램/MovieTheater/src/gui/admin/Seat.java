package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import dao.SeatDao;
import models.Seats;
import util.Utils;

@SuppressWarnings("serial")
public class Seat extends JDialog{

	private JPanel contentPane;
	private JLabel lbTitle, lbType, lbRow, lbCol;
	private JTextField txtType, txtRow, txtCol;
	private JButton btnInsUpd, btnDelCan;
	
	public Seat(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 240, 250);
		
		init();

		SeatDao dao = SeatDao.getInstance();
		Seats seat;
		Utils util = new Utils();
		int executeCd = 1;
		
		util.restrictNumber(txtRow, 1);
		util.restrictNumber(txtCol, 1);
		
		if(status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if(status == 2) {
			seat = dao.selectOne(id);
			if(seat == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtType.setText(seat.getType());
				txtRow.setText(seat.getRow()+"");
				txtCol.setText(seat.getCol()+"");
				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if(status == 3) {
			seat = dao.selectOne(id);
			if(seat == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtType.setText(seat.getType());
				txtRow.setText(seat.getRow()+"");
				txtCol.setText(seat.getCol()+"");
				txtType.setEditable(false);
				txtRow.setEditable(false);
				txtCol.setEditable(false);
				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}
		
		btnInsUpd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;
				
				String type = txtType.getText();
				int row = Integer.parseInt(txtRow.getText());
				int col = Integer.parseInt(txtCol.getText());
				int check = 0;
				
				if(statusText.equals("입력")) {
					check = checkField(row, col);
					if(check == 0) {
						result = dao.insert(type, row, col);
						if(result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER3:데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER4:데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.seatTable.setModel(main.setSeatTable(null));
							util.hiddenTableColumn(main.seatTable, 0);
							dispose();
						}
					}
				} else if (statusText.equals("수정")) {
					if(status == 2) {
						check = checkField(row, col);
						if(check == 0) {
							result = dao.update(id, type, row, col);
							if(result == -1) {
								JOptionPane.showMessageDialog(contentPane, "ER5:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
							} else if(result == 0) {
								JOptionPane.showMessageDialog(contentPane, "ER6:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
								main.seatTable.setModel(main.setSeatTable(null));
								util.hiddenTableColumn(main.seatTable, 0);
								dispose();
							}
						}
					} else if (status == 3) {
						new Seat(main, 2, id);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER7:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
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
							JOptionPane.showMessageDialog(contentPane, "ER8:데이터를 삭제할 수 없습니다.\n상영관에 등록된 좌석일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER9:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.seatTable.setModel(main.setSeatTable(null));
							util.hiddenTableColumn(main.seatTable, 0);
							dispose();
						}
					}
				} else if(statusText.equals("취소")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER10:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
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
		
		lbTitle = new JLabel("좌석정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 135, 40);
		contentPane.add(lbTitle);
		
		lbType = new JLabel("좌석구분");
		lbType.setHorizontalAlignment(SwingConstants.RIGHT);
		lbType.setBounds(25, 50, 80, 20);
		contentPane.add(lbType);
		
		txtType = new JTextField();
		txtType.setBounds(120, 50, 50, 20);
		txtType.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtType);
		
		lbRow = new JLabel("행");
		lbRow.setHorizontalAlignment(SwingConstants.RIGHT);
		lbRow.setBounds(80, 80, 25, 20);
		contentPane.add(lbRow);
		
		txtRow = new JTextField();
		txtRow.setColumns(10);
		txtRow.setBounds(120, 80, 50, 20);
		txtRow.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtRow);
		
		lbCol = new JLabel("열");
		lbCol.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCol.setBounds(80, 110, 25, 20);
		contentPane.add(lbCol);
		
		txtCol = new JTextField();
		txtCol.setBounds(120, 110, 50, 20);
		txtCol.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtCol);
		
		btnInsUpd = new JButton("입력/수정");
		btnInsUpd.setBounds(40, 160, 65, 30);
		contentPane.add(btnInsUpd);
		
		btnDelCan = new JButton("삭제/취소");
		btnDelCan.setBounds(120, 160, 65, 30);
		contentPane.add(btnDelCan);
	}
	
	private int checkField(int row, int col) {
		int check = 0;
		if(row > 5) {
			JOptionPane.showMessageDialog(contentPane, "행은 5를 넘을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			check = 1;
		} else if(col > 5) {
			JOptionPane.showMessageDialog(contentPane, "열은 5를 넘을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			check = 1;
		}
		return check;
	}
}
