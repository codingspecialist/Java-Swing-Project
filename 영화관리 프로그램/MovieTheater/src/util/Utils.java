package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import models.Combo;

public class Utils {

	public int getAge(int birthYear, int birthMonth, int birthDay) {
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);

		int age = currentYear - birthYear;
		if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
			age--;

		return age;
	}

	public int getComboIndex(JComboBox<Combo> comboBox, int key) {
		Combo item;
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			item = comboBox.getItemAt(i);
			if (item.getKey() == key) {
				return i;
			}
		}
		return 0;
	}

	public Vector<String> getTableTitleColum(String table) {
		Vector<String> colName = new Vector<>();

		if (table.equals("user")) {
			colName.add("아이디");
			colName.add("생년월일");
			colName.add("연락처");
			colName.add("탈퇴여부");
		} else if (table.equals("movie")) {
			colName.add("아이디");
			colName.add("영화제목");
			colName.add("가격");
			colName.add("연령제한");
			colName.add("상영시간");
		} else if (table.equals("place")) {
			colName.add("아이디");
			colName.add("지점명");
			colName.add("주소");
		} else if (table.equals("theater")) {
			colName.add("아이디");
			colName.add("상영관명");
			colName.add("지점명");
			colName.add("좌석구분");
		} else if (table.equals("seat")) {
			colName.add("아이디");
			colName.add("좌석구분");
			colName.add("행");
			colName.add("열");
		} else if (table.equals("discount")) {
			colName.add("아이디");
			colName.add("할인명");
			colName.add("할인값");
			colName.add("할인단위");
		} else if (table.equals("reserve")) {
			colName.add("아이디");
			colName.add("사용자아이디");
			colName.add("영화명");
			colName.add("지점명");
			colName.add("상영관명");
			colName.add("예매날짜");
			colName.add("예매시간");
			colName.add("인원");
			colName.add("결제액");
			colName.add("취소여부");
		} else if (table.equals("salesMovie")) {
			colName.add("영화제목");
			colName.add("매출액");
		} else if (table.equals("salesPlace")) {
			colName.add("지점명");
			colName.add("매출액");
		} else if (table.equals("screen")) {
			colName.add("아이디");
			colName.add("영화명");
			colName.add("지점명");
			colName.add("상영관명");
			colName.add("시작일자");
			colName.add("종료일자");
			colName.add("시작시간");
		}
		return colName;
	}

	public void hiddenTableColumn(JTable table, int index) {
		table.getColumnModel().getColumn(index).setMinWidth(0);
		table.getColumnModel().getColumn(index).setMaxWidth(0);
		table.getColumnModel().getColumn(index).setWidth(0);
	}

	public void restrictNumber(JTextField txt, int length) {
		txt.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				String text = txt.getText();
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					txt.setText(text.substring(0, text.length() - 1));
				}
				if (text.length() > length) {
					txt.setText(text.substring(0, text.length() - 1));
				}
			}
		});
	}
}
