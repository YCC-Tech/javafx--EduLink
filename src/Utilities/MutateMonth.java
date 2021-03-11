package Utilities;

import java.util.HashMap;
import java.util.Map;

public class MutateMonth {
	
	private static Map<String, Integer> months = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
		{
			put("January", 1);
			put("February", 2);
			put("March", 3);
			put("April", 4);
			put("Map", 5);
			put("June", 6);
			put("July", 7);
			put("August", 8);
			put("September", 9);
			put("October", 10);
			put("November", 11);
			put("December", 12);
		}
	};
	
	private static Map<String, String> monthName = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("1", "January");
			put("2", "February");
			put("3", "March");
			put("4", "April");
			put("5", "May");
			put("6", "June");
			put("7", "July");
			put("8", "August");
			put("9", "September");
			put("10", "October");
			put("11", "November");
			put("12", "December");
		}
	};
					
	
	public static int getNumericMonth(String textualMonth) {
		return months.get(textualMonth);
	}
					
	public static String getStringMonth(String intMonth) {
		return monthName.get(intMonth);
	}

}
