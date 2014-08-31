package com.utils;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.entry.model.Entry;
import com.entry.model.EntryJDBCTemplate;
import com.filter.model.Filters;
import com.filter.operator.MavenBuildFilterFactory;

public class GlobalData {
	public static final String FILTERSPATH = "E:\\zhou\\repo\\filter-file";
	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc-config.xml");
	
	public static MavenBuildFilterFactory mbff = new MavenBuildFilterFactory();
	public static EntryJDBCTemplate entryJDBCTemplate = (EntryJDBCTemplate) context.getBean("EntryJDBCTemplate");
	public static List<Entry> noCategories = entryJDBCTemplate.getExpEntriesWithoutCategory();
	
	public static Filters filters = mbff.unmarshal(new File(FILTERSPATH));
			
}
