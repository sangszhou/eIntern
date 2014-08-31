package com.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class GlobalData {
	
	public static String indexDir = "E:\\zhou\\kaas\\kaas\\indexrepo";
	public static String solutionDir = "E:\\zhou\\kaas\\kaas\\solutionrepo1";
	public static String problemDir = "E:\\zhou\\kaas\\kaas\\problemrepo2";
	
	// take advantage of eclipse auto complete to avoid
	// misspelling
	public static String ErrorCode = "ErrorCode";
	public static String ErrorBody = "ErrorBody";
	public static String FailedTo  = "FailedTo";
	public static String CausedBy  = "CausedBy";
	public static String Info	   = "Info";
	
	// The indexer and searcher should use same analyzer
	// to avoid unpleasant things
	public static Analyzer StAnalyzer = new StandardAnalyzer(Version.LUCENE_4_9);
	public static Analyzer SimAnalyzer = new SimpleAnalyzer(Version.LUCENE_4_9);

	public static float BoostFactor = (float) 2.0;
}
