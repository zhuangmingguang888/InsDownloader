package com.tree.insdownloader.logic.model;

public class UserInfo {

    private String displayUrl;

    private String videoUrl;

    private UserProfile userProfile;

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getDisplayUrl() {
        return this.displayUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "displayUrl='" + displayUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", userProfile=" + userProfile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return userProfile != null ? userProfile.getUserName().equals(userInfo.userProfile.getUserName()) : userInfo.userProfile == null;
    }

    @Override
    public int hashCode() {
        return 30;
    }
}
