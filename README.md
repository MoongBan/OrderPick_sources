# _OrderPick_

## ✨ Description
### 첫 프로젝트로써 Eclipse를 사용한 JAVA기반 간단한 음식 주문 프로그램.
### 구상한 기능
##### - 회원가입 및 로그인 기능
##### - 리뷰가 포함된 가게 정보와 메뉴 출력
##### - 출력된 메뉴 주문 및 잔액 입출금
##### - 주문 완료 시 해당 주문번호의 리뷰 작성 및 삭제 기능

## 🔍 Process
> ### < 로그인 전 > 
### [1]회원가입 | [2] 로그인 | [3]종료 


> ### < 로그인 후 >
 ### [1]가게보기 | [2]주문 관리 | [3]충전하기 | [4]로그아웃
 ##### [1] :: DB에 저장되어 있는 음식점의 리스트를 출력
 ##### [2] :: [1] 주문취소 | [2] 리뷰작성 | [0] 나가기 메뉴 진입
 ##### [3] :: 주문 시 사용 가능한 돈 입금
 ##### [4] :: 로그아웃 하여 초기화면으로 돌아감
 
 >### < [1] 가게보기 선택한 경우  >
### [1]주문하기 | [2] 리뷰보기  | [3] 나가기
 ##### 가게의 음식 리스트 출력 후 위 메뉴 진입
 ##### [1] :: 메뉴번호, 수량 입력 받고 잔액에서 주문금액만큼 차감
 ##### [2] :: 해당 가게의 리뷰 출력
 ##### [3] :: 로그인 후의 메뉴로 돌아감
 
>### < [2]주문관리 선택한 경우 >
### [1] 주문취소 | [2] 리뷰작성 | [0] 나가기
 ##### [1] 현재 계정에서 주문한 메뉴를 취소함
 ##### [2] 주문내역 중 주문번호를 선택하여 리뷰 작성
 ##### [0] 로그인 후의 메뉴로 돌아감

> ### [3]충전하기 선택한 경우
 ##### 현재 개인계좌에 입금


--- 
## ✏️ERD
![OrderPick_ERD](https://github.com/MoongBan/OrderPick_sources/assets/122944951/e3c3905e-ddbe-4eeb-94b3-160574e7287c)



## 🤼‍♂️Author
🐺 Park MinSeo





