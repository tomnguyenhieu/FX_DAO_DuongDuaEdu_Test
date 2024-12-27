module com.edu.duongdua.fxdao.fx_dao_duongduaedu_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires org.apache.xmlbeans;
    requires java.desktop;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.apache.commons.compress;

    opens com.edu.duongdua.fxdao to javafx.fxml;
    opens com.edu.duongdua.fxdao.model to javafx.base;
    exports com.edu.duongdua.fxdao;
    exports com.edu.duongdua.fxdao.controller;
    opens com.edu.duongdua.fxdao.controller to javafx.fxml;
}