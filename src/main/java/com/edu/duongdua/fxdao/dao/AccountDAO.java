package com.edu.duongdua.fxdao.dao;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends Account {


    private Connection conn = super.getConnection();

    //Student DAO
    public List<Account> getAllStudent(){
        List<Account> students = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE role = 4";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Account student = new Account();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setGender(rs.getString("gender"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                student.setPName(rs.getString("parents_name"));
                student.setPEmail(rs.getString("parents_email"));
                student.setPPhone(rs.getString("parents_phone"));
                student.setFee(rs.getInt("fee"));
                student.setClassId(rs.getInt("class_id"));
                student.setStatus(rs.getInt("status") == 1 ? "Đang hoạt động" : "Dừng hoạt động");
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public void addStudent(Account student){
        String sql = "INSERT INTO accounts (name, age, gender, email, password, role, phone, address, class_id, parents_name, parents_phone, parents_email, fee, status)" +
                " VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPassword());
            ps.setInt(6, 4);
            ps.setString(7, student.getPhone());
            ps.setString(8, student.getAddress());
            ps.setInt(9, student.getClassId());
            ps.setString(10, student.getPName());
            ps.setString(11, student.getPPhone());
            ps.setString(12, student.getPEmail());
            ps.setInt(13, student.getFee());
            ps.setInt(14, student.getStatus().equals("Đang hoạt động") ? 1 : 2);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Account findStudentById(int id){
        String sql = "SELECT * FROM accounts WHERE id = '"+id+"'";
        PreparedStatement ps;
        Account student = new Account();
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setGender(rs.getString("gender"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                student.setPName(rs.getString("parents_name"));
                student.setPEmail(rs.getString("parents_email"));
                student.setPPhone(rs.getString("parents_phone"));
                student.setFee(rs.getInt("fee"));
                student.setClassId(rs.getInt("class_id"));
                student.setStatus(rs.getInt("status") == 1 ? "Đang hoạt động" : "Dừng hoạt động");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return student;
    }
    public void deleteStudent(int id){
        String sql = "UPDATE accounts SET status = 2 WHERE id = '"+id+"'";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Teacher DAO
    public List<Account> getAllTeacher(){
        List<Account> teachers = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE role = 2";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Account teacher = new Account();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setAge(rs.getInt("age"));
                teacher.setGender(rs.getString("gender"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPassword(rs.getString("password"));
                teacher.setPhone(rs.getString("phone"));
                teacher.setAddress(rs.getString("address"));
                teacher.setCertificates(rs.getString("certificates"));
                teacher.setSalary(rs.getInt("salary"));
                teacher.setStatus(rs.getInt("status") == 1 ? "Đang hoạt động" : "Dừng hoạt động");
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }
    public void addTeacher(Account teacher){

    }

}