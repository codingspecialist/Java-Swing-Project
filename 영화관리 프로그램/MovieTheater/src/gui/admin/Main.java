package gui.admin;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dao.DiscountDao;
import dao.MovieDao;
import dao.PlaceDao;
import dao.ReserveDao;
import dao.SalesDao;
import dao.ScreenDao;
import dao.SeatDao;
import dao.TheaterDao;
import dao.UserDao;
import gui.user.Login;
import models.Discounts;
import models.Movies;
import models.Places;
import models.Reserves;
import models.Sales;
import models.Seats;
import models.Theaters;
import models.Users;
import util.Utils;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel, bottomPanel;
	private JScrollPane userPanel, moviePanel, screenPanel, placePanel, theaterPanel, seatPanel, discountPanel, reservePanel, salesPanel;
	private JTabbedPane tab;
	private JButton btnIns, btnUpd, btnDel, btnSel, btnAll, btnLogout;
	private JTextField txtSearch;
	private ButtonGroup rbGroup;
	private JRadioButton rbMovie, rbPlace, rbId;
	JTable userTable, movieTable, screenTable, placeTable, theaterTable, seatTable, discountTable, reserveTable, salesTable;

	Utils util = new Utils();
	
	public Main() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		userTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					String userId = (String)tm.getValueAt(table.getSelectedRow(), 0);
					new User(Main.this, 3, userId);
				}
			}
		});
		
		movieTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer) tm.getValueAt(table.getSelectedRow(), 0);
					new Movie(Main.this, 3, id);
				}
			}
		});
		
		screenTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer)tm.getValueAt(table.getSelectedRow(), 0);
					new Screen(Main.this, 3, id);
				}
			}
		});
		
		placeTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer) tm.getValueAt(table.getSelectedRow(), 0);
					new Place(Main.this, 3, id);
				}
			}
		});
		
		theaterTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer)tm.getValueAt(table.getSelectedRow(), 0);
					new Theater(Main.this, 3, id);
				}
			}
		});
		
		seatTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer)tm.getValueAt(table.getSelectedRow(), 0);
					new Seat(Main.this, 3, id);
				}
			}
		});
		
		discountTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer)tm.getValueAt(table.getSelectedRow(), 0);
					new Discount(Main.this, 3, id);
				}
			}
		});
		
		reserveTable.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				if (e.getClickCount() == 2) {
					TableModel tm = table.getModel();
					int id = (Integer)tm.getValueAt(table.getSelectedRow(), 0);
					new Reserve(Main.this, 3, id);
				}
			}
		});
		
		tab.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeVisible(tab.getSelectedIndex());
			}
		});
		
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedIndex();
				txtSearch.setText("");

				if(index == 0) {		// 회원관리
					userTable.setModel(setUserTable(""));
				} else if(index == 1) {	// 영화관리
					movieTable.setModel(setMovieTable(null));
					util.hiddenTableColumn(movieTable, 0);
				} else if(index == 2) {	// 상영관리
					screenTable.setModel(setScreenTable(null));
					util.hiddenTableColumn(screenTable, 0);
				} else if(index == 3) {	// 지점관리
					placeTable.setModel(setPlaceTable(null));
					util.hiddenTableColumn(placeTable, 0);
				} else if(index == 4) {	// 상영관관리
					theaterTable.setModel(setTheaterTable(null));
					util.hiddenTableColumn(theaterTable, 0);
				} else if(index == 5) { // 좌석관리
					seatTable.setModel(setSeatTable(null));
					util.hiddenTableColumn(seatTable, 0);
				} else if(index == 6) {	// 할인관리
					discountTable.setModel(setDiscountTable(null));
					util.hiddenTableColumn(discountTable, 0);
				} else if(index == 7) {	// 예매내역관리
					reserveTable.setModel(setReserveTable(null));
					util.hiddenTableColumn(reserveTable, 0);
				}
			}
		});
		
		btnSel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedIndex();
				String keyword = txtSearch.getText();
				
				if(index == 0) {		// 회원관리
					userTable.setModel(setUserTable(keyword));
				} else if(index == 1) {	// 영화관리
					movieTable.setModel(setMovieTable(keyword));
					util.hiddenTableColumn(movieTable, 0);
				} else if(index == 2) {	// 상영관리
					screenTable.setModel(setScreenTable(keyword));
					util.hiddenTableColumn(screenTable, 0);
				} else if(index == 3) {	// 지점관리
					placeTable.setModel(setPlaceTable(keyword));
					util.hiddenTableColumn(placeTable, 0);
				} else if(index == 4) {	// 상영관관리
					theaterTable.setModel(setTheaterTable(keyword));
					util.hiddenTableColumn(theaterTable, 0);
				} else if(index == 5) { // 좌석관리
					seatTable.setModel(setSeatTable(keyword));
					util.hiddenTableColumn(seatTable, 0);
				} else if(index == 6) {	// 할인관리
					discountTable.setModel(setDiscountTable(keyword));
					util.hiddenTableColumn(discountTable, 0);
				} else if(index == 7) {	// 예매내역관리
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false && rbId.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						reserveTable.setModel(setReserveTable(keyword));
						util.hiddenTableColumn(reserveTable, 0);
					}
				} else if(index == 8) {	// 매출관리
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						salesTable.setModel(setSalesTable(keyword));
					}
				}
			}
		});
		
		rbMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = tab.getSelectedIndex();
				String keyword = txtSearch.getText();
				
				if(index == 7) {
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false && rbId.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						reserveTable.setModel(setReserveTable(keyword));
						util.hiddenTableColumn(reserveTable, 0);
					}
				} else if(index == 8) {
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						salesTable.setModel(setSalesTable(keyword));
					}
				}
			}
		});
		
		rbId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = tab.getSelectedIndex();
				String keyword = txtSearch.getText();
				
				if(index == 7) {
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false && rbId.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						reserveTable.setModel(setReserveTable(keyword));
						util.hiddenTableColumn(reserveTable, 0);
					}
				}
			}
		});
		
		rbPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = tab.getSelectedIndex();
				String keyword = txtSearch.getText();
				
				if(index == 7) {
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false && rbId.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						reserveTable.setModel(setReserveTable(keyword));
						util.hiddenTableColumn(reserveTable, 0);
					}
				} else if(index == 8) {
					if (rbMovie.isSelected() == false && rbPlace.isSelected() == false) {
						JOptionPane.showMessageDialog(frame, "조회할 조건을 선택해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					} else {
						salesTable.setModel(setSalesTable(keyword));
					}
				}
			}
		});
		
		btnIns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedIndex();
				
				if (index == 1) { // 영화관리
					new Movie(Main.this, 1, 0);
				} else if(index == 2) {	// 상영관리
					new Screen(Main.this, 1, 0);
				} else if(index == 3) {	// 지점관리
					new Place(Main.this, 1, 0);
				} else if(index == 4) {	// 상영관관리
					new Theater(Main.this, 1, 0);
				} else if(index == 5) {	// 좌석관리
					new Seat(Main.this, 1, 0);
				} else if(index == 6) {	// 할인관리
					new Discount(Main.this, 1, 0);
				}
			}
		});
		
		btnUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedIndex();
				
				if(index == 1) {	// 영화관리
					TableModel tm = movieTable.getModel();
					int id = (Integer) tm.getValueAt(movieTable.getSelectedRow(), 0);
					new Movie(Main.this, 2, id);
				} else if(index == 2) {	// 상영관리
					TableModel tm = screenTable.getModel();
					int id = (Integer)tm.getValueAt(screenTable.getSelectedRow(), 0);
					new Screen(Main.this, 2, id);
				} else if(index == 3) {	// 지점관리
					TableModel tm = placeTable.getModel();
					int id = (Integer)tm.getValueAt(placeTable.getSelectedRow(), 0);
					new Place(Main.this, 2, id);
				} else if(index == 4) {	// 상영관관리
					TableModel tm = theaterTable.getModel();
					int id = (Integer)tm.getValueAt(theaterTable.getSelectedRow(), 0);
					new Theater(Main.this, 2, id);
				} else if(index == 5) {	// 좌석관리
					TableModel tm = seatTable.getModel();
					int id = (Integer)tm.getValueAt(seatTable.getSelectedRow(), 0);
					new Seat(Main.this, 2, id);
				} else if(index == 6) {	// 할인관리
					TableModel tm = discountTable.getModel();
					int id = (Integer)tm.getValueAt(discountTable.getSelectedRow(), 0);
					new Discount(Main.this, 2, id);
				}
			}
		});
		
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tab.getSelectedIndex();
				
				if(index == 0) {		// 회원관리
					TableModel tm = userTable.getModel();
					String userId = (String)tm.getValueAt(userTable.getSelectedRow(), 0);
					
					int check = JOptionPane.showConfirmDialog(frame, "탈퇴하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						UserDao dao = UserDao.getInstance();
						int result = dao.updateDel(userId);
						if (result == -1) {
							JOptionPane.showMessageDialog(frame, "ER01:탈퇴처리를 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(frame, "ER02:탈퇴처리를 할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "회원탈퇴가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							userTable.setModel(setUserTable(""));
							dispose();
						}
					}
				} else if(index == 1) {	// 영화관리
					TableModel tm = movieTable.getModel();
					int id = (Integer) tm.getValueAt(movieTable.getSelectedRow(), 0);
					
					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						MovieDao dao = MovieDao.getInstance();
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(frame, "ER11:데이터를 삭제할 수 없습니다.\n상영중인 영화일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(frame, "ER12:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							movieTable.setModel(setMovieTable(null));
							util.hiddenTableColumn(movieTable, 0);
							dispose();
						}
					}
				} else if(index == 2) {	// 상영관리
					TableModel tm = screenTable.getModel();
					int id = (Integer) tm.getValueAt(screenTable.getSelectedRow(), 0);
					
					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (check == JOptionPane.YES_OPTION) {
						ScreenDao dao = ScreenDao.getInstance();
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(frame, "ER21:데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result== 0) {
							JOptionPane.showMessageDialog(frame, "ER22:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							screenTable.setModel(setScreenTable(null));
							util.hiddenTableColumn(screenTable, 0);
							dispose();
						}
					}
				} else if(index == 3) {	// 지점관리
					TableModel tm = placeTable.getModel();
					int id = (Integer) tm.getValueAt(placeTable.getSelectedRow(), 0);

					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						PlaceDao dao = PlaceDao.getInstance();
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(frame, "ER31:데이터를 삭제할 수 없습니다.\n해당 지점에서 상영중인 영화가 있을 수 있습니다.", "오류",
									JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(frame, "ER32:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료",
									JOptionPane.INFORMATION_MESSAGE);
							placeTable.setModel(setPlaceTable(null));
							util.hiddenTableColumn(placeTable, 0);
							dispose();
						}
					}
				} else if(index == 4) {	// 상영관관리
					TableModel tm = theaterTable.getModel();
					int id = (Integer)tm.getValueAt(theaterTable.getSelectedRow(), 0);
					
					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(check == JOptionPane.YES_OPTION) {
						TheaterDao dao = TheaterDao.getInstance();
						result = dao.delete(id);
						if(result == -1) {
							JOptionPane.showMessageDialog(frame, "ER41:데이터를 삭제할 수 없습니다.\n해당 관에서 상영중인 영화가 있을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(frame, "ER42:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							theaterTable.setModel(setTheaterTable(null));
							util.hiddenTableColumn(theaterTable, 0);
							dispose();
						}
					}
				} else if(index == 5) {	// 좌석관리
					TableModel tm = seatTable.getModel();
					int id = (Integer)tm.getValueAt(seatTable.getSelectedRow(), 0);
					
					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(check == JOptionPane.YES_OPTION) {
						SeatDao dao = SeatDao.getInstance();
						result = dao.delete(id);
						if(result == -1) {
							JOptionPane.showMessageDialog(frame, "ER51:데이터를 삭제할 수 없습니다.\n상영관에 등록된 좌석일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(frame, "ER52:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							seatTable.setModel(setSeatTable(null));
							util.hiddenTableColumn(seatTable, 0);
							dispose();
						}
					}
				} else if(index == 6) {	// 할인관리
					TableModel tm = discountTable.getModel();
					int id = (Integer)tm.getValueAt(discountTable.getSelectedRow(), 0);
					
					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(check == JOptionPane.YES_OPTION) {
						DiscountDao dao = DiscountDao.getInstance();
						result = dao.delete(id);
						if(result == -1) {
							JOptionPane.showMessageDialog(frame, "ER61:데이터를 삭제할 수 없습니다.\n해당 할인값이 이미 적용된 예약내역이 있을 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if(result == 0) {
							JOptionPane.showMessageDialog(frame, "ER62:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							discountTable.setModel(setDiscountTable(null));
							util.hiddenTableColumn(discountTable, 0);
							dispose();
						}
					}
				} else if(index == 7) {	// 예매내역관리
					TableModel tm = reserveTable.getModel();
					int id = (Integer) tm.getValueAt(reserveTable.getSelectedRow(), 0);

					int result = 0;
					int check = JOptionPane.showConfirmDialog(frame, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (check == JOptionPane.YES_OPTION) {
						ReserveDao dao = ReserveDao.getInstance();
						result = dao.delete(id);
						if (result == -1) {
							JOptionPane.showMessageDialog(frame, "ER71:데이터를 삭제할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(frame, "ER72:데이터를 삭제할 수 없습니다.\n존재하지 않는 데이터일 수 있습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(frame, "데이터 삭제가 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
							reserveTable.setModel(setReserveTable(null));
							util.hiddenTableColumn(reserveTable, 0);
							dispose();
						}
					}
				}
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
				frame.dispose();
			}
		});
		
		frame.setSize(776, 539);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void init() {
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
		frame.setContentPane(backgroundPanel);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0, 410, 770, 100);
		backgroundPanel.add(bottomPanel);

		btnAll = new JButton("전체");
		btnAll.setBounds(20, 30, 70, 40);
		bottomPanel.add(btnAll);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(110, 30, 150, 40);
		bottomPanel.add(txtSearch);

		btnSel = new JButton("검색");
		btnSel.setBounds(270, 30, 70, 40);
		bottomPanel.add(btnSel);
		
		rbMovie = new JRadioButton("영화별");
		rbMovie.setBounds(355, 20, 85, 20);
		rbMovie.setSelected(true);
		bottomPanel.add(rbMovie);
		
		rbId = new JRadioButton("아이디별");
		rbId.setBounds(355, 40, 85, 20);
		bottomPanel.add(rbId);
		
		rbPlace = new JRadioButton("지점별");
		rbPlace.setBounds(355, 60, 85, 20);
		bottomPanel.add(rbPlace);
		
		rbGroup = new ButtonGroup();
		rbGroup.add(rbMovie);
		rbGroup.add(rbId);
		rbGroup.add(rbPlace);
		
		btnIns = new JButton("입력");
		btnIns.setBounds(440, 30, 60, 40);
		bottomPanel.add(btnIns);

		btnUpd = new JButton("수정");
		btnUpd.setBounds(510, 30, 60, 40);
		bottomPanel.add(btnUpd);

		btnDel = new JButton("삭제");
		btnDel.setBounds(580, 30, 60, 40);
		bottomPanel.add(btnDel);

		btnLogout = new JButton("Logout");
		btnLogout.setBounds(650, 30, 80, 40);
		bottomPanel.add(btnLogout);
		
		changeVisible(0);
		
		Utils util = new Utils();
		
		userTable = new JTable(setUserTable(""));
		
		movieTable = new JTable(setMovieTable(null));
		util.hiddenTableColumn(movieTable, 0);

		screenTable = new JTable(setScreenTable(null));
		util.hiddenTableColumn(screenTable, 0);
		
		placeTable = new JTable(setPlaceTable(null));
		util.hiddenTableColumn(placeTable, 0);
		
		theaterTable = new JTable(setTheaterTable(null));
		util.hiddenTableColumn(theaterTable, 0);

		seatTable = new JTable(setSeatTable(null));
		util.hiddenTableColumn(seatTable, 0);

		discountTable = new JTable(setDiscountTable(null));
		util.hiddenTableColumn(discountTable, 0);
		
		reserveTable =  new JTable(setReserveTable(null));
		util.hiddenTableColumn(reserveTable, 0);
		
		salesTable = new JTable(setSalesTable(null));
		
		tab = new JTabbedPane();
		userPanel = new JScrollPane(userTable);
		moviePanel = new JScrollPane(movieTable);
		screenPanel = new JScrollPane(screenTable);
		placePanel = new JScrollPane(placeTable);
		theaterPanel = new JScrollPane(theaterTable);
		seatPanel = new JScrollPane(seatTable);
		discountPanel = new JScrollPane(discountTable);
		reservePanel = new JScrollPane(reserveTable);
		salesPanel = new JScrollPane(salesTable);
		
		tab.add("회원정보관리", userPanel);
		tab.add("영화정보관리", moviePanel);
		tab.add("상영관리", screenPanel);
		tab.add("지점관리", placePanel);
		tab.add("상영관관리", theaterPanel);
		tab.add("좌석관리", seatPanel);
		tab.add("할인관리", discountPanel);
		tab.add("예매내역관리", reservePanel);
		tab.add("매출관리", salesPanel);

		for(int i=0; i<9; i++) {
			tab.setBackgroundAt(i, Color.WHITE);
		}
		
		Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
		insets.set(1, 0, 0, 0);
		UIManager.put("TabbedPane.contentBorderInsets", insets);
		
		tab.setBounds(0, 0, 771, 410);
		backgroundPanel.add(tab);
	}
	
	private void changeVisible(int selectedIndex) {
		if(selectedIndex == 0) {
			rbMovie.setVisible(false);
			rbPlace.setVisible(false);
			rbId.setVisible(false);
			btnIns.setVisible(false);
			btnUpd.setVisible(false);
			btnDel.setVisible(true);
		} else if(selectedIndex == 7) {
			rbMovie.setVisible(true);
			rbPlace.setVisible(true);
			rbId.setVisible(true);
			btnIns.setVisible(false);
			btnUpd.setVisible(false);
			btnDel.setVisible(true);
		} else if(selectedIndex == 8) {
			rbMovie.setVisible(true);
			rbPlace.setVisible(true);
			rbId.setVisible(false);
			btnAll.setVisible(false);
			btnIns.setVisible(false);
			btnUpd.setVisible(false);
			btnDel.setVisible(false);
		} else {
			rbMovie.setVisible(false);
			rbPlace.setVisible(false);
			rbId.setVisible(false);
			btnIns.setVisible(true);
			btnUpd.setVisible(true);
			btnDel.setVisible(true);
		}
	}
	
	public DefaultTableModel setUserTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("user");

		DefaultTableModel userModel = new DefaultTableModel(colName, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		UserDao userDao = UserDao.getInstance();
		Vector<Users> users;

		if (keyword == null) {
			users = userDao.selectAll("");
		} else {
			users = userDao.selectAll(keyword);
		}

		for (Users u : users) {
			Vector<Object> row = new Vector<>();
			row.addElement(u.getUserId());
			row.addElement(u.getBirthDate());
			row.addElement(u.getPhone());
			row.addElement(u.getDelFg());
			userModel.addRow(row);
		}

		return userModel;
	}
	
	public DefaultTableModel setMovieTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("movie");

		DefaultTableModel movieModel = new DefaultTableModel(colName, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		MovieDao movieDao = MovieDao.getInstance();
		Vector<Movies> movies;

		if (keyword == null) {
			movies = movieDao.selectAll();
		} else {
			movies = movieDao.selectKeyword(keyword);
		}

		for (Movies s : movies) {
			Vector<Object> row = new Vector<>();
			row.addElement(s.getId());
			row.addElement(s.getTitle());
			row.addElement(s.getPrice());
			row.addElement(s.getAge());
			row.addElement(s.getRunningTime());
			movieModel.addRow(row);
		}

		return movieModel;
	}
	
	public DefaultTableModel setScreenTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("screen");
		
		DefaultTableModel screenModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		ScreenDao screenDao = ScreenDao.getInstance();
		Vector<Movies> screens;
		
		if(keyword == null) {
			screens = screenDao.selectAll();
		} else {
			screens = screenDao.selectKeyword(keyword);
		}
		
		for (Movies s : screens) {
			Vector<Object> row = new Vector<>();
			row.addElement(s.getScreenId());
			row.addElement(s.getTitle());
			row.addElement(s.getPlaceName());
			row.addElement(s.getTheaterName());
			row.addElement(s.getStartDate());
			row.addElement(s.getEndDate());
			row.addElement(s.getStartTime());
			screenModel.addRow(row);
		}
		
		return screenModel;
	}
	
	public DefaultTableModel setPlaceTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("place");

		DefaultTableModel placeModel = new DefaultTableModel(colName, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		PlaceDao placeDao = PlaceDao.getInstance();
		Vector<Places> places;

		if (keyword == null) {
			places = placeDao.selectAll();
		} else {
			places = placeDao.selectKeyword(keyword);
		}

		for (Places p : places) {
			Vector<Object> row = new Vector<>();
			row.addElement(p.getId());
			row.addElement(p.getName());
			row.addElement(p.getAddr());
			placeModel.addRow(row);
		}

		return placeModel;
	}
	
	public DefaultTableModel setTheaterTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("theater");
		
		DefaultTableModel theaterModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		TheaterDao theaterDao = TheaterDao.getInstance();
		Vector<Theaters> theaters;
		
		if(keyword == null) {
			theaters = theaterDao.selectAll();
		} else {
			theaters = theaterDao.selectKeyword(keyword);
		}
		
		for (Theaters t : theaters) {
			Vector<Object> row = new Vector<>();
			row.addElement(t.getId());
			row.addElement(t.getName());
			row.addElement(t.getPlaceName());
			row.addElement(t.getSeatType());
			theaterModel.addRow(row);
		}
		
		return theaterModel;
	}
	
	public DefaultTableModel setSeatTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("seat");
		
		DefaultTableModel seatModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		SeatDao seatDao = SeatDao.getInstance();
		Vector<Seats> seats;
		
		if(keyword == null) {
			seats = seatDao.selectAll();
		} else {
			seats = seatDao.selectKeyword(keyword);
		}
		
		for (Seats s : seats) {
			Vector<Object> row = new Vector<>();
			row.addElement(s.getId());
			row.addElement(s.getType());
			row.addElement(s.getRow());
			row.addElement(s.getCol());
			seatModel.addRow(row);
		}
		
		return seatModel;
	}
	
	public DefaultTableModel setDiscountTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("discount");
		
		DefaultTableModel discountModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		DiscountDao discountDao = DiscountDao.getInstance();
		Vector<Discounts> discounts;
		
		if(keyword == null) {
			discounts = discountDao.selectAll();
		} else {
			discounts = discountDao.selectKeyword(keyword);
		}
		
		for (Discounts d : discounts) {
			Vector<Object> row = new Vector<>();
			row.addElement(d.getId());
			row.addElement(d.getName());
			row.addElement(d.getVal());
			row.addElement(d.getUnit());
			discountModel.addRow(row);
		}
		
		return discountModel;
	}
	
	public DefaultTableModel setReserveTable(String keyword) {
		Vector<String> colName = util.getTableTitleColum("reserve");

		DefaultTableModel reserveModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		ReserveDao dao = ReserveDao.getInstance();
		Vector<Reserves> reserves;

		if (rbMovie.isSelected() == true) {
			if (keyword == null) {
				reserves = dao.selectMovieAll();
			} else {
				reserves = dao.selectMovie(keyword);
			}

			if (reserves != null) {
				for (Reserves r : reserves) {
					Vector<Object> row = new Vector<>();
					row.addElement(r.getId());
					row.addElement(r.getUserId());
					row.addElement(r.getMovieTitle());
					row.addElement(r.getPlaceName());
					row.addElement(r.getTheaterName());
					row.addElement(r.getReserveDate());
					row.addElement(r.getReserveTime());
					row.addElement(r.getReserveCnt());
					row.addElement(r.getPrice());
					row.addElement(r.getDelFg());
					reserveModel.addRow(row);
				}
			}
		} else if (rbPlace.isSelected() == true) {
			if (keyword == null) {
				reserves = dao.selectPlaceAll();
			} else {
				reserves = dao.selectPlace(keyword);
			}

			if (reserves != null) {
				for (Reserves r : reserves) {
					Vector<Object> row = new Vector<>();
					row.addElement(r.getId());
					row.addElement(r.getUserId());
					row.addElement(r.getMovieTitle());
					row.addElement(r.getPlaceName());
					row.addElement(r.getTheaterName());
					row.addElement(r.getReserveDate());
					row.addElement(r.getReserveTime());
					row.addElement(r.getReserveCnt());
					row.addElement(r.getPrice());
					row.addElement(r.getDelFg());
					reserveModel.addRow(row);
				}
			}
		} else if (rbId.isSelected() == true) {
			if (keyword == null) {
				reserves = dao.selectIdAll();
			} else {
				reserves = dao.selectId(keyword);
			}

			if (reserves != null) {
				for (Reserves r : reserves) {
					Vector<Object> row = new Vector<>();
					row.addElement(r.getId());
					row.addElement(r.getUserId());
					row.addElement(r.getMovieTitle());
					row.addElement(r.getPlaceName());
					row.addElement(r.getTheaterName());
					row.addElement(r.getReserveDate());
					row.addElement(r.getReserveTime());
					row.addElement(r.getReserveCnt());
					row.addElement(r.getPrice());
					row.addElement(r.getDelFg());
					reserveModel.addRow(row);
				}
			}
		}
		return reserveModel;
	}
	
	public DefaultTableModel setSalesTable(String keyword) {
		Vector<String> colName; 
		
		if(rbMovie.isSelected() == true) {
			colName = util.getTableTitleColum("salesMovie");
		} else {
			colName = util.getTableTitleColum("salesPlace");
		}

		DefaultTableModel salesModel = new DefaultTableModel(colName, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		SalesDao dao = SalesDao.getInstance();
		Vector<Sales> sales;
		
		if (rbMovie.isSelected() == true) {
			if (keyword == null) {
				sales = dao.selectMovieAll();
			} else {
				sales = dao.selectMovie(keyword);
			}

			if (sales != null) {
				for (Sales s : sales) {
					Vector<Object> row = new Vector<>();
					row.addElement(s.getTitle());
					row.addElement(s.getPrice());
					salesModel.addRow(row);
				}
			}
		} else if (rbPlace.isSelected() == true) {
			if (keyword == null) {
				sales = dao.selectPlaceAll();
			} else {
				sales = dao.selectPlace(keyword);
			}

			if (sales != null) {
				for (Sales s : sales) {
					Vector<Object> row = new Vector<>();
					row.addElement(s.getName());
					row.addElement(s.getPrice());
					salesModel.addRow(row);
				}
			}
		}

		return salesModel;
	}

}