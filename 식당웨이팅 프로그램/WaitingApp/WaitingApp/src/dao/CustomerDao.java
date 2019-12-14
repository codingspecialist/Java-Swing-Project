package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import org.json.simple.JSONObject;

import models.CustomerCustomer;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class CustomerDao {
   private CustomerDao() {

   }

   private static CustomerDao instance = new CustomerDao();

   public static CustomerDao getInstance() {
      return instance;
   }

   private Connection conn; // DB���� ��ü
   private PreparedStatement pstmt; // Query �ۼ� ��ü
   private ResultSet rs; // Query ��� �������� ù ��° Ŀ��

   // DB�� �Է�
   public int insert(String phone, int people) {
      
      CustomerCustomer cs = new CustomerCustomer(phone, people);
      
      
      // 1.DB����
      conn = DBConnection.getConnection();

      try {
         // 2. Query �ۼ�
         pstmt = conn.prepareStatement("insert into ccs values(CCS_SEQUENCE.nextval, ?, ?, sysdate, default)");

         // 3. Query �ϼ�
         pstmt.setString(1, cs.getPhone()); // ?�� ����
         pstmt.setInt(2, cs.getPeople());
//         pstmt.setString(3, cs.getState());

         // 4. Query ����
         // (1) select > ResultSet rs = pstmt.executeQuery(); ���
         // (2) insert, update, delete > pstmt.executeUpdate(); ���
         pstmt.executeUpdate();
         return 1; // ����
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1; // ����
   }

   // �մ� ���� ��ȭ
   public int stateChange(int no, String state) {
      // 1.DB����
      conn = DBConnection.getConnection();

      try {

         // 2. Query �ۼ�
         pstmt = conn.prepareStatement("update ccs set state=? where no = ?");

         // 3. Query �ϼ�
         if (state.equals("���� ���") ) {
            pstmt.setString(1, "���� �Ϸ�"); // ?�� ����
            pstmt.setInt(2, no);

           
         } else if (state.equals("���� �Ϸ�") ) {
            pstmt.setString(1, "����"); // ?�� ����
            pstmt.setInt(2, no);

           
         } else if (state.equals("����") ) {
            pstmt.setString(1, "���� ���"); // ?�� ����
            pstmt.setInt(2, no);

            
         }

         // 4. Query ����
         pstmt.executeUpdate();

         return 1; // ����
      } catch (Exception e) {
         e.printStackTrace();
      }

      return -1; // ����
   }

   // ��ü ��� ���
   public Vector<CustomerCustomer> findByAll() {
      // 0. �÷��� ����
      Vector<CustomerCustomer> customers = new Vector<CustomerCustomer>();

      // 1. DB����
      conn = DBConnection.getConnection();

      try {
         // 2. Query �ۼ�
         pstmt = conn.prepareStatement("select * from ccs order by no asc");

         // 3. Query �ϼ�
         // �� �κ��� �����ȴ�. �������� ����ǥ�� ���ݾ�.

         // 4. Query ����
         rs = pstmt.executeQuery();

         // 5. ����� ���� rsĿ���� 0�� �ִٰ�
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
      
      // 1.DB����
      conn = DBConnection.getConnection();

      try {

         // 2. Query �ۼ�
         pstmt = conn.prepareStatement("select count(*) from ccs where state = '���� ���'");

         // 3. Query �ϼ�
         
         // 4. Query ����
         rs = pstmt.executeQuery();
//         System.out.println(rs);
         
         
         if(rs.next() == true) {
            int result = rs.getInt("count(*)");
            return result;
         }

//         return result; // ����
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return -1;
   }

   
   //�մԿ��� �޼��� ������
   public int send(String phone) {

      String api_key = "NCS69J4BVBXRAHAM";
      String api_secret = "VDS89EFWKTTFAJPKLHJUUWGLEJQSZHFR";
      Message coolsms = new Message(api_key, api_secret);

      // 4 params(to, from, type, text) are mandatory. must be filled
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("to", phone);
      params.put("from", "01034371474");
      params.put("type", "SMS");
      params.put("text", phone+"�� �������ֽñ� �ٶ��ϴ�.");
      params.put("app_version", "test app 1.2"); // application name and version

      try {
         JSONObject obj = (JSONObject) coolsms.send(params);
//         System.out.println(obj.toString());
         
         return 1;
      } catch (CoolsmsException e) {
//         System.out.println(e.getMessage());
//         System.out.println(e.getCode());
         return -1;
      }

   }
   
//   public static void main(String[] args) {
//      CustomerDao dao = CustomerDao.getInstance();
//
////      Customer cs = new Customer();
////      cs.setPhone("010-3333-4444");
////      cs.setPeople(3);
////      cs.setState("���� ���");
////      
//      dao.insert("010-2222-1111", 4);
//
////      dao.stateChange(1, 2);
//      int a = dao.waitingTeam();
//      System.out.println(a);
////      Vector<Customer> cs = dao.findByAll();
////      for (int i = 0; i < cs.size(); i++) {
////         
////         System.out.println(cs.get(i).getNo());
////      }
//      
//      
//      
////      dao.send("010-3437-1474");
//   }
//
}