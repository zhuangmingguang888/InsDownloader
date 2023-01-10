package com.tree.insdownloader.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String displayUrl;

    private String videoUrl;

    private String headUrl;

    private String userName;

    private String describe;

    private String headFileName;

    private String fileName;

    private String contentLength;

    private String time;

    private String contentType;

    private String url;

    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getHeadFileName() {
        return headFileName;
    }

    public void setHeadFileName(String headFileName) {
        this.headFileName = headFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
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


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.displayUrl);
        dest.writeString(this.videoUrl);
        dest.writeString(this.headUrl);
        dest.writeString(this.userName);
        dest.writeString(this.describe);
        dest.writeString(this.headFileName);
        dest.writeString(this.fileName);
        dest.writeString(this.contentLength);
        dest.writeString(this.time);
        dest.writeString(this.contentType);
        dest.writeString(this.url);
        dest.writeString(this.downloadUrl);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.displayUrl = source.readString();
        this.videoUrl = source.readString();
        this.headUrl = source.readString();
        this.userName = source.readString();
        this.describe = source.readString();
        this.headFileName = source.readString();
        this.fileName = source.readString();
        this.contentLength = source.readString();
        this.time = source.readString();
        this.contentType = source.readString();
        this.url = source.readString();
        this.downloadUrl = source.readString();
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.displayUrl = in.readString();
        this.videoUrl = in.readString();
        this.headUrl = in.readString();
        this.userName = in.readString();
        this.describe = in.readString();
        this.headFileName = in.readString();
        this.fileName = in.readString();
        this.contentLength = in.readString();
        this.time = in.readString();
        this.contentType = in.readString();
        this.url = in.readString();
        this.downloadUrl = in.readString();
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
                ", fileName='" + fileName + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", time='" + time + '\'' +
                ", contentType='" + contentType + '\'' +
                ", url='" + url + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
