package indexers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.utils.Converter;
import com.utils.ErrorLog;
import com.utils.GlobalData;

//	getIndexWriter and closeIndexWriter should be static
// 

public class SimpleIndexer {
	private IndexWriter indexWriter = null;
	
	public IndexWriter getIndexWriter(boolean create) throws IOException {

		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_9, 
				GlobalData.StAnalyzer);
		if(create) {
			// create a new index in the directory, removing any
			// previously indexed documents
			iwc.setOpenMode(OpenMode.CREATE);
		} else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}
		if (indexWriter == null) {
			Directory dir = FSDirectory.open(new File(GlobalData.indexDir));
			indexWriter = new IndexWriter(dir, iwc);
	    }
	    return indexWriter;
	}
	
	// IndexWriter must be close
	// Won't store any data otherwise
	public void closeIndexWriter() throws IOException {
		if(indexWriter != null) {
			indexWriter.close();
		}
	}
	
	public void indexLog(ErrorLog log) throws IOException {
		Converter converter = new Converter();
		System.out.println(log.getInfo() + " indexing log");
		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();
		
		doc.add(new TextField(GlobalData.ErrorCode, log.getErrorCode().toString(), Field.Store.YES));
		doc.add(new TextField(GlobalData.FailedTo, converter.parseString(log.getFailedTo()).toString(), Field.Store.YES));
		doc.add(new TextField(GlobalData.CausedBy, converter.parseString(log.getCausedBy()).toString(), Field.Store.YES));
		doc.add(new TextField(GlobalData.ErrorBody, converter.parseStr2Set(log.getErrorBody()).toString(), Field.Store.YES));
		doc.add(new TextField(GlobalData.Info, log.getInfo(), Field.Store.YES));
		writer.addDocument(doc);
	}
	
	public void indexLogs(List<ErrorLog> logs) throws IOException {
		// empty existing indexes
		getIndexWriter(true);
		
		for(ErrorLog ithLog : logs) {
			indexLog(ithLog);
		}
	}
	
	public static void main(String args[]) {
		Converter converter = new Converter();
		SimpleIndexer indexer = new SimpleIndexer();
		
		List<ErrorLog> logs = converter.convert2Logs(GlobalData.solutionDir);
		
		try {
			indexer.indexLogs(logs);
			indexer.closeIndexWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
