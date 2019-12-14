package dbconn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MusicFile;

public class FileDB {
	private FileDB() {
	}

	public static FileDB instance = new FileDB();

	public static FileDB getInstance() {
		return instance;
	}

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public int newFile(String filename , String username) {
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement("insert into filename values(?,?,?,?,?,?,?,?)");

			pstmt.setString(1, username+"_"+filename);
			pstmt.setString(2, username);
			pstmt.setString(3, filename);
			pstmt.setInt(4, 3);
			pstmt.setInt(5, 8);
			pstmt.setInt(6, 140);
			pstmt.setString(7,
					"V0 I[Electric_Bass_Finger] G2w . . . . . . . G2w . . . . . . . G2w . . . . . . . G2w . . . . . . . C3w . . . . . . . C3w . . . . . . . D3w . . . . . . . D3w . . . . . . .  V1 I[Slap_Bass_1] G3q . Ri G3i A#3i G3i Ri G3i Ri G3i Ri G3i A#3i G3i F3q . G3q . Ri G3i A#3i G3i Ri G3i Ri G3i Ri G3i A#3i G3i F3q . C4q . Ri C4i E4i C4i Ri C4i Ri C4i Ri C4i E4i C4i A#3q . D4q . Ri D4i F#4i D4i Ri D4i Ri D4i Ri D3i Ri Ri Ri Ri");
			pstmt.setString(8,
					"O.O.O.O.O.O.O.O.O.O.O.O.O.O.O.O.@................................@................................@................................@................................");

			pstmt.executeQuery();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// 硫붾돱 諛� ���옣.
	public int save(MusicFile musicfile) {
	      conn = DBConnection.getConnection();
	      
	      try {
				pstmt = conn.prepareStatement("update filename set track = ?,madi = ?,bpm = ?,note = ?,drumnote = ? where userfilename = ?");
				System.out.println(musicfile.getTrack());
				System.out.println(musicfile.getUserfilename());
				pstmt.setInt(1,musicfile.getTrack() );//track
				pstmt.setInt(2,musicfile.getMadi() );//madi
				pstmt.setInt(3, musicfile.getBpm());//bpm
				pstmt.setString(4, musicfile.getNote());//note
				pstmt.setString(5, musicfile.getDrumnote());//drumnote
				pstmt.setString(6, musicfile.getUserfilename());
				
				
				pstmt.executeUpdate();
				return 1;
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
			
		}
	
	public int delete(String username,String selectedFilename) {
	      conn = DBConnection.getConnection();
	      
	      try {
				pstmt = conn.prepareStatement("delete from filename where userfilename=?");

				pstmt.setString(1, username+"_"+selectedFilename);
								
				pstmt.executeUpdate();
				return 1;
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
			
		}
	   

	// 硫붿씤 �봽�젅�엫 �쇊履� �뙆�씪紐⑸줉 肉뚮━湲�.
	public ArrayList<String> loadFile(String username) {
		ArrayList<String> filename = new ArrayList<>();

		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement("select*from filename where username = ?");

			pstmt.setString(1, username);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				filename.add(rs.getString("filename"));
			}
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// �뙆�씪紐� �겢由��떆 DB�뿉�꽌 遺덈윭�삤湲� Selcet
	public MusicFile loadTrack(String selectLow) {
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement("select*from filename where filename = ?");

			pstmt.setString(1, selectLow);

			rs = pstmt.executeQuery();

			MusicFile musicfile = new MusicFile();
			if (rs.next()) {
				musicfile.setTrack(rs.getInt("track"));// �듃�옓
				musicfile.setMadi(rs.getInt("madi"));// 留덈뵒
				musicfile.setBpm(rs.getInt("bpm"));// 鍮꾪뵾�뿞
				musicfile.setNote(rs.getString("note"));// �끂�듃
				musicfile.setDrumnote(rs.getString("drumnote"));// �뱶�읆�끂�듃
			}
			return musicfile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
