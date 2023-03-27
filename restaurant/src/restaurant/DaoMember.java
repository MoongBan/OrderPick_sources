package restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoMember {
	public static Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con =
				DriverManager.getConnection("jdbc:oracle:thin:@//106.243.194.229:11223/xe", "JGM_DBA", "1111");
		return con;
	}

	//Members 테이블에 회원정보 INSERT
		public int insertMemberJoin(DtoMember member) {
			String sql = "INSERT INTO MEMBERS(MID, MPW, MNAME, MBAL) "
					+ "VALUES(?,?,?,?)";
			int insertResult = 0;
			// DB접속 >> DB 전송 >> SQL문 실행 >> 실행결과 리턴
			try {
				//1. DB접속
				Connection con = getConnection();
				//2. 접속된 DB에 쿼리문 전송 준비
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getMid());
				pstmt.setString(2, member.getMpw());
				pstmt.setString(3, member.getMname());
				pstmt.setInt(4, member.getMbalance());
				//3. 쿼리문 실행
				insertResult = pstmt.executeUpdate();
				pstmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return insertResult;
		}
		//
		//Members 테이블에 회원정보 SELECT 
		public DtoMember selectMemberLogin(String inputId, String inputpw) {
			String sql = "SELECT * FROM MEMBERS WHERE MID = ? AND MPW = ?";
			DtoMember member = null;
			try {
				//1. DB접속
				Connection con = getConnection();
				//2. 접속된 DB 쿼리문 전송 준비
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, inputId); 
				pstmt.setString(2, inputpw); 
				//3. 쿼리문 실행
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					member = new DtoMember(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4));
				}
				pstmt.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return member;
		}
}
