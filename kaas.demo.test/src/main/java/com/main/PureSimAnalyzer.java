package com.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

import com.utils.Converter;
import com.utils.ErrorLog;
import com.utils.GlobalData;

import searchers.SimpleSearcher;

public class PureSimAnalyzer {
	
	public static void main(String[] args) {
		SimpleSearcher simSearcher = new SimpleSearcher();
		Converter converter = new Converter();
		
		List<ErrorLog> problems = converter.convert2Logs(GlobalData.problemDir);
		
		//do prepare work
		IndexReader reader = null;
		IndexSearcher searcher = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(new File(GlobalData.indexDir)));
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// iterator all logs and search for answers
		for(ErrorLog ithLog : problems) {
			// generate query based on errorlog
			// true indicate that we enable custom attributes 
			// such as error code
			try {
				BooleanQuery booleanQuery = simSearcher.assembleQuery(ithLog, true);
				int hitsPerPage = 8;
				TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			    
				searcher.search(booleanQuery, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;
				System.out.println("Search " + ithLog.getInfo());
			    System.out.println("Found " + hits.length + " hits.");
			    for(int i = 0;i < hits.length; ++i) {
			    	int docId = hits[i].doc;
			    	Document d = searcher.doc(docId);
			    	System.out.println((i + 1) + ". [" + d.get(GlobalData.Info)+"]" + " " + hits[i].score + " ");	
			    }
			    			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}

}
