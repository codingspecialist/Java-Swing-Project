package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import models.Member;

public class MemberDao {
	private MemberDao() {
		
	}
	private static MemberDao instance = new MemberDao();
	public static MemberDao getInstance() {
		return instance;
	}
	
	private Connection conn; // DB 연결 객체
	private PreparedStatement pstmt;// Query 작성 개체
	private ResultSet rs; //Query 결과 데이터의 첫번째 커서
	
	//회원가입 메소드
	//성공 1,실패 -1
	public int save(Member member) {
		//1.DB연결
		conn = DBConnection.getConnection();
		
		//통신이 일어나면 무조건 try-catch
		try {
			//2. Query 작성
			pstmt = conn.prepareStatement("insert into member values(?,?,?)");
			//3. Query 완성
			pstmt.setString(1, member.getUsername());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			//4. Query 실행
			// (1)select -> ResultSet rs = pstmt.executeQuery();
			// (2)insert, update, delete = pstmt.executeUpdate();
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int findByUsernameAndPassword(String username, String password) {
		//1.DB연결
		conn = DBConnection.getConnection();
		try {
			//2.Query 작성
			pstmt = conn.prepareStatement("select username from member where username = ? and password = ?");
			//3.Query 완성
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			//4.Query 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//회원정보 보기
	public Vector<Member> findByAll(){
		//0. 컬렉션 생성
		Vector<Member> members = new Vector<Member>();
		
		//1. DB연결
		conn = DBConnection.getConnection();
		try {
			//2.Query 작성
			pstmt = conn.prepareStatement("select * from member");
			//3.Query 완성
					
			//4.Query 실행
			rs=pstmt.executeQuery();
			//5.결과를 가공
			while(rs.next()) {
				Member member = new Member();
				member.setUsername(rs.getString("username"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				
				members.add(member);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
}
