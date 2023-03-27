package restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoOrders {
	public static Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con =
				DriverManager.getConnection("jdbc:oracle:thin:@//106.243.194.229:11223/xe", "JGM_DBA", "1111");
		return con;
	}
	// 주문내역 조회 Dao
	   public ArrayList<DtoOrder> selectMyOrderList(String loginId) {
	      String sql = "SELECT O.OD_NUM, O.OD_DATE, M.MENU_NAME, O.OD_QTY, O.OD_QTY * M.MENU_PRICE, O.OD_MID "
	               + "FROM ORDERS O, MENU M "
	               + "WHERE OD_MID = ? AND O.OD_CODE = M.MENU_CODE "
	               + "ORDER BY OD_NUM ASC";
	      ArrayList<DtoOrder> myOrderList = new ArrayList<DtoOrder>();
	      try {
	         Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, loginId);
	         ResultSet rs = pstmt.executeQuery();
	         while(rs.next() ) {
	            DtoOrder odInfo = new DtoOrder();
	            odInfo.setOdNum(rs.getInt(1));
	            odInfo.setOdDate(rs.getString(2));
	            odInfo.setOdMenuName(rs.getString(3));
	            odInfo.setOdQty(rs.getInt(4));
	            odInfo.setOdtotalPrice(rs.getInt(5));
	            odInfo.setOdMid(rs.getString(6));
	            myOrderList.add(odInfo);
	         }
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return myOrderList;
	   }
	public ArrayList<DtoCafe> selectShowRest() {
		String sql = "SELECT * FROM CAFE";
		ArrayList<DtoCafe> cafeList = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				DtoCafe cafeInfo = new DtoCafe();
				cafeInfo.setCafeName(rs.getString(1));
				cafeInfo.setCafeNum(rs.getString(2));
				cafeList.add(cafeInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cafeList;
	}
	
	//메뉴 출력 기능 
	public ArrayList<DtoMenu> selectShowMenu(String cafeNumb) {
		ArrayList<DtoMenu> menuList = new ArrayList<>();
		String sql = "SELECT * FROM MENU WHERE MENU_CAFENUM = ?";
		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cafeNumb);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				DtoMenu menu = new DtoMenu();
				menu.setmCafeNum(rs.getString(1));
				menu.setmName(rs.getString(2));
				menu.setmPrice(rs.getInt(3));
				menu.setmCode(rs.getString(4));
				menuList.add(menu);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}
	
	// 주문번호 최대값 조회 - SELECT
	   public int selectMaxOdnum(String table, String column) {
		  System.out.println();
	
	      String sql = "SELECT NVL( MAX("+ column + "),0 ) FROM " + table;
	      int maxOdnum = 0;
	      try {
	         Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery(); // DB에서 명령어 실행한 결과가 rs에 그대로 담김
	         while(rs.next()) {
	            maxOdnum = rs.getInt(1);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return maxOdnum;
	   }
	   
	   // 주문정보 입력 - INSERT
	   public int insertOrder(int odnum, String odmid, String odcode, int odqty) {
	      String sql = "INSERT INTO ORDERS(OD_NUM, OD_DATE, OD_CODE, OD_QTY, OD_MID ) "
	               + "VALUES( ?, SYSDATE, ?, ?, ? )";
	      int insertResult = 0;
	      try {
	         Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         pstmt.setInt(1, odnum); 
	         pstmt.setString(2, odcode); 
	         pstmt.setInt(3, odqty); 
	         pstmt.setString(4, odmid);
	         insertResult = pstmt.executeUpdate();
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return insertResult;
	   }
	   
	   public int UpdateBalance(int price, String Mid) {
		   int updateResult = 0;
		   String sql = "UPDATE MEMBERS SET MBAL = MBAL - ? WHERE MID = ?";
		   try {
			Connection con = getConnection();
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, price);
			pstmt.setString(2, Mid);
			updateResult = pstmt.executeUpdate();
			if(updateResult == 1) {
				con.commit();
				con.setAutoCommit(true);
			} else {
				con.rollback();
			}
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		   return updateResult;
	   }
	   
	   public boolean selectReviewcount(int number) {
		   boolean result = false;
		   String sql = "SELECT RE_ODNUM FROM REVIEW WHERE RE_ODNUM = ?";
		   try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, number);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		   return result;
	   }
	   
	// 주문취소(ORDERS에서 삭제) 기능
		public int deleteOrder(int odNum, String loginId) {
			String sql = "DELETE FROM ORDERS WHERE OD_NUM = ? AND OD_MID = ? ";
			int deleteResult = 0;
			try {
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, odNum);
				pstmt.setString(2, loginId);
				deleteResult = pstmt.executeUpdate(); // DB에서 명령어 실행한 결과가 rs에 그대로 담김
			} catch (Exception e) {
				e.printStackTrace();
			}

			return deleteResult;
		}

		// 주문취소(리뷰 삭제) 기능
		public int deleteReview(int odNum) {
			String sql = "DELETE FROM REVIEW WHERE RE_ODNUM = ? ";
			int deleteResult2 = 0;
			try {
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, odNum);
				deleteResult2 = pstmt.executeUpdate(); // DB에서 명령어 실행한 결과가 rs에 그대로 담김
			} catch (Exception e) {
				e.printStackTrace();
			}
			return deleteResult2;
		}
	   
	   public int insertReview(int odNum, String comment) {
		   int insertResult = 0;
		   String sql = "INSERT INTO REVIEW "
				   + "VALUES(?, ?, ?)";
		   try {
			   Connection con = getConnection();
			   PreparedStatement pstmt = con.prepareStatement(sql);
			   pstmt.setInt(1, (selectMaxOdnum("REVIEW", "RE_NUM")+1));
			   pstmt.setInt(2, odNum);
			   pstmt.setString(3, comment);
			   insertResult = pstmt.executeUpdate();
		   } catch(Exception e) {
			   e.printStackTrace();
		   }
		return insertResult;
	   }
	   
	// 가게 리뷰 보기
		public ArrayList<DtoReview> selectShowReview(String cafeNum) {
			ArrayList<DtoReview> reviewList = new ArrayList<>();

			String sql = "SELECT O.OD_MID, M.MENU_NAME ,R.RE_CONTENT, O.OD_DATE "
					+ "FROM ORDERS O, REVIEW R, MENU M "
					+ "WHERE MENU_CAFENUM = ? AND R.RE_ODNUM = O.OD_NUM AND O.OD_CODE = M.MENU_CODE "
					+ "ORDER BY O.OD_DATE DESC";
			try {
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, cafeNum);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					DtoReview reviewDto = new DtoReview();
					reviewDto.setReWriter(rs.getString(1));
					reviewDto.setReMenu(rs.getString(2));
					reviewDto.setReContent(rs.getString(3));
					reviewDto.setReDate(rs.getString(4));
					reviewList.add(reviewDto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return reviewList;
		}
}
