package com.edu.duongdua.fxdao.dao;

import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends Comment
{
    private Connection conn = super.getConnection();

    // Lấy toàn bộ comments có trong database
    public List<Comment> getAllComments()
    {
        List<Comment> commentsList = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setStudentId(rs.getInt("student_id"));
                comment.setLessonId(rs.getInt("lesson_id"));
                comment.setComment(rs.getString("comment"));
                comment.setScore(rs.getInt("score"));
                commentsList.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentsList;
    }

    // Thêm mới comment vào trong database
    public boolean storeComment(Comment comment)
    {
        String sql = "INSERT INTO comments("
                + "student_id, lesson_id, comment, score)"
                + " VALUES(?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, comment.getStudentId());
            ps.setInt(2, comment.getLessonId());
            ps.setString(3, comment.getComment());
            ps.setInt(4, comment.getScore());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    // Lấy toàn bộ comments theo lessons (lessonId, studentName, comment, score)
    public List<Comment> getLessonComments(int lessonId)
    {
        List<Comment> commentsList = new ArrayList<>();
        String sql = "SELECT lessons.id AS lesson_id, accounts.id AS student_id, accounts.name AS student_name, comments.comment, comments.score "
            +"FROM lessons JOIN comments ON lessons.id = comments.lesson_id "
            + "JOIN accounts ON accounts.id = comments.student_id "
            + "WHERE lessons.id = " + lessonId;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Comment comment = new Comment();
                comment.setId(rs.getInt("lesson_id"));
                comment.setStudentId(rs.getInt("student_id"));
                comment.setStudentName(rs.getString("student_name"));
                comment.setComment(rs.getString("comment"));
                comment.setScore(rs.getInt("score"));
                commentsList.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentsList;
    }
}
