package com.example.da_ql_khohang.TheLoai;

public class category_model {

    private int id;
    private String tenLoai;


    public category_model() {
    }

    public category_model(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
