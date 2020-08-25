package com.Beans;

public class BoxOfficeBean {
    int type; // 1001 regular content   -1001 header
    String uploadDate;
    String uploadTime;
    String movieName;
    String boxRank;
    String boxRankChange;
    String openDate;
    String audiAcc;
    String youtubeVideoId;
    String youtubeThumbnail;
    String naverLink;
    String naverImage;
    String naverSubTitle;
    String naverDirector;
    String naverActor;
    String naverRating;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getBoxRank() {
        return boxRank;
    }

    public void setBoxRank(String boxRank) {
        this.boxRank = boxRank;
    }

    public String getBoxRankChange() {
        return boxRankChange;
    }

    public void setBoxRankChange(String boxRankChange) {
        this.boxRankChange = boxRankChange;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getYoutubeThumbnail() {
        return youtubeThumbnail;
    }

    public void setYoutubeThumbnail(String youtubeThumbnail) {
        this.youtubeThumbnail = youtubeThumbnail;
    }

    public String getNaverLink() {
        return naverLink;
    }

    public void setNaverLink(String naverLink) {
        this.naverLink = naverLink;
    }

    public String getNaverImage() {
        return naverImage;
    }

    public void setNaverImage(String naverImage) {
        this.naverImage = naverImage;
    }

    public String getNaverSubTitle() {
        return naverSubTitle;
    }

    public void setNaverSubTitle(String naverSubTitle) {
        this.naverSubTitle = naverSubTitle;
    }

    public String getNaverDirector() {
        return naverDirector;
    }

    public void setNaverDirector(String naverDirector) {
        this.naverDirector = naverDirector;
    }

    public String getNaverActor() {
        return naverActor;
    }

    public void setNaverActor(String naverActor) {
        this.naverActor = naverActor;
    }

    public String getNaverRating() {
        return naverRating;
    }

    public void setNaverRating(String naverRating) {
        this.naverRating = naverRating;
    }

    public BoxOfficeBean(int type, String uploadDate, String uploadTime, String movieName, String boxRank, String boxRankChange, String openDate, String audiAcc, String youtubeVideoId, String youtubeThumbnail, String naverLink, String naverImage, String naverSubTitle, String naverDirector, String naverActor, String naverRating) {
        this.type = type;
        this.uploadDate = uploadDate;
        this.uploadTime = uploadTime;
        this.movieName = movieName;
        this.boxRank = boxRank;
        this.boxRankChange = boxRankChange;
        this.openDate = openDate;
        this.audiAcc = audiAcc;
        this.youtubeVideoId = youtubeVideoId;
        this.youtubeThumbnail = youtubeThumbnail;
        this.naverLink = naverLink;
        this.naverImage = naverImage;
        this.naverSubTitle = naverSubTitle;
        this.naverDirector = naverDirector;
        this.naverActor = naverActor;
        this.naverRating = naverRating;
    }
}
