package com.edu.duongdua.fxdao.model;

public class Account extends Model{
    private int id;
    private String email;
    private String password;
    private int role;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;
    private String certificates;
    private int salary;
    private int classId;
    private String className;
    private String pName;
    private String pEmail;
    private String pPhone;
    private int fee;
    private String status;

    private String createdAt;
    private String updatedAt;

    public Account(int id, String email, String password, String name, int age, String gender, String phone, String address, int classId, String pName, String pEmail, String pPhone, int fee, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.classId = classId;
        this.pName = pName;
        this.pEmail = pEmail;
        this.pPhone = pPhone;
        this.fee = fee;
        this.status = status;
    }

    public Account(){
        super();
    }

    public Account(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }


    //Student contructor
    public Account(int id, String name, int age, String gender, String email, String password, String phone, String address, String pName, String pPhone, String pEmail, int fee, String className, String status) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.pName = pName;
        this.pPhone = pPhone;
        this.pEmail = pEmail;
        this.fee = fee;
        this.className = className;
        this.status = status;
    }

    //Teacher contructor
    public Account(int id, String name, int age, String gender, String email, String password, String phone, String address, String certificates, int salary, String status) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.certificates = certificates;
        this.salary = salary;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPEmail() {
        return pEmail;
    }

    public void setPEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getPPhone() {
        return pPhone;
    }

    public void setPPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
