package com.example.da_ql_khohang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QLKH";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null , 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TẠO BẢNG THÀNH VIÊN
        sqLiteDatabase.execSQL("CREATE TABLE THANHVIEN (maTV INTEGER PRIMARY KEY AUTOINCREMENT, " + "ten TEXT, "+
                "userName TEXT," +
                "passWord TEXT," +"email TEXT,"+
                "level TEXT)");

        // TẠO BẢNG LOẠI SẢN PHẨM
        sqLiteDatabase.execSQL("CREATE TABLE LOAISANPHAM (maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT)");

        // TẠO BẢNG SẢN PHẨM
        sqLiteDatabase.execSQL("CREATE TABLE SANPHAM (maSP INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSP TEXT," +
                "giaSP INTEGER," +
                "soLuong INTEGER," +
                "soLuuKho INTEGER," +
                "anhSP TEXT," +
                "maLoai INTEGER REFERENCES LOAISANPHAM(maLoai))");

        // TẠO BẢNG PHIẾU XUẤT / PHIẾU NHẬP
        sqLiteDatabase.execSQL("CREATE TABLE PHIEU (maPhieu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maSP INTEGER REFERENCES SANPHAM(maSP)," +
                "maTV TEXT REFERENCES THANHVIEN(maTV)," +
                "soLuong INTEGER," +
                "ngay TEXT," +
                "khoLuuTru TEXT," +
                "loaiPhieu INTEGER)"); // loại phiếu để 0 : phiếu nhập
                                        // loại phiếu để 1 : phiếu xuất

        // TẠO BẢNG CHI TIẾT PHIẾU NHẬP/ XUẤT
        sqLiteDatabase.execSQL("CREATE TABLE CHITIETPHIEU (IDchiTiet INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maPhieu INTEGER REFERENCES PHIEU(maPhieu)," +
                "maSP INTEGER REFERENCES SANPHAM(maSP)," +
                "gia INTEGER," +
                "soLuong INTEGER," +
                "giamGia INTEGER," +
                "loai INTEGER)"); // loại để 0 : chi tiết phiếu nhập
                                // loại để 1 : chi tiết phiếu xuất


        // THÊM DỮ LIỆU MẪU
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN VALUES(1,'Ngọc Hải','admin123','123','hai@gmail.com','admin')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISANPHAM VALUES(1,'ĐỒ ĂN Nhanh')");
        sqLiteDatabase.execSQL("INSERT INTO SANPHAM VALUES(1,'Hamboger',20000,15,5678,'',1)");
        sqLiteDatabase.execSQL("INSERT INTO PHIEU VALUES(1,1,'admin',5,'5/9/2012','5/9/2012',1),(2,1,'admin',5,'1/9/2012','1/9/2012',0)");
        sqLiteDatabase.execSQL("INSERT INTO CHITIETPHIEU VALUES(1,1,1,22000,10,10,1),(2,1,1,18000,15,0,0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISANPHAM");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SANPHAM");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHITIETPHIEU");
            onCreate(sqLiteDatabase);
        }
    }
}
