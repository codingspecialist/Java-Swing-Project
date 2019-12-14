package gui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.ReserveDao;
import dao.SeatDao;
import models.Reserves;
import models.Seats;

@SuppressWarnings("serial")
public class Seat extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbCnt;
	private JButton btnSeat[];
	private JButton btnReserve, btnBack, btnScreen;
	private JComboBox<String> comboCnt;
	private ActionListener btnListener;

	private int selectedCnt, peopleCnt;
	private ArrayList<Integer> selectedSeatNum = new ArrayList<Integer>();
	
	private String userId, reserveDate, reserveTime, beforePage;
	private int movieId, placeId, theaterId;
	
	public Seat(String userId, int movieId, int placeId, int theaterId, String reserveDate, String reserveTime, String beforePage) {
		this.userId = userId;
		this.movieId = movieId;
		this.placeId = placeId;
		this.theaterId = theaterId;
		this.reserveDate = reserveDate;
		this.reserveTime = reserveTime;
		this.beforePage = beforePage;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton)e.getSource();
					peopleCnt = comboCnt.getSelectedIndex()+1;
					int btnNum = Integer.parseInt(btn.getText());
					if(btn.getBackground().equals(Color.ORANGE)) {
							btn.setBackground(new Color(230, 236, 240));
							int arrayIndex = selectedSeatNum.indexOf(btnNum);
							selectedSeatNum.remove(arrayIndex);
							selectedCnt--;
					} else {
						if(selectedCnt<peopleCnt && selectedCnt>=0) {
							btn.setBackground(Color.ORANGE);
							selectedSeatNum.add(btnNum);
							selectedCnt++;
						} else {
							JOptionPane.showMessageDialog(frame, "선택한 인원 " + peopleCnt + "명을 초과하여 선택할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
	            }
			}
		};
		
		init();

		btnScreen.setEnabled(false);
		
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peopleCnt = comboCnt.getSelectedIndex()+1;
				if(selectedSeatNum.size() == peopleCnt) {
					String selectedSeats = "";
					selectedSeatNum.sort(null);
					for(Integer i : selectedSeatNum) {
						if(selectedSeats.equals("")) {
							selectedSeats = i+"";
						} else {
							selectedSeats += "," + i;
						}
					}
					new Payment(userId, reserveDate, reserveTime, movieId, placeId, theaterId, selectedSeats, beforePage);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "선택한 인원 " + peopleCnt + "명만큼의 좌석을 선택하지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(beforePage.equals("Movie")) {
					new SelectMovie2(userId, movieId, reserveDate);
					frame.dispose();
				} else {
					new SelectTheater2(userId, placeId, reserveDate);
					frame.dispose();
				}
			}
		});

		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
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

		lbCnt = custom.setLb("lbCnt", "예매 인원", 45, 165, 100, 20, "left", 17, "bold");
		String cnt[] = { "1", "2", "3", "4" };
		comboCnt = custom.setCombo("comboCnt", cnt, 330, 165, 50, 25);
		btnScreen = custom.setbtnBar("screenBar", "SCREEN", 220);

		SeatDao dao = SeatDao.getInstance();
		Seats seat = dao.selectSeat(placeId, theaterId);
		String seatRow[] = new String[seat.getRow()];
		String seatCol[] = new String[seat.getCol()];
		btnSeat = new JButton[seat.getRow()*seat.getCol()];
		
		for(int row = 0; row < seatRow.length; row++) {
			for(int col = 0; col < seatCol.length; col++) {
				int num = (row*seatCol.length) + (col+1);
				int moveX = 70;
				int moveY = 60;
				btnSeat[num-1] = custom.setBtnSeat("btnSeat"+num, num+"",  45 + (moveX * col), 275 + (moveY * row));
				btnSeat[num-1].addActionListener(btnListener);
			}	
		}
		
		ReserveDao rDao = ReserveDao.getInstance();
		Reserves reserve = rDao.selectedSeats(movieId, placeId, theaterId, reserveDate, reserveTime);
		
		if(reserve.getSeat() != null) {
			String splitAlredySelectedSeat[] = reserve.getSeat().split("\\,");
			for(int i=0; i<btnSeat.length; i++) {
				String num = btnSeat[i].getText();
				for(int j=0; j<splitAlredySelectedSeat.length; j++) {
					if(splitAlredySelectedSeat[j].equals(num)) {
						btnSeat[i].setBackground(new Color(53, 121, 247));
						btnSeat[i].setEnabled(false);
					}
				}
			}
		}

		btnReserve = custom.setBtnBlue("btnReserve", "예매하기", 600);
		btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);
	}
}