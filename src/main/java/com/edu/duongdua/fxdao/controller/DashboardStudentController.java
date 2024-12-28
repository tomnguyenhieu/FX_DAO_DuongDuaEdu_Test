package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;
import com.edu.duongdua.fxdao.model.Comment;
import com.edu.duongdua.fxdao.model.Lesson;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardStudentController extends Controller implements Initializable {
    double maxLesson = 10;
    double maxStudent = 10;

    @FXML
    PieChart agePChart;
    @FXML
    Label avgStudentAgeLb;
    @FXML
    Label maleLabel;
    @FXML
    Label femaleLabel;
    @FXML
    CategoryAxis ctgAxis;
    @FXML
    NumberAxis numAxis;
    @FXML
    LineChart totalStudentLChart;
    @FXML
    VBox topStudentVb;
    @FXML
    VBox classInfoVb;

    private void numberAnim(Label lb, int num){
        final int[] count = {0};
        int milliDuration;
        if (num <= 10) {
            milliDuration = 100;
        } else{
            milliDuration = 30;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(milliDuration), event -> {
                    if (count[0] < num) {
                        count[0]++;
                        lb.setText(Integer.toString(count[0]));
                    }
                })
        );
        timeline.setCycleCount(num);
        timeline.play();
    }

    // Xử lí thêm giao diện
    private HBox addStudentInTop(int top, String name, String className, String address, int score){
        HBox hbox = new HBox();
        hbox.setPrefWidth(287);
        hbox.setPrefHeight(66);

        Label topLb = new Label(Integer.toString(top));
        topLb.setAlignment(Pos.CENTER);
        topLb.setFont(new Font(22));
        topLb.setTextFill(Color.BLACK);
        topLb.setPrefHeight(66);
        topLb.setPrefWidth(66);
        topLb.setStyle("-fx-border-color: #8A3030;-fx-font-weight: bold; -fx-border-width: 0 0 1 0;");

        VBox vBox = new VBox();
        vBox.setPrefHeight(66);
        vBox.setPrefWidth(179);
        vBox.setPadding(new Insets(0,0,0,10));

        Label scoreLb = new Label(Integer.toString(score) + "đ");
        scoreLb.setPrefHeight(66);
        scoreLb.setPrefWidth(66);
        scoreLb.setFont(new Font(22));
        scoreLb.setAlignment(Pos.CENTER);
        scoreLb.setStyle("-fx-font-weight: bold; -fx-background-color:  #DCDCDC; -fx-background-radius: 90;-fx-background-insets: 7;");

        Label nameLb = new Label(name);
        nameLb.setPrefWidth(240);
        nameLb.setPrefHeight(44);
        nameLb.setFont(new Font(24));
        nameLb.setTextFill(Paint.valueOf("#30475e"));
        nameLb.setStyle("-fx-font-weight: bold;");
        nameLb.setAlignment(Pos.BOTTOM_LEFT);


        Label infoStudentLb = new Label(className + ", " + address);
        infoStudentLb.setPrefWidth(240);
        infoStudentLb.setPrefHeight(22);
        infoStudentLb.setFont(new Font(12));
        infoStudentLb.setAlignment(Pos.TOP_LEFT);


        vBox.getChildren().addAll(nameLb, infoStudentLb);
        hbox.getChildren().addAll(topLb, vBox, scoreLb);

        return hbox;
    }
    private VBox addClassBox(String name, int total_lesson, int total_student){
        double maxWidth = 200;

        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.THREE_PASS_BOX);
        ds.setWidth(8);
        ds.setHeight(8);
        ds.setRadius(3.5);


        VBox vBox = new VBox();
        vBox.setPrefHeight(68);
        vBox.setPrefWidth(250);
        vBox.setPadding(new Insets(0,0,0,15));
        vBox.setStyle("-fx-background-color:  #FFFFFF; -fx-background-radius: 12;");
        vBox.setEffect(ds);

        Label lb = new Label(name);
        lb.setPrefHeight(32);
        lb.setPrefWidth(90);
        lb.setFont(new Font(14));
        lb.setStyle("-fx-font-weight: bold;");
        lb.setAlignment(Pos.BOTTOM_LEFT);

        Rectangle lessonRec = new Rectangle();
        lessonRec.setWidth(maxWidth * (total_lesson / maxLesson) + 4);
        lessonRec.setHeight(12);
        lessonRec.setStrokeWidth(0);
        lessonRec.setFill(Paint.valueOf("#F05454"));

        Rectangle studentRec = new Rectangle();
        studentRec.setWidth(maxWidth * (total_student / maxStudent) + 4);
        studentRec.setHeight(12);
        studentRec.setStrokeWidth(0);
        studentRec.setFill(Paint.valueOf("#30475E"));

        vBox.getChildren().addAll(lb, lessonRec, studentRec);
        return vBox;
    }

    // Xử lí chuẩn bị data
    private void topStudentSetup(){
        String latestMonth = lessonList.getLast().getTitle().substring(3);
        List<Account> topStudentInMonth = new ArrayList<Account>();
        // Lưu điểm tương ứng với top
        int[] topStudentScore = new int[commentList.size()];
        int top = 1;

        // Sắp xếp list comment theo điểm
        for(int i = 1; i < commentList.size(); i++){
            // Insertion sort
            Comment key = commentList.get(i);
            int j = i-1;

            while(j >= 0 && commentList.get(j).getScore() < key.getScore()){
                commentList.set(j+1, commentList.get(j));
                j = j - 1;
            }
            commentList.set(j + 1, key);
        }
        for(Comment comment : commentList){
            if(lessonDAO.getLessonInfoById(comment.getLessonId()).getTitle().substring(3).equals(latestMonth)){
                // Nếu comment trong tháng mới nhất
                // Thêm học sinh vào top
                topStudentInMonth.add(accountDAO.findStudentById(comment.getStudentId()));
                // Thêm điểm vào top
                topStudentScore[top] = comment.getScore();
                top++;
            }
        }
        top = 1;
        for(Account student : topStudentInMonth){
            // Lấy thông tin của học sinh tương ứng thứ hạng
            String name = student.getName();
            String className = classesDao.findByID(student.getClassId()).getClassName();
            String address = student.getAddress();
            int score = topStudentScore[top];
            topStudentVb.getChildren().add(addStudentInTop(top, name, className,address,score));
            top++;
            if(top == 11){
                break;
            }
        }
    }
    private void pChartSetup(){
        int ageUnder12Count = 0;
        int ageUnder22Count = 0;
        int ageOver22Count = 0;
        List<Account> studentList = accountDAO.getAllStudent();
        for(Account student : studentList){
            int studentAge = student.getAge();
            if(studentAge <= 12){
                // Tuổi dưới 12
                ageUnder12Count++;
            }else if(studentAge <= 22){
                // Tuổi dưới 22
                ageUnder22Count++;
            }else {
                // Tuổi trên 22
                ageOver22Count++;
            }
        }
        PieChart.Data ageUnder12CountData = new PieChart.Data("Dưới 12 tuổi",ageUnder12Count);
        PieChart.Data ageUnder22CountData = new PieChart.Data("Dưới 22 tuổi",ageUnder22Count);
        PieChart.Data ageOver22CountData = new PieChart.Data("Trên 22 tuổi",ageOver22Count);
        agePChart.getData().clear();
        agePChart.getData().addAll(ageUnder12CountData, ageUnder22CountData, ageOver22CountData);
        agePChart.setLabelsVisible(true);
        agePChart.setLabelLineLength(1);
        agePChart.setLegendVisible(false);
        agePChart.setStartAngle(0);
        agePChart.getData().get(0).getNode().setStyle("-fx-background-color: #F05454;");
        agePChart.getData().get(1).getNode().setStyle("-fx-background-color: #8A3030;");
        agePChart.getData().get(2).getNode().setStyle("-fx-background-color: #30475E;");
    }
    private void avgAgeSetup(){
        int studentCount = 0;
        int studentAgeSum = 0;
        int avgAge = 0;
        List<Account> studentList = accountDAO.getAllStudent();
        // Tính tổng tuổi và số lượng học viên
        for(Account student : studentList){
            studentAgeSum += student.getAge();
            studentCount++;
        }

        avgAge = studentAgeSum / studentCount;
        numberAnim(avgStudentAgeLb, avgAge);
    }
    private void genderSetup(){
        int maleCount = 0;
        int femaleCount = 0;
        List<Account> studentList = accountDAO.getAllStudent();
        for(Account student : studentList){
            if(student.getGender().equals("Nam")){
                // Nếu học sinh là nam
                maleCount++;
            }else{
                femaleCount++;
            }
        }
        numberAnim(maleLabel, maleCount);
        numberAnim(femaleLabel, femaleCount);
    }
    private void lChartSetup(){
        totalStudentLChart.setTitle("Tổng số học viên theo tháng");
        totalStudentLChart.setLegendVisible(false);
        numAxis.setLabel("Số học viên");
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        List<String> monthArr = new ArrayList<>();

        // Với mỗi lesson, đếm học sinh dựa trên comment
        for(Lesson lesson : lessonList){
            String month = lesson.getTitle().substring(3);
            int total_student = 0;
            // Tạo list học sinh điểm kiểm tra trùng
            List<Integer> studentIdList = new ArrayList<>();
            for(Comment comment : commentList){
                // Với mỗi comment hay học sinh của lesson
                int studentId = comment.getStudentId();
                if(comment.getLessonId() == lesson.getId() && !studentIdList.contains(studentId)){
                    // Học sinh học lesson chưa được đếm
                    total_student++;
                    studentIdList.add(studentId);
                }
            }
            if(!monthArr.contains(month)){
                // Chưa có tháng của lesson hiện tại
                monthArr.add(lesson.getTitle().substring(3));
                dataSeries.getData().add(new XYChart.Data<>(month, total_student));
            }
        }
        totalStudentLChart.getData().add(dataSeries);
    }
    private void classInfoSetup(){
        for(Classes classes : classesList){
            String name = classes.getClassName();
            int totalLesson = 0;
            int totalStudent = 0;

            // Đếm học sinh
            for(Account account : studentsList){
                if(account.getClassId() == classes.getClassId()){
                    totalStudent++;
                }
            }

            // Đếm số tiết
            for(Lesson lesson : lessonList){
                if(lesson.getClassId() == classes.getClassId()){
                    totalLesson++;
                }
            }
            classInfoVb.getChildren().add(addClassBox(name, totalLesson, totalStudent));
        }
    }

    // Hàm khởi tạo
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pChartSetup();
        avgAgeSetup();
        genderSetup();
        lChartSetup();
        topStudentSetup();
        classInfoSetup();
    }
}
