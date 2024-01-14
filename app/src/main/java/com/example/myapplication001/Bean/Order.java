package com.example.myapplication001.Bean;

import java.io.Serializable;

public class Order implements Serializable {
//  'id','goods_name','goods_price','goods_log o','number','create_time'
private String goods_price;
private String goods_name;
private String goods_logo;
private int number;
private int id;
private String create_time;

    public Order(String goods_price, String goods_name, String goods_logo, int number, int id, String create_time) {
        this.goods_price = goods_price;
        this.goods_name = goods_name;
        this.goods_logo = goods_logo;
        this.number = number;
        this.id = id;
        this.create_time = create_time;
    }

    public Order() {
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


}
