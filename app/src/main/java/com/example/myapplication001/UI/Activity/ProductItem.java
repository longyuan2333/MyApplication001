package com.example.myapplication001.UI.Activity;


import com.example.myapplication001.Bean.Products;

public class ProductItem extends Products {
    public ProductItem(Products product) {
        this.id = product.getId();
        this.goods_name = product.getgoods_name();
        this.goods_introduce = product.getgoods_introduce();
        this.description = product.getDescription();
        this.goods_price = product.getgoods_price();
        this.goods_logo = product.getgoods_logo();
    }

    public ProductItem() {
    }

    public ProductItem(String goods_name, String goods_introduce, String description, float goods_price, String goods_logourl) {
        super(goods_name, goods_introduce, description, goods_price, goods_logourl);

    }

    private  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
