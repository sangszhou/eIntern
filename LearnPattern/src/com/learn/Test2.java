package com.learn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//If you need to match a text against a regular expression pattern 
//more than one time, you need to create a Pattern instance using 
//the Pattern.compile() method. Here is an example of how to do that:

public class Test2 {
	public static void main(String args[]) {
		String text    =
		        "This is the text to be searched " +
		        "for occurrences of the http:// pattern.";

		String patternString = ".*http://.*";

		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(text);

		boolean matches = matcher.matches();

		System.out.println("matches = " + matches);
	}
}
