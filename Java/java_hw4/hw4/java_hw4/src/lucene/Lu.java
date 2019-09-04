package lucene;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Lu {
	public static void main(String[] args) {
		Lu w = new Lu();
		String sourceFilePath = "/Users/yryang/Desktop/gitcode/java_hw4";
		String filePath = "/Users/yryang/Desktop/gitcode/lucene";// 创建索引的存储目录

		//清空已有的索引信息
//		File deleteFile = new File(filePath);
//		String[] delete = deleteFile.list();
//		for (String name : delete) {
//			File temp = new File(filePath, name);
//			if (temp.isDirectory()) {
//
//			} else {
////				temp.delete();
//			}
//		}
		w.createIndex(filePath, sourceFilePath);// 创建索引
		w.searrh(filePath, "糖尿病");// 搜索
	}

	public void createIndex(String filePath, String sourceFilePath) {
		File f = new File(filePath);
		IndexWriter iwr = null;
		try {
			Directory dir = FSDirectory.open(f);
			Analyzer analyzer = new IKAnalyzer();

			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
			iwr = new IndexWriter(dir, conf);// 建立IndexWriter。固定套路
			File sourceFileFolder = new File(sourceFilePath);
			String[] fileList = sourceFileFolder.list();

			for (int i = 0; i < fileList.length; i++) {

				File temp = new File(sourceFilePath, fileList[i]);
				if (temp.isDirectory()) {

				} else {
//					System.out.println(fileList[i]);
					Document doc = getDocument(fileList[i]);
					iwr.addDocument(doc);// 添加doc，Lucene的检索是以document为基本单位
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			iwr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Document getDocument(String filename) {

		Document doc = new Document();

		// doc中内容由field构成，在检索过程中，Lucene会按照指定的Field依次搜索每个document的该项field是否符合要求。
		try {
			File f = new File(filename);
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f));

			Field fName = new TextField("filename", filename, Field.Store.YES);
			doc.add(fName);

			@SuppressWarnings("resource")
			String content = new Scanner(f).useDelimiter("\\Z").next();
			Field fContet = new TextField("content", content, Field.Store.YES);
			doc.add(fContet);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;

	}

	public String[] searrh(String filePath, String queryString) {
		File f = new File(filePath);
		String[] result = new String[101];
		int i = 0;
		try {
			IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(f)));
			String queryStr = queryString;
			Analyzer analyzer = new IKAnalyzer();
			// 指定field为“name”，Lucene会按照关键词搜索每个doc中的name。
			QueryParser parser = new QueryParser(Version.LUCENE_4_10_0, "content", analyzer);
//			QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_4_10_0, new String[] { "id", "pic" },
//					analyzer);

			Query query = parser.parse(queryStr);
			TopDocs hits = searcher.search(query, 20);// 前面几行代码也是固定套路，使用时直接改field和关键词即可

			int n = hits.totalHits;
			result[i] = String.valueOf(n);
			i++;

//			System.out.println(n);
			for (ScoreDoc doc : hits.scoreDocs) {

				Document d = searcher.doc(doc.doc);

//				System.out.println(d.get("filename"));
				result[i] = d.get("filename");
				i++;
//				System.out.println(d.get("content"));
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
