package com.example.livza.FireClasses;

import java.util.Objects;

//this class reference the child(categorie_item.xml) of categories Recyclerview of activity_menu.xml
public class Categorieitem {
    private String imageid;
    private String categorie;
    private String Key;

    public Categorieitem(String imageid, String categorie,String Key) {
        this.imageid = imageid;
        this.categorie = categorie;
        this.Key=Key;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorieitem)) return false;
        Categorieitem that = (Categorieitem) o;
        return Objects.equals(imageid, that.imageid) &&
                Objects.equals(categorie, that.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageid, categorie);
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}