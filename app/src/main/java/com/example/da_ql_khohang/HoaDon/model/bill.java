package com.example.da_ql_khohang.HoaDon.model;

public class bill {
    private int maPhieu, maSP, sl, loai;
    private String mathanhvien, khoLuuTru, ngayluutru, tensp;


    public int getMaPhieu() {
        return maPhieu;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public String getKhoLuuTru() {
        return khoLuuTru;
    }

    public void setKhoLuuTru(String khoLuuTru) {
        this.khoLuuTru = khoLuuTru;
    }

    public String getNgayluutru() {
        return ngayluutru;
    }

    public void setNgayluutru(String ngayluutru) {
        this.ngayluutru = ngayluutru;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
}
