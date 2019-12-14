package gui.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.ComboDao;
import dao.ScreenDao;
import models.Combo;
import models.Movies;
import util.Utils;

@SuppressWarnings("serial")
public class Screen extends JDialog {

	private JPanel contentPane;
	private JLabel lbTitle, lbMovieTitle, lbPlace, lbTheater, lbDate, lbBtwDate, lbTime;
	private JComboBox<Combo> comboPlace, comboTheater, comboMovie;
	private JTextField tfStartDate, tfEndDate, tfTime;
	private JButton btnInsUpd, btnDelCan;

	public Screen(Main mainFrame, int status, int id) {
		Main main = mainFrame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 348, 316);

		init();

		Utils util = new Utils();
		int executeCd = 1;

		ComboDao cDao = ComboDao.getInstance();
		Vector<Combo> comboMovies = cDao.setCombo("movie");
		if (comboMovies == null) {
			Combo comboNull = new Combo(0, "없음");
			comboMovie.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER1:영화가 존재하지 않습니다.\n영화정보를 먼저 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			executeCd = -1;
		} else {
			for (Combo combo : comboMovies) {
				comboMovie.addItem(combo);
			}
		}

		Vector<Combo> comboPlaces = cDao.setCombo("place");
		if (comboPlaces == null) {
			Combo comboNull = new Combo(0, "없음");
			comboPlace.addItem(comboNull);
			JOptionPane.showMessageDialog(this, "ER2:지점이 존재하지 않습니다.\n지점정보를 먼저 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			executeCd = -1;
		} else {
			for (Combo combo : comboPlaces) {
				comboPlace.addItem(combo);
			}
			
			if(status == 1) {
				Combo selectedPlace = (Combo) comboPlace.getSelectedItem();
				int placeId = selectedPlace.getKey();
				comboTheater.removeAllItems();

				Vector<Combo> comboTheaters = cDao.setCombo("theater", placeId);
				if (comboTheaters == null) {
					Combo comboNull = new Combo(0, "없음");
					comboTheater.addItem(comboNull);
					JOptionPane.showMessageDialog(null, "ER3:상영관이 존재하지 않습니다.\n상영관정보를 먼저 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				} else {
					for (Combo combo : comboTheaters) {
						comboTheater.addItem(combo);
					}
				}
			}
		}
		
		comboPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Combo selectedPlace = (Combo) comboPlace.getSelectedItem();
				int placeId = selectedPlace.getKey();
				comboTheater.removeAllItems();

				Vector<Combo> comboTheaters = cDao.setCombo("theater", placeId);
				if (comboTheaters == null) {
					Combo comboNull = new Combo(0, "없음");
					comboTheater.addItem(comboNull);
					JOptionPane.showMessageDialog(null, "ER4:상영관이 존재하지 않습니다.\n상영관정보를 먼저 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				} else {
					for (Combo combo : comboTheaters) {
						comboTheater.addItem(combo);
					}
				}
			}
		});

		ScreenDao sdao = ScreenDao.getInstance();
		Movies movie;

		if (status == 1) {
			btnInsUpd.setText("입력");
			btnDelCan.setText("취소");
		} else if (status == 2) {
			movie = sdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER5:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfStartDate.setText(movie.getStartDate());
				tfTime.setText(movie.getStartTime());
				tfEndDate.setText(movie.getEndDate());
				comboMovie.setSelectedIndex(util.getComboIndex(comboMovie, movie.getId()));
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, movie.getPlaceId()));
				comboTheater.setSelectedIndex(util.getComboIndex(comboTheater, movie.getTheaterId()));

				btnInsUpd.setText("수정");
				btnDelCan.setText("취소");
			}
		} else if (status == 3) {
			movie = sdao.selectOne(id);
			if (movie == null) {
				JOptionPane.showMessageDialog(this, "ER6:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				tfStartDate.setText(movie.getStartDate());
				tfTime.setText(movie.getStartTime());
				tfEndDate.setText(movie.getEndDate());
				comboMovie.setSelectedIndex(util.getComboIndex(comboMovie, movie.getId()));
				comboPlace.setSelectedIndex(util.getComboIndex(comboPlace, movie.getPlaceId()));
				comboTheater.setSelectedIndex(util.getComboIndex(comboTheater, movie.getTheaterId()));

				tfStartDate.setEditable(false);
				tfTime.setEditable(false);
				tfEndDate.setEditable(false);
				comboMovie.setEnabled(false);
				comboPlace.setEnabled(false);
				comboTheater.setEnabled(false);

				btnInsUpd.setText("수정");
				btnDelCan.setText("삭제");
			}
		}

		btnInsUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnInsUpd.getText();
				int result = 0;

				Combo selectedMovie = (Combo) comboMovie.getSelectedItem();
				int movieId = selectedMovie.getKey();

				Combo selectedPlace = (Combo) comboPlace.getSelectedItem();
				int placeId = selectedPlace.getKey();

				Combo selectedTheater = (Combo) comboTheater.getSelectedItem();
				int theaterId = selectedTheater.getKey();

				String startDate = tfStartDate.getText();
				String endDate = tfEndDate.getText();
				String startTime = tfTime.getText();

				if (statusText.equals("입력")) {
					result = sdao.insert(movieId, placeId, theaterId, startDate, endDate, startTime);
					if (result == -1) {
						JOptionPane.showMessageDialog(contentPane, "ER7:상영정보 데이터를 입력할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else if (result == 0) {
						JOptionPane.showMessageDialog(contentPane, "ER8:상영정보 데이터를 입력할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "데이터 입력이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						main.screenTable.setModel(main.setScreenTable(null));
						util.hiddenTableColumn(main.screenTable, 0);
						dispose();
					}
				} else if (statusText.equals("수정")) {
					if (status == 2) {
						result = sdao.update(id, movieId, placeId, theaterId, startDate, endDate, startTime);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER9:상영정보 데이터를 수정할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER10:상영정보 데이터를 수정할 수 없습니다.\n이미 존재하는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.screenTable.setModel(main.setScreenTable(null));
							util.hiddenTableColumn(main.screenTable, 0);
							dispose();
						}
					} else if (status == 3) {
						new Screen(main, 2, id);
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER11:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
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
						result = sdao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER12:상영정보 데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER13:상영정보 데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							main.screenTable.setModel(main.setScreenTable(null));
							util.hiddenTableColumn(main.screenTable, 0);
							dispose();
						}
					}
				} else if (statusText.equals("취소")) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER14:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
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
		
		lbTitle = new JLabel("상영정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(109, 0, 137, 40);
		contentPane.add(lbTitle);

		lbMovieTitle = new JLabel("영화제목");
		lbMovieTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMovieTitle.setBounds(14, 50, 80, 20);
		contentPane.add(lbMovieTitle);

		comboMovie = new JComboBox<Combo>();
		comboMovie.setBounds(106, 50, 168, 20);
		contentPane.add(comboMovie);

		lbDate = new JLabel("상영기간");
		lbDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDate.setBounds(14, 80, 80, 20);
		contentPane.add(lbDate);

		tfStartDate = new JTextField();
		tfStartDate.setColumns(10);
		tfStartDate.setBounds(106, 80, 86, 20);
		contentPane.add(tfStartDate);

		lbBtwDate = new JLabel("~");
		lbBtwDate.setHorizontalAlignment(SwingConstants.CENTER);
		lbBtwDate.setBounds(195, 84, 20, 15);
		contentPane.add(lbBtwDate);

		tfEndDate = new JTextField();
		tfEndDate.setBounds(217, 80, 86, 20);
		contentPane.add(tfEndDate);

		lbTime = new JLabel("시작시간");
		lbTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTime.setBounds(14, 170, 80, 20);
		contentPane.add(lbTime);

		tfTime = new JTextField();
		tfTime.setBounds(106, 170, 197, 20);
		contentPane.add(tfTime);

		lbPlace = new JLabel("상영지점");
		lbPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPlace.setBounds(14, 110, 80, 20);
		contentPane.add(lbPlace);

		comboPlace = new JComboBox<Combo>();
		comboPlace.setBounds(106, 110, 102, 20);
		contentPane.add(comboPlace);

		lbTheater = new JLabel("상영관");
		lbTheater.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTheater.setBounds(38, 138, 56, 20);
		contentPane.add(lbTheater);

		comboTheater = new JComboBox<Combo>();
		comboTheater.setBounds(106, 138, 102, 20);
		contentPane.add(comboTheater);

		btnInsUpd = new JButton();
		btnInsUpd.setBounds(98, 216, 76, 28);
		contentPane.add(btnInsUpd);

		btnDelCan = new JButton();
		btnDelCan.setBounds(198, 216, 76, 28);
		contentPane.add(btnDelCan);
	}

}