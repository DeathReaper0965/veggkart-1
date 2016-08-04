package com.example.prince.vegkart;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aniPC on 16-Jun-16.
 */
public class Product {
  @SerializedName("proname")
  private String productName;
  @SerializedName("price")
  private String price;
  @SerializedName("quantity")
  private String quantity;
  @SerializedName("catname")
  private String category;
  @SerializedName("stock")
  private String stock;
  @SerializedName("photo")
  private String photoPath;

  public Product() {
    this.quantity = "0";

  }

  public Product(String productName, String description, String price, String category) {
    this.productName = productName;
    this.price = price;
    this.quantity = "0";
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return ((new Gson()).toJson(this));
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }
}

