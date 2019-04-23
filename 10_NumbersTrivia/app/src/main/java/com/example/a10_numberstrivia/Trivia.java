package com.example.a10_numberstrivia;
import com.google.gson.annotations.SerializedName;

public class Trivia {

    @SerializedName("text")
    private String text;

    @SerializedName("number")
    private int number;

    @SerializedName("found")
    private boolean found;

    @SerializedName("type")
    private String type;

    public Trivia(String text, int number, boolean found, String type) {
        this.text = text;
        this.number = number;
        this.found = found;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}