package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddClassController extends Controller implements Initializable
{
    private String tmpClassName;
    private boolean edit = false;

    public String getTmpClassName() {
        return tmpClassName;
    }

    public void setTmpClassName(String tmpClassName) {
        this.tmpClassName = tmpClassName;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    @FXML
    private TextField inputClass;
    @FXML
    private ComboBox<String> cbTeachersName;

    // Khởi tạo combo box tên các giáo viên có trong database
    public void initCBTeacherName()
    {
        for (Account teacher : accountDAO.getAllTeacher())
        {
            if (teacher.getStatus().equals("Đang hoạt động"))
            {
                cbTeachersName.getItems().add(teacher.getName());
            }
        }
    }

    // Thiết lập form edit lớp
    public void initEditClassForm(String className, String teacherName)
    {
        inputClass.setText(className);
        cbTeachersName.setValue(teacherName.substring(11));
    }

    // Hàm thêm mới lớp vào database
    public void storeClass()
    {
        Classes classObj = new Classes();
        classObj.setClassName(inputClass.getText());
        classObj.setClassDeleted(1);

        if (cbTeachersName.getValue() != null)
        {
            for (Account teacher : accountDAO.getAllTeacher())
            {
                if (teacher.getName().equals(cbTeachersName.getValue()))
                {
                    classObj.setClassTeacherId(teacher.getId());
                }
            }
        } else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui long chon giao vien!");
            alert.show();
        }
        classesDao.storeClass(classObj);
    }

    // Event đóng
    public void close(ActionEvent event){
        // phat event dong window
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    // Hàm xử lý click vào nút Confirm (Thêm mới lớp/Sửa thông tin lớp)
    public void onActionConfirm(ActionEvent event)
    {
        if (!edit)
        {
            storeClass();
            close(event);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Them moi thanh cong!");
            alert.show();
        } else
        {
            Classes classObj = classesDao.findByName(tmpClassName);
            classObj.setClassName(inputClass.getText());

            for (Account teacher : accountDAO.getAllTeacher())
            {
                if (cbTeachersName.getValue().equals(teacher.getName()))
                {
                    classObj.setClassTeacherId(teacher.getId());
                }
            }

            classesDao.updateClass(classObj);
            close(event);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Sua thanh cong!");
            alert.show();
        }
    }

    // Hàm xử lý click vào nút Cancel
    public void onActionCancel(ActionEvent event)
    {
        close(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCBTeacherName();
    }
}
