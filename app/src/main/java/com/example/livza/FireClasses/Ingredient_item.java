package com.example.livza.FireClasses;

public class Ingredient_item {
    private int checkbox;
    private String name;

    public Ingredient_item(int checkbox, String name) {
        this.checkbox = checkbox;
        this.name = name;
    }

    public Ingredient_item() {
    }

    public int getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(int checkbox) {
        this.checkbox = checkbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
