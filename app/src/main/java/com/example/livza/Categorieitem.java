package com.example.livza;
//this class reference the child(categorie_item.xml) of categories Recyclerview of activity_menu.xml
public class Categorieitem {
    private int imageid;
    private String categorie;

    public Categorieitem(int imageid, String categorie) {
        this.imageid = imageid;
        this.categorie = categorie;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}