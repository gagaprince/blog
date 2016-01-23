package com.prince.myproj.blog.configs;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer5X;
import org.lionsoul.jcseg.core.JcsegTaskConfig;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by gagaprince on 16-1-2.
 */
public class LuceneConfiger {
    private String indexPath;
    private DirectoryReader reader;
    private IndexWriter indexWriter;
    private Analyzer luceneAnalyzer;
    private IndexSearcher indexSearcher;


    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public DirectoryReader getIndexReader(String path) throws CorruptIndexException, IOException {
        if(reader==null){
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(path)));
        }else{
            DirectoryReader dr = DirectoryReader.openIfChanged(reader);
            if(dr!=null){
                reader.close();
                reader = dr;
            }
        }
        return reader;
    }

    public DirectoryReader getIndexReader() throws CorruptIndexException, IOException{
        return getIndexReader(this.indexPath);
    }

    public IndexWriter getIndexWriter() throws IOException{
        if(indexWriter==null || !indexWriter.isOpen()){
            Directory dir = FSDirectory.open(Paths.get(this.indexPath));
            Analyzer luceneAnalyzer = giveMeAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(luceneAnalyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(dir,iwc);
        }
        return indexWriter;
    }

    public Analyzer giveMeAnalyzer(){
        if(luceneAnalyzer == null){
            luceneAnalyzer = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE);
            JcsegAnalyzer5X jcseg = (JcsegAnalyzer5X) luceneAnalyzer;
            JcsegTaskConfig jcsegTaskConfig = jcseg.getTaskConfig();
            jcsegTaskConfig.setAppendCJKPinyin(true);
            jcsegTaskConfig.setAppendCJKSyn(true);
        }
        return luceneAnalyzer;
    }

    public IndexSearcher getSearcher(IndexReader r){
        //如果searcher是空的，或者 searcher中的reader已经关闭
        if(indexSearcher == null || indexSearcher.getIndexReader().getRefCount() <= 0){
            indexSearcher = new IndexSearcher(r);
        }
        return indexSearcher;
    }
}
