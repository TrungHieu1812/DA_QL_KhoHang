package com.example.da_ql_khohang.ThanhVien;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.ChiTietNvBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateNvBinding;
import com.example.da_ql_khohang.databinding.ItemNvBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class Member_Adapter extends RecyclerView.Adapter<Member_Adapter.ViewHolder> {
    Context context;
    private List<Member_Model> list;
    private List<Member_Model> list_filter;

    public Member_Adapter(Context context, List<Member_Model> list) {
        this.context = context;
        this.list = list;
        this.list_filter = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ItemNvBinding binding = ItemNvBinding.inflate(LayoutInflater.from(parent.getContext()));
       ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int i) {
        holder.binding.tvTenNv.setText(list.get(i).getTen_nv());
        holder.binding.tvSdt.setText(String.valueOf(list.get(i).getSdt_nv()));
        Glide.with(this.context).load(list.get(i).getImg()).into(holder.binding.imgNv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chiTiet_nv(i);
            }
        });
    }

    public void chiTiet_nv(int index){
        ChiTietNvBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.chi_tiet_nv,null);
        binding = ChiTietNvBinding.inflate(inflater,view,false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

         binding.tvTenNv.setText(list.get(index).getTen_nv());
         binding.tvIdNv.setText(String.valueOf(list.get(index).getId_nv()));
         binding.tvTkNv.setText(list.get(index).getTk_nv());
         binding.tvRoleNv.setText(list.get(index).getRole());
         binding.tvSdtNv.setText(String.valueOf(list.get(index).getSdt_nv()));

         binding.btnXoa.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bottomSheetDialog.dismiss();
             }
         });

         binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDialog();
             }
         });

    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_nv,null);

        DialogUpdateNvBinding dialogUpdateNvBinding = DialogUpdateNvBinding.inflate(inflater,view,false);
        builder.setView(dialogUpdateNvBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogUpdateNvBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialogUpdateNvBinding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public class ViewHolder extends RecyclerView.ViewHolder{
  private final ItemNvBinding binding;
        public ViewHolder(@NonNull ItemNvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}