package review.service;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;

import review.exception.NotExistException;
import review.model.ReviewCrawler;
import review.model.dto.Review;

public class ReviewService {
	private static ReviewService instance = new ReviewService();
	private ReviewCrawler reviewCrawler = ReviewCrawler.getInstance();
	
	private ReviewService() {}
	
	public static ReviewService getInstance() {
		return instance;
	}

	public void doCrawling(String url) throws NotExistException {
		reviewCrawler.getReviews(url);
	}

	public ArrayList<Review> getAllReviews() {
		return reviewCrawler.getReviewList();
	}
	// 옵션 따라서 리뷰 검색
	public ArrayList<Review> getReviewById(String id) {
		ArrayList<Review> reviewList = new ArrayList<>();
		for (Review review : getAllReviews()) {
			if (review != null && review.getId().equals(id)) {
				reviewList.add(review);
			}
		}
		return reviewList;
	}
	// 옵션 따라서 리뷰 검색
	public ArrayList<Review> getReviewByOption(String option) {
		ArrayList<Review> reviewList = new ArrayList<>();
		for (Review review : getAllReviews()) {
			if (review != null && review.getOption().equals(option)) {
				reviewList.add(review);
			}
		}
		return reviewList;
	}
	// 날짜 따라서 리뷰 검색
	public ArrayList<Review> getReviewByDate(String date) {
		ArrayList<Review> reviewList = new ArrayList<>();
		for (Review review : getAllReviews()) {
			if (review != null && review.getDate().equals(date)) {
				reviewList.add(review);
			}
		}
		return reviewList;
	}
	// 별 갯수 따라서 리뷰 검색
	public ArrayList<Review> getReviewByStar(int star) {
		ArrayList<Review> reviewList = new ArrayList<>();
		for (Review review : getAllReviews()) {
			if (review != null && review.getStar() == star) {
				reviewList.add(review);
			}
		}
		return reviewList;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray convertJSON() throws NotExistException{
		if (getAllReviews().size() == 0) {
			throw new NotExistException("xx JSON으로 변환할 리뷰가 없습니다 xx");
		}
		JSONArray jsonArray = new JSONArray();
		for (Review review : getAllReviews()) {
			jsonArray.add(review.toHashMap());
		}
		return jsonArray;
	}
	
	public void saveJSON(JSONArray jsonArray) throws IOException {
			String path = "json";
			File Folder = new File(path);
			if (!Folder.exists()) {
				try {
					Folder.mkdir();
					System.out.println("-- JSON 폴더가 생성 되었습니다 --");
				} catch (Exception e) {
//					e.getStackTrace()
				}
			}
			FileWriter file = new FileWriter(path + "/" + new SimpleDateFormat("yy-MM-dd HH_mm_ss").format(new Date()) + ".json");
			file.write(jsonArray.toJSONString());
			file.flush();
			file.close();
	}
}
