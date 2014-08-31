package com.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadLine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String txt = 
//		"utionException: Failed to execute goal com.ebay.raptor.build:assembler-maven-plugin:1.1.4:deploy (default-cli) on project cosfsweb: Failed to deploy package for stack j2ee_war  Error code is ";
//		"[ERROR] Re-run Maven using the -X switch to enable full debug logging.\n" +
//		"[ERROR]\n" +
//		"[ERROR] For more information about the errors and possible solutions, please read the following articles:\n" + 
//		"[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/NoGoalSpecifiedException\n";

		//System.out.println(txt);
		
		String txt1 = "asd dfs dsfg dsfg";
		
		String patternString = "([^\\s]*) ([^\\s]*) (.*)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(txt1);
		
		while(matcher.find()) {
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
		}
	}

}
