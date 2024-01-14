package com.example.myapplication001.Bean;

import java.io.Serializable;

public class Products implements Serializable {
    protected int id;
    protected String goods_name;
    protected String goods_introduce;
    protected String description;
    protected float goods_price;
    protected String goods_logo;
    public Products(String goods_name, String goods_introduce, String description, float goods_price, String goods_logourl) {
        this.goods_name = goods_name;
        this.goods_introduce = goods_introduce;
        this.description = description;
        this.goods_price = goods_price;
        this.goods_logo = goods_logourl;
    }

    public Products() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getgoods_name() {
        return goods_name;
    }

    public void setgoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getgoods_introduce() {
        return goods_introduce;
    }

    public void setgoods_introduce(String goods_introduce) {
        this.goods_introduce = goods_introduce;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getgoods_price() {
        return goods_price;
    }

    public void setgoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public String getgoods_logo() {
        return goods_logo;
    }

    public void setgoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }




}
