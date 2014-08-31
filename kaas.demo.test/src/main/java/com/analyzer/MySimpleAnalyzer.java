package com.analyzer;

import java.util.List;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.util.Version;

public class MySimpleAnalyzer extends AbstractProblemAnalyzer {

	@Override
	public List<String> tokenizeString(String contents) {
		// TODO Auto-generated method stub
		return tokenizeString(new SimpleAnalyzer(Version.LUCENE_4_9),
				contents);
	}
}
