package review.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import review.exception.NotExistException;
import review.model.dto.Review;

public class ReviewCrawler {
	private static ReviewCrawler instance = new ReviewCrawler();
	private static ArrayList<Review> reviewList = new ArrayList<>();
	
	public ArrayList<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(ArrayList<Review> reviewList) {
		ReviewCrawler.reviewList = reviewList;
	}

	public static ReviewCrawler getInstance() {
		return instance;
	}

	public void getReviews(String url) throws NotExistException {
		try {
			System.out.println("Target : " + Jsoup.connect(url).get().title());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String commentUrl = commentUrl(url); // 코멘트 url로 변환
		if (commentUrl == null) {
			throw new NotExistException("xx 잘못된 url입니다. xx\n");
		}
		Document doc = null;
		Elements idAndDate;
		Elements option;
		Elements star;
		Elements comments;
		String iAndD;
		int w = 0;
		int reviewListSize = 0;
		while (true) {
			sleep(0.2, "page " + ++w + " ");
			try {
				doc = Jsoup.connect(commentUrl + w).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			idAndDate = doc.select("#review_list > dd > ul > li > p.member"); // id와 날짜
			if (idAndDate.size() == 0) {
				System.out.print("No more Reviews!\n");
				break;
			} else {
				sleep(0.2, ".");
			}
			option = doc.select("#review_list > dd > ul > li > p.option"); // 선택 옵션
			star = doc.select("#review_list > dd > ul > li > div.icon_star"); // 별 개수
			comments = doc.select("#review_list > dd > ul > li > div.contents > p"); // 코멘트
			sleep(0.2, ".");
			reviewListSize = reviewList.size();
			for (int i = 0; i < comments.size(); i++) {
				reviewList.add(new Review());
				iAndD = idAndDate.get(i).text();
				reviewList.get(i + reviewListSize).setId(iAndD.substring(0, 7));
				reviewList.get(i + reviewListSize).setOption(option.get(i).text().substring(5));
				reviewList.get(i + reviewListSize).setDate(iAndD.substring(10, 20));
				reviewList.get(i + reviewListSize).setStar(star.get(i).getElementsByClass("reviewbg star_on").size());
				reviewList.get(i + reviewListSize).setComment(comments.get(i).text());
			}
			sleep(0.2, ".");
			sleep(0.2, " OK\n");
		}
	}

	public static String commentUrl(String url) {
		// 정규표현식 사용하여 일반 url을 코멘트 url로 변환
		StringBuilder builder = new StringBuilder();
		Pattern idPattern = Pattern.compile("goods_id=([0-9]+)");
		Matcher idMatcher = idPattern.matcher(url);
		if (idMatcher.find()) {
			builder.append("http://www.fashionplus.co.kr/mall/include/comment_list.asp?block=0&goods_id_comment=");
			builder.append(idMatcher.group(1));
			builder.append("&GoTopage=");
			return builder.toString();
		} else {
			return null;
		}
	}

	public static void sleep(double second, String message) {
		try {
			Thread.sleep((long) (second * 1000));
			System.out.print(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}