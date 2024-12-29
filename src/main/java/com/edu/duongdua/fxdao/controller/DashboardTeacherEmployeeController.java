package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardTeacherEmployeeController extends Controller implements Initializable
{
    private static final Logger log = LoggerFactory.getLogger(DashboardTeacherEmployeeController.class);
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
    private Label totalTeachersLabel;
    @FXML
    private Label averageTeacherAgesLabel;
    @FXML
    private Label totalStaffsLabel;
    @FXML
    private Label averageStaffAgesLabel;
    @FXML
    private ComboBox<Integer> cbYears;
    @FXML
    private TableView<Account> tblTeachers;
    @FXML
    private TableColumn<Account, Integer> idCol;
    @FXML
    private TableColumn<Account, String> nameCol;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private NumberAxis yAxis2;

    // Render biểu đồ đường chứa thông tin tổng giáo viên & nhân viên theo tháng
    public void renderLineChart()
    {
        yAxis.setLabel("Số lượng");
        // Giáo viên
        XYChart.Series<String, Number> dataSeriesTeachers = new XYChart.Series<>();
        for (Bill bill : billDAO.getBillStatistical(2))
        {
            dataSeriesTeachers.getData().add(new XYChart.Data<>(bill.getTime(), bill.getCountMembers()));
        }
        dataSeriesTeachers.setName("Giáo viên");
        lineChart.getData().add(dataSeriesTeachers);

        // Nhân viên
        XYChart.Series<String, Number> dataSeriesEmployees = new XYChart.Series<>();
        for (Bill bill : billDAO.getBillStatistical(3))
        {
            dataSeriesEmployees.getData().add(new XYChart.Data<>(bill.getTime(), bill.getCountMembers()));
        }
        dataSeriesEmployees.setName("Nhân viên");
        lineChart.getData().add(dataSeriesEmployees);
    }

    // Init giá trị cho box giáo viên và nhân viên
    public void initBoxData()
    {
        for (Bill bill : billDAO.getBillStatistical(2))
        {
            if (bill.getTime().equals(time))
            {
                totalTeachersLabel.setText(Integer.toString(bill.getCountMembers()));
                averageTeacherAgesLabel.setText(Integer.toString((int) bill.getAvgAge()));
            }
        }

        for (Bill bill : billDAO.getBillStatistical(3))
        {
            if (bill.getTime().equals(time))
            {
                totalStaffsLabel.setText(Integer.toString(bill.getCountMembers()));
                averageStaffAgesLabel.setText(Integer.toString((int) bill.getAvgAge()));
            }
        }
    }

    // Init combox chọn năm
    public void initYearsComboBox()
    {
        List<Integer> years = new ArrayList<>();
        for (Bill bill : billDAO.getBillStatistical(2))
        {
            if (!years.contains(Integer.parseInt(bill.getTime().substring(3))))
            {
                years.add(Integer.parseInt(bill.getTime().substring(3)));
            }
        }
        for (Integer year : years)
        {
            cbYears.getItems().add(year);
        }
    }

    // Hàm xử lý trả về danh sách giáo viên theo năm được chọn
    public void onActionChooseYear()
    {
        int year = cbYears.getValue();
        List<Integer> teachersId = new ArrayList<>();
        for (Bill bill : billList)
        {
            if (Integer.parseInt(bill.getTime().substring(3)) == year && bill.getType() == 1)
            {
                if (!teachersId.contains(bill.getAccount_id()))
                {
                    teachersId.add(bill.getAccount_id());
                }
            }
        }
        List<Account> teachersList = new ArrayList<>();
        for (Integer id : teachersId)
        {
            if (accountDAO.findTeacherById(id) != null)
            {
                Account teacher = accountDAO.findTeacherById(id);
                teachersList.add(teacher);
            }
        }
        final ObservableList<Account> data = FXCollections.observableArrayList();
        for (Account teacher : teachersList)
        {
            data.addAll(teacher);
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        tblTeachers.setItems(data);
    }

    // Hàm chọn giáo viên trong table các giáo viên trong năm được chọn
    public void onMouseClickRenderTeacherLessonsChart()
    {
        barChart.getData().clear();
        int year = cbYears.getValue();
        if (tblTeachers.getSelectionModel().getSelectedItem() != null)
        {
            List<Bill> teacherLessons = billDAO.getTeacherBillDataById(tblTeachers.getSelectionModel().getSelectedItem().getId());
            List<Bill> teacherLessonsByYear = new ArrayList<>();
            XYChart.Series<String, Number> dataSeriesLessons = new XYChart.Series<>();
            dataSeriesLessons.setName("Số lượng lessons");
            for (Bill bill : teacherLessons)
            {
                if (Integer.parseInt(bill.getTime().substring(3)) == year)
                {
                    teacherLessonsByYear.add(bill);
                }
            }
            if (!teacherLessonsByYear.isEmpty())
            {
                for (Bill bill : teacherLessonsByYear)
                {
                    if (bill.getAccount_id() == tblTeachers.getSelectionModel().getSelectedItem().getId() && bill.getLessonQty() != 0)
                    {
                        dataSeriesLessons.getData().add(new XYChart.Data<>(bill.getTime(), bill.getLessonQty()));
                    }
                }
                barChart.getData().add(dataSeriesLessons);
            } else
            {
                barChart.getData().clear();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderLineChart();
        initBoxData();
        initYearsComboBox();
    }
}
