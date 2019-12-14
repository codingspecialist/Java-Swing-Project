package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.CustomerDao;
import dao.CustomerDaoCustomer;
import models.CustomerCustomer;

public class ManagerMainFrame extends JFrame {
	CustomerDaoCustomer daoc = CustomerDaoCustomer.getInstance();
	private JPanel contentPane;
	private JTable table;
	private String phone, state;
	private int no;
	
	//테이블의 파라미터 
	public static Vector<String> getCustomerName() {
		Vector<String> customerName = new Vector<String>();
		customerName.add("NO"); // 시퀀스
		customerName.add("PHONE"); // 시퀀스
		customerName.add("PEOPLE"); // 시퀀스
		customerName.add("TIME"); // 시퀀스
		customerName.add("STATE"); // 시퀀스

		return customerName;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerMainFrame frame = new ManagerMainFrame();
					frame.setLocationRelativeTo(null);
					new CustomerMainFrame().setVisible(true);
            		frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public ManagerMainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//중앙 정렬

		// JTable 데이터 매핑하기 (데이터, 칼럼이름, 테이블모델)
		// 1. 칼럼이름
		Vector<String> customerName = getCustomerName();
		// 2. 데이터
		CustomerDao dao = CustomerDao.getInstance();
		Vector<CustomerCustomer> customers = dao.findByAll();
		// 3. 테이블모델 칼럼이름, 행 갯수
		DefaultTableModel tableModel = new DefaultTableModel(customerName, 0);

		// 4. for문 돌면서 한 행씩 데이터 집어 넣기
		
		for (int i = 0; i < customers.size(); i++) {
			Vector<Object> row = new Vector<>();
			row.addElement(customers.get(i).getNo());
			row.addElement(customers.get(i).getPhone());
			row.addElement(customers.get(i).getPeople());
			row.addElement(customers.get(i).getTime());
			row.addElement(customers.get(i).getState());
			tableModel.addRow(row);
		}
		
		
		table = new JTable(tableModel);
		
		
		
		//테이블 바을 선택했을 때 마우스 액션
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == table) {
					int row = table.getSelectedRow();
					phone = table.getValueAt(row, 1).toString();

				}
			}
		});
		contentPane.setLayout(null);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcm = table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);

		}

		JLabel label = new JLabel("\uD68C\uC6D0\uC815\uBCF4");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(5, 5, 815, 40);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 45, 815, 326);
		contentPane.add(scrollPane);
		
		
		
		//새로 고침 버튼 액션
		JButton btnNew = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		btnNew.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		btnNew.setBounds(100, 380, 150, 70);
		contentPane.add(btnNew);
		btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerMainFrame frame = new ManagerMainFrame();
				setVisible(false);
				new ManagerMainFrame().setVisible(true);
				
				

			}
		});
		
		//문자 전송 버튼 액션
		JButton btnmessage = new JButton("\uBB38\uC790 \uC548\uB0B4");
		btnmessage.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		btnmessage.setBounds(340, 380, 150, 70);
		contentPane.add(btnmessage);

		btnmessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnmessage) {
					System.out.println(phone);
					dao.send(phone);
					JOptionPane.showMessageDialog(null, " 문자를 발송하였습니다.");
				} else {

				}

			}
		});
		
		//상태 변환 버튼 액션 
		JButton btnstate = new JButton("\uC0C1\uD0DC\uBCC0\uACBD");
		btnstate.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		btnstate.setBounds(580, 380, 150, 70);
		contentPane.add(btnstate);
		btnstate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (e.getSource() == btnstate) {

						int row = table.getSelectedRow();
						int no = (int) (table.getValueAt(row, 0));
						String state = table.getValueAt(row, 4).toString();
						System.out.println(no + state);
						dao.stateChange(no, state);
						ManagerMainFrame frame = new ManagerMainFrame();
						setVisible(false);	
						new ManagerMainFrame().setVisible(true);
						CustomerMainFrame.wait.setText(daoc.select()+"");
						System.out.println("출력");

//						MainFrame MF = new MainFrame();

//						MF.wait.setText("");

					} 
					
					
				} catch (Exception e2) {
					StateException frame = new StateException();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
				

			}
		});

	}


}