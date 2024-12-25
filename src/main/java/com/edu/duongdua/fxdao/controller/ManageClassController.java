package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageClassController extends Controller implements Initializable
{
    @FXML
    private TilePane tilePane;

    // Xử lý logic
    // Khởi tạo class box
    public void initClassBox(String className, String teacherName, int totalStudents)
    {
        VBox vbox = new VBox();
        vbox.setMinSize(240, 150);
        vbox.setPrefSize(240, 150);
        vbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");

        Label label = new Label(className);
        label.setMinSize(240, 46);
        label.setPrefSize(240, 46);
        label.setFont(new Font("Roboto", 24));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setCursor(Cursor.HAND);
//        label.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickLoadListLessons);
        label.setStyle("-fx-font-weight: bold; -fx-background-color:  #F05454; -fx-background-radius: 8 8 0 0");

        HBox hbox = new HBox();
        hbox.setMinSize(200, 100);
        hbox.setPrefSize(200, 100);

        VBox vboxText = new VBox();
        vboxText.setMinSize(190, 82);
        vboxText.setPrefSize(190, 82);
        vboxText.setPadding(new Insets(0, 0, 0, 12));
        vboxText.setStyle("-fx-background-color:  #ffffff; -fx-background-radius: 6");
        HBox.setMargin(vboxText, new Insets(10, 0, 10, 10));

        Label teacherNameLabel = new Label();
        teacherNameLabel.setText("Giáo viên: " + teacherName);
//        teacherNameLabel.setText("Giáo viên: " + "TEST");
        teacherNameLabel.setMinSize(190, 41);
        teacherNameLabel.setFont(new Font(14));

        Label totalStudentsLabel = new Label();
        totalStudentsLabel.setText("Học sinh: " + totalStudents + " em");
//        totalStudentsLabel.setText("Học sinh: " + "TEST" + " em");
        totalStudentsLabel.setMinSize(190, 41);
        totalStudentsLabel.setFont(new Font(14));

        vboxText.getChildren().addAll(teacherNameLabel, totalStudentsLabel);

        VBox vboxIcon = new VBox();
        AnchorPane editIconContainer = new AnchorPane();
//        editIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickEditClass);
        editIconContainer.setMinSize(40, 52);
        editIconContainer.setCursor(Cursor.HAND);
        FontIcon editIcon = new FontIcon();
        editIcon.setIconLiteral("fas-pen");
        editIcon.setLayoutX(10);
        editIcon.setLayoutY(35);
        editIcon.setIconColor(Color.WHITE);
        editIcon.setIconSize(20);
        editIconContainer.getChildren().add(editIcon);

        AnchorPane trashIconContainer = new AnchorPane();
//        trashIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickRemoveClass);
        trashIconContainer.setMinSize(40, 52);
        trashIconContainer.setCursor(Cursor.HAND);
        FontIcon trashIcon = new FontIcon();
        trashIcon.setIconLiteral("fas-trash-alt");
        trashIcon.setLayoutX(10);
        trashIcon.setLayoutY(30);
        trashIcon.setIconColor(Color.WHITE);
        trashIcon.setIconSize(20);
        trashIconContainer.getChildren().add(trashIcon);

        vboxIcon.getChildren().addAll(editIconContainer, trashIconContainer);

        hbox.getChildren().addAll(vboxText, vboxIcon);

        vbox.getChildren().addAll(label, hbox);

        tilePane.getChildren().add(vbox);
    }
    // Hiển thị lớp có trong database
    public void displayClass()
    {
        List<Classes> classData = getClassData();
        for (Classes _class : classData)
        {
            initClassBox(_class.getClassName(), _class.getClassTeacherName(), _class.getClassTotalStudents());
        }
    }

    // Xử lý data
    // Hàm trả về mảng object chứa tên giáo viên & tổng học sinh theo lớp
    public List<Classes> getClassData()
    {
        List<Classes> data = new ArrayList<>();
        for (Classes _class : classesList)
        {
            Classes classObj = new Classes();
            // Lấy tên lớp & tên giáo viên
            for (Account teacher : teachersList)
            {
                if (_class.getClassTeacherId() == teacher.getId())
                {
                    classObj.setClassName(_class.getClassName());
                    classObj.setClassTeacherName(teacher.getName());
                }
            }
            // Lấy tổng số học sinh có trong lớp
            int count = 0;
            for (Account student : studentsList)
            {
                if (_class.getClassId() == student.getClassId())
                {
                    count += 1;
                }
                classObj.setClassTotalStudents(count);
            }
            data.add(classObj);
        }
        return data;
    }

    // Xử lý event
    // Thêm mới lớp
    public void onActionAddClass()
    {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/AddClassModal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnCloseRequest((event) -> {
                tilePane.getChildren().clear();
                displayClass();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classesList = classesDao.getAllClasses();
        teachersList = accountDAO.getAllTeacher();
        studentsList = accountDAO.getAllStudent();

        displayClass();
    }
}
