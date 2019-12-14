package gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DBConnection;
import dao.ReserveDao;
import dao.SeatDao;
import dao.UserDao;
import models.Movies;
import models.Reserves;
import models.Seats;
import models.Theaters;
import models.Users;
import util.Utils;

@SuppressWarnings("serial")
public class SelectTheater2 extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnMovie[][], btnBack;
	private JLabel lbBox[], lbMovie[], lbRunningTime[];

	private int firstY = 20;
	private int moveY = 55;
	private int firstX = 15;
	private int btnMovieY = firstY + moveY;
	private int btnMoveX = 100;
	private int jj = 0;
	private int jjj = 3;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	private JComboBox<String> comboCnt;
	private ActionListener btnListener;

	private ArrayList<Movies> movies = new ArrayList<>();
	private ArrayList<Theaters> theaters = new ArrayList<>();

	private String userId, reserveDate;
	private int placeId;
	
	private String sql;

	public SelectTheater2(String userId, int placeId, String reserveDate) {
		this.userId = userId;
		this.placeId = placeId;
		this.reserveDate = reserveDate;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton) e.getSource();
					String btnName = btn.getName();
					
					conn = DBConnection.getConnection();
					
					try {
						sql = "SELECT M.ID, M.AGE, T.ID AS THEATER_ID, S.START_TIME";
						sql += " FROM MOVIE M";
						sql += " INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID";
						sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
						sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID AND T.PLACE_ID = P.ID";
						sql += " WHERE P.ID = ? AND ? BETWEEN S.START_DATE AND S.END_DATE";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, placeId);
						pstmt.setString(2, reserveDate);

						rs = pstmt.executeQuery();

						while (rs.next()) {
							Movies movie = new Movies();
							movie.setId(rs.getInt("ID"));
							movie.setAge(rs.getInt("AGE"));
							movie.setTheaterId(rs.getInt("THEATER_ID"));
							movie.setStartTime(rs.getString("START_TIME"));
							movies.add(movie);
						}
						
						int num = Integer.parseInt(btnName.substring(8, 9));
						int num2 = Integer.parseInt(btnName.substring(9, 10));
						
						UserDao dao = UserDao.getInstance();
						Users user = dao.selectBirth(userId);
						
						String birth = user.getBirthDate()+"";
						int birthYear = Integer.parseInt(birth.substring(0, 4));
						int birthMonth = Integer.parseInt(birth.substring(4, 6));
						int birthDay = Integer.parseInt(birth.substring(6));
						
						Utils util = new Utils();
						int age = util.getAge(birthYear, birthMonth, birthDay);

						if(age >= movies.get(num).getAge()) {
							int movieId = movies.get(num).getId();
							int theaterId = movies.get(num).getTheaterId();
							
							String splitTime[] = movies.get(num).getStartTime().split("\\|");
							String reserveTime = splitTime[num2];

							new Seat(userId, movieId, placeId, theaterId, reserveDate, reserveTime, "Theater");
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame, "관람 가능한 나이가 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e2) {
					}
				}
			}
		};

		init();

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectDate(userId, placeId, "Theater");
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("영화 예매 프로그램 ver1.0");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		conn = DBConnection.getConnection();

		try {
			sql = "SELECT M.ID, M.TITLE, M.AGE, M.RUNNING_TIME, P.ID AS PLACE_ID, T.ID AS THEATER_ID, S.START_TIME";
			sql += " FROM MOVIE M";
			sql += " INNER JOIN SCREEN S ON M.ID = S.MOVIE_ID";
			sql += " INNER JOIN PLACE P ON S.PLACE_ID = P.ID";
			sql += " INNER JOIN THEATER T ON S.THEATER_ID = T.ID AND T.PLACE_ID = P.ID";
			sql += " WHERE P.ID = ? AND ? BETWEEN S.START_DATE AND S.END_DATE";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, placeId);
			pstmt.setString(2, reserveDate);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Movies movie = new Movies();
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setAge(rs.getInt("AGE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				movie.setPlaceId(rs.getInt("PLACE_ID"));
				movie.setTheaterId(rs.getInt("THEATER_ID"));
				movie.setStartTime(rs.getString("START_TIME"));
				
				String splitTime[] = movie.getStartTime().split("\\|");
				for (int i = 0; i < splitTime.length; i++) {
					ReserveDao rDao = ReserveDao.getInstance();
					Reserves reserve = rDao.selectedSeats(movie.getId(), movie.getPlaceId(), movie.getTheaterId(), reserveDate, splitTime[i]);
					if(i == splitTime.length-1) {
						movie.setSeat(movie.getSeat() + reserve.getSeat());
					} else if (i == 0){
						movie.setSeat(reserve.getSeat() + "|");
						
					} else {
						if (movie.getSeat() != null) {
							movie.setSeat(movie.getSeat() + reserve.getSeat() + "|");
						} else {
							movie.setSeat(movie.getSeat() + "|");
						}
					}
				}
								
				movies.add(movie);
			}

			lbBox = new JLabel[movies.size()];
			lbMovie = new JLabel[movies.size()];
			lbRunningTime = new JLabel[movies.size()];
			
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);
			int rowCnt = 0;
			int boxRowCnt = 0;
			
			for (int i = 0; i < movies.size(); i++) {
				if (i == 0) {
					lbBox[i] = custom.setLbBox("lbBox"+i, movies.get(i).getAge() + "", 17, firstY, panel);
					lbMovie[i] = custom.setLb("lbMovie"+i, movies.get(i).getTitle(), 55, firstY, 300, 20, "left", 14, "plain", panel);
					lbRunningTime[i] = custom.setLb("lbRunningTime"+i, movies.get(i).getRunningTime()+"분", 360, firstY + 2, 50, 20, "left", 14, "bold", panel);
				} else {
					lbBox[i] = custom.setLbBox("lbBox"+i, movies.get(i).getAge() + "", 17, firstY + moveY, panel);
					lbMovie[i] = custom.setLb("lbMovie"+i, movies.get(i).getTitle(), 55, firstY + 2 + moveY, 300, 20, "left", 14, "plain", panel);
					lbRunningTime[i] = custom.setLb("lbThlbRunningTimeeater"+i, movies.get(i).getRunningTime()+"분", 360, firstY + 2 + moveY, 50, 20, "left", 14, "bold", panel);
				}
				
				panel.add(lbBox[i]);
				panel.add(lbMovie[i]);
				panel.add(lbRunningTime[i]);
				rowCnt = i;
				
				String splitTime[] = movies.get(i).getStartTime().split("\\|");
				String splitSeat[] = movies.get(i).getSeat().split("\\|");
				for (int j = 0; j < splitTime.length; j++) {
					if (j > jjj) {
						jj = jj + 4;
						btnMovieY = btnMovieY + 80;
						jjj = jjj + 4;
					}
					
					SeatDao dao = SeatDao.getInstance();
					Seats seat = dao.selectSeat(movies.get(i).getPlaceId(), movies.get(i).getTheaterId());
					int seatCnt = seat.getRow() * seat.getCol();
					
					int selectedSeatCnt = 0;
					if(splitSeat[j].equals("null")) {
						selectedSeatCnt = 0;
					} else if(splitSeat[j].equals("nullnull")) {
						selectedSeatCnt = 0;
					} else if(splitSeat[j].equals("")) {
						selectedSeatCnt = 0;
					} else {
						selectedSeatCnt = splitSeat[j].split("\\,").length;
					}
					int remainingSeats = seatCnt - selectedSeatCnt;


					btnMovie = new JButton[movies.size()][splitTime.length];
					btnMovie[i][j] = custom.setBtnMovie("btnMovie" + i + j, splitTime[j], remainingSeats + "석", firstX + (btnMoveX * (j - jj)), btnMovieY);
					btnMovie[i][j].addActionListener(btnListener);
					panel.add(btnMovie[i][j]);
					
					if((j+1) % 4 == 1) {
						boxRowCnt++;
					}
				}
				
				firstY = btnMovieY + moveY;
				btnMovieY = firstY + moveY * 2;
				jj = 0;
				jjj = 3;
			}
			
			panel.setPreferredSize(new Dimension(400, 20 + (71 * (rowCnt+1)) + (94 * boxRowCnt)));
			
			JScrollPane sp = new JScrollPane();
			sp.setViewportView(panel);
			sp.setBounds(0, 120, 422, 500);
			backgroundPanel.add(sp);

			btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}