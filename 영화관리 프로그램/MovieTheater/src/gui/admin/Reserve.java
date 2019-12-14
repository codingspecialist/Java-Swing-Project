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

import dao.ReserveDao;
import models.Reserves;
import util.Utils;

@SuppressWarnings("serial")
public class Reserve extends JDialog {

	private JPanel contentPane;
	private JButton btnDelCan;
	private JLabel lbTitle, lbUserId, lbMovie, lbPlace, lbTheater, lbSeat, lbPrice, lbCardNo, lbInsDt, lbReserveDate,
			lbReserveTime, lbCnt, lbDiscount, lbDelFg, lbDelDt;
	private JTextField txtUserId, txtMovie, txtPlace, txtTheater, txtSeat, txtReserveCnt, txtPrice,
			txtCardNo1, txtCardNo2, txtCardNo3, txtCardNo4, txtInsDt, txtReserveTime, txtReserveDate,
			txtDiscount, txtDelFg, txtDelDt;

	public Reserve(Main mainFrame, int status, int id) {
		Main main = mainFrame;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 432);

		init();

		ReserveDao dao = ReserveDao.getInstance();
		Reserves reserve;
		Utils util = new Utils();
		int executeCd = 1;

		if (status == 3) {
			reserve = dao.selectOne(id);
			if (reserve == null) {
				JOptionPane.showMessageDialog(this, "ER1:잘못된 호출입니다.", "오류", JOptionPane.ERROR_MESSAGE);
				executeCd = -1;
			} else {
				txtUserId.setText(reserve.getUserId());
				txtUserId.setEditable(false);
				txtMovie.setText(reserve.getMovieTitle());
				txtMovie.setEditable(false);
				txtPlace.setText(reserve.getPlaceName());
				txtPlace.setEditable(false);
				txtTheater.setText(reserve.getTheaterName());
				txtTheater.setEditable(false);
				txtReserveDate.setText(reserve.getReserveDate());
				txtReserveDate.setEditable(false);
				txtReserveTime.setText(reserve.getReserveTime());
				txtReserveTime.setEditable(false);
				txtReserveCnt.setText(reserve.getReserveCnt() + "");
				txtReserveCnt.setEditable(false);
				txtSeat.setText(reserve.getSeat() + "");
				txtSeat.setEditable(false);
				txtPrice.setText(reserve.getPrice() + "");
				txtPrice.setEditable(false);
				txtDiscount.setText(reserve.getDiscountName());
				txtDiscount.setEditable(false);
				txtCardNo1.setText(reserve.getCardNo().substring(0, 4) + "");
				txtCardNo1.setEditable(false);
				txtCardNo2.setText(reserve.getCardNo().substring(4, 8) + "");
				txtCardNo2.setEditable(false);
				txtCardNo3.setText(reserve.getCardNo().substring(8, 12) + "");
				txtCardNo3.setEditable(false);
				txtCardNo4.setText(reserve.getCardNo().substring(12, 16) + "");
				txtCardNo4.setEditable(false);
				txtInsDt.setText(reserve.getInsDt());
				txtInsDt.setEditable(false);
				txtDelFg.setText(reserve.getDelFg());
				txtDelFg.setEditable(false);
				txtDelDt.setText(reserve.getDelDt());
				txtDelDt.setEditable(false);
			}
		}

		btnDelCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statusText = btnDelCan.getText();

				if (statusText.equals("삭제")) {
					int result = 0;
					int check = JOptionPane.showConfirmDialog(contentPane, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(contentPane, "ER2:데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(contentPane, "ER3:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(contentPane, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							main.reserveTable.setModel(main.setReserveTable(null));
							util.hiddenTableColumn(main.reserveTable, 0);
							dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "ER4:정상적인 호출이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});

		setVisible(true);

		if (executeCd == -1) {
			dispose();
		}
	}

	public void init() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("영화관 관리 프로그램 ver1.0");

		lbTitle = new JLabel("예매정보");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 137, 40);
		contentPane.add(lbTitle);

		lbUserId = new JLabel("아이디");
		lbUserId.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUserId.setBounds(10, 51, 80, 20);
		contentPane.add(lbUserId);

		txtUserId = new JTextField();
		txtUserId.setBounds(102, 51, 168, 20);
		contentPane.add(txtUserId);

		lbMovie = new JLabel("영화명");
		lbMovie.setHorizontalAlignment(SwingConstants.RIGHT);
		lbMovie.setBounds(10, 81, 80, 20);
		contentPane.add(lbMovie);

		txtMovie = new JTextField();
		txtMovie.setBounds(102, 81, 90, 20);
		contentPane.add(txtMovie);

		lbPlace = new JLabel("지점명");
		lbPlace.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPlace.setBounds(10, 111, 80, 20);
		contentPane.add(lbPlace);

		txtPlace = new JTextField();
		txtPlace.setBounds(102, 111, 90, 20);
		contentPane.add(txtPlace);

		lbTheater = new JLabel("상영관명");
		lbTheater.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTheater.setBounds(10, 141, 80, 20);
		contentPane.add(lbTheater);

		txtTheater = new JTextField();
		txtTheater.setBounds(102, 141, 90, 20);
		contentPane.add(txtTheater);

		lbSeat = new JLabel("좌석");
		lbSeat.setHorizontalAlignment(SwingConstants.RIGHT);
		lbSeat.setBounds(10, 171, 80, 20);
		contentPane.add(lbSeat);

		txtSeat = new JTextField();
		txtSeat.setBounds(102, 171, 90, 20);
		contentPane.add(txtSeat);

		lbPrice = new JLabel("결제금액");
		lbPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPrice.setBounds(10, 201, 80, 20);
		contentPane.add(lbPrice);

		txtPrice = new JTextField();
		txtPrice.setBounds(102, 201, 90, 20);
		contentPane.add(txtPrice);

		lbCardNo = new JLabel("카드번호");
		lbCardNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCardNo.setBounds(10, 231, 80, 20);
		contentPane.add(lbCardNo);

		txtCardNo1 = new JTextField();
		txtCardNo1.setBounds(102, 231, 47, 20);
		contentPane.add(txtCardNo1);

		txtCardNo2 = new JTextField();
		txtCardNo2.setBounds(161, 231, 47, 20);
		contentPane.add(txtCardNo2);

		txtCardNo3 = new JTextField();
		txtCardNo3.setBounds(220, 231, 47, 20);
		contentPane.add(txtCardNo3);

		txtCardNo4 = new JTextField();
		txtCardNo4.setBounds(279, 231, 47, 20);
		contentPane.add(txtCardNo4);

		lbInsDt = new JLabel("예약일");
		lbInsDt.setHorizontalAlignment(SwingConstants.RIGHT);
		lbInsDt.setBounds(10, 261, 80, 20);
		contentPane.add(lbInsDt);

		txtInsDt = new JTextField();
		txtInsDt.setBounds(102, 261, 120, 20);
		contentPane.add(txtInsDt);

		lbReserveDate = new JLabel("예매날짜");
		lbReserveDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lbReserveDate.setBounds(204, 81, 80, 20);
		contentPane.add(lbReserveDate);

		txtReserveDate = new JTextField();
		txtReserveDate.setBounds(296, 81, 90, 20);
		contentPane.add(txtReserveDate);

		lbReserveTime = new JLabel("예매시간");
		lbReserveTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lbReserveTime.setBounds(204, 111, 80, 20);
		contentPane.add(lbReserveTime);

		txtReserveTime = new JTextField();
		txtReserveTime.setBounds(296, 111, 90, 20);
		contentPane.add(txtReserveTime);

		lbCnt = new JLabel("예매인원");
		lbCnt.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCnt.setBounds(204, 141, 80, 20);
		contentPane.add(lbCnt);

		txtReserveCnt = new JTextField();
		txtReserveCnt.setBounds(296, 141, 90, 20);
		contentPane.add(txtReserveCnt);

		lbDiscount = new JLabel("할인구분");
		lbDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDiscount.setBounds(204, 201, 80, 20);
		contentPane.add(lbDiscount);

		txtDiscount = new JTextField();
		txtDiscount.setBounds(296, 201, 90, 20);
		contentPane.add(txtDiscount);

		lbDelFg = new JLabel("취소여부");
		lbDelFg.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDelFg.setBounds(204, 261, 80, 20);
		contentPane.add(lbDelFg);

		txtDelFg = new JTextField();
		txtDelFg.setBounds(296, 261, 90, 20);
		contentPane.add(txtDelFg);

		lbDelDt = new JLabel("취소일시");
		lbDelDt.setHorizontalAlignment(SwingConstants.RIGHT);
		lbDelDt.setBounds(204, 291, 80, 20);
		contentPane.add(lbDelDt);

		txtDelDt = new JTextField();
		txtDelDt.setBounds(296, 291, 90, 20);
		contentPane.add(txtDelDt);

		btnDelCan = new JButton("삭제");
		btnDelCan.setBounds(310, 339, 76, 28);
		contentPane.add(btnDelCan);
	}

}