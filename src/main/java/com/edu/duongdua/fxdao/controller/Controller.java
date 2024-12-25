package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Classes;

import java.util.List;

public class Controller extends Main
{
    public ClassesDAO classesDao;

    public List<Classes> classesList;
    public List<Accounts> teachersList;
    public List<Accounts> studentsList;
}
