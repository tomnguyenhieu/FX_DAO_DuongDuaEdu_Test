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


}