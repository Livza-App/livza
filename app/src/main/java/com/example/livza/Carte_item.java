package com.example.livza;
//This class for items on the Cart layout "activity_cart_order"
public class Carte_item {
    private String imgid;
    private  String itm_name,item_price,itm_qte,ingredient;


    public Carte_item(String imgid, String itm_name, String item_price, String itm_qte, String ingredient) {
        this.imgid = imgid;
        this.itm_name = itm_name;
        this.item_price = item_price;
        this.itm_qte = itm_qte;
        this.ingredient=ingredient;
    }

    public Carte_item() {
    }

    public String getImgid() {
        return imgid;
    }

    public String getItm_name() {
        return itm_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public String getItm_qte() {
        return itm_qte;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public void setItm_name(String itm_name) {
        this.itm_name = itm_name;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public void setItm_qte(String itm_qte) {
        this.itm_qte = itm_qte;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
