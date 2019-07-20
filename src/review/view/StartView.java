package review.view;

import review.controller.ReviewController;

public class StartView {
	public static void main(String[] args) {
		ReviewController controller = ReviewController.getInstance();
		System.out.println("== 받아온 리뷰 없이 JSON 저장 시도 ==");
		controller.saveJSON();
		System.out.println("== 잘못된 URL로 크롤링 시도 ==");
		controller.bringReviewFromSite("ERROR");
		System.out.println("== 정상 URL로 크롤링 시도 ==");
		controller.bringReviewFromSite("http://www.fashionplus.co.kr/mall/goods/goods.asp?goods_id=104469458");
		System.out.println("== 모든 리뷰 받아오기 시도 ==");
		controller.reviewListView();
		System.out.println("== 잘못된 ID 검색 시도 ==");
		controller.reviewViewById("ERROR");
		System.out.println("== 정상 ID 검색 시도 ==");
		controller.reviewViewById("khj****");
		System.out.println("== 잘못된 옵션 검색 시도 ==");
		controller.reviewViewByOption("ERROR");
		System.out.println("== 정상 옵션 검색 시도 ==");
		controller.reviewViewByOption("화이트 L");
		System.out.println("== 잘못된 날짜 검색 시도 ==");
		controller.reviewViewByDate("ERROR");
		System.out.println("== 정상 옵션 검색 시도 ==");
		controller.reviewViewByDate("2019/07/01");
		System.out.println("== 잘못된 별점 검색 (0) 시도 ==");
		controller.reviewViewByStar(0);
		System.out.println("== 잘못된 별점 검색 (7) 시도 ==");
		controller.reviewViewByStar(7);
		System.out.println("== 정상 별점 검색 (3) 시도 ==");
		controller.reviewViewByStar(3);
		System.out.println("== 현재 크롤링된 리뷰 JSON 저장 시도 ==");
		controller.saveJSON();
	}
}
