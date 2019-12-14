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

import dao.DiscountDao;
import models.Discounts;
import util.Utils;

@SuppressWarnings("serial")
public class Discount extends JDialog{

	private JPanel contentPane;
	private JLabel lbTitle, lbName, lbVal, lbUnit;
	private JTextField txtName, txtVal, txtUnit;
	private JButton btnInsUpd, btnDelCan;
	
	public Discount(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 240, 250);
		
		init();

		DiscountDao dao = DiscountDao.getInstance();
		Discounts discount;
		Utils util = new Utils();
		int executeCd = 1;
		
		util.restrictNumber(txtVal, 4);
		
		if(status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if(status == 2) {
			discount = dao.selectOne(id);
			if(discount == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtName.setText(discount.getName());
				txtVal.setText(discount.getVal()+"");
				txtUnit.setText(discount.getUnit());
				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if(status == 3) {
			discount = dao.selectOne(id);
			if(discount == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtName.setText(discount.getName());
				txtVal.setText(discount.getVal()+"");
				txtUnit.setText(discount.getUnit());
				txtName.setEditable(false);
				txtVal.setEditable(false);
				txtUnit.setEditable(false);
				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}
		
		btnInsUpd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;
				
				String name = txtName.getText();
				int val = Integer.parseInt(txtVal.getText());
				String unit = txtUnit.getText();
				int check = 0;
				
				if(statusText.equals("입력")) {
					check = checkField(unit, val);
					if(check == 0) {
						result = dao.insert(name, val, unit);
						if(result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER3:데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER4:데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.discountTable.setModel(main.setDiscountTable(null));
							util.hiddenTableColumn(main.discountTable, 0);
							dispose();
						}
					}
				} else if (statusText.equals("수정")) {
					if(status == 2) {
						check = checkField(unit, val);
						if(check == 0) {
							result = dao.update(id, name, val, unit);
							if(result == -1) {
								JOptionPane.showMessageDialog(contentPane, "ER5:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
							} else if(result == 0) {
								JOptionPane.showMessageDialog(contentPane, "ER6:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
								main.discountTable.setModel(main.setDiscountTable(null));
								util.hiddenTableColumn(main.discountTable, 0);
								dispose();
							}
						}
					} else if (status == 3) {
						new Discount(main, 2, id);
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
							JOptionPane.showMessageDialog(contentPane, "ER8:데이터를 삭제할 수 없습니다.\n해당 할인값이 이미 적용된 예약내역이 있을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER9:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.discountTable.setModel(main.setDiscountTable(null));
							util.hiddenTableColumn(main.discountTable, 0);
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
		
		lbTitle = new JLabel("할인정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 135, 40);
		contentPane.add(lbTitle);
		
		lbName = new JLabel("할인명");
		lbName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbName.setBounds(30, 50, 65, 20);
		contentPane.add(lbName);
		
		txtName = new JTextField();
		txtName.setBounds(110, 50, 80, 20);
		contentPane.add(txtName);
		
		lbVal = new JLabel("할인값");
		lbVal.setHorizontalAlignment(SwingConstants.RIGHT);
		lbVal.setBounds(30, 80, 65, 20);
		contentPane.add(lbVal);
		
		txtVal = new JTextField();
		txtVal.setColumns(10);
		txtVal.setBounds(110, 80, 80, 20);
		txtVal.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtVal);
		
		lbUnit = new JLabel("할인단위");
		lbUnit.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUnit.setBounds(30, 110, 65, 20);
		contentPane.add(lbUnit);
		
		txtUnit = new JTextField();
		txtUnit.setBounds(110, 110, 80, 20);
		txtUnit.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtUnit);
		
		btnInsUpd = new JButton("입력/수정");
		btnInsUpd.setBounds(40, 160, 65, 30);
		contentPane.add(btnInsUpd);
		
		btnDelCan = new JButton("삭제/취소");
		btnDelCan.setBounds(120, 160, 65, 30);
		contentPane.add(btnDelCan);
	}
	
	private int checkField(String unit, int val) {
		int check = 0;
		
		if(unit.equals("원")) {
			if(val > 7000) {
				JOptionPane.showMessageDialog(contentPane, "단위가 원 일경우 할인값은 7000을 넘을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				check = 1;
			}
		} else if(unit.equals("%")) {
			if(val > 100) {
				JOptionPane.showMessageDialog(contentPane, "단위가 % 일경우 할인값은 100을 넘을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				check = 1;
			}
		} else {
			JOptionPane.showMessageDialog(contentPane, "단위는 원 또는 %로만 입력가능합니다.", "오류", JOptionPane.ERROR_MESSAGE);
			check = 1;
		}
		
		return check;
	}
}