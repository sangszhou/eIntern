package com.learn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 从下面的例子可以看出，group和括号是一一对应的
// 注意，引号内部的空格是起作用的

public class MultipleGroup {
	public static void main(String args[]) {
		String text    =
		          "John writes about this, and John Doe writes about that," +
		                  " and John Wayne writes about everything."
		        ;

		String patternString1 = "(John) (.+?) ";

		Pattern pattern = Pattern.compile(patternString1);
		Matcher matcher = pattern.matcher(text);

		while(matcher.find()) {
		    System.out.println("found: " + matcher.group(1) +
		                       " "       + matcher.group(2));
		}
	}
}
