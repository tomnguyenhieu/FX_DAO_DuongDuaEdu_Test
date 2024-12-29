package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private ComboBox<String> classStatusCb;
    private AnchorPane contentPane;

    public AnchorPane getContentPane() {
        return contentPane;
    }

    public void setContentPane(AnchorPane contentPane) {
        this.contentPane = contentPane;
    }

    // Xử lý logic
    // Khởi tạo class box
    public void initClassBox(Classes classObj, int classStatus)
    {
        VBox vbox = new VBox();
        vbox.setMinSize(240, 150);
        vbox.setPrefSize(240, 150);
        vbox.setStyle("-fx-background-color:  #30475E; -fx-background-radius: 8");

        Label label = new Label(classObj.getClassName());
        label.setMinSize(240, 46);
        label.setPrefSize(240, 46);
        label.setFont(new Font("Roboto", 24));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        if (classStatus == 1)
        {
            label.setCursor(Cursor.HAND);
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickLoadListLessons);
        }
        label.setStyle("-fx-font-weight: bold; -fx-background-color:  #F05454; -fx-background-radius: 8 8 0 0");

        HBox hbox = new HBox();
        hbox.setMinSize(200, 100);
        hbox.setPrefSize(200, 100);

        VBox vboxText = new VBox();
        vboxText.setMinSize(190, 82);
        vboxText.setPrefSize(190, 82);
        if (classStatus == 2) {
            vboxText.setMinSize(218, 82);
            vboxText.setPrefSize(218, 82);
        }
        vboxText.setPadding(new Insets(0, 0, 0, 12));
        vboxText.setStyle("-fx-background-color:  #ffffff; -fx-background-radius: 6");
        HBox.setMargin(vboxText, new Insets(10, 0, 10, 10));
        if (classStatus == 1)
        {
            vboxText.setCursor(Cursor.HAND);
            vboxText.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickViewClassInfo);
        }

        Label teacherNameLabel = new Label();
        teacherNameLabel.setText("Giáo viên: " + classObj.getClassTeacherName());
        teacherNameLabel.setMinSize(190, 41);
        teacherNameLabel.setFont(new Font(14));

        Label totalStudentsLabel = new Label();
        totalStudentsLabel.setText("Học sinh: " + classObj.getClassTotalStudents() + " em");
        totalStudentsLabel.setMinSize(190, 41);
        totalStudentsLabel.setFont(new Font(14));

        vboxText.getChildren().addAll(teacherNameLabel, totalStudentsLabel);

        VBox vboxIcon = new VBox();
        AnchorPane editIconContainer = new AnchorPane();
        if (classStatus == 1) {
            editIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickEditClass);
            editIconContainer.setMinSize(40, 52);
            editIconContainer.setCursor(Cursor.HAND);

            FontIcon editIcon = new FontIcon();
            editIcon.setIconLiteral("fas-pen");
            editIcon.setLayoutX(10);
            editIcon.setLayoutY(35);
            editIcon.setIconColor(Color.WHITE);
            editIcon.setIconSize(20);
            editIconContainer.getChildren().add(editIcon);
        }

        AnchorPane trashIconContainer = new AnchorPane();
        if (classStatus == 1) {
            trashIconContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickRemoveClass);
            trashIconContainer.setMinSize(40, 52);
            trashIconContainer.setCursor(Cursor.HAND);

            FontIcon trashIcon = new FontIcon();
            trashIcon.setIconLiteral("fas-trash-alt");
            trashIcon.setLayoutX(10);
            trashIcon.setLayoutY(30);
            trashIcon.setIconColor(Color.WHITE);
            trashIcon.setIconSize(20);
            trashIconContainer.getChildren().add(trashIcon);
        }

        vboxIcon.getChildren().addAll(editIconContainer, trashIconContainer);
        hbox.getChildren().addAll(vboxText, vboxIcon);
        vbox.getChildren().addAll(label, hbox);
        tilePane.getChildren().add(vbox);
    }

    // Khởi tạo combo box chọn classes theo status
    public void initComboBoxStatus()
    {
        classStatusCb.getItems().add("Đang hoạt động");
        classStatusCb.getItems().add("Dừng hoạt động");

        classStatusCb.setValue("Đang hoạt động");
    }

    // Hiển thị lớp có trong database
    public void displayClass(int classStatus)
    {
        List<Classes> classesInfo = classesDao.getClassesInfo(classStatus);
        for (Classes _class : classesInfo)
        {
            initClassBox(_class, classStatus);
        }
    }

    // Xử lý event
    // Thêm mới lớp
    public void onActionAddClass()
    {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddClass.fxml"));
            Parent root = loader.load();
            AddClassController addClassController = loader.getController();
            addClassController.setEdit(false);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnCloseRequest((event) -> {
                tilePane.getChildren().clear();
                displayClass(1);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Hiển thị thông tin chi tiết của lớp
    public void onMouseClickViewClassInfo(MouseEvent event)
    {
        VBox vBox = (VBox) event.getSource();
        Label teacherNameLabel = (Label) vBox.getChildren().getFirst();
        String teacherName = teacherNameLabel.getText().substring(11);
        HBox hBox = (HBox) vBox.getParent();
        VBox vBox1 = (VBox) hBox.getParent();
        Label classNameLabel = (Label) vBox1.getChildren().getFirst();
        String className = classNameLabel.getText();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_ViewClassInfo.fxml"));
            Parent root = loader.load();
            ViewClassInfoController viewClassInfoController = loader.getController();
            viewClassInfoController.initClassInfo(className, teacherName);
            viewClassInfoController.renderTblStudents(className);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Sửa thông tin của lớp
    public void onMouseClickEditClass(MouseEvent event)
    {
        AnchorPane iconContainer = (AnchorPane) event.getSource();
        VBox vboxIcons = (VBox) iconContainer.getParent();
        HBox hbox = (HBox) vboxIcons.getParent();
        VBox vboxParent = (VBox) hbox.getParent();
        Label classLabel = (Label) vboxParent.getChildren().getFirst();

        VBox vboxText = (VBox) hbox.getChildren().getFirst();
        Label teacherLabel = (Label) vboxText.getChildren().getFirst();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddClass.fxml"));
            Parent root = loader.load();
            AddClassController addClassController = loader.getController();

            addClassController.setEdit(true);
            addClassController.initEditClassForm(classLabel.getText(), teacherLabel.getText());
            addClassController.setTmpClassName(classLabel.getText());

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnCloseRequest((_event) -> {
                tilePane.getChildren().clear();
                displayClass(1);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa lớp
    public void onMouseClickRemoveClass(MouseEvent event)
    {
        AnchorPane iconContainer = (AnchorPane) event.getSource();
        VBox vboxIcons = (VBox) iconContainer.getParent();
        HBox hbox = (HBox) vboxIcons.getParent();
        VBox vboxParent = (VBox) hbox.getParent();
        Label classLabel = (Label) vboxParent.getChildren().getFirst();
        String className = classLabel.getText();

        for (Classes _class : classesDao.getAllClasses())
        {
            if (_class.getClassName().equals(className))
            {
                classesDao.deleteClass(_class);
            }
        }
        tilePane.getChildren().clear();
        displayClass(1);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Xóa thành công!");
        alert.show();
    }

    // Export mẫu excel nhật kí theo lớp
    public void onActionExportExcel()
    {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_ExportFormExcel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnCloseRequest((event) -> { });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Hàm chọn lớp muốn hiển thị (lớp đang hoạt động/đã bị xóa)
    public void onSelectStatusCb()
    {
        String _classStatus = classStatusCb.getValue();
        int classStatus = 1;
        if (_classStatus.equals("Dừng hoạt động")) {
            classStatus = 2;
        }

        tilePane.getChildren().clear();
        displayClass(classStatus);
    }

    // Hàm hiển thị danh sách các lessons theo lớp
    public void onMouseClickLoadListLessons(MouseEvent event)
    {
        Label classLabel = (Label) event.getSource();
        String className = classLabel.getText();
        Classes classObj = new Classes();
        for (Classes _class : classesDao.getAllClasses())
        {
            if (_class.getClassName().equals(className))
            {
                classObj = _class;
            }
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/Scene_ManageDiary.fxml"));
            Parent root = fxmlLoader.load();
            ManageDiaryController manageDiaryController = fxmlLoader.getController();
            manageDiaryController.displayLessons(classObj);
            manageDiaryController.setClassObj(classObj);
            manageDiaryController.setClassName(className);
            contentPane.getChildren().removeAll();
            contentPane.getChildren().setAll(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxStatus();
        displayClass(1);
    }
}
