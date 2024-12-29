package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.BillDAO;
import com.edu.duongdua.fxdao.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageDiaryController extends Controller
{
    private int count = 0;
    private Classes classObj;
    private boolean isConfirm = false;
    private int lessonId = 0;
    public Classes getClassObj() {
        return classObj;
    }
    public String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassObj(Classes classObj) {
        this.classObj = classObj;
    }

    @FXML
    private VBox lessonsContainer;
    @FXML
    private TableView<Lesson> tblComment1;
    @FXML
    private TableColumn<Lesson, String> tblCol1Title;
    @FXML
    private TableColumn<Lesson, String> tblCol1Content;
    @FXML
    private TableColumn<Lesson, String> tblCol1Class;
    @FXML
    private TableColumn<Lesson, String> tblCol1Teacher;
    @FXML
    private TableView<Comment> tblComment2;
    @FXML
    private TableColumn<Comment, String> tblCol2StudentName;
    @FXML
    private TableColumn<Comment, String> tblCol2StudentComment;
    @FXML
    private TableColumn<Comment, Integer> tblCol2StudentScore;

    // Hàm khởi tạo các nút lessons
    public void initLessonBtn(int count)
    {
        String lessonName = "Lesson";
        Button lessonBtn = new Button(lessonName + " " + count);
        lessonBtn.setId("lessonBtnTest");
        lessonBtn.setMinSize(260, 55);
        lessonBtn.setPrefSize(200, 55);
        lessonBtn.setCursor(Cursor.HAND);
        lessonBtn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        lessonBtn.setTextFill(Color.BLACK);
        lessonBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClickRenderTables);
        lessonsContainer.getChildren().add(lessonBtn);
    }

    // Hàm hiển thị các lessons theo lớp được chọn
    public void displayLessons(Classes classObj)
    {
        for (int i = 0; i < lessonDAO.getAllLessons().size(); i++)
        {
            if (lessonDAO.getAllLessons().get(i).getClassId() == classObj.getClassId())
            {
                count += 1;
                initLessonBtn(count);
            }
        }
    }

    // Hàm reset trạng thái activate của các nút lessons
    public void resetAllBtn(int count)
    {
        for (int i = 0; i < count; i++)
        {
            Button btn = (Button) lessonsContainer.getChildren().get(i);
            btn.setTextFill(Color.BLACK);
            btn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        }
        if (!lessonsContainer.getChildren().isEmpty())
        {
            Button btn = (Button) lessonsContainer.getChildren().getLast();
            btn.setTextFill(Color.BLACK);
            btn.setStyle("-fx-background-color: #D9D9D9; -fx-font-size: 20");
        }
    }

    // Hàm render bảng thông tin nhật kí của lesson được chọn
    public void loadTable1Comment(Lesson lesson)
    {
        final ObservableList<Lesson> data = FXCollections.observableArrayList();
        data.add(lesson);

        tblCol1Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblCol1Content.setCellValueFactory(new PropertyValueFactory<>("content"));
        tblCol1Class.setCellValueFactory(new PropertyValueFactory<>("className"));
        tblCol1Teacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

        tblComment1.setItems(data);
    }

    // Hàm render nhật kí chi tiết của lesson được chọn
    public void loadTable2Comment(Lesson lesson)
    {
        final ObservableList<Comment> data = FXCollections.observableArrayList();
        for (Comment comment : commentDAO.getLessonComments(lesson.getId()))
        {
            data.addAll(comment);
        }
        tblCol2StudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tblCol2StudentComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        tblCol2StudentScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tblComment2.setItems(data);
    }

    // Hàm xử lý sự kiện onclick vào lesson hiện thông tin của lesson đó
    public void onMouseClickRenderTables(MouseEvent event)
    {
        Button btn = (Button)event.getSource();
        String text = btn.getText();
        int btnLessonId = Integer.parseInt(text.substring(7));
        resetAllBtn(count);
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: #F05454; -fx-font-size: 20");
        List<Lesson> lessonList = lessonDAO.getLessonsByClassId(classesDao.findByName(className));
        for (int i = 0; i < lessonList.size(); i++)
        {
            if (i == (btnLessonId-1))
            {
                lessonId = lessonList.get(i).getId();
                loadTable1Comment(lessonDAO.getLessonInfoById(lessonList.get(i).getId()));
                loadTable2Comment(lessonList.get(i));
            }
        }
    }

    // Hàm đọc thông tin trong excel nhật kí được tải lên
    public Lesson readExcelInfo(File file)
    {
        Lesson lesson = new Lesson();
        try
        {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow titleRow = sheet.getRow(0);
            XSSFRow contentRow = sheet.getRow(1);
            XSSFRow classRow = sheet.getRow(2);

            XSSFCell titleCell = titleRow.getCell(1);
            XSSFCell contentCell = contentRow.getCell(1);
            XSSFCell classCell = classRow.getCell(1);

            String titleValue = titleCell.getStringCellValue();
            String contentValue = contentCell.getStringCellValue();
            String classValue = classCell.getStringCellValue();

            lesson.setTitle(titleValue);
            lesson.setContent(contentValue);
            lesson.setClassId(classesDao.findByName(classValue).getClassId());
            lesson.setClassName(classValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    // Hàm đọc các comments có trong excel nhật kí được tải lên
    public List<Comment> readExcelComment(File file)
    {
        List<Comment> commentList = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int i = 4; i <= rows; i++)
            {
                Comment comment = new Comment();
                XSSFRow studentCommentRow = sheet.getRow(i);
                XSSFCell studentIdCell = studentCommentRow.getCell(0);
                XSSFCell studentCommentCell = studentCommentRow.getCell(2);
                XSSFCell studentScoreCell = studentCommentRow.getCell(3);

                int studentIdValue = (int)studentIdCell.getNumericCellValue();
                String studentCommentValue = studentCommentCell.getStringCellValue();
                int studentScoreValue = (int)studentScoreCell.getNumericCellValue();

                comment.setStudentId(studentIdValue);
                comment.setComment(studentCommentValue);
                comment.setScore(studentScoreValue);

                commentList.add(comment);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commentList;
    }

    // Hàm store nhật kí
    public void storeDiary(File file, Lesson lesson)
    {
        // Lưu vào db -> set id, className, teacherName cho lesson để có data -> render lên tableview
        lessonDAO.storeLesson(lesson);
        lesson.setId(lessonDAO.findByTitle(lesson.getTitle()).getId());
        lesson.setClassName(lessonDAO.getLessonInfoById(lesson.getId()).getClassName());
        lesson.setTeacherName(lessonDAO.getLessonInfoById(lesson.getId()).getTeacherName());

        // Đọc comments trong nhật kí -> trả về list comments -> lưu vào db
        for (Comment comment : readExcelComment(file))
        {
            comment.setLessonId(lesson.getId());
            commentDAO.storeComment(comment);
        }
        // Reload các nút lessons
        lessonsContainer.getChildren().clear();
        count = 0;
        displayLessons(classObj);
    }

    // Hàm check trùng lặp nhật kí
    public boolean isDuplicateLesson(Lesson lessonObj)
    {
        for (Lesson lesson : lessonDAO.getAllLessons())
        {
            if (lessonObj.getTitle().equals(lesson.getTitle()) && lessonObj.getClassId() == lesson.getId())
            {
                return true;
            }
        }
        return false;
    }

    // Thêm mới teacherBill vào trong database
    public void addTeacherBill(Lesson lessonObj)
    {
        Account teacher = accountDAO.getTeacherData(lessonObj);
        Bill billObj = new Bill();
        billObj.setAccount_id(teacher.getId());
        billObj.setTime(teacher.getTime());
        billObj.setTotal_price(teacher.getSalary() * teacher.getLessonCount());
        billObj.setType(1);
        boolean isDuplicate = false;
        for (Bill bill : billDAO.getAllBill())
        {
            if (bill.getAccount_id() == teacher.getId() && bill.getTime().equals(teacher.getTime()))
            {
                isDuplicate = true;
                bill.setTotal_price(teacher.getSalary() * teacher.getLessonCount());
                billDAO.updateBill(bill);
            }
        }
        if (!isDuplicate || billDAO.getAllBill().isEmpty())
        {
            billDAO.addBill(billObj);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Thêm bill thành công!");
            alert.show();
        }
    }

    // Thêm mới studentBill vào trong database
    public void addStudentBill(File file, Lesson lessonObj)
    {
        for (Comment comment : readExcelComment(file))
        {
            for (Account student : accountDAO.getAllAccount(4))
            {
                if (comment.getStudentId() == student.getId())
                {
                    Bill billObj = new Bill();
                    billObj.setAccount_id(student.getId());
                    billObj.setTime(lessonObj.getTitle().substring(3));
                    billObj.setType(4);
                    boolean isDuplicate = false;
                    for (Bill bill : billDAO.getAllBill())
                    {
                        if (bill.getAccount_id() == student.getId() && bill.getTime().equals(lessonObj.getTitle().substring(3)))
                        {
                            isDuplicate = true;
                        }
                    }
                    if (!isDuplicate || billDAO.getAllBill().isEmpty())
                    {
                        billDAO.addBill(billObj);
                    }
                }
            }
        }
    }

    // Hàm xử lý sự kiện upload excel nhật kí
    public void onActionBtnUploadFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn excel nhật kí để tải lên!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(stage);
        // Tạo mới stage -> hiện confirmUploadScene
        Stage _stage = new Stage();
        FXMLLoader loader;
        ConfirmUploadController confirmUploadController;
        try {
            loader = new FXMLLoader(Main.class.getResource("view/Modal_ConfirmUpload.fxml"));
            Parent root = loader.load();
            confirmUploadController = loader.getController();
            confirmUploadController.setNameLabelText(file.getName());
            Scene scene = new Scene(root);
            _stage.setScene(scene);
            _stage.initStyle(StageStyle.TRANSPARENT);
            _stage.initModality(Modality.APPLICATION_MODAL);
            Lesson lesson = readExcelInfo(file);
            if (!isDuplicateLesson(lesson))
            {
                _stage.show();
                _stage.setOnCloseRequest((event) -> {
                    isConfirm = confirmUploadController.isConfirm();
                    if (isConfirm)
                    {
                        storeDiary(file, lesson);
                        resetAllBtn(count);
                        Button lessonBtn = (Button) lessonsContainer.getChildren().getLast();
                        lessonBtn.setStyle("-fx-background-color: #F05454; -fx-font-size: 20");
                        lessonBtn.setTextFill(Color.WHITE);
                        loadTable1Comment(lesson);
                        loadTable2Comment(lesson);
                        addTeacherBill(lesson);
                        addStudentBill(file, lesson);
                    }
                });
            } else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("File bị trùng!");
                alert.show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Hàm xử lý sự kiện export excel nhật kí
    public void onActionExportExcel(ActionEvent event)
    {
        if (lessonId != 0)
        {
            Lesson lesson = lessonDAO.getLessonInfoById(lessonId);
            List<Comment> commentList = commentDAO.getLessonComments(lessonId);
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
            );
            File saveFile = fileChooser.showSaveDialog(stage);
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Sheet1");
                XSSFRow titleRow = sheet.createRow(0);
                XSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Thời gian");
                XSSFCell titleCellValue = titleRow.createCell(1);
                titleCellValue.setCellValue(lesson.getTitle());
                XSSFRow contentRow = sheet.createRow(1);
                XSSFCell contentCell = contentRow.createCell(0);
                contentCell.setCellValue("Nội dung bài học");
                XSSFCell contentCellValue = contentRow.createCell(1);
                contentCellValue.setCellValue(lesson.getContent());
                XSSFRow classRow = sheet.createRow(2);
                XSSFCell classCell = classRow.createCell(0);
                classCell.setCellValue("Lớp");
                XSSFCell classCellValue = classRow.createCell(1);
                classCellValue.setCellValue(lesson.getClassName());
                XSSFRow labelRow = sheet.createRow(3);
                XSSFCell labelID = labelRow.createCell(0);
                labelID.setCellValue("ID");
                XSSFCell labelName = labelRow.createCell(1);
                labelName.setCellValue("Tên");
                XSSFCell labelComment = labelRow.createCell(2);
                labelComment.setCellValue("Nhận xét");
                XSSFCell labelScore = labelRow.createCell(3);
                labelScore.setCellValue("Điểm số");
                for (int i = 0; i < commentList.size(); i++)
                {
                    XSSFRow row = sheet.createRow(i + 4);
                    XSSFCell idCell = row.createCell(0);
                    idCell.setCellValue(commentList.get(i).getStudentId());
                    XSSFCell nameCell = row.createCell(1);
                    nameCell.setCellValue(commentList.get(i).getStudentName());
                    XSSFCell commentCell = row.createCell(2);
                    commentCell.setCellValue(commentList.get(i).getComment());
                    XSSFCell scoreCell = row.createCell(3);
                    scoreCell.setCellValue(commentList.get(i).getScore());
                }
                try (FileOutputStream fileOut = new FileOutputStream(saveFile.getPath())) {
                    workbook.write(fileOut);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Export nhật kí thành công!");
                    alert.show();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Export thất bại!");
                alert.show();
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui lòng chọn lesson để export nhật kí!");
            alert.show();
        }
    }
}
