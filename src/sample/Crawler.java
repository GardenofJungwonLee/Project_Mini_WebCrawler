package sample;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
    public static void main(String[] args) throws IOException {
    	String url = "http://www.fashionplus.co.kr/mall/include/comment_list.asp?block=0&goods_id_comment=101637296&GoTopage=1";
        Document doc = Jsoup.connect(url).get();
        Elements comments = doc.select("#review_list > dd > ul > li > div.contents > p");
        for(Element comment : comments) {
        	System.out.println(comment.text());
        }
    	String s = "http://www.fashionplus.co.kr/mall/goods/goods.asp?goods_id=98227358";   
    	Pattern idPattern = Pattern.compile("goods_id=([0-9]+)");
    	Matcher idMatcher = idPattern.matcher(s);
    	if(idMatcher.find()) {
    		System.out.println(idMatcher.group(1));
    	}
    }
}