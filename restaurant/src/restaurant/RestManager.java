package restaurant;

import java.util.ArrayList;
import java.util.Scanner;

public class RestManager {

	private Scanner scan = new Scanner(System.in);
	private DaoMember mdao = new DaoMember();
	private DaoOrders odao = new DaoOrders();
	private String loginId = null;
	private int balance = 0;
	private String cafeNumb = null;
	private ArrayList<DtoMenu> MenuList = null;

	public String getLoginId() {
		return loginId;
	}

	public String getCafeNumb() {
		return cafeNumb;
	}

	public void setCafeNumb(String cafeNumb) {
		this.cafeNumb = cafeNumb;
	}

	public int showMenu() {
		if (loginId == null) { // 로그인 안되었을 때
			System.out.println("\n=============================");
			System.out.println(" [1]회원가입 | [2]로그인 | [0]종료");
			System.out.println("===============================");
		} else { // 로그인 되었을 때 실행
			if (cafeNumb == null) { // 메뉴 안봤을 때
				System.out.println("\n[로그인아이디 : " + loginId + " | 잔액 : " + balance + "원]");
				System.out.println("======================================");
				System.out.println(" [1]가게보기 | [2]주문 관리 | [3]충전하기 | [4]로그아웃");
				System.out.println("======================================");
			} else { // 메뉴 보기
				System.out.println("\n=================================");
				System.out.println("[1]주문하기 | [2] 리뷰보기  | [3] 나가기");
				System.out.println("=================================");
			}
		}

		System.out.print("메뉴선택>>");
		return scan.nextInt();
	}

	// 회원가입 기능
	public void createAccount() {
		// Members 테이블에 INSERT
		System.out.println("[1]회원가입");
		System.out.print("아이디입력>>");
		String mid = scan.next();
		System.out.print("비밀번호 입력>>");
		String mpw = scan.next();
		System.out.print("이름 입력(5글자 제한)>>");
		String mname = scan.next();
		System.out.print("입금할 금액>>");
		int mbalance = scan.nextInt();
		DtoMember member = new DtoMember();
		member.setMid(mid);
		member.setMpw(mpw);
		member.setMname(mname);
		member.setMbalance(mbalance);
		
		int insertResult = mdao.insertMemberJoin(member);
		if (insertResult > 0) {
			System.out.println("회원가입 되었습니다.");
		} else {
			System.out.println("회원가입에 실패하였습니다.");
		}

	}

	// 로그인 기능
	public void memberLogin() {
		System.out.println("[2]로그인");
		System.out.print("로그인할 아이디>>");
		String inputId = scan.next();
		System.out.print("로그인할 비밀번호>>");
		String inputpw = scan.next();
		DtoMember loginMember = mdao.selectMemberLogin(inputId, inputpw); // 회원정보 찾는 select문 기능

		if (loginMember != null) {
			loginId = loginMember.getMid();
			balance = loginMember.getMbalance();
			System.out.println("로그인 되었습니다.");
		} else {
			System.out.println("아이디/비밀번호가 일치하지 않습니다.");
		}
	}

	public void showRest() {
		System.out.println("[1] 가게보기\n");
		ArrayList<DtoCafe> cafeList = odao.selectShowRest();
		for (int i = 0; i < cafeList.size(); i++) {
			System.out.println("[" + (i + 1) + "]" + cafeList.get(i).getCafeName());
		}
		System.out.print("\n원하는 가게 번호 입력([0]번 입력 시 초기 화면 )>>");
		int cafeIdx = scan.nextInt() - 1;
		if (cafeIdx == -1) {
			System.out.print("[0]종료");
		} else if (cafeIdx + 1 > cafeList.size() || cafeIdx < -1) {
			System.out.println("선택한 가게가 없습니다.");
		} else {
			cafeNumb = cafeList.get(cafeIdx).getCafeNum();
			MenuList = odao.selectShowMenu(cafeNumb);
			if (1 > MenuList.size()) {
				System.out.println("메뉴가 없습니다.");
				setCafeNumb(null);
			} else {
				System.out.println();
				showMenuList();
			}
		}
	}
	//가게에 들어가서 메뉴 보기
	public void showMenuList() {
		for (int i = 0; i < MenuList.size(); i++) {
			System.out.println("[" + (i + 1) + "]" + MenuList.get(i).toString());
		}
	}

	// 주문관리
	public void myOrderList() {
		System.out.println("[2]주문 관리");
		ArrayList<DtoOrder> myOdList = odao.selectMyOrderList(loginId);
		if(myOdList.size() > 0) { // 주문 내역이 있으면
			for (int i = 0; i < myOdList.size(); i++) {
				System.out.print("\n[주문번호] " + myOdList.get(i).getOdNum());
				System.out.print(" [주문자ID] " + myOdList.get(i).getOdMid());
				System.out.print(" [주문상품] " + myOdList.get(i).getOdMenuName());
				System.out.print(" [주문수량] " + myOdList.get(i).getOdQty() + "개");
				System.out.print(" [주문금액] " + myOdList.get(i).getOdtotalPrice() + "원");
				System.out.print(" [주문일] " + myOdList.get(i).getOdDate());
				System.out.print("\n----------------------------------------");
			}
			System.out.println();
			System.out.println("===================================");
			System.out.println("[1] 주문취소 | [2] 리뷰작성 | [0] 나가기");
			System.out.println("===================================");
			System.out.print("메뉴 선택>>");
			switch (scan.nextInt()) {
			case 1:
				deleteOrder(myOdList);
				break;
			case 2:
				insertReview(myOdList);
				break;
			case 0:
				System.out.println("[0]나가기");
			}
		} else {
			System.out.println("주문 내역이 없습니다.");
		}
	}
	
	// 리뷰 작성
	public void insertReview(ArrayList<DtoOrder> myOdList) {
		DtoOrder order = searchOrderNum("리뷰 작성", myOdList);
		if(order != null) {
			int validOdnum = order.getOdNum();
			if(validOdnum != -1) {
				if (!odao.selectReviewcount(validOdnum)) {
					scan.nextLine();
					System.out.print("리뷰 내용 (최대 50글자) >>");
					String comment = scan.nextLine();
					int insertResult = odao.insertReview(validOdnum, comment);
					if (insertResult > 0) {
						System.out.println("리뷰가 작성되었습니다.");
					} else {
						System.out.println("리뷰작성 실패");
					}
				} else {
					System.out.println("이미 리뷰를 작성하였습니다.");
				}
			}
		}
	}
	// 주문 & 리뷰 삭제
	public void deleteOrder(ArrayList<DtoOrder> myOdList) {
		DtoOrder order = searchOrderNum("주문 취소", myOdList);
		if(order != null) {
			int validOdnum = order.getOdNum();
			if (odao.selectReviewcount(validOdnum)) {
				int deleteResult2 = odao.deleteReview(validOdnum);
				if (deleteResult2 > 0) {
					System.out.println("작성된 리뷰가 삭제되었습니다.");
				}
			}
			int deleteResult = odao.deleteOrder(validOdnum, loginId);
			if (deleteResult > 0) {
				int updatResult = odao.UpdateBalance(-order.getOdtotalPrice(), loginId);
				if (updatResult > 0) {
					System.out.println("주문이 취소되었습니다.");
					balance += order.getOdtotalPrice();
				}
			} else {
				System.out.println("주문취소에 실패했습니다.");
			}
		}
	}
	
	public void memberLogout() {
		System.out.println("[3]로그아웃");
		System.out.println("로그아웃 되었습니다.");
		loginId = null;
	}

	// 주문 - 리뷰 작성 가능
	// 상품 주문 기능 메소드 - ORDERLIST테이블 INSERT
	public void newOrder() {
		showMenuList();
		System.out.print("\n메뉴번호 선택>>");
		int selectFood = scan.nextInt() - 1;
		if (selectFood >= -1 && selectFood <= MenuList.size() - 1) {
			DtoMenu menu = MenuList.get(selectFood);
			int price = menu.getmPrice();
			System.out.print("[" + menu.getmName() + "] ");
			System.out.println("[" + price + "원] 선택");
			System.out.print("주문수량>>");
			int odqty = scan.nextInt();
			int maxOdnum = odao.selectMaxOdnum("ORDERS", "OD_NUM");
			int odnum = maxOdnum + 1;
			String odcode = menu.getmCode();
			int totPrice = price * odqty;
			if (totPrice > balance) { // 주문 금액보다 잔액 클때
				System.out.println("주문하려는 금액이 현재 잔액보다 큽니다.");
			} else {
				int insertResult = odao.insertOrder(odnum, loginId, odcode, odqty);
				int updateResult = odao.UpdateBalance(totPrice, loginId);
				if (insertResult > 0) { // 주문에 insert됐을 때
					if (updateResult > 0) { // 금액이 차감 됐을 때
						balance -= totPrice;
						System.out.println("주문되었습니다.");
					}
				} else {
					System.out.println("주문처리에 실패하였습니다.");
				}
			}
		}
	}

	// 리뷰 보기 메소드
	public void showReview() {
		System.out.println("리뷰 보기");
		ArrayList<DtoReview> reviewList = odao.selectShowReview(cafeNumb);
		if (reviewList.size() > 0) {
			for (int i = 0; i < reviewList.size(); i++) {
				System.out.println("[작성자] : " + reviewList.get(i).getReWriter());
				System.out.println("[메뉴명] : " + reviewList.get(i).getReMenu());
				System.out.println("[내용] : " + reviewList.get(i).getReContent());
				System.out.println("[작성일] : " + reviewList.get(i).getReDate());
				System.out.println("---------------------------------------------");
			}
		} else {
			System.out.println("이 가게는 리뷰가 없습니다.");
		}
	}

	public void deposit() {
		System.out.print("입금할 금액 >>");
		int dMoney = scan.nextInt();
		if (dMoney > 0) {
			int updateResult = odao.UpdateBalance(-dMoney, loginId);
			if (updateResult > 0) {
				System.out.println("입금이 완료되었습니다.");
				balance += dMoney;
			} else {
				System.out.println("입금에 실패했습니다.");
			}
		} else {
			System.out.println("0이상의 숫자를 입력해주세요.");
		}
	}
	
	public DtoOrder searchOrderNum(String word, ArrayList<DtoOrder> orderList) {
		DtoOrder order = null;
		String sentence = word + "할 주문번호 >>";
		System.out.print(sentence);
		int idx = -1;
		int odNum = scan.nextInt();
		for (int i = 0; i < orderList.size(); i++) { // 실제 주문번호 찾기
			if (odNum == orderList.get(i).getOdNum()) {
				idx = i;
				break;
			}
		}
		if (idx == -1) {
			System.out.println("주문 내역이 없습니다.");
		} else {
			order = orderList.get(idx);
		}
		return order;
	}
}
