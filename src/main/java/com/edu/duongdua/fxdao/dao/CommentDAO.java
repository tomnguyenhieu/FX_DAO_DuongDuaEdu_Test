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

    // Tìm comment bằng id
    public Comment findByID(int commentId)
    {
        Comment comment = new Comment();
        String sql = "SELECT * FROM comments WHERE id = " +commentId;
        PreparedStatement ps;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                comment.setId(rs.getInt("id"));
                comment.setStudentId(rs.getInt("student_id"));
                comment.setLessonId(rs.getInt("lesson_id"));
                comment.setComment(rs.getString("comment"));
                comment.setScore(rs.getInt("score"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    // Thêm mới comment vào trong database
    public boolean storeClass(Comment comment)
    {
        String sql = "INSERT INTO comments("
                + "student_id, lesson_id, comment, score)"
                + " VALUES(?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, comment.getId());
            ps.setInt(2, comment.getStudentId());
            ps.setInt(3, comment.getLessonId());
            ps.setString(4, comment.getComment());
            ps.setInt(5, comment.getScore());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return false;
        }
    }
}
