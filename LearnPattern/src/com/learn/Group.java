package com.learn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Group {
	public static void main(String args[]) {
		String text    =
		          "John writes about this, and John writes about that," +
		                  " and John writes about everything. "
		        ;

		String patternString1 = "(Jo.n)";

		Pattern pattern = Pattern.compile(patternString1);
		Matcher matcher = pattern.matcher(text);

		while(matcher.find()) {
		    System.out.println("found: " + matcher.group(1));
		}
	}
}
