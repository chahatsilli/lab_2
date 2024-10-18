package com.example.lab1chahat;

public class dental_appoint {
    private int id;
    private String paintentName;
    private int phone;
    private int payment;

    public dental_appoint(int id, String paintentName, int phone, int payment) {
        this.id =id;
        this.paintentName =paintentName;
        this.phone =phone;
        this.payment =payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaintentName() {
        return paintentName;
    }

    public void setPaintentName(String paintentName) {
        this.paintentName = paintentName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
