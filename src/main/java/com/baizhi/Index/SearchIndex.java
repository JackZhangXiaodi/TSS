package com.baizhi.Index;

import com.baizhi.entity.Poet;
import com.baizhi.entity.Poetry;
import com.baizhi.util.PageIndex;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 检索索引数据
 * 1.准备关键子
 * 2.分析处理
 * 3.匹配检索索引
 * 4.返回结果
 */
@Repository
public class SearchIndex {
    public PageIndex all(String art, Integer pageNow , Integer pageSize) throws IOException, ParseException, InvalidTokenOffsetsException {
//        创建索引数据存储的位置
        FSDirectory fsDirectory = FSDirectory.open(Paths.get("D:\\JAVA\\FarmeWork\\WorkSpace\\lucene\\mytest\\02"));
//        创建索引读入器
        IndexReader indexReader = DirectoryReader.open(fsDirectory);
//        创建索引检测器
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//      创建默认系统检索器
        QueryParser queryParser = new QueryParser("content",new IKAnalyzer());

//      指定检索内容

        Query query=queryParser.parse("name:"+art+"OR title"+art+"OR content"+art);
//      分页数据
//      指定当前显示索引条数
        TopDocs topDocs = null;
        PageIndex pageIndex = new PageIndex();
        if(pageNow==null || pageNow==1 ){
            topDocs= indexSearcher.search(query,pageNow*pageSize);
        }else if(pageNow>1){
            topDocs = indexSearcher.search(query,(pageNow-1)*pageSize);
            //lunece分页中需要获取每一页的最后一条数据 的文档对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc scoreDoc = scoreDocs[scoreDocs.length - 1];
            topDocs=indexSearcher.searchAfter(scoreDoc,query,pageSize);
        }
//        查询总条数
        int totalHits = topDocs.totalHits;
        pageIndex.setTotal(totalHits);
//      高亮查询
        QueryScorer scorer = new QueryScorer(query);
//      使用自定义高亮查询
        Formatter formatter = new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
//        创建高亮器
        Highlighter  highlighter= new Highlighter(formatter,scorer);
        List<Poetry> poetries = new ArrayList<>();
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            Poetry poetry1 = new Poetry();
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            //获取诗人
            String author = highlighter.getBestFragment(new IKAnalyzer(), "name", document.get("name"));
            //获取title
            String title = highlighter.getBestFragment(new IKAnalyzer(), "title", document.get("title"));
            //获取高亮的最佳片段
            String content = highlighter.getBestFragment(new IKAnalyzer(), "content", document.get("content"));
            Poet poet = new Poet();
            //判断是否是诗人域中的词
            if(author==null){
                poet.setName(document.get("name"));
                poetry1.setPoets(poet);
            }else{
                poet.setName(author);
                poetry1.setPoets(poet);
            }
//           判断是否是title域中的片段
            if(title==null){
                poetry1.setTitle(document.get("title"));
            }else{
                poetry1.setTitle(title);
            }
//            判断content中是否存在
            if(content==null){
                poetry1.setContent(document.get("content"));
            }else{
                poetry1.setContent(content);
            }
            poetries.add(poetry1);
            pageIndex.setPoetries(poetries);
        }
        indexReader.close();
        return pageIndex;
    }
}
