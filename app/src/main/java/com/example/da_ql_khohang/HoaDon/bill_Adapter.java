package com.example.da_ql_khohang.HoaDon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.HoaDon.model.bill;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.TheLoai.category_DAO;
import com.example.da_ql_khohang.TheLoai.category_model;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogChiTietHoaDonBinding;
import com.example.da_ql_khohang.databinding.ItemHoaDonBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class bill_Adapter extends RecyclerView.Adapter < bill_Adapter.ViewHolder > implements Filterable {
    private Context context;
    private ArrayList < bill > list;


    private bill_dao dao;

    public bill_Adapter(Context context, ArrayList < bill > list) {
        this.context = context;
        this.list = list;
        dao = new bill_dao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHoaDonBinding binding = ItemHoaDonBinding.inflate(LayoutInflater.from(parent.getContext()));
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        bill bill = list.get(i);
        int index = i;

        holder.binding.tvMaHoaDon.setText("Mã hóa đơn: " + bill.getMaPhieu());

        holder.binding.tvNgayXuatHoaDon.setText("Ngày: " + bill.getNgayluutru());

        Product_DAO spDAO = new Product_DAO(context);
        Product_model sp = spDAO.getProdById(bill.getMaSP());
        holder.binding.tvMaNhanVien.setText("Tên sản phẩm: " + sp.getTenSP());
        String name = sp.getTenSP();
        double tongtien = bill.getSl() * sp.getGiaBan();

        holder.binding.tvGiaTien.setText("Tổng tiền: " + tongtien);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(index,tongtien,name);
            }
        });
    }


    public void showBottomSheet(int index,double tongtien,String name) {
        DialogChiTietHoaDonBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_chi_tiet_hoa_don, null);
        binding = DialogChiTietHoaDonBinding.inflate(inflater, view, false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.tvHoaDon.setText("Mã hóa đơn : "+list.get(index).getMaPhieu()+"");
        binding.tvNhanVien.setText("Nhân Viên : "+list.get(index).getMathanhvien());
        binding.tvTenSanPham.setText("Tên sản phẩm : "+name);
        binding.tvSoLuong.setText("Số lượng : "+list.get(index).getSl());
        binding.tvGiaTien.setText("Ngày lưu kho : "+list.get(index).getNgayluutru());
        binding.tvThanhTien.setText("Thành tiền : "+tongtien);

        binding.btnHuyHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        binding.btnXoaHoaDonChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Hiện tại bạn không thể hóa hóa đơn này!!!", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemHoaDonBinding binding;

        public ViewHolder(@NonNull ItemHoaDonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}




