package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDao {
	
	private ClientDao() {}
	private static ClientDao instance = new ClientDao();
	
	public static ClientDao getInstance() {
		return instance;
	}
	
	// DB 연결, Query 작성, Query 결과
	private Connection conn=null;
	private PreparedStatement pstmt =null;
	private ResultSet result=null;
	
	public Connection getConnection() {
		Connection conn=null;
		String url="jdbc:oracle:this:@localhost:1521:xe";
	    String uid="scott";
	    String upw = "1234";
	    String driver = "oracle.jdbc.driver.OracleDriver";
	    
	    try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, uid, upw);
			System.out.println("DB연결 완료");
		} catch (Exception e) {
				e.printStackTrace();
				System.out.println("DB연결 실패");
		}
	    
	    return conn;
	    
	}
	
	public void close(Connection conn, PreparedStatement pstmt, ResultSet result) {
		try {
			if(conn!=null) conn.close();
			if(pstmt!=null) pstmt.close();
			if(result!=null) result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn, PreparedStatement pstmt) {
		try {
			if(conn!=null) conn.close();
			if(pstmt!=null) pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
public int accessClient(ClientDto dto) {
	conn=getConnection();
	String query = "insert into clientInfo (seq, phone, nParty, receTime) values (?,?,?,?)";
	
	try {
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, dto.getSeq() );
		pstmt.setString(2, dto.getPhone() );
		pstmt.setInt(3, dto.getnParty() );
		pstmt.setTimestamp(4, dto.getReceTime());
		pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return 1;
				
	}



//update
//출력
//





	

}
