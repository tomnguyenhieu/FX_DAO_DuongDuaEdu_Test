package com.edu.duongdua.fxdao.controller;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.edu.duongdua.fxdao.model.Account;
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

public class AddEmployeeController extends Controller implements Initializable {
    private boolean edit;
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    private int employeeId;
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    @FXML
    Button cancelBtn;
    @FXML
    Button confirmBtn;

    @FXML
    TextField nameFld;
    @FXML
    ComboBox ageCb;
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
    TextField certificateFld;
    @FXML
    TextField salaryFld;
    @FXML
    ComboBox statusCb;

    @FXML
    public void close(ActionEvent event){
        // phat event dong window
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
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
    public void setTextField(String name, int age, String gender, String email, String password, String phone, String address, String status, String certificate, int salary){
        // khoi tao thong tin dien san trong form
        nameFld.setText(name);
        ageCb.setValue(age);
        genderCb.setValue(gender);
        emailFld.setText(email);
        passFld.setText(password);
        phoneFld.setText(phone);
        addressCb.setValue(address);
        certificateFld.setText(certificate);
        salaryFld.setText(String.valueOf(salary));
        statusCb.setValue(status);
    }
    public void onConfirmClick(ActionEvent event){
        if(ageCb.getValue() != null && genderCb.getValue() != null){
            //comboBox khong null
            String name = nameFld.getText();
            String age = ageCb.getValue().toString();
            String gender = genderCb.getValue().toString();
            String email = emailFld.getText();
            String pass = passFld.getText();
            String phone = phoneFld.getText();
            String address = addressCb.getValue().toString();
            String status = statusCb.getValue().toString();
            String certificates = certificateFld.getText();
            int salary = Integer.parseInt(salaryFld.getText());
            Account employee = new Account();
            employee.setName(name);
            employee.setAge(Integer.parseInt(age));
            employee.setGender(gender);
            employee.setEmail(email);
            employee.setPassword(pass);
            employee.setPhone(phone);
            employee.setAddress(address);
            employee.setStatus(status);
            employee.setCertificates(certificates);
            employee.setSalary(salary);
            if(name.isEmpty() || gender.isEmpty() || email.isEmpty() || pass.isEmpty() || address.isEmpty()){
                // Nhap thieu thong tin
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin");
                alert.showAndWait();
            } else {
                if(!edit){
                    // tao moi student
//                    acc.addEmployee(name, Integer.parseInt(age), gender, email, pass, phone, address, certificate, salary);
                    accountDAO.addEmployee(employee);
                    close(event);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm nhân viên thành công");
                    alert.showAndWait();
                } else{
                    // chinh sua nhan vien
//                    acc.editEmployeeById(name, Integer.parseInt(age), gender, email, pass, phone, address, status, certificate, salary, employeeId);
                    employee.setId(employeeId);
                    accountDAO.updateEmployee(employee);
                    close(event);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Sửa thông tin nhân viên thành công");
                    alert.showAndWait();
                }
            }
        }else {
            // Nhap thieu thong tin
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUpForm();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
