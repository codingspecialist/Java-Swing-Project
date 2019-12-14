package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;

import org.json.simple.JSONObject;

import models.CustomerCustomer;
import models.WaitingMember;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class CustomerDaoCustomer {
   private CustomerDaoCustomer() {

   }

   private static CustomerDaoCustomer instance = new CustomerDaoCustomer();

   public static CustomerDaoCustomer getInstance() {
      return instance;
   }

   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
  

   
   public int select() {
	   int rowcount = 0;
	   conn = DBConnectionCuntomer.getConnection();
	   
	   try {
		   pstmt = conn.prepareStatement("SELECT COUNT(CASE WHEN state='���� ���' THEN 1 END) count FROM ccs");
		   rs=pstmt.executeQuery();
		   rs.next();
		   rowcount = rs.getInt("count");
		   System.out.println(rowcount);

		   return rowcount;

		   
	} catch (Exception e) {
		e.printStackTrace();
	}
	   return -1;
   }
   
   
   
   
   
   // DB�� �Է�
   public int insert(String number, int people) {

      String phone2 = this.change(number); //���� ��ȣ�� 00011112222 �̷������� ���ƿ��� 000-1111-2222�� �ٲٴ� �Լ�
      CustomerCustomer cs = new CustomerCustomer(phone2, people);
//      Customer cs = new Customer(phone, people);
      
      conn = DBConnectionCuntomer.getConnection();

      try {
         
         pstmt = conn.prepareStatement("insert into ccs values(CCS_SEQUENCE.nextval, ?, ?, sysdate, default)");

         pstmt.setString(1, cs.getPhone());
         pstmt.setInt(2, cs.getPeople());      

         pstmt.executeUpdate();
         return 1; // ����
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1; // ����
   }

   // �մ� ���� ��ȭ
   public int stateChange(int no, String state) {
      
      conn = DBConnectionCuntomer.getConnection();

      try {

         
         pstmt = conn.prepareStatement("update ccs set state=? where no = ?");

         
         if (state == "���� ���") {
            pstmt.setString(1, "���� �Ϸ�"); // ?�� ����
            pstmt.setInt(2, no);

         } else if (state == "���� �Ϸ�") {
            pstmt.setString(1, "����"); // ?�� ����
            pstmt.setInt(2, no);

         } else if (state == "����") {
            pstmt.setString(1, "���� ���"); // ?�� ����
            pstmt.setInt(2, no);

         }

         
         pstmt.executeUpdate();

         return 1; // ����
      } catch (Exception e) {
         e.printStackTrace();
      }

      return -1; // ����
   }

   // ��ü ��� ���
   public Vector<CustomerCustomer> findByAll() {
      
      Vector<CustomerCustomer> customers = new Vector<CustomerCustomer>();

      
      conn = DBConnectionCuntomer.getConnection();

      try {
         
         pstmt = conn.prepareStatement("select * from ccs order by no asc");

         rs = pstmt.executeQuery();

         
         while (rs.next()) { // �����Ͱ� ������ true, ������ false rsĿ���� 1�� ����.
            CustomerCustomer customer = new CustomerCustomer();
            customer.setNo(rs.getInt("no"));
            customer.setPhone(rs.getString("phone"));
            customer.setPeople(rs.getInt("people"));
            customer.setTime(rs.getTimestamp("time"));
            customer.setState(rs.getString("state"));
            customers.add(customer);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      return customers;
   }

   // ���� ������� �� �� ���
   public int waitingTeam() {

      
      conn = DBConnectionCuntomer.getConnection();

      try {

         pstmt = conn.prepareStatement("select count(*) from ccs where state = '���� ���'");

         rs = pstmt.executeQuery();

         if (rs.next() == true) {
            int result = rs.getInt("count(*)");
            return result;
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      return -1;
   }

   // �մԿ��� �޼��� ������
   public int send(String phone) {

      String api_key = "NCS69J4BVBXRAHAM";
      String api_secret = "VDS89EFWKTTFAJPKLHJUUWGLEJQSZHFR";
      Message coolsms = new Message(api_key, api_secret);

      // 4 params(to, from, type, text) are mandatory. must be filled
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("to", phone);
      params.put("from", "01034371474");
      params.put("type", "SMS");
      params.put("text", phone + "�� �������ֽñ� �ٶ��ϴ�.");
      params.put("app_version", "test app 1.2"); // application name and version

      try {
         JSONObject obj = (JSONObject) coolsms.send(params);
         System.out.println(obj.toString());
         return 1;
      } catch (CoolsmsException e) {
         return -1;
      }

   }

   //phone���Ŀ� �°� �ٲٱ�
   public String change(String number) {

      String num1 = number.substring(0, 3);

      String num2 = number.substring(3, 7);

      String num3 = number.substring(7, 11);

      return num1 + "-" + num2 + "-" + num3;
   }

//   public static void main(String[] args) {
////      CustomerDao dao = CustomerDao.getInstance();
//
////      Customer cs = new Customer();
////      cs.setPhone("010-3333-4444");
////      cs.setPeople(3);
////      cs.setState("���� ���");
////      
////      dao.insert("010-2222-1111", 4);
//
////      dao.stateChange(1, "���� ���");
////      dao.stateChange(1, "���� �Ϸ�");
////      int a = dao.waitingTeam();
////      System.out.println(a);
////      Vector<Customer> cs = dao.findByAll();
////      for (int i = 0; i < cs.size(); i++) {
////         
////         System.out.println(cs.get(i).getNo());
////      }
//
////      dao.send("010-3437-1474");
//   }

}