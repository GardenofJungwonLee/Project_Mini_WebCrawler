package review.model.dto;

import java.util.HashMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Review {
	private String id;
	private String option;
	private String date;
	private int star;
	private String comment;
	
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("option", option);
		map.put("date", date);
		map.put("star", Integer.toString(star));
		map.put("comment", comment);
		return map;
	}
	
	@Override
	public String toString () {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append("\t");
		builder.append(option);
		builder.append("\t");
		builder.append(date);
		builder.append("\t");
		String stars = "";
		for(int i = 0; i < star; i++) {
			stars += "★";
		}
		if(star != 5) {
			for(int i = 0; i < 5 - star; i++) {
				stars += "☆";
			}
		}
		builder.append(stars);
		builder.append("\t");
		builder.append(comment);
		return builder.toString();
	}
}
