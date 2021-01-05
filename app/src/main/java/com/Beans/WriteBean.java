package com.Beans;

import android.net.Uri;

public class WriteBean {
    String isNew;
    String type; // image , text , title, empty

    String text;
    String align;
    String fontsize;


    Uri imageUri;
    String imageName;
    String rotation;
    String width;
    String height;

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public WriteBean(String isNew , String type, String text, String align, String fontsize, Uri imageUri, String imageName, String rotation, String width, String height) {
        this.isNew = isNew;
        this.type = type;
        this.text = text;
        this.align = align;
        this.fontsize = fontsize;
        this.imageUri = imageUri;
        this.imageName = imageName;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
    }
}
