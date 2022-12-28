package com.tree.insdownloader.logic.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String displayUrl;

    private String videoUrl;

    private String headUrl;

    private String userName;

    private String describe;

    private String headFileName;

    private String photoFileName;

    public String getHeadFileName() {
        return headFileName;
    }

    public void setHeadFileName(String headFileName) {
        this.headFileName = headFileName;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getDescribe() {
        return describe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", displayUrl='" + displayUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", describe='" + describe + '\'' +
                ", headFileName='" + headFileName + '\'' +
                ", photoFileName='" + photoFileName + '\'' +
                '}';
    }
}
