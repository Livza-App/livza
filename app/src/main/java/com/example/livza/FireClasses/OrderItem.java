package com.example.livza.FireClasses;

//this class of Order Items
public class OrderItem {
    private String Id,Price,Date,Status;

    //Constrectors
    public OrderItem(String id, String price, String date, String status) {
        Id = id;
        Price = price;
        Date = date;
        Status = status;
    }

    public OrderItem() {
    }

    //in case of an order Canceld
    public OrderItem(String id, String date, String status) {
        Id = id;
        Date = date;
        Status = status;
    }

    //Seters & geters


    public String getId() {
        return Id;
    }

    public String getPrice() {
        return Price;
    }

    public String getDate() {
        return Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
