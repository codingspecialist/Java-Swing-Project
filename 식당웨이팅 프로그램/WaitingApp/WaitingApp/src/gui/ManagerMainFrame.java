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
	
	//���̺��� �Ķ���� 
	public static Vector<String> getCustomerName() {
		Vector<String> customerName = new Vector<String>();
		customerName.add("NO"); // ������
		customerName.add("PHONE"); // ������
		customerName.add("PEOPLE"); // ������
		customerName.add("TIME"); // ������
		customerName.add("STATE"); // ������

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
		setLocationRelativeTo(null);//�߾� ����

		// JTable ������ �����ϱ� (������, Į���̸�, ���̺��)
		// 1. Į���̸�
		Vector<String> customerName = getCustomerName();
		// 2. ������
		CustomerDao dao = CustomerDao.getInstance();
		Vector<CustomerCustomer> customers = dao.findByAll();
		// 3. ���̺�� Į���̸�, �� ����
		DefaultTableModel tableModel = new DefaultTableModel(customerName, 0);

		// 4. for�� ���鼭 �� �྿ ������ ���� �ֱ�
		
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
		
		
		
		//���̺� ���� �������� �� ���콺 �׼�
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
		label.setFont(new Font("���� ���", Font.BOLD, 19));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(5, 5, 815, 40);
		contentPane.add(label);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 45, 815, 326);
		contentPane.add(scrollPane);
		
		
		
		//���� ��ħ ��ư �׼�
		JButton btnNew = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		btnNew.setFont(new Font("���� ���", Font.BOLD, 19));
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
		
		//���� ���� ��ư �׼�
		JButton btnmessage = new JButton("\uBB38\uC790 \uC548\uB0B4");
		btnmessage.setFont(new Font("���� ���", Font.BOLD, 19));
		btnmessage.setBounds(340, 380, 150, 70);
		contentPane.add(btnmessage);

		btnmessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnmessage) {
					System.out.println(phone);
					dao.send(phone);
					JOptionPane.showMessageDialog(null, " ���ڸ� �߼��Ͽ����ϴ�.");
				} else {

				}

			}
		});
		
		//���� ��ȯ ��ư �׼� 
		JButton btnstate = new JButton("\uC0C1\uD0DC\uBCC0\uACBD");
		btnstate.setFont(new Font("���� ���", Font.BOLD, 19));
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
						System.out.println("���");

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