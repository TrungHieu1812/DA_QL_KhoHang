package com.example.da_ql_khohang.SanPham;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogAddProdBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateProdBinding;
import com.example.da_ql_khohang.databinding.ItemProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class prod_Adapter extends RecyclerView.Adapter<prod_Adapter.ViewHolder> implements Filterable {


    private Context context;
    private List<Product_model> list;
    private List<Product_model> list_filter;
    public prod_Adapter(Context context, List<Product_model> list) {
        this.context = context;
        this.list = list;
        this.list_filter = list;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProdBinding binding = ItemProdBinding.inflate(LayoutInflater.from(parent.getContext()));
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int i) {
        holder.binding.tvnameProd.setText(list.get(i).getTenSP());
        holder.binding.tvIDProd.setText(String.valueOf(list.get(i).getId()));
        holder.binding.tvQtyProd.setText(String.valueOf(list.get(i).getSoLuong()));
        holder.binding.tvPriceProd.setText(String.valueOf(list.get(i).getGiaBan()));
        Glide.with(this.context).load(list.get(i).getImg()).into(holder.binding.imgItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(i);
            }
        });


    }


    public void showBottomSheet(int index) {
        BottomsheetProdBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bottomsheet_prod, null);
        binding = BottomsheetProdBinding.inflate(inflater,view,false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.tvTenSP.setText(list.get(index).getTenSP());
        binding.tvMaSP.setText(String.valueOf(list.get(index).getId()));
        binding.tvQty.setText(String.valueOf(list.get(index).getSoLuong()));
        binding.tvGiaSP.setText(String.valueOf(list.get(index).getGiaBan()));
        binding.tvSoLuukho.setText(String.valueOf(list.get(index).getSoLuuKho()));
        binding.tvNgayLuukho.setText(list.get(index).getNgayLuuKho());
        binding.tvNgayXuatkho.setText(list.get(index).getNgayXuatKho());

        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_prod, null);

        DialogUpdateProdBinding dialogBinding = DialogUpdateProdBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        dialogBinding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProdBinding binding;

        public ViewHolder(@NonNull ItemProdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
