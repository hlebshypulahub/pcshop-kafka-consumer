package com.capgemini.pcshopoe.kafka.consumer;

import java.util.Collection;

public class Order {

    private int orderId;
    private Collection<Integer> parts;

    public Order() {
    }

    public Order(Collection<Integer> parts) {
        this.parts = parts;
    }

    public int getOrderId() {
        return orderId;
    }

    public Collection<Integer> getParts() {
        return parts;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setParts(Collection<Integer> parts) {
        this.parts = parts;
    }
}
