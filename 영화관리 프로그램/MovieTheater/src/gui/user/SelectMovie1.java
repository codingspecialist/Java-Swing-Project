package gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DBConnection;
import dao.UserDao;
import models.Movies;
import models.Users;
import util.Utils;

@SuppressWarnings("serial")
public class SelectMovie1 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbBox[], lbMovieName[], lbTime[], lbTitle;
	private JButton btnBack;
	
	private static final String SQL = "SELECT DISTINCT M.ID, M.TITLE, M.AGE, M.RUNNING_TIME FROM MOVIE M INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID WHERE ? BETWEEN S.START_DATE AND S.END_DATE";
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private int addnum;
	private int movieId;
	private ArrayList<Movies> movies = new ArrayList<>();

	private String userId, reserveDate;

	public SelectMovie1(String userId, String reserveDate) {
		this.userId = userId;
		this.reserveDate = reserveDate;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectDate(userId, 0, "Movie");
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, reserveDate);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setAge(rs.getInt("AGE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				movies.add(movie);
			}

			backgroundPanel = new JPanel();
			frame.setContentPane(backgroundPanel);
			frame.setTitle("영화 예매 프로그램 ver1.0");

			CustomUI custom = new CustomUI(backgroundPanel);
			custom.setPanel();

			lbBox = new JLabel[movies.size()];
			lbMovieName = new JLabel[movies.size()];
			lbTime = new JLabel[movies.size()];

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);

			for (int j = 0; j < movies.size(); j++) {
				int moveY = 55;
				addnum++;
				lbBox[j] = custom.setLbBox("lbBox" + j, movies.get(j).getAge() + "", 35, 20 + (moveY * j), panel);
				lbMovieName[j] = custom.setLb("lbMovieName" + j, movies.get(j).getTitle(), 75, 22 + (moveY * j), 300, 20, "left", 14, "plain", panel);
				lbTime[j] = custom.setLb("lbTime" + j, movies.get(j).getRunningTime() + "분", 80, 22 + (moveY * j), 300, 20, "right", 13, "plain", panel);

				panel.add(lbBox[j]);
				panel.add(lbMovieName[j]);
				panel.add(lbTime[j]);

				lbMovieName[j].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {
						String movieTitle = e.getSource().toString();
						int movieAge = 0;

						for (int i = 0; i < lbMovieName.length; i++) {
							if (movieTitle.contains(movies.get(i).getTitle())) {
								movieId = movies.get(i).getId();
								movieAge = movies.get(i).getAge();
							}
						}

						UserDao dao = UserDao.getInstance();
						Users user = dao.selectBirth(userId);

						String birth = user.getBirthDate() + "";
						int birthYear = Integer.parseInt(birth.substring(0, 4));
						int birthMonth = Integer.parseInt(birth.substring(4, 6));
						int birthDay = Integer.parseInt(birth.substring(6));

						Utils util = new Utils();
						int age = util.getAge(birthYear, birthMonth, birthDay);

						if (age >= movieAge) {
							new SelectMovie2(userId, movieId, reserveDate);
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame, "관람 가능한 나이가 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}

					}
				});
			}
			panel.setPreferredSize(new Dimension(400, 20 + 55 * addnum));

			JScrollPane sp = new JScrollPane();
			sp.setViewportView(panel);
			sp.setBounds(0, 120, 422, 500);
			backgroundPanel.add(sp);

			lbTitle = custom.setLb("lbTitle", "예매 내역", 100, 85, 220, 185, "center", 20, "bold");
			btnBack = custom.setBtnWhite("btnBack", "이전으로", 650);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}