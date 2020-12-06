package com.example.livza;

import android.widget.ImageView;

public class User {
    private String User_name,Phone_number;
    private int id_user,id_Profile_img;

    //Constrectors
    public User(){}
    public User(int id_user,String User_name,String Phone_number){
        this.id_user=id_user;
        this.User_name=User_name;
        this.Phone_number=Phone_number;
    }
    public User(int id_user,String User_name,String Phone_number,int id_Profile_img){
        this.id_user=id_user;
        this.User_name=User_name;
        this.Phone_number=Phone_number;
        this.id_Profile_img=id_Profile_img;
    }


    //Geters

    public int getId_user() {
        return id_user;
    }

    public String getUser_name() {
        return User_name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public int getProfile_img() {
        return id_Profile_img;
    }

    //Sters
    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public void setProfile_img(int id_Profile_img) {
        id_Profile_img = id_Profile_img;
    }
}
