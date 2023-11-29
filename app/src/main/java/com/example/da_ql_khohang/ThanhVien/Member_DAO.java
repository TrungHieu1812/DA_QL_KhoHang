package com.example.da_ql_khohang.ThanhVien;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da_ql_khohang.database.DBHelper;

import java.util.ArrayList;

public class Member_DAO {
    DBHelper dbHelper;

    public Member_DAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    public ArrayList<Member_Model> getAll_mem(){
        ArrayList<Member_Model> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] ds_cot = new String[]{"*"};
        Cursor cs = db.query("THANHVIEN",ds_cot,null,null,null,null,null);

        if (cs.moveToFirst()){
           while (!cs.isAfterLast()){
               Member_Model member = new Member_Model();
               member.setId_nv(cs.getInt(0));
               member.setTen(cs.getString(1));
               member.setUsername(cs.getString(2));
               member.setPasswd(cs.getString(3));
               member.setEmail(cs.getString(4));
               member.setLevel(cs.getString(5));

               list.add(member);
               cs.moveToNext();
           }


        }

        return list;
    }
    public long insert_mem(Member_Model member){

        SQLiteDatabase db =dbHelper.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put("ten",member.getTen());
        values.put("userName",member.getUsername() );
        values.put("passWord",member.getPasswd());
        values.put("email",member.getEmail());
        values.put("level",member.getLevel());


       return db.insert("THANHVIEN",null,values);

    }
    public long update_mem(Member_Model member){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten",member.getTen());
        values.put("userName",member.getUsername() );
        values.put("passWord",member.getPasswd());
        values.put("email",member.getEmail());
        values.put("level",member.getLevel());

        return db.update("THANHVIEN",values,"maTV=?",new String[]{String.valueOf(member.getId_nv())});
    }

    public int delete_mem(int id){

     SQLiteDatabase db = dbHelper.getWritableDatabase();

    return db.delete("THANHVIEN","maTV=?",new String[]{String.valueOf(id)});

    }

    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN where maTV = ? and userName = ? and passWord = ?",
                new String [] {user, pass});
        int row = cursor.getCount();
        return (row >= 0);
    }
}
