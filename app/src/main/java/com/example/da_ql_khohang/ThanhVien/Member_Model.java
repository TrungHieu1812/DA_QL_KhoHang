package com.example.da_ql_khohang.ThanhVien;

public class Member_Model {
    private int id_nv;
    private String ten,username,passwd,email,level;

    public Member_Model() {

    }

    public Member_Model(int id_nv, String ten, String username, String passwd, String email, String level) {
        this.ten = ten;
        this.id_nv = id_nv;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.level = level;
    }

    public Member_Model(int id, String tenNv) {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level= level;
    }
}