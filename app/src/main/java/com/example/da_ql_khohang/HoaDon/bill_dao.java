package com.example.da_ql_khohang.HoaDon;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.da_ql_khohang.DataBase.DBHelper;
import com.example.da_ql_khohang.HoaDon.model.bill;

import java.util.ArrayList;
import java.util.List;

public class bill_dao {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public bill_dao(Context context) {
        this.context = context;
        dbHelper = new DBHelper(this.context);

        db = dbHelper.getWritableDatabase();

    }

    public void update_bill(bill a) {
        ContentValues values = new ContentValues();
        values.put("maSP", a.getMaSP());
        values.put("maTV", a.getMathanhvien());
        values.put("loaiPhieu", a.getLoai());
        values.put("khoLuuTru", a.getKhoLuuTru());
        values.put("soLuong", a.getSl());
        values.put("ngay", a.getNgayluutru());

        long Ketqua = db.update("PHIEU", values, "maSP =?", new String[]{String.valueOf(a.getMaPhieu())});
        if (Ketqua >= 0) {
            Toast.makeText(context, "update Hoa don thanh cong!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "update Hoa don KHONG thanh cong!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert_bill(bill a) {
        ContentValues values = new ContentValues();
        values.put("maSP", a.getMaSP());
        values.put("maTV", a.getMathanhvien());
        values.put("loaiPhieu", a.getLoai());
        values.put("khoLuuTru", a.getKhoLuuTru());
        values.put("soLuong", a.getSl());
        values.put("ngay", a.getNgayluutru());

        long Ketqua = db.insert("PHIEU", null, values);
        if (Ketqua >= 0) {
            Toast.makeText(context, "Them Hoa don thanh cong!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Them Hoa don KHONG thanh cong!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete_bill(bill a) {
        long Ketqua = db.delete("PHIEU", "maSP =?", new String[]{String.valueOf(a.getMaPhieu())});
        if (Ketqua >= 0) {
            Toast.makeText(context, "delete Hoa don thanh cong!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "delete Hoa don KHONG thanh cong!!!", Toast.LENGTH_SHORT).show();
        }
    }
//    public List<bill> getAll() {
//        String sql = "SELECT * FROM PHIEU";
//        return getData(sql);
//    }
//    public ArrayList< bill >getData(String sql){
//        ArrayList <bill>listbill= new ArrayList <>();
//        String truyvan="SELECT *FROM PHIEU";
//        Cursor ketqua=db.rawQuery(truyvan,null);
//        if (ketqua.getCount()>0){
//            ketqua.moveToFirst();
//            while (ketqua.moveToNext()){
//                bill bill= new bill();
//                bill.setMaPhieu(ketqua.getInt(0));
//               bill.setMaSP(ketqua.getInt(1));
//               bill.setMathanhvien(ketqua.getString(2));
//               bill.setTensp(ketqua.getString(3));
//               bill.setSl(ketqua.getInt(4));
//               bill.setNgayxuat(ketqua.getString(5));
//                bill.setNgayluutru(ketqua.getString(6));
//
//               listbill.add(bill);
//
//            }
//            ketqua.close();
//        }
//
//        return  listbill;
//    }

    public List < bill > getAllPhieu() {
        List < bill > phieuList = new ArrayList <>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            String query = "SELECT * FROM PHIEU";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    bill bill = new bill();
                    bill.setMaPhieu(cursor.getInt(0));
                    bill.setMaSP(cursor.getInt(1));
                    bill.setMathanhvien(cursor.getString(2));
                    bill.setSl(cursor.getInt(3));
                    bill.setKhoLuuTru(cursor.getString(5));
                    bill.setNgayluutru(cursor.getString(4));
                    bill.setLoai(cursor.getInt(6));
                    phieuList.add(bill);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzz", "Loi");
        }

        return phieuList;
    }

}
