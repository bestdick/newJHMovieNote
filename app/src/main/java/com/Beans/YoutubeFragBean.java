package com.Beans;

import java.util.ArrayList;

public class YoutubeFragBean {
    int type; // 1001 random youtube 추천 header , 1002 random youtube 추천 main ,  1003 channel 추천 header ,  1004 channel 추천 main
    // 1005 = 더보기 ....
    String channelId;
    String channelThumbnail;
    String title;
    String description;
    String thumbnailUrl;
    String channelTitle;
    String videoId;

    ArrayList<YoutubeChannelBean> channelList;

    public ArrayList<YoutubeChannelBean> getChannelList() {
        return channelList;
    }

    public void setChannelList(ArrayList<YoutubeChannelBean> channelList) {
        this.channelList = channelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannelThumbnail() {
        return channelThumbnail;
    }

    public void setChannelThumbnail(String channelThumbnail) {
        this.channelThumbnail = channelThumbnail;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public YoutubeFragBean(int type, String channelId, String channelThumbnail, String title, String description, String thumbnailUrl, String channelTitle, String videoId, ArrayList<YoutubeChannelBean> channelList)  {
        this.type = type;
        this.channelId = channelId;
        this.channelThumbnail = channelThumbnail;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.channelTitle = channelTitle;
        this.videoId = videoId;
        this.channelList = channelList;
    }
}
