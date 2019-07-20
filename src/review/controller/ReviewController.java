package review.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;

import review.exception.NotExistException;
import review.model.dto.Review;
import review.service.ReviewService;
import review.view.EndView;
import review.view.FailView;

public class ReviewController {
	private static ReviewController instance = new ReviewController();
	private ReviewService service = ReviewService.getInstance();
	
	private ReviewController() {}

	public static ReviewController getInstance() {
		return instance;
	}
	// 
	public void bringReviewFromSite(String url) {
		try {
			service.doCrawling(url);
		} catch (NotExistException e) {
			FailView.failMessageView(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			FailView.failMessageView(e.getMessage());
			e.printStackTrace();
		}
	}
	// 모든 리뷰 검색
	public void reviewListView() {
		ArrayList<Review> reviewList = service.getAllReviews();
		if (reviewList.size() != 0) {
			EndView.reviewListView(reviewList);
		} else {
			EndView.messageView("-- 등록 된 리뷰가 없습니다 --");
		}
	}
	// ID별 리뷰 검색
	public void reviewViewById(String id) {
		ArrayList<Review> reviewList = service.getReviewById(id);
		if (reviewList.size() != 0) {
			EndView.messageView("-- ID가 " + id + "인 리뷰를 검색합니다 --");
			EndView.reviewListView(reviewList);
		} else {
			EndView.messageView("-- 해당 ID의 리뷰가 없습니다 --");
		}
	} 
	// 옵션별 리뷰 검색
	public void reviewViewByOption(String option) {
		ArrayList<Review> reviewList = service.getReviewByOption(option);
		if (reviewList.size() != 0) {
			EndView.messageView("-- 옵션이 " + option + "인 리뷰를 검색합니다 --");
			EndView.reviewListView(reviewList);
		} else {
			EndView.messageView("-- 해당 옵션의 리뷰가 없습니다 --");
		}
	} 
	// 날짜별 리뷰 검색
	public void reviewViewByDate(String date) {
		ArrayList<Review> reviewList = service.getReviewByDate(date);
		if (reviewList.size() != 0) {
			EndView.messageView("-- 날짜가 " + date + "인 리뷰를 검색합니다 --");
			EndView.reviewListView(reviewList);
		} else {
			EndView.messageView("-- 해당 날짜의 리뷰가 없습니다 --");
		}
	} 
	// 별점별 리뷰 검색
	public void reviewViewByStar(int star) {
		ArrayList<Review> reviewList = service.getReviewByStar(star);
		if (reviewList.size() != 0) {
			EndView.messageView("-- 별점이 " + star + "인 리뷰를 검색합니다 --");
			EndView.reviewListView(reviewList);
		} else if (star > 5) {
			EndView.messageView("-- 최대 별점은  5입니다 --");
		} else if (star < 1) {
			EndView.messageView("-- 최소 별점은  1입니다 --");
		} else {
			EndView.messageView("-- 해당 별점의 리뷰가 없습니다 --");
		}
	} 
	// 리뷰를 json 파일로 변환
	public void saveJSON() {
		JSONArray jsonArray;
		try {
			jsonArray = service.convertJSON();
		} catch (NotExistException e) {
			FailView.failMessageView(e.getMessage());
			e.printStackTrace();
			return;
		}
		try {
			service.saveJSON(jsonArray);
		} catch (IOException e) {
			FailView.failMessageView(e.getMessage());
			e.printStackTrace();
		}
		EndView.messageView("-- 정상적으로 변환 되었습니다 --");
	}
}