package com.lee.woosuk.DTOs;

import android.graphics.drawable.Drawable;

public class ImgDTO {
    private Drawable icon;
    private String userName;
    private String message;
    private String time;
    private String img;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ImgDTO() {}
    public ImgDTO(String userName, String message, String time, String img) {
        this.userName = userName;
        this.message = message;
        this.time = time;
        this.img = img;
    }

}
