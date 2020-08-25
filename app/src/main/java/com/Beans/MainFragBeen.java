package com.Beans;

public class MainFragBeen {
    // type
    // 1001 Header lik My (single)
    // 1002 My status
    // 1003 My list
    // 1004 Header like Share(single)
    // 1005 Share list
    // 1006 Header lik Etc (single)
    // 1007 Etc list
    // 2001 footer

    int type;

    String myListCount;
    String myLikeCount;
    String myCommentCount;

    String isPrivate;

    String thumbnailUrl;
    String authorName;
    String title;
    String description;
    String hit;
    String date;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMyListCount() {
        return myListCount;
    }

    public void setMyListCount(String myListCount) {
        this.myListCount = myListCount;
    }

    public String getMyLikeCount() {
        return myLikeCount;
    }

    public void setMyLikeCount(String myLikeCount) {
        this.myLikeCount = myLikeCount;
    }

    public String getMyCommentCount() {
        return myCommentCount;
    }

    public void setMyCommentCount(String myCommentCount) {
        this.myCommentCount = myCommentCount;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MainFragBeen(int type, String myListCount, String myLikeCount, String myCommentCount, String isPrivate, String thumbnailUrl, String authorName, String title, String description, String hit, String date) {
        this.type = type;
        this.myListCount = myListCount;
        this.myLikeCount = myLikeCount;
        this.myCommentCount = myCommentCount;
        this.isPrivate = isPrivate;
        this.thumbnailUrl = thumbnailUrl;
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.hit = hit;
        this.date = date;
    }
}
