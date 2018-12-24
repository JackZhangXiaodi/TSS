package com.baizhi;

import com.baizhi.dao.PoetryDAO;
import com.baizhi.entity.Poetry;
import com.baizhi.service.PoetryService;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TsApplicationTests {
    @Autowired
    private PoetryDAO poetryDAO;
    @Autowired
    private PoetryService poetryService;
    @Test
    public void test() {
        List<Poetry> poetries = poetryDAO.queryAll();
        for (Poetry poetry : poetries) {
            System.out.println(poetry);
        }
    }
    @Test
    public void test2() throws IOException {
            List<Poetry> all = poetryService.findAll();
//        创建索引数据存储的位置
            FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\JAVA\\FarmeWork\\WorkSpace\\lucene\\mytest\\02"));
//        创建写入器
            IndexWriter indexWriter = new IndexWriter(fsDirectory, new IndexWriterConfig(new IKAnalyzer()));
//        创建文档对象
            Document document = null;
            for (Poetry poetry : all) {
                document = new Document();
                document.add(new IntField("id",poetry.getId(), Field.Store.YES));
                document.add(new StringField("name",poetry.getPoets().getName(),Field.Store.YES));
                document.add(new StringField("title",poetry.getTitle(),Field.Store.YES));
                document.add(new TextField("content",poetry.getContent(),Field.Store.YES));
                indexWriter.addDocument(document);
            }
            indexWriter.commit();
            indexWriter.close();
    }
    @Test
    public void test3() throws IOException, ParseException, InvalidTokenOffsetsException {
//        创建索引数据存储的位置
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\JAVA\\FarmeWork\\WorkSpace\\lucene\\mytest\\02"));
//        创建索引读入器
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
//        创建索引检测器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//      创建默认系统检索器
        QueryParser queryParser = new QueryParser("content",new IKAnalyzer());
//      指定检索内容
        Query query = null;
        query=queryParser.parse("鹅鹅鹅");
        query = queryParser.parse("name:李白 OR content:李白");

        int pageNow =1;
        int pageSize=10;
//      分页数据
//      指定当前显示索引条数
        TopDocs topDocs = null;
        if(pageNow==1 || pageNow<1){
            topDocs= indexSearcher.search(query,pageNow*pageSize);
        }else if(pageNow>1){
            topDocs = indexSearcher.search(query,(pageNow-1)*pageSize);
            //lunece分页中需要获取每一页的最后一条数据 的文档对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc scoreDoc = scoreDocs[scoreDocs.length - 1];
            topDocs=indexSearcher.searchAfter(scoreDoc,query,pageSize);
        }

//      高亮查询
        QueryScorer scorer = new QueryScorer(query);
//      使用自定义高亮查询
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
//        创建高亮器
        Highlighter highlighter= new Highlighter(formatter,scorer);

//        List<Poetry> poetries = new ArrayList<>();
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
//            Poetry poetry1 = new Poetry();
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
//            //获取Id
//            String id = String.valueOf(poetry.getId());
//            String s1 = document.get(id);
//            Integer id2 = new Integer(s1);
//            poetry1.setId(id2);
//            //获取title
//            poetry1.setTitle(document.get(poetry.getTitle()));
            //获取高亮的最佳片段
            String s = highlighter.getBestFragment(new IKAnalyzer(), "content", document.get("content"));
           System.out.println(document.get("name")+"|"+document.get("title")+"|"+document.get("content")+s);
        }
    }

    @Test
    public void test5(){

    }
}

