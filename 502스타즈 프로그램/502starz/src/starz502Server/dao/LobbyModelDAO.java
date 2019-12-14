package starz502Server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import starz502Server.GameServer.DataExportToAllClient;
import starz502Server.models.LobbyModel;
import starz502Server.models.LobbyModelUser;

public class LobbyModelDAO {
	
	public LobbyModelDAO() {
	}

	private static Connection conn; // DB 연결 객체
	private static PreparedStatement pstmt,pstmt2; // Query 작성 객체
	private static ResultSet rs; // Query 결과 커서

	public Vector<LobbyModelUser> getLobbyData(LobbyModel lobbyModel,DataExportToAllClient dataExportToAllClient) {
		
		//1. DB 연결
		conn = DBConnection.getConnection();
		Vector<LobbyModelUser> lobbyModelUserList = new Vector<LobbyModelUser>();
		try {			
			//2. Query 작성
			readyCheck(lobbyModel.getLobbyModelUser().get(0).getStz_ready(),lobbyModel.getLobbyModelUser().get(0).getStz_username());
			
			String query = "select stz_username, stz_rating, stz_logstate, stz_level , stz_ready from stz_user order by stz_rating desc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			int isReadyCount = 0;
			Vector<String> readyUsernameList = new Vector<String>();
			while(rs.next()) {
				LobbyModelUser	lobbyModelUser = new LobbyModelUser();
				lobbyModelUser.setStz_username(rs.getString("stz_username"));
				lobbyModelUser.setStz_rating(rs.getInt("stz_rating"));
				lobbyModelUser.setStz_logstate(rs.getString("stz_logstate"));
				lobbyModelUser.setStz_level(rs.getInt("stz_level"));				
				lobbyModelUser.setStz_ready(rs.getString("stz_ready"));
				if(rs.getString("stz_ready").equals("1")) {
					isReadyCount++;
					readyUsernameList.add(rs.getString("stz_username"));
					System.out.println("레디카운트  >> "+isReadyCount);
					if(isReadyCount==4) {						
						dataExportToAllClient.startDataToReadyClient(readyUsernameList);						
						isReadyCount=0;
						for (String username : readyUsernameList) {
							readyCheck(username);
						}
						//readyUsernameList.setSize(0);
						readyUsernameList.clear();
					}
				}
				lobbyModelUserList.add(lobbyModelUser);
			}
			
			return lobbyModelUserList; 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
		
	}
	
	public void readyCheck(String state,String username) {
		conn=DBConnection.getConnection();
		try {
			String query="UPDATE stz_user set STZ_ready=? where stz_username=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, state);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void readyCheck(String username) {
		conn=DBConnection.getConnection();
		try {
			String query="UPDATE stz_user set STZ_ready='0' where stz_username=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
