package com.example.da_ql_khohang.HoaDon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.HoaDon.model.bill;
import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.databinding.ItemHoaDonBinding;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bill bill = list.get(position);

        holder.binding.tvMaHoaDon.setText("Mã hóa đơn: " + bill.getMaPhieu());

        holder.binding.tvNgayXuatHoaDon.setText("Ngày: " + bill.getNgayluutru());

        Product_DAO spDAO = new Product_DAO(context);
        Product_model sp = spDAO.getProdById(bill.getMaSP());
        holder.binding.tvMaNhanVien.setText("Tên sản phẩm: " + sp.getTenSP());
        double tongtien = bill.getSl() * sp.getGiaBan();

        holder.binding.tvGiaTien.setText("Tổng tiền: " + tongtien);
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




