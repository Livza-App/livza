package com.example.livza;

import android.widget.ImageView;

public class User {
    private String username,phone,ImageID;
    private int id_user,id_Profile_img;

    //Constrectors
    public User(){}
    public User(int id_user,String User_name,String Phone_number){
        this.id_user=id_user;
        this.username=User_name;
        this.phone=Phone_number;
    }
    public User(int id_user,String User_name,String Phone_number,int id_Profile_img){
        this.id_user=id_user;
        this.username=User_name;
        this.phone=Phone_number;
        this.id_Profile_img=id_Profile_img;
    }

    //Geters

    public int getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public int getProfile_img() {
        return id_Profile_img;
    }

    public String getImageID(){
        return ImageID;
    }

    //Sters
    public void setUsername(String user_name) {
        username = user_name;
    }

    public void setPhone(String phone_number) {
        phone = phone_number;
    }

    public void setProfile_img(int id_Profile_img) {
        id_Profile_img = id_Profile_img;
    }

    public void setImageID(String imageID){
        ImageID=imageID;
    }
}
