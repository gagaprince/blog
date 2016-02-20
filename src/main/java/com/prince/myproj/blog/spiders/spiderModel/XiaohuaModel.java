package com.prince.myproj.blog.spiders.spiderModel;

import java.util.List;

/**
 * Created by gagaprince on 15-12-27.
 */
public class XiaohuaModel {
    private String currentUrl;
    private boolean isLast;
    private List<FolderModel> folderModels;

    public String toString(){
        StringBuffer sb = new StringBuffer("");
        sb.append("\n");
        sb.append("是否是最后一页：").append(isLast).append("\n");
        sb.append("信息列表：").append("\n");
        int size = folderModels.size();
        for(int i=0;i<size;i++){
            FolderModel folderModel = folderModels.get(i);
            sb.append("详细页地址：").append(folderModel.getDetailUrl()).append("\n");
            sb.append("相册标题：").append(folderModel.getTitle()).append("\n");
            sb.append("封面图片地址：").append(folderModel.getCover()).append("\n");
            sb.append("详细地址：").append("\n");
            List<String> jpgs = folderModel.getJpgs();
            if(jpgs!=null){
                int jsize = jpgs.size();
                for(int j=0;j<jsize;j++){
                    String jpg = jpgs.get(j);
                    sb.append("    地址").append(j+1).append(":").append(jpg).append("\n");
                }
            }
            sb.append("-----------------------").append("\n");
        }

        return sb.toString();
    }

    public List<FolderModel> getFolderModels() {
        return folderModels;
    }

    public void setFolderModels(List<FolderModel> folderModels) {
        this.folderModels = folderModels;
    }


    public String getCurrentUrl() {
        return currentUrl;
    }


    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }


    public boolean isLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }
}
