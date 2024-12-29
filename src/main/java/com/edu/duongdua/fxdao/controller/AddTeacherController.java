package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddTeacherController extends Controller implements Initializable {
    private boolean edit;
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    private int teacherId;
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @FXML
    Button cancelBtn;
    @FXML
    Button confirmBtn;

    @FXML
    TextField nameFld;
    @FXML
    ComboBox<Integer> ageCb;
    @FXML
    ComboBox genderCb;
    @FXML
    TextField emailFld;
    @FXML
    TextField passFld;
    @FXML
    TextField phoneFld;
    @FXML
    ComboBox addressCb;
    @FXML
    ComboBox statusCb;
    @FXML
    TextField certificateFld;
    @FXML
    TextField salaryFld;

    // xử lí event đóng window
    @FXML
    public void close(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    // Khởi tạo thông tin các form
    private void setUpForm() throws SQLException {
        // Khoi tao cac comboBox
        genderCb.getItems().add("Nam");
        genderCb.getItems().add("Nữ");
        for(int i = 1; i < 100; i++){
            ageCb.getItems().add(i);
        }

        statusCb.getItems().add("Đang hoạt động");
        statusCb.getItems().add("Dừng hoạt động");
        List<String> cityLst = Arrays.asList(
                "Hà Nội", "Hải Phòng", "Hồ Chí Minh", "Đà Nẵng", "Cần Thơ",
                "Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định",
                "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Quảng Ninh", "Lào Cai",
                "Yên Bái", "Điện Biên", "Lai Châu", "Sơn La", "Hòa Bình",
                "Thái Nguyên", "Tuyên Quang", "Phú Thọ", "Bắc Giang", "Bắc Kạn",
                "Cao Bằng", "Lạng Sơn", "Hà Giang", "Thanh Hóa", "Nghệ An",
                "Hà Tĩnh", "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Quảng Nam",
                "Quảng Ngãi", "Bình Định", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
                "Bình Thuận", "Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông",
                "Lâm Đồng", "Bình Phước", "Tây Ninh", "Bình Dương", "Đồng Nai",
                "Bà Rịa - Vũng Tàu", "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh",
                "Vĩnh Long", "Đồng Tháp", "An Giang", "Kiên Giang", "Hậu Giang",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
        );
        addressCb.getItems().addAll(cityLst);
    }

    public void setTextField(String name, int age, String gender, String email, String password, String phone, String address, String certificates, int salary, int status) {
        nameFld.setText(name);
        ageCb.setValue(age);
        genderCb.setValue(gender);
        emailFld.setText(email);
        passFld.setText(password);
        phoneFld.setText(phone);
        certificateFld.setText(certificates);
        salaryFld.setText(Integer.toString(salary));
        statusCb.setValue(status);
    }

    public void onConfirmClick(ActionEvent event) {

        if (
                nameFld.getText().isEmpty() || ageCb.getItems().isEmpty() || genderCb.getItems().isEmpty() ||
                emailFld.getText().isEmpty() || passFld.getText().isEmpty() || phoneFld.getText().isEmpty() ||
                addressCb.getItems().isEmpty() || statusCb.getItems().isEmpty() ||
                certificateFld.getText().isEmpty() || salaryFld.getText().isEmpty()
            )
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        } else {
            Account teacher = new Account();
            teacher.setName(nameFld.getText());
            teacher.setAge(ageCb.getValue());
            teacher.setGender(genderCb.getValue().toString());
            teacher.setEmail(emailFld.getText());
            teacher.setPassword(passFld.getText());
            teacher.setPhone(phoneFld.getText());
            teacher.setAddress(addressCb.getValue().toString());
            teacher.setCertificates(certificateFld.getText());
            teacher.setSalary(Integer.parseInt(salaryFld.getText()));
            teacher.setStatus(statusCb.getItems().toString());

            teacher.setId(teacherId);
            if (edit) {
                accountDAO.updateTeacher(teacher);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Sửa giáo viên thành công");
                alert.showAndWait();
            } else {
                accountDAO.addTeacher(teacher);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Thêm giáo viên thành công");
                alert.showAndWait();
            }

            close(event);

        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUpForm();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}