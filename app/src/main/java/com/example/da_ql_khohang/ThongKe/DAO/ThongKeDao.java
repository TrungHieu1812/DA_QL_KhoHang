package com.example.da_ql_khohang.ThongKe.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.da_ql_khohang.DataBase.DBHelper;
import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.ThongKe.ThongKe_model;

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


    public List<ThongKe_model> getTKSL(int loaiPhieu) {
        db = dbHelper.getReadableDatabase();
        List<ThongKe_model> list = new ArrayList<>();
        String query = "SELECT sp.tenSP, sp.anhSP, SUM(p.soLuong) as tongSoLuong " +
                "FROM PHIEU p " +
                "JOIN SANPHAM sp ON p.maSP = sp.maSP " +
                "WHERE p.loaiPhieu = ?" +
                "GROUP BY p.maSP";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loaiPhieu)});
        if (cursor.moveToFirst()) { // Di chuyển con trỏ đến hàng đầu tiên
            do {
                ThongKe_model tk = new ThongKe_model();
                tk.setTensp(cursor.getString(0));
                String uriString = cursor.getString(1);
                if (uriString != null) {
                    Uri uri = Uri.parse(uriString);
                    tk.setHinhsp(uri);
                }
                tk.setSoLuong(cursor.getInt(2));
                list.add(tk);
            } while (cursor.moveToNext()); // Di chuyển con trỏ đến hàng tiếp theo
        }
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return list;
    }
    public List<ThongKe_model> getTKTien(int loaiPhieu) {
        db = dbHelper.getReadableDatabase();
        List<ThongKe_model> list = new ArrayList<>();
        String query = "SELECT sp.tenSP, sp.anhSP, SUM(p.soLuong * sp.giaSP) as tongTien " +
                "FROM SANPHAM sp " +
                "JOIN PHIEU p ON sp.maSP = p.maSP " +
                "WHERE p.loaiPhieu = ?" +
                "GROUP BY p.maSP";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loaiPhieu)});
        if (cursor.moveToFirst()) { // Di chuyển con trỏ đến hàng đầu tiên
            do {
                ThongKe_model tk = new ThongKe_model();
                tk.setTensp(cursor.getString(0));
                String uriString = cursor.getString(1);
                if (uriString != null) {
                    Uri uri = Uri.parse(uriString);
                    tk.setHinhsp(uri);
                }
                tk.setTongTien(cursor.getLong(2));
                list.add(tk);
            } while (cursor.moveToNext()); // Di chuyển con trỏ đến hàng tiếp theo
        }
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return list;
    }
    public ArrayList<ThongKe_model> getTKTienTheoNgay(int loaiPhieu,String fromDate, String toDate) {
        db = dbHelper.getReadableDatabase();
        ArrayList<ThongKe_model> list = new ArrayList<>();
        String query = "SELECT sp.tenSP, sp.anhSP, SUM(p.soLuong * sp.giaSP) as tongTien " +
                "FROM SANPHAM sp " +
                "JOIN PHIEU p ON sp.maSP = p.maSP " +
                "WHERE p.loaiPhieu = ? AND p.ngay BETWEEN ? AND ? " +
                "GROUP BY p.maSP";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loaiPhieu),fromDate,toDate});
        if (cursor.moveToFirst()) { // Di chuyển con trỏ đến hàng đầu tiên
            do {
                ThongKe_model tk = new ThongKe_model();
                tk.setTensp(cursor.getString(0));
                String uriString = cursor.getString(1);
                if (uriString != null) {
                    Uri uri = Uri.parse(uriString);
                    tk.setHinhsp(uri);
                }
                tk.setTongTien(cursor.getLong(2));
                list.add(tk);
            } while (cursor.moveToNext()); // Di chuyển con trỏ đến hàng tiếp theo
        }
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return list;
    }

    public long getDoanhThu(int loaiPhieu) {
        db = dbHelper.getReadableDatabase();
        long doanhThu = 0;
        String query = "SELECT SUM(p.soLuong * sp.giaSP) as doanhThu " +
                "FROM PHIEU p " +
                "JOIN SANPHAM sp ON sp.maSP = p.maSP " +
                "WHERE p.loaiPhieu = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loaiPhieu)});
        if (cursor.moveToFirst()) { // Di chuyển con trỏ đến hàng đầu tiên
            doanhThu = cursor.getLong(0); // Lấy giá trị doanhThu từ con trỏ
        }
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return doanhThu;
    }

    @SuppressLint("Range")
    public ArrayList<ThongKe_model> getSLSPTheoNgay(int type,String fromDate, String toDate) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT sp.tenSP, sp.anhSP, SUM(p.soLuong) as tongSoLuong " +
                "FROM PHIEU p " +
                "JOIN SANPHAM sp ON p.maSP = sp.maSP " +
                "WHERE p.loaiPhieu = ? AND p.ngay BETWEEN ? AND ? " +
                "GROUP BY p.maSP";

        Cursor cursor = database.rawQuery(query,  new String[]{String.valueOf(type),fromDate, toDate});

        ArrayList<ThongKe_model> list = new ArrayList<>();

        if (cursor.moveToFirst()) { // Di chuyển con trỏ đến hàng đầu tiên
            do {
                ThongKe_model tk = new ThongKe_model();
                tk.setTensp(cursor.getString(0));
                String uriString = cursor.getString(1);
                if (uriString != null) {
                    Uri uri = Uri.parse(uriString);
                    tk.setHinhsp(uri);
                }
                tk.setSoLuong(cursor.getInt(2));
                list.add(tk);
            } while (cursor.moveToNext()); // Di chuyển con trỏ đến hàng tiếp theo
        }
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return list;
    }

}
