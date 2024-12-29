package com.edu.duongdua.fxdao.dao;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Lesson;

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
    public void updateStudent(Account student){
        String sql = "UPDATE accounts SET name = ?, age = ?, gender = ?, email = ?, password = ?, role = ?, phone = ?, address = ?, class_id = ?, parents_name = ?, parents_phone = ?, parents_email = ?, fee = ?, status = ? WHERE id = ?";
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
            ps.setInt(15, student.getId());
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

    //Employee DAO
    public List<Account> getAllEmployee(){
        List<Account> teachers = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE role = 3";
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
    public void addEmployee(Account employee){
        String sql = "INSERT INTO accounts (name, age, gender, email, password, role, phone, address, status, certificates, salary)" +
                " VALUE (?,?,?,?,?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setInt(2, employee.getAge());
            ps.setString(3, employee.getGender());
            ps.setString(4, employee.getEmail());
            ps.setString(5, employee.getPassword());
            ps.setInt(6, 3);
            ps.setString(7, employee.getPhone());
            ps.setString(8, employee.getAddress());
            ps.setInt(9, employee.getStatus().equals("Đang hoạt động") ? 1 : 2);
            ps.setString(10, employee.getCertificates());
            ps.setInt(11, employee.getSalary());
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateEmployee(Account employee){
        String sql = "UPDATE accounts " +
                "SET NAME = ?, age = ?, gender = ?, email = ?, PASSWORD = ?, phone = ?, address = ?, status = ?, certificates = ?, salary = ? WHERE id = ?";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setInt(2, employee.getAge());
            ps.setString(3, employee.getGender());
            ps.setString(4, employee.getEmail());
            ps.setString(5, employee.getPassword());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getAddress());
            ps.setInt(8, employee.getStatus().equals("Đang hoạt động") ? 1 : 2);
            ps.setString(9, employee.getCertificates());
            ps.setInt(10, employee.getSalary());
            ps.setInt(11, employee.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Account findEmployeeById(int id){
        String sql = "SELECT * FROM accounts WHERE id = '"+id+"'";
        PreparedStatement ps;
        Account employee = new Account();
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setAge(rs.getInt("age"));
                employee.setGender(rs.getString("gender"));
                employee.setEmail(rs.getString("email"));
                employee.setPassword(rs.getString("password"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setStatus(rs.getInt("status") == 1 ? "Đang hoạt động" : "Dừng hoạt động");
                employee.setCertificates(rs.getString("certificates"));
                employee.setSalary(rs.getInt("salary"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
    public void deleteEmployee(int id){
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
                teacher.setRole(2);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }
    public void addTeacher(Account teacher){
        String sql = "INSERT INTO accounts (name, age, gender, email, password, role, phone, address, status, certificates, salary)" +
                " VALUE (?,?,?,?,?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getName());
            ps.setInt(2, teacher.getAge());
            ps.setString(3, teacher.getGender());
            ps.setString(4, teacher.getEmail());
            ps.setString(5, teacher.getPassword());
            ps.setInt(6, 2);
            ps.setString(7, teacher.getPhone());
            ps.setString(8, teacher.getAddress());
            ps.setInt(9, teacher.getStatus().equals("Đang hoạt động") ? 1 : 2);
            ps.setString(10, teacher.getCertificates());
            ps.setInt(11, teacher.getSalary());
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTeacher(Account teacher){
        String sql = "UPDATE accounts " +
                     "SET NAME = ?, age = ?, gender = ?, email = ?, PASSWORD = ?, phone = ?, address = ?, status = ?, certificates = ?, salary = ? WHERE id = ?";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getName());
            ps.setInt(2, teacher.getAge());
            ps.setString(3, teacher.getGender());
            ps.setString(4, teacher.getEmail());
            ps.setString(5, teacher.getPassword());
            ps.setString(6, teacher.getPhone());
            ps.setString(7, teacher.getAddress());
            ps.setInt(8, teacher.getStatus().equals("Đang hoạt động") ? 1 : 2);
            ps.setString(9, teacher.getCertificates());
            ps.setInt(10, teacher.getSalary());
            ps.setInt(11, teacher.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Account findTeacherById(int id){
        String sql = "SELECT * FROM accounts WHERE id = '"+id+"'";
        PreparedStatement ps;
        Account teacher = new Account();
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setAge(rs.getInt("age"));
                teacher.setGender(rs.getString("gender"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPassword(rs.getString("password"));
                teacher.setPhone(rs.getString("phone"));
                teacher.setAddress(rs.getString("address"));
                teacher.setStatus(rs.getInt("status") == 1 ? "Đang hoạt động" : "Dừng hoạt động");
                teacher.setCertificates(rs.getString("certificates"));
                teacher.setSalary(rs.getInt("salary"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }
    public void deleteTeacher(int id){
        String sql = "UPDATE accounts SET status = 2 WHERE id = '"+id+"'";
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Account getTeacherData(Lesson lesson)
    {
        String sql = "SELECT DISTINCT accounts.id AS account_id, accounts.name AS teacher_name, accounts.salary, "
                + "COUNT(lessons.id) AS lesson_count, "
                + "CAST(DATE_FORMAT(STR_TO_DATE(lessons.title, '%d/%m/%Y'), '%m/%Y') AS CHAR) AS month "
                + "FROM classes JOIN lessons ON classes.id = lessons.class_id "
                + "LEFT JOIN accounts ON classes.teacher_id = accounts.id "
                + "WHERE classes.name = '" +lesson.getClassName()+ "' AND classes.deleted = 1 "
                + "GROUP BY accounts.id, accounts.name, accounts.salary, month";
        PreparedStatement ps;
        Account teacher = new Account();
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                teacher.setId(rs.getInt("account_id"));
                teacher.setName(rs.getString("teacher_name"));
                teacher.setSalary(rs.getInt("salary"));
                teacher.setLessonCount(rs.getInt("lesson_count"));
                teacher.setTime(rs.getString("month"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }
    public Account getTeacherLessonsPerMonth(int teacherId, int year)
    {
        Account teacher = new Account();
        String sql = "SELECT a.id AS teacher_id, a.name AS teacher_name, "
                + "MONTH(STR_TO_DATE(l.title, '%d/%m/%Y')) AS month, COUNT(*) AS total_lessons "
                + "FROM lessons l JOIN classes c ON l.class_id = c.id JOIN accounts a "
                + "ON c.teacher_id = a.id WHERE a.id = " +teacherId+ " AND l.title LIKE '%/" +year+ "' "
                + "GROUP BY a.id, a.name, MONTH(STR_TO_DATE(l.title, '%d/%m/%Y')) ORDER BY a.id, month";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                teacher.setId(rs.getInt("teacher_id"));
                teacher.setName(rs.getString("teacher_name"));
                teacher.setTime(rs.getString("month"));
                teacher.setLessonCount(rs.getInt("total_lessons"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }
}