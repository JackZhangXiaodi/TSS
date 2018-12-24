package com.baizhi.Index;

import com.baizhi.entity.Poetry;
import com.baizhi.service.PoetryService;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/***
 * 创建索引
 * 1.准备数据
 * 2.处理分析(分词)
 * 3.生成索引数据(词元)
 */
@Repository
public class CreatIndex {
    public void creatIndex(List<Poetry> all) throws IOException {
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
}
