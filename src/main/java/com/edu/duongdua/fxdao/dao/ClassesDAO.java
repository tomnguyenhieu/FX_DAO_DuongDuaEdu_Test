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

    // Lấy thông tin của lớp học (tên lớp, tên giáo viên, số lượng học sinh)
    public List<Classes> getClassesInfo()
    {
        List<Classes> classesInfo = new ArrayList<>();
        String sql = "SELECT c.id, c.name AS class_name, a_teacher.name AS teacher_name, "
                + "COUNT(a_student.id) AS total_students FROM classes c "
                + "LEFT JOIN accounts a_teacher ON c.teacher_id = a_teacher.id "
                + "AND a_teacher.role = 2 LEFT JOIN accounts a_student "
                + "ON c.id = a_student.class_id AND a_student.role = 4 "
                + "WHERE a_teacher.status = 1 AND c.deleted = 1 GROUP BY c.id, c.name, a_teacher.name "
                + "ORDER BY c.id";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Classes _class = new Classes();
//                _class.setClassId(rs.getInt("id"));
                _class.setClassName(rs.getString("class_name"));
                _class.setClassTeacherName(rs.getString("teacher_name"));
                _class.setClassTotalStudents(rs.getInt("total_students"));
                classesInfo.add(_class);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classesInfo;
    }

    // Tìm thông tin lớp học bằng tên lớp
    public Classes findByName(String className)
    {
        Classes classObj = new Classes();
        String sql = "SELECT * FROM classes WHERE name = '" +className+ "' AND deleted = 1";
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                classObj.setClassId(rs.getInt("id"));
                classObj.setClassTeacherId(rs.getInt("teacher_id"));
                classObj.setClassName(rs.getString("name"));
                classObj.setClassDeleted(rs.getInt("deleted"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classObj;
    }

    // Tìm thông tin lớp học bằng id của lớp
    public Classes findByID(int classId)
    {
        Classes classObj = new Classes();
        String sql = "SELECT * FROM classes WHERE id = " +classId+ " AND deleted = 1";
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                classObj.setClassId(rs.getInt("id"));
                classObj.setClassTeacherId(rs.getInt("teacher_id"));
                classObj.setClassName(rs.getString("name"));
                classObj.setClassDeleted(rs.getInt("deleted"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classObj;
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

    // Sửa (edit/update) thông tin của lớp
    public boolean updateClass(Classes classObj)
    {
        String sql = "UPDATE classes SET name = '" +classObj.getClassName()+ "', teacher_id = '" +classObj.getClassTeacherId()+ "'  WHERE id = '" +classObj.getClassId()+ "'";
//        String sql = "UPDATE classes SET name = 'TEST' , teacher_id =   WHERE id = '" +classObj.getClassId()+ "'";
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa lớp trong database
    public void deleteClass(Classes _class)
    {
        String sql = "UPDATE classes SET deleted = 2 WHERE id = " + _class.getClassId();
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
