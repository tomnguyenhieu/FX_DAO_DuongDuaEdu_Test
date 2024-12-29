package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.AccountDAO;
import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Bill;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageTeacherController extends Controller implements Initializable {
    Account teacher = null;
    double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;

    @FXML
    private TableView<Account> teachersTable;
    @FXML
    private TableColumn<Account, String> idCol;
    @FXML
    private TableColumn<Account, String> nameCol;
    @FXML
    private TableColumn<Account, String> ageCol;
    @FXML
    private TableColumn<Account, String> genderCol;
    @FXML
    private TableColumn<Account, String> emailCol;
    @FXML
    private TableColumn<Account, String> passCol;
    @FXML
    private TableColumn<Account, String> phoneCol;
    @FXML
    private TableColumn<Account, String> addressCol;
    @FXML
    private TableColumn<Account, String> certificateCol;
    @FXML
    private TableColumn<Account, String> salaryCol;
    @FXML
    private TableColumn<Account, String> statusCol;

    @FXML
    private TableView<Bill> teacherDetailTable;
    @FXML
    private TableColumn<Bill, String> detailIdCol;
    @FXML
    private TableColumn<Bill, String> detailNameCol;
    @FXML
    private TableColumn<Bill, String> monthCol;
    @FXML
    private TableColumn<Bill, String> lessonQtyCol;
    @FXML
    private TableColumn<Bill, String> monthSalaryCol;
    @FXML
    private TableColumn<Bill, String> detailStatusCol;
    @FXML
    private TableColumn<Bill, String> updateSalarySttCol;

    @FXML
    private StackPane detailPane;

    // Hàm load bảng danh sách giáo viên
    public void refreshTeacherTable () {
        final ObservableList<Account> TeacherList = FXCollections.observableArrayList();
        List<Account> teachersList = accountDAO.getAllAccount(2);

        TeacherList.addAll(teachersList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        certificateCol.setCellValueFactory(new PropertyValueFactory<>("certificates"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        teachersTable.setItems(TeacherList);
    }

    // Hàm xử lí sự kiện thêm giáo viên
    @FXML
    public void onAddTeacherClick(){
        // bat stage form them giáo viên
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddTeacher.fxml"));
            Parent root = loader.load();
            AddTeacherController addTeacherController = loader.getController();
            addTeacherController.setEdit(false);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest((event1) -> {
                refreshTeacherTable();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditTeacherClick() {
        detailPane.setVisible(false);
        // Lay thong tin student de hien len form
        teacher = teachersTable.getSelectionModel().getSelectedItem();
        TableView.TableViewSelectionModel<Account> studentSelection = teachersTable.getSelectionModel();
        if(teacher != null) {
            System.out.println(teacher.getName());
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddTeacher.fxml"));
                Parent root = loader.load();
                AddTeacherController addTeacherController = loader.getController();
                addTeacherController.setTextField(
                        teacher.getName(), teacher.getAge(), teacher.getGender(),
                        teacher.getEmail(), teacher.getPassword(), teacher.getPhone(),
                        teacher.getAddress(), teacher.getCertificates(), teacher.getSalary(),
                        teacher.getStatus().equals("Đang hoạt động") ? 1 : 2
                );
                addTeacherController.setEdit(true);
                addTeacherController.setTeacherId(teacher.getId());

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                stage.setOnCloseRequest((event) -> {
                    refreshTeacherTable();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            // chua chon student
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Chọn một giáo viên để sửa");
            alert.showAndWait();
        }
    }

    public void onDeleteTeacherClick () {
        // Lay id teacher
        teacher = teachersTable.getSelectionModel().getSelectedItem();
        if(teacher != null){
            int teacherId = teacher.getId();
            accountDAO.deleteAccountById(teacherId);
            refreshTeacherTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa giáo viên thành công");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Chọn giáo viên để xóa!");
            alert.showAndWait();
        }
    }

    public void refreshDetailTable () {
        final ObservableList<Bill> billList = FXCollections.observableArrayList();

        List<Bill> teacherSalary = new ArrayList<>();
//        List<String> years = new ArrayList<>();

        int teacherId = teachersTable.getSelectionModel().getSelectedItem().getId();
//        for (Bill bill : billList) {
//            if (teacherId == bill.getAccount_id() && !years.contains(bill.getTime().substring(3))) {
//                years.add(bill.getTime().substring(3));
//            }
//        }

        teacherSalary = billDAO.getTeacherBillDataById(teacherId);

        for(Bill bill : teacherSalary){
            billList.addAll(bill);
        }
        detailIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        detailNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        lessonQtyCol.setCellValueFactory(new PropertyValueFactory<>("lessonQty"));
        monthSalaryCol.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        detailStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        teacherDetailTable.setItems(billList);

        // Tạo button cập nhật
        Callback<TableColumn<Bill, String>, TableCell<Bill, String>> cellFoctory = (TableColumn<Bill, String> param) -> {
            final TableCell<Bill, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Bill bill = getTableView().getItems().get(getIndex());
                        if (!bill.getStatus().equals("Đã thanh toán")){
                            Label lb = new Label("Cập nhật");
                            lb.setTextFill(Color.WHITE);

                            HBox hbox = new HBox(lb);
                            hbox.setMaxWidth(100);
                            hbox.setStyle("-fx-alignment:center; -fx-cursor: hand; -fx-background-color:  #30475E; -fx-background-radius: 8;");

                            hbox.setOnMouseClicked((MouseEvent event) -> {
                                Bill bill1 = billDAO.findBillById(teacherDetailTable.getSelectionModel().getSelectedItem().getId());
                                System.out.println(bill1.getId());
                                bill1.setStatus("Đã thanh toán");
                                billDAO.updateBill(bill1);
                                refreshDetailTable();
                            });
                            hbox.setOnMouseEntered((MouseEvent event) -> {
                                HBox myHbox = (HBox) event.getSource();
                                myHbox.setEffect(new ColorAdjust(0,0,0,-0.2));
                            });
                            hbox.setOnMouseExited((MouseEvent event) -> {
                                HBox myHbox = (HBox) event.getSource();
                                myHbox.setEffect(new ColorAdjust(0,0,0,0));
                            });

                            setGraphic(hbox);
                            setText(null);
                        } else{
                            FontIcon font = new FontIcon("fas-check-circle");
                            font.setIconSize(12);
                            HBox hb = new HBox(font);
                            hb.setMaxWidth(100);
                            hb.setStyle("-fx-alignment:center;-fx-background-color:  #FFFFFF;");
                            setGraphic(hb);
                            setText(null);
                        }
                    }
                }
            };
            return cell;
        };
        updateSalarySttCol.setCellFactory(cellFoctory);
    }

    boolean firstClick = true;
    public void onTeacherSelected (MouseEvent event) {
        teacher = teachersTable.getSelectionModel().getSelectedItem();
        if(teacher != null){
            double posX = event.getX();
            double posY = event.getY();
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.15), detailPane);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(.15), detailPane);

            if(posX + 23 < 1080 - 526 && posY + 107 < 594-120){
                detailPane.setLayoutX(posX + 30);
                detailPane.setLayoutY(posY + 120);
            } else if (posX + 23 >= 1080 - 526){
                if(posY + 107 >= 594 -100){
                    detailPane.setLayoutX(1080-526);
                    detailPane.setLayoutY(594-100);
                } else {
                    detailPane.setLayoutX(1080-526);
                    detailPane.setLayoutY(posY + 120);
                }
            } else{
                detailPane.setLayoutX(posX + 40);
                detailPane.setLayoutY(594 - 100);
            }
            if(firstClick){
                translateTransition.setFromX(-200);
                translateTransition.setFromY(-90);
                translateTransition.setToX(0);
                translateTransition.setToY(0);
                scaleTransition.setFromX(0.1);
                scaleTransition.setFromY(0.1);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                translateTransition.play();
                scaleTransition.play();
            }
            detailPane.setVisible(firstClick);
            firstClick = !firstClick;
            refreshDetailTable();
        }
    }

    // Tạo hiệu ứng
    public void onMouseEnter(MouseEvent event){
        HBox hbox = (HBox) event.getSource();
        hbox.setEffect(new ColorAdjust(0,0,0,-0.2));
    }
    public void onMouseExit(MouseEvent event){
        HBox hbox = (HBox) event.getSource();
        hbox.setEffect(new ColorAdjust(0,0,0,0));
    }
    public void onBtnAddPressed(MouseEvent event) {
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        StackPane stkPane = (StackPane)(event.getSource());
        stkPane.setScaleY(1.1);
        stkPane.setScaleX(1.1);
        orgTranslateX = ((StackPane)(event.getSource())).getTranslateX();
        orgTranslateY = ((StackPane)(event.getSource())).getTranslateY();
    }
    public void onBtnAddDragged(MouseEvent event) {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;
        ((StackPane)(event.getSource())).setTranslateX(newTranslateX);
        ((StackPane)(event.getSource())).setTranslateY(newTranslateY);
    }
    public void onBtnExit(MouseEvent event){
        StackPane stkPane = (StackPane)(event.getSource());
        stkPane.setScaleY(1);
        stkPane.setScaleX(1);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTeacherTable();
        detailPane.setVisible(false);
    }
}