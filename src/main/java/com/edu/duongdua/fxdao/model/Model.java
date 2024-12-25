package com.edu.duongdua.fxdao.model;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.db.DBConnect;

import java.sql.Connection;

public class Model extends Main
{
    private DBConnect db;

    public Model() {
        db = new DBConnect();
    }

    public Connection getConnection() {
        return this.db.getConnection();
    }
}
