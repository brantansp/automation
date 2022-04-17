package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	/**
	 * By using regex pattern return total count of a string in a given value example str001wes will return 6
	 * Usage: | totalCountOfString | content| value|
	 * @param content
	 * @param value
	 * @return
	 * 
	 */
	
	public static int totalCountOfString(String content, String value) {

		 int Count = 0;
		 Pattern pattern = Pattern.compile(value);
		 Matcher matcher = pattern.matcher(content);
		 
		 System.out.println(value);
		 
		 while (matcher.find()) {
			 System.out.println(matcher.toString());
 		      Count++;
		 }
		 
		return Count;
		
	}
	
	
	/**
	 * Usage: | extractNumber| content|
	 * Returns number from string
	 * @param content
	 * @return int
	 * 
	 */
	 
	public static String extractNumber(String content) {
		
		String digits = content.replaceAll("[^0-9]", "");
		return digits;
	
	}
	
	
	/** 
	 * Extract a particular content from a string
	 * Usage: | extractContent| content| pattern|
	 * @return content
	 * 
	 */
	
	public static String extractContent(String content,String pattern) {
		

		String result = "";
		Pattern pattern1 = Pattern.compile(pattern);
		Matcher matcher = pattern1.matcher(content);
		
		if (matcher.find()){
			result = matcher.group(1);
		}
		
		
		return result;
		
	}
	

}
