package com.prince.myproj.blog.models;

/**
 * Created by gagaprince on 15-12-20.
 */
public class FriendLinkModel {
    private int id;
    private String link;
    private String desc;
    private int rank;

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
