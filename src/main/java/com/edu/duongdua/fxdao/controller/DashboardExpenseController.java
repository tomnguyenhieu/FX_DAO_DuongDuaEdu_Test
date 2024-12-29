package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardExpenseController extends Controller implements Initializable
{
    private LocalDate today = LocalDate.now();
    private Integer currentMonth = today.getMonthValue();
    private Integer currentYear = today.getYear();
    private String time = currentMonth.toString() + "/" + currentYear.toString();
    private int year = 0;

    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Label totalTeachersSalary;
    @FXML
    private Label totalStaffsSalary;
    @FXML
    private Label totalCSVC;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private NumberAxis yAxis2;

    // Xu ly logic
    public void renderLineChart()
    {
        yAxis.setLabel("Tổng lương");

        // Giáo viên
        XYChart.Series<String, Number> dataSeriesTeachers = new XYChart.Series<>();
        List<String> allTeacherMonthList = new ArrayList<>();

        for(Bill billForMonth : billList){
            String month = billForMonth.getTime();
            if(!allTeacherMonthList.contains(month) && billForMonth.getType() == 1){
                allTeacherMonthList.add(month);
                int totalSalary = 0;
                for(Bill bill : billList){
                    if(bill.getTime().equals(month) && bill.getType() == 1){
                        totalSalary += bill.getTotal_price();
                    }
                }
                dataSeriesTeachers.getData().add(new XYChart.Data<>(month, totalSalary));
            }
        }
        // Nhân viên
        XYChart.Series<String, Number> dataSeriesStaffs = new XYChart.Series<>();
        List<String> allStaffMonthList = new ArrayList<>();

        for(Bill billForMonth : billList){
            String month = billForMonth.getTime();
            if(!allStaffMonthList.contains(month) && billForMonth.getType() == 2){
                allStaffMonthList.add(month);
                int totalSalary = 0;
                for(Bill bill : billList){
                    if(bill.getTime().equals(month) && bill.getType() == 2){
                        totalSalary += bill.getTotal_price();
                    }
                }
                dataSeriesStaffs.getData().add(new XYChart.Data<>(month, totalSalary));
            }
        }
        dataSeriesTeachers.setName("Giáo viên");
        dataSeriesStaffs.setName("Nhân viên");

        lineChart.getData().add(dataSeriesTeachers);
        lineChart.getData().add(dataSeriesStaffs);

    }
    public void updateTeachersSalary()
    {
        int totalSalary = 0;
        for(Bill bill : billList){
            if(bill.getTime().equals(time) && bill.getType() == 1){
                totalSalary += bill.getTotal_price();
            }
        }
        String strTotalTeachersSalary = Integer.toString(totalSalary);
        totalTeachersSalary.setText(strTotalTeachersSalary);
    }
    public void updateStaffsSalary()
    {
        int totalSalary = 0;
        for(Bill bill : billList){
            if(bill.getTime().equals(time) && bill.getType() == 2){
                totalSalary += bill.getTotal_price();
            }
        }
        String strTotalStaffsSalary = Integer.toString(totalSalary);
        totalStaffsSalary.setText(strTotalStaffsSalary);
    }
    public void updateFacilities()
    {
        int totalFacilities = 0;
        for(Bill bill : billList){
            if(bill.getTime().equals(time) && bill.getType() == 3){
                totalFacilities += bill.getQuantity();
            }
        }
        String strTotalFacilities = Integer.toString(totalFacilities);
        totalCSVC.setText(strTotalFacilities);
    }
    public File uploadFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Excel file to upload");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
    public void renderBarChart()
    {
        yAxis2.setLabel("Số lượng");
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        List<String> allMonthList = new ArrayList<>();

        for(Bill billForMonth : billList){
            String month = billForMonth.getTime();
            if(!allMonthList.contains(month) && billForMonth.getType() == 3){
                allMonthList.add(month);
                int totalFacilities = 0;
                for(Bill bill : billList){
                    if(bill.getTime().equals(month) && bill.getType() == 3){
                        totalFacilities += bill.getQuantity();
                    }
                }
                dataSeries.getData().add(new XYChart.Data<>(month, totalFacilities));
            }
        }
        dataSeries.setName("Số lượng");
        barChart.getData().clear();
        barChart.getData().add(dataSeries);
    }
    public ArrayList<List<String>> readExcel(File file)
    {
        ArrayList<List<String>> arrayList = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            for (int i = 1; i <= rows; i++)
            {
                List<String> list = new ArrayList<>();

                XSSFRow csvcRow = sheet.getRow(i);
                XSSFCell nameCell = csvcRow.getCell(1);
                XSSFCell quantityCell = csvcRow.getCell(2);
                XSSFCell priceCell = csvcRow.getCell(3);
                XSSFCell totalPriceCell = csvcRow.getCell(4);
                XSSFCell timeCell = csvcRow.getCell(5);

                String nameCellValue = nameCell.getStringCellValue();
                double quantityCellValue = quantityCell.getNumericCellValue();
                Integer _quantityCellValue = (int) quantityCellValue;
                String __quantityCellValue = _quantityCellValue.toString();

                double priceCellValue = priceCell.getNumericCellValue();
                Integer _priceCellValue = (int) priceCellValue;
                String __priceCellValue = _priceCellValue.toString();

                double totalPriceCellValue = totalPriceCell.getNumericCellValue();
                Integer _totalPriceCellValue = (int) totalPriceCellValue;
                String __totalPriceCellValue = _totalPriceCellValue.toString();

                String timeCellValue = timeCell.getStringCellValue();

                list.add(nameCellValue);
                list.add(__quantityCellValue);
                list.add(__priceCellValue);
                list.add(__totalPriceCellValue);
                list.add(timeCellValue);

                arrayList.add(list);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    // Xu ly event
    public void onActionUploadBtn()
    {
        boolean isSuccess = false;
        File excel = uploadFile();
//        Files files = new Files();
        ArrayList<List<String>> arrayList = readExcel(excel);
        for (List<String> item : arrayList)
        {
            String name = item.getFirst();
            String quantity = item.get(1);
            int _quantity = Integer.parseInt(quantity);
            String price = item.get(2);
            int _price = Integer.parseInt(price);
            String totalPrice = item.get(3);
            int _totalPrice = Integer.parseInt(totalPrice);
            String month = item.getLast().substring(3);

            Bill bill = new Bill();
            bill.setName(name);
            bill.setQuantity(_quantity);
            bill.setPrice(_price);
            bill.setTotal_price(_totalPrice);
            bill.setTime(month);
            bill.setType(4);
            billDAO.addBill(bill);
            isSuccess = true;
            updateFacilities();
            renderBarChart();
        }
        if (isSuccess)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Upload thanh cong!");
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderLineChart();
        updateTeachersSalary();
        updateStaffsSalary();
        updateFacilities();
        renderBarChart();
    }
}
