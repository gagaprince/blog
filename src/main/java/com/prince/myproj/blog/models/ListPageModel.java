package com.prince.myproj.blog.models;

/**
 * Created by gagaprince on 15-12-20.
 */
public class ListPageModel {
    private long allCount;
    private long allPage;
    private int pno;
    private int psize;

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public long getAllCount() {
        return allCount;
    }

    public int getPno() {
        return pno;
    }

    public int getPsize() {
        return psize;
    }

    public long getAllPage() {
        return allPage;
    }

    public void setAllPage(long allPage) {
        this.allPage = allPage;
    }
}
