package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

import static java.lang.Math.abs;

public class DashboardEarningController extends Controller implements Initializable {
    public Integer yearSelected;
    public Integer totalEarningByYear = 0;
    public Integer avgMonthlyEarning;
    public Integer totalInterset;
    public Double increase;

    @FXML
    private ComboBox<Integer> selectYearCB;
    @FXML
    private BarChart<String, Integer> earningByYearChart;
    @FXML
    private Label totalEarningLb;
    @FXML
    private Label avgEarningLb;
    @FXML
    private Label totalInterestLb;
    @FXML
    private Label increaseLb;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    public void initYearsCB() {
        List<Integer> years = new ArrayList<>();
        for (Bill bill : billList)
        {
            if (!years.contains(Integer.parseInt(bill.getTime().substring(3))))
            {
                years.add(Integer.parseInt(bill.getTime().substring(3)));
            }
        }
        for (Integer year : years)
        {
            selectYearCB.getItems().add(year);
        }
    }

    public void onActionSelectYear () {
        totalEarningByYear = 0;
        yearSelected = selectYearCB.getValue();
        List<Bill> billsByYear =  new ArrayList<>();
        List<Integer> months = new ArrayList<>();

        // Lấy các bill có năm bằng năm được chọn
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected && bill.getType() == 4) {
                // add bill của năm được chọn vào list
                billsByYear.add(bill);

                // cộng dồn tổng thu của năm
                totalEarningByYear += bill.getTotal_price();

                // gán các tháng của năm vào mảng months
                if (!months.contains(Integer.parseInt(bill.getTime().substring(0, 2)))) {
                    months.add(Integer.parseInt(bill.getTime().substring(0, 2)));
                }
            }
        }
        Collections.sort(months);

        List<Bill> earningByMonths = new ArrayList<>();
        for (Integer month : months) {
            int monthlyEarning = 0;
            Bill _bill = new Bill();
            for (Bill bill : billsByYear) {
                if (Integer.parseInt(bill.getTime().substring(0, 2)) == month) {
                    monthlyEarning += bill.getTotal_price();
                }
            }
            // set thuộc tính: tháng, tổng thu tháng cho bill
            _bill.setTime(month.toString());
            _bill.setTotal_price(monthlyEarning);
            earningByMonths.add(_bill);
        }

        for (Integer month : months)
        {
            System.out.println(month);
        }

        // Tính doanh thu trung bình
        avgMonthlyEarning = totalEarningByYear / months.size();

        renderBarChart(earningByMonths);
        renderLabelData();
    }

    public void renderBarChart (List<Bill> earningByMonths) {
        earningByYearChart.getData().clear();
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>();
        for (Bill monthlyEarning : earningByMonths) {
            dataSeries.getData().add(new XYChart.Data<>(monthlyEarning.getTime(), monthlyEarning.getTotal_price()));
        }

        yAxis.setLabel("Tổng thu");
        dataSeries.setName("Tháng");
        earningByYearChart.getData().add(dataSeries);
    }

    public void renderLabelData () {
        totalEarningLb.setText(totalEarningByYear.toString());
        avgEarningLb.setText(avgMonthlyEarning.toString() + "/tháng");

        int totalSpending = 0;

        // Lặp qua bill và tính tổng chi
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected && bill.getType() != 4) {
                totalSpending += bill.getTotal_price();
            }
        }

        // tính tổng lãi theo năm và set cho label
        totalInterset = totalEarningByYear - totalSpending;
        totalInterestLb.setText(Integer.toString(totalInterset));

        // Tính tổng thu của năm trước
        double lastYearEarning = 0;
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected - 1 && bill.getType() == 4) {
                lastYearEarning += bill.getTotal_price();
            }
        }

        // Tính tổng chi của năm trước
        double lastYearSpending = 0;
        for (Bill bill : billList) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected - 1 && bill.getType() != 4) {
                lastYearSpending += bill.getTotal_price();
            }
        }

        // Tính tổng lãi của năm trước và set cho label
        double lastYearInterest = lastYearEarning - lastYearSpending;
        if (lastYearInterest != 0) {
            increase = ((totalInterset - lastYearInterest) / abs(lastYearInterest)) * 100;
            increaseLb.setText(increase.toString() + "%");
        } else {
            increaseLb.setText(totalInterset.toString());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        initYearsCB();
    }
}
