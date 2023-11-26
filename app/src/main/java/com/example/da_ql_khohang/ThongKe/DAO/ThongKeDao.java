package com.example.da_ql_khohang.ThongKe.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da_ql_khohang.ThongKe.Top;
import com.example.da_ql_khohang.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {
    DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

    public ThongKeDao(Context context){
        this.context =context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Top> getSoLuongNhap(){
        String sqlTop = "select maSP, count(soLuong) as soluong from phieu where loai=0 group by maSP ";
        List<Top> list = new ArrayList<>();
//        SanPhamDao spDao = new SanPhamDao(context);
        Cursor cursor = db.rawQuery(sqlTop,null);
        while (cursor.moveToNext()){
            Top top = new Top();
//            SanPham sp = spDao.getID(cursor.getString(cursor.getColumnIndex("maSP")));
//            top.setTenSanPham(s.getTenSanPham());
            top.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            list.add(top);
        }
        return list;
    }
}
    public List<Top> getSoLuongXuat(){
        String sqlTop = "select maSP, count(soLuong) as soluong from phieu where loai=1 group by maSP ";
        List<Top> list = new ArrayList<>();
//        SanPhamDao spDao = new SanPhamDao(context);
        Cursor cursor = db.rawQuery(sqlTop,null);
        while (cursor.moveToNext()){
            Top top = new Top();
//            SanPham sp = spDao.getID(cursor.getString(cursor.getColumnIndex("maSP")));
//            top.setTenSanPham(s.getTenSanPham());
            top.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soluong"))));
            list.add(top);
        }
        return list;
    }
}
