package searchers;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.utils.Converter;
import com.utils.ErrorLog;
import com.utils.GlobalData;

public class SimpleSearcher {
	
	public BooleanQuery assembleQuery(ErrorLog log, boolean customAttribute) throws Exception {
		Converter converter = new Converter();
		
		QueryParser errorBodyParser = new QueryParser(Version.LUCENE_4_9, GlobalData.ErrorBody, GlobalData.StAnalyzer);
		Set<String> messege1 = converter.parseStr2Set(log.getErrorBody());
		System.out.println(converter.extractCore(messege1.toString()));
		Query query1 = errorBodyParser.parse(converter.extractCore(messege1.toString()));
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
		// should avoid such customized attributes since it involves 
		// human interventions
		if(customAttribute) {
			
			if(log.getErrorCode().size() > 0) {
				//System.out.println(converter.extractCore(log.getErrorCode().toString()));
				QueryParser errorCodeParser = new QueryParser(Version.LUCENE_4_9, GlobalData.ErrorCode, GlobalData.StAnalyzer);
			
				Query query2 = errorCodeParser.parse(converter.extractCore(log.getErrorCode().toString())); // no enough, throw exception
				query2.setBoost(GlobalData.BoostFactor);
				booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
			}
			
			if(log.getFailedTo().size() > 0) {
				QueryParser failedToParser = new QueryParser(Version.LUCENE_4_9, GlobalData.FailedTo, new SimpleAnalyzer(Version.LUCENE_4_9));
				
				List<String> messege = converter.parseString(log.getFailedTo());
				//System.out.println(converter.extractCore(messege.toString()));
				Query query3 = failedToParser.parse(converter.extractCore(messege.toString()));
				
				//Query query3 = failedToParser.parse(converter.extractCore(log.getFailedTo().toString()));
				query3.setBoost(GlobalData.BoostFactor);
				booleanQuery.add(query3, BooleanClause.Occur.SHOULD);
				
			}
			
			if(log.getCausedBy().size() > 0) {
				//System.out.println(converter.extractCore(log.getCausedBy().toString()));
				QueryParser CausedByParser = new QueryParser(Version.LUCENE_4_9, GlobalData.CausedBy, GlobalData.StAnalyzer);
				//Query query4 = CausedByParser.parse(converter.extractCore(log.getCausedBy().toString()));
				
				List<String> messege = converter.parseString(log.getCausedBy());
				//System.out.println(converter.extractCore(messege.toString()));
				Query query4 = CausedByParser.parse(converter.extractCore(messege.toString()));
				
				query4.setBoost(GlobalData.BoostFactor);
				booleanQuery.add(query4, BooleanClause.Occur.SHOULD);
				
			}
				
		}
		return booleanQuery;
	}
	
	public static void main(String args[]) {
		
//		"org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute goal com.ebay.raptor.build:assembler-maven-plugin:1.1.3:deploy (default-cli) on project cosfsweb: Failed to deploy package for stack j2ee_war  Error code is 29 - /var/lib/jenkins/jobs/fulcrum3-ci/builds/2014-06-06_17-41-25/archive/cos-fulcrum-r2-lite/cosfsweb/target/ROOT.war is invalid. More information on this error code is available at : https://wiki.vip.corp.ebay.com/display/PaaSCCOE/Assembler-2-Errors#Assembler-2-Errors-E29	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:217)
//		at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:153)
//		at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:145)
//		at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilde
//		
		IndexReader reader;
		try {
			// yet another multi-field search solution
			QueryParser errorCodeParser = new QueryParser(Version.LUCENE_4_9, GlobalData.ErrorCode, GlobalData.StAnalyzer);
			Query query1 = errorCodeParser.parse("100 ");
			
			QueryParser errorBodyParser = new QueryParser(Version.LUCENE_4_9, GlobalData.ErrorBody, GlobalData.StAnalyzer);
			Query query2 = errorBodyParser.parse("org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute "
					+ "goal com.ebay.raptor.build:assembler-maven-plugin:1.1.3:deploy (default-cli) on project cosfsweb: Failed to "
					+ "deploy package for stack j2ee_war  Error code is 29 "
					+ "- /var/lib/jenkins/jobs/fulcrum3-ci/builds/2014-06-06_17-41-25/archive/cos-fulcrum-r2-lite/cosfsweb/target/ROOT.war "
					+ "at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:153)"
					+ "at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:145)"
					+ "at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilde");
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
			booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
			
			// Search
			reader = DirectoryReader.open(FSDirectory.open(new File(GlobalData.indexDir)));
			IndexSearcher searcher = new IndexSearcher(reader);
			
			int hitsPerPage = 10;
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		    //searcher.search(query, collector); //single field search
			searcher.search(booleanQuery, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			
		    System.out.println("Found " + hits.length + " hits.");
		    for(int i = 0;i < hits.length; ++i) {
		    	int docId = hits[i].doc;
		    	Document d = searcher.doc(docId);
		    	System.out.println((i + 1) + ". [" + d.get(GlobalData.ErrorCode) + "]\t[" + d.get(GlobalData.FailedTo)+"]");
		    	System.out.println(hits[i].score);
		    
		    }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	}
}
