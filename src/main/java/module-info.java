module com.edu.duongdua.fxdao.fx_dao_duongduaedu_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;

    opens com.edu.duongdua.fxdao to javafx.fxml;
    exports com.edu.duongdua.fxdao;
    exports com.edu.duongdua.fxdao.controller;
    opens com.edu.duongdua.fxdao.controller to javafx.fxml;
}