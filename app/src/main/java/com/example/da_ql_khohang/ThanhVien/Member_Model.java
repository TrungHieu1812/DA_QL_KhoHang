package com.example.da_ql_khohang.ThanhVien;

public class Member_Model {
    private int id_nv;
    private String ten_nv,tk_nv,mk_nv,role;
     private int sdt_nv;
    private String img;
    public Member_Model() {
    }

    public Member_Model(int id_nv, String ten_nv, String tk_nv, String mk_nv, String role, int sdt_nv, String img) {
        this.id_nv = id_nv;
        this.ten_nv = ten_nv;
        this.tk_nv = tk_nv;
        this.mk_nv = mk_nv;
        this.role = role;
        this.sdt_nv = sdt_nv;
        this.img = img;
    }

    public int getId_nv() {
        return id_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public String getTen_nv() {
        return ten_nv;
    }

    public void setTen_nv(String ten_nv) {
        this.ten_nv = ten_nv;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTk_nv() {
        return tk_nv;
    }

    public void setTk_nv(String tk_nv) {
        this.tk_nv = tk_nv;
    }

    public String getMk_nv() {
        return mk_nv;
    }

    public void setMk_nv(String mk_nv) {
        this.mk_nv = mk_nv;
    }

    public int getSdt_nv() {
        return sdt_nv;
    }

    public void setSdt_nv(int sdt_nv) {
        this.sdt_nv = sdt_nv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
