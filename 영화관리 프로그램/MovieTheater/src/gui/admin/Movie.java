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

import dao.MovieDao;
import models.Movies;
import util.Utils;

@SuppressWarnings("serial")
public class Movie extends JDialog {

	private JPanel contentPane;
	private JLabel lbTitle, lbMovieTitle, lbAge, lbPrice, lbRunning;
	private JTextField tfMovieTitle, tfAge, tfPrice, tfRunning;
	private JButton btnInsUpd, btnDelCan;

	public Movie(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 314, 296);
		
		init();

		Utils util = new Utils();

		MovieDao mdao = MovieDao.getInstance();
		Movies movie;

		int executeCd = 1;

		if (status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if (status == 2) {
			movie = mdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfMovieTitle.setText(movie.getTitle());
				tfPrice.setText(movie.getPrice() + "");
				tfAge.setText(movie.getAge() + "");
				tfRunning.setText(movie.getRunningTime() + "");

				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if (status == 3) {
			movie = mdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER2:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfMovieTitle.setText(movie.getTitle());
				tfPrice.setText(movie.getPrice() + "");
				tfAge.setText(movie.getAge() + "");
				tfRunning.setText(movie.getRunningTime() + "");

				tfMovieTitle.setEditable(false);
				tfPrice.setEditable(false);
				tfAge.setEditable(false);
				tfRunning.setEditable(false);

				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}

		btnInsUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;

				String title = tfMovieTitle.getText();
				int price = Integer.parseInt(tfPrice.getText());
				int age = Integer.parseInt(tfAge.getText());
				int running = Integer.parseInt(tfRunning.getText());

				if (statusText.equals("입력")) {
					result = mdao.insert(title, price, age, running);
					if (result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER3:데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else if (result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER4:데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.movieTable.setModel(main.setMovieTable(null));
						util.hiddenTableColumn(main.movieTable, 0);
						dispose();
					}
				} else if (statusText.equals("수정")) {
					if (status == 2) {
						result = mdao.update(id, title, price, age, running);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER5:데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER6:데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.movieTable.setModel(main.setMovieTable(null));
							util.hiddenTableColumn(main.movieTable, 0);
							dispose();
						}
					} else if (status == 3) {
						new Movie(main, 2, id);
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
					int check = JOptionPane.showConfirmDialog(contentPane, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (check == JOptionPane.YES_OPTION) {
						result = mdao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER8:데이터를 삭제할 수 없습니다.\n상영중인 영화일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER9:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.movieTable.setModel(main.setMovieTable(null));
							util.hiddenTableColumn(main.movieTable, 0);
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
		
		lbTitle = new JLabel("영화정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(66, 0, 137, 40);
		contentPane.add(lbTitle);

		lbMovieTitle = new JLabel("영화제목");
		lbMovieTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMovieTitle.setBounds(9, 52, 80, 20);
		contentPane.add(lbMovieTitle);

		tfMovieTitle = new JTextField();
		tfMovieTitle.setBounds(101, 52, 151, 20);
		contentPane.add(tfMovieTitle);
		tfMovieTitle.setColumns(10);

		lbAge = new JLabel("관람나이");
		lbAge.setHorizontalAlignment(SwingConstants.RIGHT);
		lbAge.setBounds(-9, 84, 98, 20);
		contentPane.add(lbAge);

		tfAge = new JTextField();
		tfAge.setColumns(10);
		tfAge.setBounds(101, 86, 102, 20);
		contentPane.add(tfAge);

		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(101, 116, 102, 20);
		contentPane.add(tfPrice);

		lbPrice = new JLabel("가격");
		lbPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPrice.setBounds(33, 116, 56, 20);
		contentPane.add(lbPrice);

		lbRunning = new JLabel("러닝타임");
		lbRunning.setHorizontalAlignment(SwingConstants.RIGHT);
		lbRunning.setBounds(33, 146, 56, 20);
		contentPane.add(lbRunning);

		tfRunning = new JTextField();
		tfRunning.setColumns(10);
		tfRunning.setBounds(101, 146, 102, 20);
		contentPane.add(tfRunning);

		btnInsUpd = new JButton();
		btnInsUpd.setBounds(66, 191, 76, 28);
		contentPane.add(btnInsUpd);

		btnDelCan = new JButton();
		btnDelCan.setBounds(166, 191, 76, 28);
		contentPane.add(btnDelCan);
	}
}