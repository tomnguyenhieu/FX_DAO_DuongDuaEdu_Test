package com.edu.duongdua.fxdao.controller;

import com.edu.duongdua.fxdao.Main;
import com.edu.duongdua.fxdao.dao.AccountDAO;
import com.edu.duongdua.fxdao.dao.ClassesDAO;
import com.edu.duongdua.fxdao.model.Account;
import com.edu.duongdua.fxdao.model.Classes;

import java.util.List;

public class Controller extends Main
{
    public ClassesDAO classesDao = new ClassesDAO();;
    public AccountDAO accountDAO = new AccountDAO();

    public List<Classes> classesList = classesDao.getAllClasses();
    public List<Account> teachersList = accountDAO.getAllTeacher();
    public List<Account> studentsList = accountDAO.getAllStudent();
}
