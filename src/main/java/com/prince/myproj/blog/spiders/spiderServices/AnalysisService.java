package com.prince.myproj.blog.spiders.spiderServices;

import com.prince.myproj.blog.spiders.spiderModel.FolderModel;
import com.prince.myproj.blog.spiders.spiderModel.XiaohuaModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gagaprince on 15-12-27.
 */
@Service
public class AnalysisService {

    private String root = "http://www.xiaohuar.com";

    public XiaohuaModel analysisXiaoHuaIndex(String content,String url){
        XiaohuaModel xiaohuaModel = new XiaohuaModel();

        List<FolderModel> folderModels = new ArrayList<FolderModel>();

        xiaohuaModel.setCurrentUrl(url);
        xiaohuaModel.setFolderModels(folderModels);

        Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByClass("masonry_brick");
        for(Element e:elements){
            Element eImg = e.getElementsByClass("img").get(0);
            Element aImg = eImg.getElementsByTag("a").get(0);
            Element aImgtag = aImg.getElementsByTag("img").get(0);
            String title = aImgtag.attr("alt");
            String cover = aImgtag.attr("src");
            if (cover.startsWith("/"))cover=root+cover;
            String detailUrl = aImg.attr("href").replace("p-","s-");
            if (!detailUrl.contains("s-"))continue;
            if (detailUrl.startsWith("/"))detailUrl=root+detailUrl;

            FolderModel folderModel = new FolderModel();
            folderModel.setCover(cover);
            folderModel.setTitle(title);
            folderModel.setDetailUrl(detailUrl);

            folderModels.add(folderModel);
        }

        Element listPage = doc.getElementById("page");
        Elements pageAs = listPage.getElementsByTag("a");
        Element lastPage = pageAs.get(pageAs.size()-1);
        if(lastPage.html().equals("尾页")){
            xiaohuaModel.setIsLast(false);
        }else {
            xiaohuaModel.setIsLast(true);
        }

        return xiaohuaModel;
    }

    public void analysisXiaoHuaDetail(String content,FolderModel folderModel){
        Document doc = Jsoup.parse(content);
        Element imgFrame = doc.getElementsByClass("pic_img_gallery").get(0);
        Elements atags = imgFrame.getElementsByTag("a");
        List<String> jpgs = new ArrayList<String>();
        folderModel.setJpgs(jpgs);

        int size = atags.size();
        for(int i=0;i<size;i++){
            Element atag = atags.get(i);
            String jpg = root + atag.attr("href");
            jpgs.add(jpg);
        }

    }
}
