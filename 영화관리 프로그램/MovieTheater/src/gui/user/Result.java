package gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.ReserveDao;
import models.Reserves;

@SuppressWarnings("serial")
public class Result extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbIcon, lbTitle;
	private JLabel lbTitleMovie, lbMovie, lbTitleDate, lbDate, lbTitleCnt, lbCnt, lbTitleSeat, lbSeat, lbTitlePrice, lbPrice, lbTitleDt, lbDt;
	private JButton btnMain;
	
	private String userId;

	public Result(String userId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		ReserveDao rDao = ReserveDao.getInstance();
		Reserves reserve = rDao.selectRecent(userId);
		lbMovie.setText(reserve.getMovieTitle());
		lbDate.setText(reserve.getReserveDate());
		lbCnt.setText(reserve.getReserveCnt()+"인");
		lbSeat.setText(reserve.getSeat().replace(",", ", "));
		lbPrice.setText(NumberFormat.getInstance().format(reserve.getPrice()) + "원");
		lbDt.setText(reserve.getInsDt());
		
		btnMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Main(userId);
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

		lbIcon = custom.setLbImg("lbIcon", 4, 160, 130);
		lbTitle = custom.setLb("lbTitle", "예매가 완료되었습니다", 100, 150, 220, 185, "center", 20, "bold");

		lbTitleMovie = custom.setLb("lbTitleMovie", "영화제목", 35, 310, 100, 20, "left", 17, "bold");
		lbMovie = custom.setLb("lbMovie", "계절과 계절 사이", 195, 310, 180, 20, "right", 17, "plain");

		lbTitleDate = custom.setLb("lbTitleDate", "상영일자", 35, 360, 100, 20, "left", 17, "bold");
		lbDate = custom.setLb("lbDate", "19.10.08", 195, 360, 180, 20, "right", 17, "plain");

		lbTitleCnt = custom.setLb("lbTitleCnt", "예매인원", 35, 410, 100, 20, "left", 17, "bold");
		lbCnt = custom.setLb("lbCnt", "2인", 195, 410, 180, 20, "right", 17, "plain");

		lbTitleSeat = custom.setLb("lbTitleSeat", "좌석번호", 35, 460, 100, 20, "left", 17, "bold");
		lbSeat = custom.setLb("lbSeat", "A4, E8", 195, 460, 180, 20, "right", 17, "plain");

		lbTitlePrice = custom.setLb("lbTitlePrice", "결제금액", 35, 510, 100, 20, "left", 17, "bold");
		lbPrice = custom.setLb("lbPrice", "7,000원", 195, 510, 180, 20, "right", 17, "plain");

		lbTitleDt = custom.setLb("lbTitleDt", "예매일자", 35, 560, 130, 20, "left", 17, "bold");
		lbDt = custom.setLb("lbDt", "19.10.05 12:27", 195, 560, 180, 20, "right", 17, "plain");

		btnMain = custom.setBtnBlue("btnMain", "메인으로", 655);
	}
}