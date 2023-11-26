package com.example.da_ql_khohang.ThongKe;

public class Top {
    int masp, hinhsp, soluong;
    double giaban;
    String ten;

    public Top(double giaban, String ten) {
        this.giaban = giaban;
        this.ten = ten;
    }

    public Top(int soluong, String ten) {
        this.soluong = soluong;
        this.ten = ten;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(int hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getGiaban() {
        return giaban;
    }

    public void setGiaban(double giaban) {
        this.giaban = giaban;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Top(int masp, int hinhsp, double giaban, String ten) {
        this.masp = masp;
        this.hinhsp = hinhsp;
        this.giaban = giaban;
        this.ten = ten;
    }

    public Top(int masp, int hinhsp, int soluong, String ten) {
        this.masp = masp;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
        this.ten = ten;
    }

    public Top() {
    }
}
