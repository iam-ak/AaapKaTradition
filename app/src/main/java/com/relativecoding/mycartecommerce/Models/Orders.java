package com.relativecoding.mycartecommerce.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Orders {

    String orderId;
    ArrayList itemId;
    String orderDate;
    String totalPrice;

    public Orders(String orderId, ArrayList itemId, String orderDate, String totalPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Orders() {
    }

    public ArrayList getItemId() {
        return itemId;
    }

    public void setItemId(ArrayList itemId) {
        this.itemId = itemId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
