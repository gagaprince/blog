package com.prince.myproj.blog.models;

/**
 * Created by gagaprince on 15-12-20.
 */
public class MusicModel {
    private int id;
    private String name;
    private String singer;
    private String album;
    private String bgImg;
    private String src;

    public int getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getBgImg() {
        return bgImg;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getSrc() {
        return src;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
