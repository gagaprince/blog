package com.prince.myproj.blog.services;

import com.prince.myproj.blog.configs.LuceneConfiger;
import com.prince.myproj.blog.dao.DailyDao;
import com.prince.myproj.blog.models.*;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by gagaprince on 16-1-2.
 */
@Service
public class LuceneService {
    public static final Logger logger = Logger.getLogger(LuceneService.class);

    @Autowired
    public LuceneConfiger blogLuceneConfiger;

    @Autowired
    public DailyService dailyService;
    @Autowired
    public FeService feService;
    @Autowired
    public VideoService videoService;

    public void createIndex() throws IOException {
        IndexWriter indexWriter = null;
        try {
            indexWriter = blogLuceneConfiger.getIndexWriter();
            deleteAllIndex(indexWriter);
            createBlogIndex(preparedDailyIndex(), indexWriter);
            createBlogIndex(preparedFeIndex(), indexWriter);
            createBlogIndex(preparedVideoIndex(), indexWriter);
//            createlifeIndex();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if(indexWriter!=null){
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void deleteAllIndex(IndexWriter indexWriter) throws IOException {
        indexWriter.deleteAll();
        indexWriter.forceMergeDeletes();
    }

    public List<BlogIndexModel> preparedDailyIndex(){
        List<DailyModel> dailyModels = dailyService.getDailyListByPage(0, 10000, "");
        List<BlogIndexModel> blogIndexModels = new ArrayList<BlogIndexModel>();
        int size = dailyModels.size();
        for(int i=0;i<size;i++){
            DailyModel dailyModel = dailyModels.get(i);
            BlogIndexModel blogIndexModel = new BlogIndexModel();
            blogIndexModel.setTitle(dailyModel.getTitle());
            blogIndexModel.setContent(dailyModel.getContent());
            blogIndexModel.setImg(dailyModel.getPic());
            blogIndexModel.setLink("/blog/detail/" + dailyModel.getId());
            blogIndexModels.add(blogIndexModel);
        }
        return blogIndexModels;
    }
    public List<BlogIndexModel> preparedFeIndex(){
        List<BlogIndexModel> blogIndexModels = new ArrayList<BlogIndexModel>();
        List<FeModel> feModels = feService.getFeListByPage(0,10000);
        int size = feModels.size();
        for(int i=0;i<size;i++){
            FeModel feModel = feModels.get(i);
            BlogIndexModel blogIndexModel = new BlogIndexModel();
            blogIndexModel.setTitle(feModel.getName());
            blogIndexModel.setContent(feModel.getDesc());
            blogIndexModel.setImg(feModel.getCover());
            blogIndexModel.setLink("/blog/fe/detail/"+feModel.getId());
            blogIndexModels.add(blogIndexModel);
        }
        return blogIndexModels;
    }
    public List<BlogIndexModel> preparedVideoIndex(){
        List<BlogIndexModel> blogIndexModels = new ArrayList<BlogIndexModel>();
        List<VideoModel> videoModels = videoService.getVideosByPage(0, 10000, "");
        int size = videoModels.size();
        for(int i=0;i<size;i++){
            VideoModel videoModel = videoModels.get(i);
            BlogIndexModel blogIndexModel = new BlogIndexModel();
            blogIndexModel.setTitle(videoModel.getTitle());
            blogIndexModel.setContent(videoModel.getDesc());
            blogIndexModel.setImg(videoModel.getCover());
            blogIndexModel.setLink("/blog/video/detail/"+videoModel.getId());
            blogIndexModels.add(blogIndexModel);
        }
        return blogIndexModels;
    }
    public void createlifeIndex(){}

    public void createBlogIndex(List<BlogIndexModel> blogIndexModels,IndexWriter indexWriter) throws IOException {
        if (blogIndexModels==null)return;
        int size = blogIndexModels.size();
        for(int i=0;i<size;i++){
            BlogIndexModel blogIndexModel = blogIndexModels.get(i);
            Field fieldTitle = new TextField("title",blogIndexModel.getTitle(), Field.Store.YES);
            Field fieldContent = new TextField("content",blogIndexModel.getContent(), Field.Store.YES);
            Field fieldImg = new StringField("img",blogIndexModel.getImg(), Field.Store.YES);
            Field fieldLink = new StringField("link",blogIndexModel.getLink(), Field.Store.YES);
            Document doc = new Document();
            doc.add(fieldTitle);
            doc.add(fieldContent);
            doc.add(fieldImg);
            doc.add(fieldLink);
            indexWriter.addDocument(doc);
        }
        indexWriter.commit();
    }

    public List<BlogIndexModel> searchBlog(String key,ListPageModel listPageModel) throws ParseException, IOException {
        int pno = listPageModel.getPno();
        int psize = listPageModel.getPsize();

        Analyzer luceneAnalyzer = blogLuceneConfiger.giveMeAnalyzer();
        IndexSearcher searcher = blogLuceneConfiger.getSearcher(blogLuceneConfiger.getIndexReader());
        Query query = null;
        ScoreDoc[] hits = null;
        MultiFieldQueryParser qp = new MultiFieldQueryParser(new String[]{"title","content"}, luceneAnalyzer);
        query = qp.parse(key);
        List<BlogIndexModel> blogIndexModels = new ArrayList<BlogIndexModel>();
        if (searcher != null) {
            TopDocs results = searcher.search(query,10000);
            hits = results.scoreDocs;

            int begin = pno*psize;
            int end = Math.min(begin+psize,hits.length);
            int allCount = hits.length;
            listPageModel.setAllCount(allCount);
            listPageModel.setAllPage((allCount-1)/psize+1);

            for(int i=begin;i<end;i++){
                ScoreDoc selectDoc = hits[i];
                Document hitDoc = searcher.doc(selectDoc.doc);

                BlogIndexModel blogIndexModel = new BlogIndexModel();
                blogIndexModel.setContent(hitDoc.get("content"));
                blogIndexModel.setTitle(hitDoc.get("title"));
                blogIndexModel.setImg(hitDoc.get("img"));
                blogIndexModel.setLink(hitDoc.get("link"));
                blogIndexModels.add(blogIndexModel);
            }
        }
        return blogIndexModels;
    }

}
