package com.example.da_ql_khohang.TheLoai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da_ql_khohang.DataBase.DBHelper;
import com.example.da_ql_khohang.SanPham.Product_model;

import java.util.ArrayList;

public class category_DAO {

    private final DBHelper dbHelper;
    private category_model cate;

    public category_DAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    //    thêm loại sp
    public boolean insertCate(String tenLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        long check = db.insert("LOAISANPHAM", null, values);
        if (check == -1)
            return false;
        return true;

    }

    //    xóa loại sp

    //    1: xóa thành công - 0: xóa thất bại - -1: sp có tồn tại, k đc xóa
    public int deleteCate(int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM SANPHAM WHERE maLoai = ?", new String[]{String.valueOf(maLoai)});
        if (c.getCount() != 0) {
            return -1;
        }

        long check = db.delete("LOAISANPHAM", "maLoai = ?", new String[]{String.valueOf(maLoai)});
        if (check == -1)
            return 0;
        return 1;
    }
    //  sửa loại sp
    public boolean updateCate(category_model loaisp){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai",loaisp.getTenLoai());
        long check = db.update("LOAISANPHAM",contentValues, "maLoai = ?",new String[]{String.valueOf(loaisp.getId())});
        if (check == -1)
            return false;
        return true;
    }

    //    lấy danh sách loại sp
    public ArrayList<category_model> getDataCate() {
        ArrayList<category_model> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM LOAISANPHAM", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                cate = new category_model();
                cate.setId(c.getInt(0));
                cate.setTenLoai(c.getString(1));
                list.add(cate);
                c.moveToNext();
            }
        }
        return list;
    }



    public category_model getCatById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM LOAISANPHAM WHERE maLoai = ?",  new String[]{String.valueOf(id)});
        if (c.getCount() != 0){
            c.moveToFirst();
            return new category_model(c.getInt(0),c.getString(1));
        }
        return null;
    }
}
