package com.edu.duongdua.fxdao.model;

public class Classes extends Model
{
    private int classId;
    private int classTeacherId;
    private String className;
    private int classDeleted;
    private String classTeacherName;
    private int classTotalStudents;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(int classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassDeleted() {
        return classDeleted;
    }

    public void setClassDeleted(int classDeleted) {
        this.classDeleted = classDeleted;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    public int getClassTotalStudents() {
        return classTotalStudents;
    }

    public void setClassTotalStudents(int classTotalStudents) {
        this.classTotalStudents = classTotalStudents;
    }
}
