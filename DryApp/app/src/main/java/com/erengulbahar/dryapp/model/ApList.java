package com.erengulbahar.dryapp.model;

public class ApList
{
    public String type;
    public String id;
    public String email;
    public String name;
    public String surname;
    public String address;
    public String phone;
    public String count;
    public String price;
    public Object time;

    public ApList(String type,String id, String email, String name, String surname, String address, String phone, String count, String price, Object time)
    {
        this.type = type;
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.count = count;
        this.price = price;
        this.time = time;
    }
}