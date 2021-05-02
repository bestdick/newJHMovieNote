package com.NewMovieNote.models;

public class BoxItemModel {
    Integer rank;
    String title;
    String imageUrl;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BoxItemModel(Integer rank, String title, String imageUrl) {
        this.rank = rank;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
