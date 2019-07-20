package review.view;

import java.util.ArrayList;

import review.model.dto.Review;

public class EndView {

	public static void reviewListView(ArrayList<Review> reviewList) {
		System.out.println("ID\t옵션\t날짜\t\t별점\t댓글");
		for (Review review : reviewList) {
			System.out.println(review);
		}
	}

	public static void messageView(String message) {
		System.out.println(message);
	}

}
