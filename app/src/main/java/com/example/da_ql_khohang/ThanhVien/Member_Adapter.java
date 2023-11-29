package com.example.da_ql_khohang.ThanhVien;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.ChiTietNvBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateNvBinding;
import com.example.da_ql_khohang.databinding.ItemNvBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Member_Adapter extends RecyclerView.Adapter<Member_Adapter.ViewHolder> {
    Context context;
    private List<Member_Model> list;
    private List<Member_Model> list_filter;
    Member_DAO dao;
    RecyclerView rcv;
    Member_Adapter adapter;


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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = position;
        holder.tv_ten.setText(list.get(index).getTen());
        holder.tv_mail.setText(String.valueOf(list.get(index).getEmail()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chiTiet_nv(index);
            }
        });
    }

    public void chiTiet_nv(int index) {

        ChiTietNvBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.chi_tiet_nv, null);
        binding = ChiTietNvBinding.inflate(inflater, view, false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();


        binding.tvTenNv.setText(list.get(index).getTen());
        binding.tvIdNv.setText(String.valueOf(list.get(index).getId_nv()));
        binding.tvTkNv.setText(list.get(index).getUsername());
        binding.tvLevelNv.setText(list.get(index).getLevel());
        binding.tvEmailNv.setText(list.get(index).getEmail());

        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               xoaNv(index, bottomSheetDialog);

            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(index,bottomSheetDialog);
            }
        });
    }

            public void xoaNv(int position, BottomSheetDialog bsdialog) {
                dao = new Member_DAO(context);
                int check = dao.delete_mem(list.get(position).getId_nv());
                switch (check) {
                    case 1:
                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                        mbuilder.setMessage("Bạn có muốn xoá không?");
                        mbuilder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.clear();
                                list = dao.getAll_mem();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                bsdialog.dismiss();
                            }
                        });
                        mbuilder.setPositiveButton("Không", null);
                        mbuilder.show();
                        break;

                    case 0:
                        Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

    private void showDialog(int position, BottomSheetDialog bsdialog) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_nv, null);
        DialogUpdateNvBinding nvBinding = DialogUpdateNvBinding.inflate(inflater,view,false);

        builder.setView(nvBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // tạo dữ liệu cũ trong update
        nvBinding.etSuaTenNv.setText(list.get(position).getTen());
        nvBinding.etSuaUsernameNv.setText(list.get(position).getUsername());
        nvBinding.etSuaPasswdNv.setText(list.get(position).getPasswd());
        nvBinding.etSuaLevelNv.setText(list.get(position).getLevel());
        nvBinding.etSuaMailNv.setText(list.get(position).getEmail());
//
        nvBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String ten_nv = nvBinding.etSuaTenNv.getText().toString();
           int id = list.get(position).getId_nv();
           String username_nv = nvBinding.etSuaUsernameNv.getText().toString();
           String passwd_nv = nvBinding.etSuaPasswdNv.getText().toString();
           String level_nv = nvBinding.etSuaLevelNv.getText().toString();
          String email_nv = nvBinding.etSuaMailNv.getText().toString();

           Member_Model member = list.get(position);
           dao = new Member_DAO(context);
           if (ten_nv.isEmpty() || username_nv.isEmpty() || passwd_nv.isEmpty() || level_nv.isEmpty() || email_nv.isEmpty() ) {
               Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
           } else {
               member.setTen(ten_nv);
               member.setUsername(username_nv);
               member.setPasswd(passwd_nv);
               member.setEmail(email_nv);
               member.setLevel(level_nv);
              if(dao.update_mem(member) != -1){
                  list.clear();
                  list = dao.getAll_mem();
                  notifyDataSetChanged();
                  bsdialog.dismiss();
                  Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                  alertDialog.dismiss();
              }
               else {
                  Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
              }
           }
       }

        });


      nvBinding.btnHuy.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            alertDialog.cancel();
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
    private void loadData() {
        dao = new Member_DAO(context);
        list = dao.getAll_mem();
        adapter = new Member_Adapter(context, list);
        rcv.setAdapter(adapter);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ten, tv_mail;
        LinearLayout linear_nv;

        public ViewHolder(@NonNull ItemNvBinding binding) {
            super(binding.getRoot());

            tv_ten = itemView.findViewById(R.id.tv_tenNv);
            tv_mail = itemView.findViewById(R.id.tv_email);
            linear_nv = itemView.findViewById(R.id.linear_tv);
        }

    }
}