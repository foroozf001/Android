package com.example.a12_rijksmuseumapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArtResponse {
    @SerializedName("elapsedMilliseconds")
    private int elapsedMilliseconds;
    @SerializedName("count")
    private int count;
    @SerializedName("countFacets")
    private Object countFacets;
    @SerializedName("facets")
    private List<Object> facets;
    @SerializedName("artObjects")
    private List<Art> artObjects;

    public int getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(int elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getCountFacets() {
        return countFacets;
    }

    public void setCountFacets(Object countFacets) {
        this.countFacets = countFacets;
    }

    public List<Object> getFacets() {
        return facets;
    }

    public void setFacets(List<Object> facets) {
        this.facets = facets;
    }

    public List<Art> getArtObjects() {
        return artObjects;
    }

    public void setArtObjects(List<Art> artObjects) {
        this.artObjects = artObjects;
    }
}
