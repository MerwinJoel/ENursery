package com.enursery.rit;

public class plant_add {
    String pId;
    String pName;
    String pLocation;
    String pDesc;
    String pImage;
    String pUploadByID;
    String pUploadByName;
    String pOrderByName;
    String pOrderedById;

    public String getpOrderByName() {
        return pOrderByName;
    }

    public void setpOrderByName(String pOrderByName) {
        this.pOrderByName = pOrderByName;
    }

    public String getpOrderedById() {
        return pOrderedById;
    }

    public void setpOrderedById(String pOrderedById) {
        this.pOrderedById = pOrderedById;
    }

    public String getpUploadByID() {
        return pUploadByID;
    }

    public void setpUploadByID(String pUploadByID) {
        this.pUploadByID = pUploadByID;
    }

    public String getpUploadByName() {
        return pUploadByName;
    }

    public void setpUploadByName(String pUploadByName) {
        this.pUploadByName = pUploadByName;
    }


    public plant_add() {
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpLocation() {
        return pLocation;
    }

    public void setpLocation(String pLocation) {
        this.pLocation = pLocation;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public plant_add(String pId, String pName, String pLocation, String pDesc, String pImage, String pUploadByID, String pUploadByNmae) {
        this.pId = pId;
        this.pName = pName;
        this.pLocation = pLocation;
        this.pDesc = pDesc;
        this.pImage = pImage;
        this.pUploadByName = pUploadByNmae;
        this.pUploadByID = pUploadByID;
    }
}


