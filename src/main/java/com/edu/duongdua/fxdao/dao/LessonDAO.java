package com.edu.duongdua.fxdao.dao;

import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Comment;
import com.edu.duongdua.fxdao.model.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO extends Lesson
{
    private Connection conn = super.getConnection();

    // Lấy tất cả lessons trong database
    public List<Lesson> getAllLessons()
    {
        List<Lesson> lessonList = new ArrayList<>();
        String sql = "SELECT * FROM lessons";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
                lessonList.add(lesson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lessonList;
    }

    // Tìm thông tin lesson bằng id của lớp
    public Lesson findByID(int lessonId)
    {
        Lesson lesson = new Lesson();
        String sql = "SELECT * FROM lessons WHERE id = " +lessonId;
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                lesson.setId(rs.getInt("id"));
                lesson.setClassId(rs.getInt("class_id"));
                lesson.setTitle(rs.getString("title"));
                lesson.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    // Thêm mới lesson vào trong database
    public boolean storeLesson(Lesson lesson)
    {
        String sql = "INSERT INTO lessons("
                + "class_id, title, content)"
                + " VALUES(?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, lesson.getId());
            ps.setInt(2, lesson.getClassId());
            ps.setString(3, lesson.getTitle());
            ps.setString(4, lesson.getContent());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    // Xóa lớp trong database
    public boolean deleteLesson(Lesson lesson)
    {
        String sql = "DELETE FROM lessons WHERE id = " +lesson.getId();
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
