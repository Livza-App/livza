package com.example.livza;

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