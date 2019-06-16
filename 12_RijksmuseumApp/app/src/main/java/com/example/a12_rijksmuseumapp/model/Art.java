package com.example.a12_rijksmuseumapp.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class Art {
    @SerializedName("links")
    private Object links;
    @SerializedName("id")
    private String id;
    @SerializedName("objectNumber")
    private String objectNumber;
    @SerializedName("title")
    private String title;
    @SerializedName("hasImage")
    private boolean hasImage;
    @SerializedName("principalOrFirstMaker")
    private String principalOrFirstMaker;
    @SerializedName("longTitle")
    private String longTitle;
    @SerializedName("showImage")
    private boolean showImage;
    @SerializedName("permitDownload")
    private boolean permitDownload;
    @SerializedName("webImage")
    private Object webImage;
    @SerializedName("headerImage")
    private Object headerImage;
    @SerializedName("productionPlaces")
    private List<String> productionPlaces;

    private String webImageUrl;
    private String headerImageUrl;

    public Art(Object links, String id, String objectNumber, String title, boolean hasImage,
               String principalOrFirstMaker, String longTitle, boolean showImage, boolean permitDownload,
               Object webImage, Object headerImage, List<String> productionPlaces) {
        this.links = links;
        this.id = id;
        this.objectNumber = objectNumber;
        this.title = title;
        this.hasImage = hasImage;
        this.principalOrFirstMaker = principalOrFirstMaker;
        this.longTitle = longTitle;
        this.showImage = showImage;
        this.permitDownload = permitDownload;
        this.webImage = webImage;
        this.headerImage = headerImage;
        this.productionPlaces = productionPlaces;
    }

    public Art() {

    }

    public String getWebImageUrl() {
        if (webImage == null && !webImageUrl.isEmpty()) {
            return webImageUrl;
        } else {
            return ((LinkedTreeMap) this.webImage).get("url").toString();
        }
    }

    public void setWebImageUrl(String webImageUrl) {
        this.webImageUrl = webImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getHeaderImageUrl() {
        if (headerImage == null && !headerImageUrl.isEmpty()) {
            return headerImageUrl;
        } else {
            return ((LinkedTreeMap) this.headerImage).get("url").toString();
        }
    }

    public Object getLinks() {
        return this.links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectNumber() {
        return this.objectNumber;
    }

    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasImage() {
        return this.hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getPrincipalOrFirstMaker() {
        return this.principalOrFirstMaker;
    }

    public void setPrincipalOrFirstMaker(String principalOrFirstMaker) {
        this.principalOrFirstMaker = principalOrFirstMaker;
    }

    public String getLongTitle() {
        return this.longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public boolean isShowImage() {
        return this.showImage;
    }

    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
    }

    public boolean isPermitDownload() {
        return this.permitDownload;
    }

    public void setPermitDownload(boolean permitDownload) {
        this.permitDownload = permitDownload;
    }

    public Object getWebImage() {
        return this.webImage;
    }

    public void setWebImage(Object webImage) {
        this.webImage = webImage;
    }

    public Object getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Object headerImage) {
        this.headerImage = headerImage;
    }

    public List<String> getProductionPlaces() {
        return productionPlaces;
    }

    public void setProductionPlaces(List<String> productionPlaces) {
        this.productionPlaces = productionPlaces;
    }
}
