package starz502Server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;

import starz502Server.calculator.ResultCalculator;
import starz502Server.models.LobbyModel;
import starz502Server.models.ResultModel;

public class ResultModelDAO {
	public ResultModelDAO() {}

	
	private static Connection conn; //DB ���� ��ü
	private static PreparedStatement pstmt,pstmt2; //Query �ۼ� ��ü
	private static ResultSet rs,rs2; //Query ��� Ŀ��
	
	public ResultModel resultUserData(String username, int rank) {
		//1. DB ����
		conn = DBConnection.getConnection();
		try {			
			//2. Query �ۼ�
			String query = "select stz_rating, stz_exp, stz_wexp, stz_level from stz_user where stz_username=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,username);
			rs = pstmt.executeQuery();	
			
			String query2 = "update stz_user set stz_rating=?, stz_exp=?, stz_wexp=?, stz_level=? where stz_username=?";
			pstmt2=conn.prepareStatement(query2);
			
			
			
			if(rs.next()) {	
				ResultModel resultModel = new ResultModel();
				resultModel.setStz_username(username);
				resultModel.setStz_rating(rs.getInt("stz_rating"));
				resultModel.setStz_exp(rs.getInt("stz_exp"));
				resultModel.setStz_wexp(rs.getInt("stz_wexp"));
				resultModel.setStz_level(rs.getInt("stz_level"));
				resultModel.setRank(rank);
				ResultCalculator resultCalculator = new ResultCalculator();
				resultModel= resultCalculator.resultCalculator(resultModel,rank); //�ι�° �Ķ���� rank �ʿ�
				pstmt2.setInt(1, resultModel.getStz_rating());
				pstmt2.setInt(2, resultModel.getStz_exp());
				pstmt2.setInt(3, resultModel.getStz_wexp());
				pstmt2.setInt(4, resultModel.getStz_level());
				pstmt2.setString(5, username);
				pstmt2.executeUpdate();
				Gson gson = new Gson();
				System.out.println(gson.toJson(resultModel));
				return resultModel; //�긦 ������ ��.
			}else {
				System.out.println("0");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
		
	}
}

