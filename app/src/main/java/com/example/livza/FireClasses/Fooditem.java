package com.example.livza.FireClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

//this class reference the child(menu_item.xml) of menuitems listview of activity_menu.xml
public class Fooditem {
    private String imgid,ingredients;
    private String time,title,subtitle;
    private String price,rating;

    public Fooditem(){

    }

    public Fooditem(String imgid, String ingredients, String time, String price, String title, String subtitle, String rating) {
        this.imgid = imgid;
        this.time = time;
        this.price = price;
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
        this.ingredients=ingredients;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fooditem)) return false;
        Fooditem fooditem = (Fooditem) o;
        return Objects.equals(imgid, fooditem.imgid) &&
                Objects.equals(ingredients, fooditem.ingredients) &&
                Objects.equals(time, fooditem.time) &&
                Objects.equals(title, fooditem.title) &&
                Objects.equals(subtitle, fooditem.subtitle) &&
                Objects.equals(price, fooditem.price) &&
                Objects.equals(rating, fooditem.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgid, ingredients, time, title, subtitle, price, rating);
    }

    public ArrayList<String> ingredientsArray(){
        return new ArrayList<String>(Arrays.asList(ingredients.split("\\s*-\\s*")));
    }
}