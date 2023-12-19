package com.example.da_ql_khohang.ThanhVien;

public class Member_Model {
    private int id_nv;
    private String fullname,username,passwd,email,avatar;

    private int role;


    public Member_Model() {

    }

    public Member_Model(int id_nv, String fullname, String username, String passwd, String email, String avatar, int role) {
        this.id_nv = id_nv;
        this.fullname = fullname;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.avatar = avatar;
        this.role = role;
    }

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}