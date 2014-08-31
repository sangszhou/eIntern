package com.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.entry.model.Entry;
import com.filter.model.Cause;
import com.filter.model.Filter;

public class AssembleDS {

	public JSONObject assembleJson(List<Entry> list) {
		JSONObject res = new JSONObject();
		JSONArray  arr = new JSONArray();
		
		for(Entry ithEntry:list) {
			JSONObject obj = new JSONObject();
			obj.put("Access Time", ithEntry.getAccessTime());
			obj.put("IDE Type", ithEntry.getIDEType());
			obj.put("IDE Version", ithEntry.getIDEVersion());
			
			obj.put("ID", ithEntry.getId());
//			obj.put("Username", ithEntry.getUsername());
//			obj.put("Host", ithEntry.getKind());
//			obj.put("What", ithEntry.getWhat());
			obj.put("Exception", ithEntry.getException());
			
			arr.put(obj);
		}
		res.put("data", arr);
		return res;
	}
	
	public Filter assembleFilter(String categoryName, String filterName, String filterDescription,
			String keywords, String patterns) {
	
		Filter newFilter = new Filter();
		if(keywords != null && !keywords.isEmpty()) {
			JSONArray jsonArray = new JSONArray(keywords);
			System.out.println("keyword array's size: " + jsonArray.length());
			if(jsonArray != null) {
				for(int i = 0; i < jsonArray.length(); i ++) {
					System.out.println(jsonArray.get(i).toString());
					Cause cause = new Cause();
					cause.setKeyword(jsonArray.get(i).toString());
					newFilter.getCause().add(cause);
				}
			}
		} else {
			System.out.println("got nothing");
		}
		
		if(patterns != null && !patterns.isEmpty()) {
			JSONArray jsonArray = new JSONArray(patterns);
			System.out.println("pattern array's size: " + jsonArray.length());
			if(jsonArray != null) {
				for(int i = 0; i < jsonArray.length(); i ++) {
					if(jsonArray.get(i).toString() == null) continue;
					System.out.println(jsonArray.get(i).toString());
					Cause cause = new Cause();
					cause.setPattern(jsonArray.get(i).toString());
					newFilter.getCause().add(cause);
				}
			}
		}
		
		newFilter.setName(filterName);
		newFilter.setDescription(filterDescription);		
		return newFilter;
	}

}
