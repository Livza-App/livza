package com.example.livza;

public class Fooditem {
    private int imgid;
    private String time,price,title,subtitle,rating;

    public Fooditem(int imgid, String time, String price, String title, String subtitle, String rating) {
        this.imgid = imgid;
        this.time = time;
        this.price = price;
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
