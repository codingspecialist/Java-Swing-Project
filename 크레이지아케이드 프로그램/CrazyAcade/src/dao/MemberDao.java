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
	
	private Connection conn; // DB ���� ��ü
	private PreparedStatement pstmt;// Query �ۼ� ��ü
	private ResultSet rs; //Query ��� �������� ù��° Ŀ��
	
	//ȸ������ �޼ҵ�
	//���� 1,���� -1
	public int save(Member member) {
		//1.DB����
		conn = DBConnection.getConnection();
		
		//����� �Ͼ�� ������ try-catch
		try {
			//2. Query �ۼ�
			pstmt = conn.prepareStatement("insert into member values(?,?,?)");
			//3. Query �ϼ�
			pstmt.setString(1, member.getUsername());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			//4. Query ����
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
		//1.DB����
		conn = DBConnection.getConnection();
		try {
			//2.Query �ۼ�
			pstmt = conn.prepareStatement("select username from member where username = ? and password = ?");
			//3.Query �ϼ�
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			//4.Query ����
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//ȸ������ ����
	public Vector<Member> findByAll(){
		//0. �÷��� ����
		Vector<Member> members = new Vector<Member>();
		
		//1. DB����
		conn = DBConnection.getConnection();
		try {
			//2.Query �ۼ�
			pstmt = conn.prepareStatement("select * from member");
			//3.Query �ϼ�
					
			//4.Query ����
			rs=pstmt.executeQuery();
			//5.����� ����
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
