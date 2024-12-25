package com.edu.duongdua.fxdao.dao;

import com.edu.duongdua.fxdao.model.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassesDAO extends Classes
{
    private Connection conn = super.getConnection();

    // Lấy danh sách các lớp có trong database
    public List<Classes> getAllClasses()
    {
        List<Classes> classes = new ArrayList<>();
        String sql = "SELECT * FROM classes WHERE deleted = 1";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Classes _class = new Classes();
                _class.setClassId(rs.getInt("id"));
                _class.setClassTeacherId(rs.getInt("teacher_id"));
                _class.setClassName(rs.getString("name"));
                _class.setClassDeleted(rs.getInt("deleted"));
                classes.add(_class);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classes;
    }

    // Thêm mới lớp vào trong database
    public boolean storeClass(Classes _class)
    {
        String sql = "INSERT INTO classes(teacher_id, name, deleted) VALUES(?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, _class.getClassTeacherId());
            ps.setString(2, _class.getClassName());
            ps.setInt(3, _class.getClassDeleted());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }
}
