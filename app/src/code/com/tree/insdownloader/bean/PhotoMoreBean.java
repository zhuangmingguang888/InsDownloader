package com.tree.insdownloader.bean;

public class PhotoMoreBean {

    private int name;
    private int photo;

    public PhotoMoreBean(int name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
