package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.analyzer.MySimpleAnalyzer;

public class Converter {
	public List<ErrorLog> convert2Logs(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		List<ErrorLog> res = new ArrayList<ErrorLog>();
		
		for(int i = 0; i < files.length; i ++) {
			ErrorLog temp = convert2Log(files[i]);
			res.add(temp);
		}
		return res;
	}
	
	public ErrorLog convert2Log(File fileName) {
		ErrorLog res = new ErrorLog();
		// abandon  ".txt" 
		res.setInfo(fileName.getName().substring(0, fileName.getName().length()-4));
		
		String nextLine = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			while((nextLine = reader.readLine()) != null) {
				
				handleLine(res, nextLine);
			}
			reader.close();
			//System.out.println(res.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	// If begin with [INFO] or [WARN], abandon
	// if begin with Failed to, record it 
	// if begin with Error code, record it
	// if begin with Caused by, record it
	private void handleLine(ErrorLog log, String line) {
		if(line.contains("[INFO]")) return;
		if(line.contains("[WARN]")) return;
		
		String patternString = "Failed to ([^\\s]*) ([^\\s]*) ([^\\s]*) ";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(line);
		
		Set<String> failedTo = new HashSet<String>();
		
		while(matcher.find()) {
			failedTo.add(matcher.group(1) + " ");
			failedTo.add(matcher.group(2) + " ");
			failedTo.add(matcher.group(3) + " ");
		}
		log.setFailedTo(failedTo);
		
		patternString = "Caused by: ([^\\s]*)";
		pattern = Pattern.compile(patternString);
		matcher = pattern.matcher(line);
		
		Set<String> causedBy = new HashSet<String>();
		while(matcher.find()) {
			causedBy.add(matcher.group(1));
		}
		log.setCausedBy(causedBy);
		
		patternString = "Error code is ([0-9]*)";
		pattern = Pattern.compile(patternString);
		matcher = pattern.matcher(line);
		
		Set<String> errorCode = new HashSet<String>();
		while(matcher.find()) {
			errorCode.add(matcher.group(1));
		}
		log.setErrorCode(errorCode);
		
		log.setErrorBody(line);
	}
	
	// input like "[100, 200]"
	// output 100, 200
	public String extractCore(String str) {
		if(str == null || str.length() < 2)	
			return str;
		int len = str.length();
		return str.substring(1, len-1);
	}
	
	// queryParser and analyzer not working as expected
	// so, I have to do the parser myself
	// dirty, but had to
	// use classes under com.analyzer
	public List<String> parseString(Set<String> strs) {
		List<String> res = new ArrayList<String>();
		
		MySimpleAnalyzer simAnalyzer = new MySimpleAnalyzer();
		for(String ithStr : strs) {
			List<String> temp = simAnalyzer.tokenizeString(ithStr);
			res.addAll(temp);
		}
		
		return res;
	}
	
	public List<String> parseString(String str) {
		List<String> res = (new MySimpleAnalyzer()).tokenizeString(str);
		return res;
	}
	
	public Set<String> parseStr2Set(Set<String> strs) {
		Set<String> res = new HashSet<String>();
		
		MySimpleAnalyzer simAnalyzer = new MySimpleAnalyzer();
		for(String ithStr : strs) {
			List<String> temp = simAnalyzer.tokenizeString(ithStr);
			res.addAll(temp);
		}
		
		return res;
	}
	
	public Set<String> parseStr2Set(String str) {
		List<String> res = (new MySimpleAnalyzer()).tokenizeString(str);
		return (new HashSet<String>(res));
	}
}
