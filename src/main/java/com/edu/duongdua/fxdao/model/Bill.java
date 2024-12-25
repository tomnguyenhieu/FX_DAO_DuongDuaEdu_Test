package com.edu.duongdua.fxdao.model;

public class Bill {
    private int id;
    private String month;
    private int lessonQty;
    private int monthlySalary;
    private String name;
    private String bClass;
    private String date;
    private int price;
    private String status;

    //Teacher bill
    public Bill(int id, String month, int lessonQty, int monthlySalary, String name, String status) {
        this.id = id;
        this.month = month;
        this.lessonQty = lessonQty;
        this.monthlySalary = monthlySalary;
        this.name = name;
        this.status = status;
    }

    //Student bill
    public Bill(int id, String name, String bClass, String date, int price, String status) {
        this.id = id;
        this.name = name;
        this.bClass = bClass;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getLessonQty() {
        return lessonQty;
    }

    public void setLessonQty(int lessonQty) {
        this.lessonQty = lessonQty;
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getbClass() {
        return bClass;
    }

    public void setbClass(String bClass) {
        this.bClass = bClass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
