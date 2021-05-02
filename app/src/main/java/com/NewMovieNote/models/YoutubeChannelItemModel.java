package com.NewMovieNote.models;

public class YoutubeChannelItemModel {
    // --- channel
    String channelId ;
    String channelTitle;
    String channelThumbnail;

    public YoutubeChannelItemModel(String channelId, String channelTitle, String channelThumbnail) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channelThumbnail = channelThumbnail;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getChannelThumbnail() {
        return channelThumbnail;
    }

    public void setChannelThumbnail(String channelThumbnail) {
        this.channelThumbnail = channelThumbnail;
    }
}
