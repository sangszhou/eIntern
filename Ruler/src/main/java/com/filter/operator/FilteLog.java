package com.filter.operator;

import java.util.regex.Pattern;

import com.filter.model.Cause;
import com.filter.model.Filter;

/*
 * filter contains at lease one causes
 * cause contains  one keyword or pattern 
 */

public class FilteLog {
	
	public boolean isMatch(String log, Filter filter) {
		//System.out.println("filter cause size is : " + filter.getCause().size());
		for(Cause cause : filter.getCause()) {
			System.out.println("next one");
			if(!isMatchCause(log, cause)) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean isMatchCause(String content, Cause cause) {
		if (isMatchContent(content, cause)
				|| isMatchKeyword(content, cause)
				|| isMatchPattern(content, cause)) {
			return true;
		}
		return false;
	}
	
	protected boolean isMatchContent(String content, Cause cause) {
		if (cause.getValue() != null && content.equals(cause.getValue())) {
			return true;
		}
		return false;
	}
	
	protected boolean isMatchKeyword(String content, Cause cause) {
		//System.out.println("is match keyword " + cause.getKeyword());
		//System.out.println("The pattern is " + cause.getPattern());
		if (cause.getKeyword() != null && content.contains(cause.getKeyword())) {
			return true;
		}
		return false;
	}
	
	protected boolean isMatchPattern(String content, Cause cause) {
		System.out.println("is match pattern " + cause.getPattern());
		if (cause.getPattern() != null) {
			if (Pattern.compile(cause.getPattern(), Pattern.DOTALL).matcher(content).matches()) {
			//if (StringUtils.isEmpty(StringUtils.getFirstFound(fullstack, cause.getPattern(), true))) {
				return true;
			}
		}
		return false;
	}
	
}
