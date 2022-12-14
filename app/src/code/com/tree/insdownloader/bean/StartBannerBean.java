package com.tree.insdownloader.bean;

public class StartBannerBean {

    private int imageId;
    private int methodName;
    private int serialNumber;
    private int operatorName;
    private int subOperatorName;

    public StartBannerBean(int imageId, int methodName, int serialNumber, int operatorName, int subOperatorName) {
        this.imageId = imageId;
        this.methodName = methodName;
        this.serialNumber = serialNumber;
        this.operatorName = operatorName;
        this.subOperatorName = subOperatorName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getMethodName() {
        return methodName;
    }

    public void setMethodName(int methodName) {
        this.methodName = methodName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(int operatorName) {
        this.operatorName = operatorName;
    }

    public int getSubOperatorName() {
        return subOperatorName;
    }

    public void setSubOperatorName(int subOperatorName) {
        this.subOperatorName = subOperatorName;
    }
}
