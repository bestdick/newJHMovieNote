package com.NewMovieNote.models;

public class YoutubeSuggestionItemModel {

    // --- channel
    String channelId ;
    String channelTitle;
    String channelThumbnail;

    // --- youtube
    String videoId;
    String videoTitle;
    String videoThumbnail;

    public YoutubeSuggestionItemModel(String channelId, String channelTitle, String channelThumbnail, String videoId, String videoTitle, String videoThumbnail) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channelThumbnail = channelThumbnail;
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoThumbnail = videoThumbnail;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }
}
