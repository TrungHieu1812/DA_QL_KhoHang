package com.example.da_ql_khohang.SanPham;

public class Product_model {

    private int id;
    private String tenSP;
    private int giaBan;
    private int soLuong;
    private int soLuuKho;
    private String img;

    private int maLoai;


    public Product_model() {
    }

    public Product_model(int id,String tenSP, int giaBan, int soLuong, int soLuuKho, String img, int maLoai) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.soLuuKho = soLuuKho;
        this.img = img;
        this.maLoai = maLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuuKho() {
        return soLuuKho;
    }

    public void setSoLuuKho(int soLuuKho) {
        this.soLuuKho = soLuuKho;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
