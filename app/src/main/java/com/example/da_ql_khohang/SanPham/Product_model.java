package com.example.da_ql_khohang.SanPham;

public class Product_model {

    private int id;
    private String tenSP;
    private int giaBan;
    private int soLuong;
    private int soLuuKho;
    private String ngayLuuKho;
    private String ngayXuatKho;
    private String img;

    public Product_model() {
    }

    public Product_model(int id, String tenSP, int giaBan, int soLuong, int soLuuKho, String ngayLuuKho, String ngayXuatKho, String img) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.soLuuKho = soLuuKho;
        this.ngayLuuKho = ngayLuuKho;
        this.ngayXuatKho = ngayXuatKho;
        this.img = img;
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

    public String getNgayLuuKho() {
        return ngayLuuKho;
    }

    public void setNgayLuuKho(String ngayLuuKho) {
        this.ngayLuuKho = ngayLuuKho;
    }

    public String getNgayXuatKho() {
        return ngayXuatKho;
    }

    public void setNgayXuatKho(String ngayXuatKho) {
        this.ngayXuatKho = ngayXuatKho;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
