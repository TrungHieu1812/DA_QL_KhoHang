package com.example.da_ql_khohang.ThongKe;

import android.net.Uri;

public class ThongKe_model {
    private String tensp;
    private Uri hinhsp;
    private int soLuong;
    private int soLuongNhap;
    private int soLuongXuat;

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public long getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(long doanhThu) {
        this.doanhThu = doanhThu;
    }

    public long getTienNhan() {
        return tienNhan;
    }

    public void setTienNhan(long tienNhan) {
        this.tienNhan = tienNhan;
    }

    public long getTienXuat() {
        return tienXuat;
    }

    public void setTienXuat(long tienXuat) {
        this.tienXuat = tienXuat;
    }

    private int soLuongTon;
    private long tongTien,doanhThu,tienNhan,tienXuat;

    public Uri getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(Uri hinhsp) {
        this.hinhsp = hinhsp;
    }

    public ThongKe_model() {
    }

    public ThongKe_model(String tensp,  int soLuong) {
        this.tensp = tensp;
        this.soLuong = soLuong;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }


    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }
}
