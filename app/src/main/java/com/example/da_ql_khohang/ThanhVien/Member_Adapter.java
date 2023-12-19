package com.example.da_ql_khohang.ThanhVien;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.databinding.ChiTietNvBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateNvBinding;
import com.example.da_ql_khohang.databinding.ItemNvBinding;

import com.example.da_ql_khohang.databinding.ItemProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Member_Adapter extends RecyclerView.Adapter<Member_Adapter.ViewHolder> implements Filterable {
    Context context;
    private List<Member_Model> list;
    private List<Member_Model> list_filter;
    private Member_DAO dao;


    public Member_Adapter(Context context, List<Member_Model> list, Member_DAO dao) {
        this.context = context;
        this.list = list;
        this.list_filter = list;
        this.dao = dao;
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
        holder.binding.tvTenNv.setText(list.get(index).getFullname());
        holder.binding.tvEmail.setText(list.get(index).getEmail());
        if (list.get(index).getAvatar() != null){
            Glide.with(this.context).load(list.get(index).getAvatar()).into(holder.binding.imgNv);
        }else {
            holder.binding.imgNv.setImageResource(R.drawable.avatar);
        }

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


        binding.tvTenNv.setText(list.get(index).getFullname());
        binding.tvIdNv.setText(String.valueOf(list.get(index).getId_nv()));
        binding.tvTkNv.setText(list.get(index).getUsername());
        binding.tvEmailNv.setText(list.get(index).getEmail());
        if (list.get(index).getRole() == 0) {
            binding.tvLevelNv.setText("ADMIN");
        } else {
            binding.tvLevelNv.setText("THỦ KHO");
        }


        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaNv(index, bottomSheetDialog);

            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(index, bottomSheetDialog);
            }
        });
    }

    public void xoaNv(int position, BottomSheetDialog bsdialog) {
        dao = new Member_DAO(context);
        int check = dao.delete_mem(list.get(position).getId_nv());
        switch (check) {
            case 1:
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                mbuilder.setTitle("Thông báo");
                mbuilder.setMessage("Bạn có muốn xoá không?");
                mbuilder.setIcon(R.drawable.baseline_warning_24);
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
        DialogUpdateNvBinding nvBinding = DialogUpdateNvBinding.inflate(inflater, view, false);

        builder.setView(nvBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // tạo dữ liệu cũ trong update
        nvBinding.etSuaTenNv.setText(list.get(position).getFullname());
        nvBinding.etSuaUsernameNv.setText(list.get(position).getUsername());
        nvBinding.etSuaPasswdNv.setText(list.get(position).getPasswd());
        nvBinding.etSuaMailNv.setText(list.get(position).getEmail());
        nvBinding.etSuaAvatarNv.setText(list.get(position).getAvatar());
//
        nvBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_nv = nvBinding.etSuaTenNv.getText().toString();
                String username_nv = nvBinding.etSuaUsernameNv.getText().toString();
                String passwd_nv = nvBinding.etSuaPasswdNv.getText().toString();
                String email_nv = nvBinding.etSuaMailNv.getText().toString();
                String ava_nv = nvBinding.etSuaAvatarNv.getText().toString();

                Member_Model member = list.get(position);
                dao = new Member_DAO(context);
                if (ten_nv.isEmpty() || username_nv.isEmpty() || passwd_nv.isEmpty() || email_nv.isEmpty()) {
                    Toast.makeText(context, "Không được để trống các trường bắt buộc!", Toast.LENGTH_SHORT).show();
                } else {
                    member.setFullname(ten_nv);
                    member.setUsername(username_nv);
                    member.setPasswd(passwd_nv);
                    member.setEmail(email_nv);
                    member.setAvatar(ava_nv);
                    if (dao.update_mem(member) != -1) {
                        list.clear();
                        list = dao.getAll_mem();
                        notifyDataSetChanged();
                        bsdialog.dismiss();
                        Toast.makeText(context, "Update thành viên thành công!", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update thành viên thất bại!", Toast.LENGTH_SHORT).show();
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNvBinding binding;

        public ViewHolder(@NonNull ItemNvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = list_filter;
                } else {
                    ArrayList<Member_Model> listFilter = new ArrayList<>();
                    for (Member_Model mem : list_filter) {
                        if (mem.getFullname().toLowerCase().contains(search)) {
                            listFilter.add(mem);
                        }
                    }
                    list = listFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Member_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}