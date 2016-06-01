package com.example.ishitasinha.foodorderingapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ishitasinha on 30/05/16.
 */
public class Items extends RealmObject {
    @PrimaryKey
    String id;
    @Required
    String name, type, imgUrl;
    String description;
    int quantity = 0;

    public Items() {

    }

    public Items(String id, String name, String description, String type, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.imgUrl = url;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
