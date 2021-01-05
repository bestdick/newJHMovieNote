package com.Beans;

public class YoutubeChannelBean {


        String channelId;
        String channelTitle;
        String channelThumbnailUrl;


    public String getChannelThumbnailUrl() {
        return channelThumbnailUrl;
    }

    public void setChannelThumbnailUrl(String channelThumbnailUrl) {
        this.channelThumbnailUrl = channelThumbnailUrl;
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

    public YoutubeChannelBean(String channelId, String channelTitle, String channelThumbnailUrl) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channelThumbnailUrl = channelThumbnailUrl;
    }
}
