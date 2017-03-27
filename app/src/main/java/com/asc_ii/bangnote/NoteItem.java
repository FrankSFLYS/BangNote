package com.asc_ii.bangnote;

import org.litepal.crud.DataSupport;

/**
 * Created by FrankFLY on 2017/3/13.
 */

public class NoteItem extends DataSupport {

    private int id;
    private boolean isCreatedByUser;
    private long createTime;
    private long lastEditTime;
    private String title;
    private String text;
    private String prevText;
    private String picDir;
    private String HTMLDir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getCreatedByUser() {
        return isCreatedByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.isCreatedByUser = createdByUser;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicDir() {
        return picDir;
    }

    public void setPicDir(String picDir) {
        this.picDir = picDir;
    }

    public String getHTMLDir() {
        return HTMLDir;
    }

    public void setHTMLDir(String HTMLDir) {
        this.HTMLDir = HTMLDir;
    }

    public String getPrevText() {
        return prevText;
    }

    public void setPrevText(String prevText) {
        this.prevText = prevText;
    }

}
