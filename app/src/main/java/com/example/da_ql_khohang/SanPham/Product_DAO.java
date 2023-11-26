package com.example.da_ql_khohang.SanPham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da_ql_khohang.DataBase.DBHelper;
import com.example.da_ql_khohang.TheLoai.category_model;

import java.util.ArrayList;

public class Product_DAO {

    private final DBHelper dbHelper;
    private Product_model prod;

    public Product_DAO(Context context) {
        dbHelper = new DBHelper(context);
    }


    public ArrayList<Product_model> getAllProduct() {
        ArrayList<Product_model> listProd = new ArrayList<Product_model>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT sp.maSP, sp.tenSP, sp.giaSP, sp.soLuong, sp.soLuuKho, sp.anhSP, sp.maLoai, lo.tenLoai FROM SANPHAM sp, LOAISANPHAM lo WHERE sp.maLoai = lo.maLoai ORDER BY sp.maSP DESC", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    prod = new Product_model();
                    prod.setId(cursor.getInt(0));
                    prod.setTenSP(cursor.getString(1));
                    prod.setGiaBan(cursor.getInt(2));
                    prod.setSoLuong(cursor.getInt(3));
                    prod.setSoLuuKho(cursor.getInt(4));
                    prod.setImg(cursor.getString(5));
                    prod.setMaLoai(cursor.getInt(6));
                    listProd.add(prod);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzzzzzzzz", "Lỗiiiiii");
        }
        return listProd;
    }

    public boolean insertProd(String tenSP,int giaSP,int soLuong,int soLuuKho,String anhSP,int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSP", tenSP);
        values.put("giaSP", giaSP);
        values.put("soLuong", soLuong);
        values.put("soLuuKho", soLuuKho);
        values.put("anhSP", anhSP);
        values.put("maLoai", maLoai);
        long check = db.insert("SANPHAM", null, values);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public int deleteProd(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEU WHERE maSP = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() != 0) {
            return -1;
        }

        long check = db.delete("SANPHAM", "maSP=?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }


    public boolean updateProd(Product_model prod){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSP", prod.getTenSP());
        values.put("giaSP", prod.getGiaBan());
        values.put("soLuong", prod.getSoLuong());
        values.put("soLuuKho", prod.getSoLuuKho());
        values.put("anhSP", prod.getImg());
        values.put("maLoai", prod.getMaLoai());
        long check = db.update("SANPHAM",values, "maSP = ?",new String[]{String.valueOf(prod.getId())});
        if (check == -1)
            return false;
        return true;
    }

    public Product_model getProdById(int id) {
        prod = new Product_model();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM SANPHAM WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            prod.setId(c.getInt(0));
            prod.setTenSP(c.getString(1));
        }
        return prod;
    }




}
