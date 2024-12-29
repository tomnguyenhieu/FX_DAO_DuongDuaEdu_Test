package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewClassInfoController extends Controller
{
    @FXML
    private Label className;
    @FXML
    private Label teacherName;
    @FXML
    private TableView<Account> tblStudents;
    @FXML
    private TableColumn<Account, String> colName;
    @FXML
    private TableColumn<Account, Integer> colAge;

    // Hàm khởi tạo tên lớp & tên giáo viên
    public void initClassInfo(String classNameValue, String teacherNameValue)
    {
        className.setText(classNameValue);
        teacherName.setText("Giáo viên: " + teacherNameValue);
    }

    // Hiển thị các học sinh có trong lớp
    public void renderTblStudents(String className)
    {
        final ObservableList<Account> data = FXCollections.observableArrayList();
        List<Account> studentList = accountDAO.getAllStudent();
        for (Account student : studentList)
        {
            if (student.getClassId() == classesDao.findByName(className).getClassId())
            {
                data.add(student);
            }
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tblStudents.setItems(data);
    }

    public void close(ActionEvent event){
        // phat event dong window
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void onActionClose(ActionEvent event)
    {
        close(event);
    }
}
