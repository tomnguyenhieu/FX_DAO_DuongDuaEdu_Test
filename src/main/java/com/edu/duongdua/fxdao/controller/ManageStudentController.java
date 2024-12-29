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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

public class ManageStudentController extends Controller implements Initializable{
    Account student = null;
    double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    @FXML
    private TableView<Account> studentsTable;
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
    private TableColumn<Account, String> pNameCol;
    @FXML
    private TableColumn<Account, String> pPhoneCol;
    @FXML
    private TableColumn<Account, String> pEmailCol;
    @FXML
    private TableColumn<Account, String> feeCol;
    @FXML
    private TableColumn<Account, String> classNameCol;
    @FXML
    private TableColumn<Account, String> statusCol;

    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, String> bNameCol;
    @FXML
    private TableColumn<Bill, String> bDateCol;
    @FXML
    private TableColumn<Bill, String> bPriceCol;
    @FXML
    private TableColumn<Bill, String> bStatusCol;
    @FXML
    private TableColumn<Bill, String> bUpdateCol;
    @FXML
    private StackPane billStkPane;

    public void refreshBillTable(){
        final ObservableList<Bill> BillList = FXCollections.observableArrayList();
        List<Bill> billList = billDAO.getAllBill();
        int studentId = studentsTable.getSelectionModel().getSelectedItem().getId();
        for(Bill bill : billList){
            if(studentId == bill.getAccount_id()){
                bill.setPrice(accountDAO.findStudentById(studentId).getFee());
                BillList.add(bill);
            }
        }
        bNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bDateCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        bPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        bStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        billTable.setItems(BillList);

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
                                Bill bill1 = billDAO.findBillById(billTable.getSelectionModel().getSelectedItem().getId());
                                bill1.setStatus("Đã thanh toán");
                                billDAO.updateBill(bill1);
                                refreshBillTable();
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
        bUpdateCol.setCellFactory(cellFoctory);

    }
    public void refreshStudentTable() {
        final ObservableList<Account> StudentList = FXCollections.observableArrayList();
        List<Account> studentsList = accountDAO.getAllAccount(4);
        for (Account student : studentsList){
            for (Classes accClass : classesList){
                if (student.getClassId() == accClass.getClassId()){
                    student.setClassName(accClass.getClassName());
                }
            }
        }
        StudentList.addAll(studentsList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        pPhoneCol.setCellValueFactory(new PropertyValueFactory<>("pPhone"));
        pEmailCol.setCellValueFactory(new PropertyValueFactory<>("pEmail"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));
        classNameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        studentsTable.setItems(StudentList);
    }

    @FXML
    private void onAddStudentClick(){
        // bat stage form them student
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddStudent.fxml"));
            Parent root = loader.load();
            AddStudentController addStudentController = loader.getController();
            addStudentController.setEdit(false);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest((event1) -> {
                refreshStudentTable();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onEditStudentClick() {
        billStkPane.setVisible(false);
        // Lay thong tin student de hien len form
        student = studentsTable.getSelectionModel().getSelectedItem();
        TableView.TableViewSelectionModel<Account> studentSelection = studentsTable.getSelectionModel();
        if(student != null) {
            System.out.println(student.getName());
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/Modal_AddStudent.fxml"));
                Parent root = loader.load();
                AddStudentController addStudentController = loader.getController();
                addStudentController.setTextField(student.getName(), student.getAge(), student.getGender(), student.getEmail(), student.getPassword(), student.getPhone(), student.getAddress(), student.getClassName(), student.getPName(), student.getPPhone(), student.getPEmail(), student.getFee(), student.getStatus());
                addStudentController.setEdit(true);
                addStudentController.setStudentId(student.getId());

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                stage.setOnCloseRequest((event) -> {
                    refreshStudentTable();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            // chua chon student
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Chọn một học sinh để sửa");
            alert.showAndWait();
        }
    }
    public void onDeleteStudentClick(){
        // Lay id student va bo vao ham xoa
        student = studentsTable.getSelectionModel().getSelectedItem();
        if(student != null){
            int studentId = student.getId();
            accountDAO.deleteAccountById(studentId);
            refreshStudentTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa sinh viên thành công");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Chọn vào học sinh để xóa!");
            alert.showAndWait();
        }
    }
    boolean firstClick = true;
    public void onStudentTableClick(MouseEvent event){
        student = studentsTable.getSelectionModel().getSelectedItem();
        if(student != null){
            double posX = event.getX();
            double posY = event.getY();
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.15), billStkPane);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(.15), billStkPane);

            if(posX + 23 < 1080 - 526 && posY + 107 < 594-120){
                billStkPane.setLayoutX(posX + 30);
                billStkPane.setLayoutY(posY + 120);
            } else if (posX + 23 >= 1080 - 526){
                if(posY + 107 >= 594 -100){
                    billStkPane.setLayoutX(1080-526);
                    billStkPane.setLayoutY(594-100);
                } else {
                    billStkPane.setLayoutX(1080-526);
                    billStkPane.setLayoutY(posY + 120);
                }
            } else{
                billStkPane.setLayoutX(posX + 40);
                billStkPane.setLayoutY(594 - 100);
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
            billStkPane.setVisible(firstClick);
            firstClick = !firstClick;
            refreshBillTable();
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

    //Hàm khởi tạo
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshStudentTable();
        billStkPane.setVisible(false);
    }
}
