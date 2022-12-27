package com.tree.insdownloader.logic.model;

public class UserProfile {
    private String headUrl;

    private String userName;

    private String describe;

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return this.describe;
    }
}
