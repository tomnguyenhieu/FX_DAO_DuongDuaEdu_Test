package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddClassController extends ClassesDAO implements Initializable
{
    private ClassesDAO dao;
    private List<Classes> classes;

    @FXML
    private TextField inputClass;
    @FXML
    private ComboBox<Integer> cbTeachersName;

    // Khởi tạo combo box tên các giáo viên có trong database
    public void initCBTeacherName()
    {
        for (Classes _class : classes)
        {
            cbTeachersName.getItems().add(_class.getClassTeacherId());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new ClassesDAO();
        classes = dao.getAllClasses();

        initCBTeacherName();
    }
}
