package restaurant;

public class RestMain {

	public static void main(String[] args) {
		RestManager rManager = new RestManager();
		boolean run = true;
		
		while(run) {
			int selectMenu = rManager.showMenu();
			if(rManager.getLoginId() == null) {
				switch(selectMenu) {
				case 1:
					// 회원가입
					rManager.createAccount();
					break;
				case 2:
					// 로그인
					rManager.memberLogin();
					break;
				case 0:
					run = false;
					break;
				}
			} else { // 로그인 되었을 때 실행
				
				if(rManager.getCafeNumb() == null) { // 매뉴 안봤을 때
					switch(selectMenu) {
					case 1:
						rManager.showRest(); // 카페 & 메뉴보기
						break;
					case 2: 
						rManager.myOrderList(); // 주문보기
						break;
					case 3: // 입금하기
						rManager.deposit();
						break;
					case 4:
						rManager.memberLogout();
						break;
					}
				} else { // 메뉴 봤을 때
					switch(selectMenu) {
					case 1: //주문하기
						rManager.newOrder();
						break;
					case 2: //리뷰 보기
						rManager.showReview(); 
						break;
					case 3: // 나가기
						rManager.setCafeNumb(null);
						break;
					}
				}
			}
		}
		System.out.println("종료");
	}
}
