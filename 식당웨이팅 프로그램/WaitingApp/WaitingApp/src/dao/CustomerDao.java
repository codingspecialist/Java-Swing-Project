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

   private Connection conn; // DB연결 객체
   private PreparedStatement pstmt; // Query 작성 개체
   private ResultSet rs; // Query 결과 데이터의 첫 번째 커서

   // DB에 입력
   public int insert(String phone, int people) {
      
      CustomerCustomer cs = new CustomerCustomer(phone, people);
      
      
      // 1.DB연결
      conn = DBConnection.getConnection();

      try {
         // 2. Query 작성
         pstmt = conn.prepareStatement("insert into ccs values(CCS_SEQUENCE.nextval, ?, ?, sysdate, default)");

         // 3. Query 완성
         pstmt.setString(1, cs.getPhone()); // ?의 순서
         pstmt.setInt(2, cs.getPeople());
//         pstmt.setString(3, cs.getState());

         // 4. Query 실행
         // (1) select > ResultSet rs = pstmt.executeQuery(); 사용
         // (2) insert, update, delete > pstmt.executeUpdate(); 사용
         pstmt.executeUpdate();
         return 1; // 정상
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1; // 실패
   }

   // 손님 상태 변화
   public int stateChange(int no, String state) {
      // 1.DB연결
      conn = DBConnection.getConnection();

      try {

         // 2. Query 작성
         pstmt = conn.prepareStatement("update ccs set state=? where no = ?");

         // 3. Query 완성
         if (state.equals("입장 대기") ) {
            pstmt.setString(1, "입장 완료"); // ?의 순서
            pstmt.setInt(2, no);

           
         } else if (state.equals("입장 완료") ) {
            pstmt.setString(1, "퇴장"); // ?의 순서
            pstmt.setInt(2, no);

           
         } else if (state.equals("퇴장") ) {
            pstmt.setString(1, "입장 대기"); // ?의 순서
            pstmt.setInt(2, no);

            
         }

         // 4. Query 실행
         pstmt.executeUpdate();

         return 1; // 성공
      } catch (Exception e) {
         e.printStackTrace();
      }

      return -1; // 실패
   }

   // 전체 명단 출력
   public Vector<CustomerCustomer> findByAll() {
      // 0. 컬렉션 생성
      Vector<CustomerCustomer> customers = new Vector<CustomerCustomer>();

      // 1. DB연결
      conn = DBConnection.getConnection();

      try {
         // 2. Query 작성
         pstmt = conn.prepareStatement("select * from ccs order by no asc");

         // 3. Query 완성
         // 이 부분은 생략된다. 쿼리문에 물음표가 없잖아.

         // 4. Query 실행
         rs = pstmt.executeQuery();

         // 5. 결과를 가공 rs커서가 0에 있다가
         while (rs.next()) { // 데이터가 있으면 true, 없으면 false rs커서가 1로 간다.
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
   
   
   
   // 현재 대기중인 팀 수 출력
   
   public int waitingTeam() {
      
      // 1.DB연결
      conn = DBConnection.getConnection();

      try {

         // 2. Query 작성
         pstmt = conn.prepareStatement("select count(*) from ccs where state = '입장 대기'");

         // 3. Query 완성
         
         // 4. Query 실행
         rs = pstmt.executeQuery();
//         System.out.println(rs);
         
         
         if(rs.next() == true) {
            int result = rs.getInt("count(*)");
            return result;
         }

//         return result; // 성공
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return -1;
   }

   
   //손님에게 메세지 보내기
   public int send(String phone) {

      String api_key = "NCS69J4BVBXRAHAM";
      String api_secret = "VDS89EFWKTTFAJPKLHJUUWGLEJQSZHFR";
      Message coolsms = new Message(api_key, api_secret);

      // 4 params(to, from, type, text) are mandatory. must be filled
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("to", phone);
      params.put("from", "01034371474");
      params.put("type", "SMS");
      params.put("text", phone+"님 입장해주시기 바랍니다.");
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
////      cs.setState("입장 대기");
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