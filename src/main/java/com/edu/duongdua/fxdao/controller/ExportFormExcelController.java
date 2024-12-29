package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class ExportFormExcelController extends Controller implements Initializable
{
    private LocalDate today = LocalDate.now();
    private Integer currentDay = today.getDayOfMonth();
    private Integer currentMonth = today.getMonthValue();
    private Integer currentYear = today.getYear();
    private String time = currentDay.toString() + "/" + currentMonth.toString() + "/" + currentYear.toString();

    @FXML
    private ComboBox<String> cbClasses;

    // Khởi tạo combo box chứa các lớp có trong database
    public void initClassesComboBox()
    {
        for (Classes _class : classesDao.getClassesInfo(1))
        {
            cbClasses.getItems().add(_class.getClassName());
        }
    }

    // Hàm export form excel mẫu theo lớp
    public boolean exportExcel()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        File exportFile = fileChooser.showSaveDialog(stage);
        String filePath = exportFile.getPath();
        List<Classes> classes = classesDao.getAllClasses();
        try
        {
            for (Classes _class : classes)
            {
                if (cbClasses.getValue().equals(_class.getClassName()))
                {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Sheet1");

                    XSSFRow titleRow = sheet.createRow(0);
                    XSSFCell titleCell = titleRow.createCell(0);
                    titleCell.setCellValue("Thời gian");
                    XSSFCell titleCellValue = titleRow.createCell(1);
                    titleCellValue.setCellValue(time);

                    XSSFRow contentRow = sheet.createRow(1);
                    XSSFCell contentCell = contentRow.createCell(0);
                    contentCell.setCellValue("Nội dung bài học");

                    XSSFRow classRow = sheet.createRow(2);
                    XSSFCell classCell = classRow.createCell(0);
                    classCell.setCellValue("Lớp");
                    XSSFCell classCellValue = classRow.createCell(1);
                    classCellValue.setCellValue(_class.getClassName());

                    XSSFRow labelRow = sheet.createRow(3);
                    XSSFCell labelID = labelRow.createCell(0);
                    labelID.setCellValue("ID");
                    XSSFCell labelName = labelRow.createCell(1);
                    labelName.setCellValue("Tên");
                    XSSFCell labelComment = labelRow.createCell(2);
                    labelComment.setCellValue("Nhận xét");
                    XSSFCell labelScore = labelRow.createCell(3);
                    labelScore.setCellValue("Điểm số");

                    List<Account> studentsList = new ArrayList<>();
                    for (Account student : accountDAO.getAllAccount(4))
                    {
                        if (student.getClassId() == _class.getClassId())
                        {
                            studentsList.add(student);
                        }
                    }

                    for (int i = 0; i < studentsList.size(); i++)
                    {
                        XSSFRow row = sheet.createRow(i + 4);
                        XSSFCell cell1 = row.createCell(0);
                        cell1.setCellValue(studentsList.get(i).getId());
                        XSSFCell cell2 = row.createCell(1);
                        cell2.setCellValue(studentsList.get(i).getName());
                    }
                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return false;
    }

    // Close event
    public void close(ActionEvent event)
    {
        // phat event dong window
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void onActionConfirm(ActionEvent event)
    {
        exportExcel();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Export thanh cong!");
        alert.show();
        close(event);
    }

    // Hàm xử lý sự kiện click vào nút cancel
    public void onActionCancel(ActionEvent event)
    {
        close(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initClassesComboBox();
    }
}