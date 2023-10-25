package com.example.da_ql_khohang.TheLoai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.BottomsheetCategoryBinding;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateCategoryBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateProdBinding;
import com.example.da_ql_khohang.databinding.ItemCategoryBinding;
import com.example.da_ql_khohang.databinding.ItemProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class category_Adapter extends RecyclerView.Adapter<category_Adapter.ViewHolder> implements Filterable {


    private Context context;
    private List<category_model> list;
    private List<category_model> list_filter;
    public category_Adapter(Context context, List<category_model> list) {
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
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()));
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int i) {
        holder.binding.tvnameCate.setText(list.get(i).getTenLoai());
        holder.binding.tvIDCate.setText(String.valueOf(list.get(i).getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(i);
            }
        });


    }


    public void showBottomSheet(int index) {
        BottomsheetCategoryBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bottomsheet_prod, null);
        binding = BottomsheetCategoryBinding.inflate(inflater,view,false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.tvTenLoai.setText(list.get(index).getTenLoai());
        binding.tvMaLoai.setText(String.valueOf(list.get(index).getId()));

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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_category, null);

        DialogUpdateCategoryBinding dialogBinding = DialogUpdateCategoryBinding.inflate(inflater, view, false);
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
        private final ItemCategoryBinding binding;

        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
