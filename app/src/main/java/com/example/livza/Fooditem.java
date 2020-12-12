package com.example.livza;
//this class reference the child(menu_item.xml) of menuitems listview of activity_menu.xml
public class Fooditem {
    private String imgid,ingredients;
    private String time,title,subtitle;
    private float price,rating;

    public Fooditem(String imgid, String time, float price, String title, String subtitle, float rating) {
        this.imgid = imgid;
        this.time = time;
        this.price = price;
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
