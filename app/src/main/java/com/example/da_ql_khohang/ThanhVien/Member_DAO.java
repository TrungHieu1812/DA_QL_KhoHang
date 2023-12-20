package com.example.da_ql_khohang.ThanhVien;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da_ql_khohang.database.DBHelper;
import com.example.da_ql_khohang.SanPham.Product_model;

import java.util.ArrayList;
import java.util.List;

public class Member_DAO {
    DBHelper dbHelper;

    private Member_Model mem;

    private SharedPreferences share;

    public Member_DAO(Context context) {
        dbHelper = new DBHelper(context);
        share = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
    }

    public ArrayList<Member_Model> getAll_mem() {
        ArrayList<Member_Model> list = new ArrayList<Member_Model>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cs = db.rawQuery("SELECT * FROM THANHVIEN ", null);
            if (cs.getCount() > 0) {
                cs.moveToFirst();
                while (!cs.isAfterLast()) {
                    mem = new Member_Model();
                    mem.setId_nv(cs.getInt(0));
                    mem.setFullname(cs.getString(1));
                    mem.setUsername(cs.getString(2));
                    mem.setPasswd(cs.getString(3));
                    mem.setEmail(cs.getString(4));
                    mem.setAvatar(cs.getString(5));
                    mem.setRole(cs.getInt(6));
                    list.add(mem);
                    cs.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzzzzzzzz", "Lỗiiiiii");
        }

        return list;
    }

    public boolean insert_mem(Member_Model member) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullName", member.getFullname());
        values.put("userName", member.getUsername());
        values.put("passWord", member.getPasswd());
        values.put("email", member.getEmail());
        values.put("avatar", member.getAvatar());
        values.put("role", member.getRole());

        long row = db.insert("THANHVIEN", null, values);
        return (row > 0);
    }

    public long update_mem(Member_Model member) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullName", member.getFullname());
        values.put("userName", member.getUsername());
        values.put("passWord", member.getPasswd());
        values.put("email", member.getEmail());
        values.put("avatar", member.getAvatar());
        values.put("role", member.getRole());

        return db.update("THANHVIEN", values, "maTV=?", new String[]{String.valueOf(member.getId_nv())});
    }

    public int delete_mem(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete("THANHVIEN", "maTV=?", new String[]{String.valueOf(id)});

    }

    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN where userName = ? and passWord = ?",
                new String[]{user, pass});

        if (c.getCount() != 0) {
            c.moveToFirst();
            SharedPreferences.Editor editor = share.edit();
            editor.putInt("maTV", c.getInt(0));
            editor.putString("fullName", c.getString(1));
            editor.putString("userName", c.getString(2));
            editor.putString("passWord", c.getString(3));
            editor.putString("email", c.getString(4));
            editor.putString("avatar", c.getString(5));
            editor.putInt("role", c.getInt(6));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }
}
