package com.example.da_ql_khohang.TheLoai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.da_ql_khohang.databinding.BottomsheetCategoryBinding;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateCategoryBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateProdBinding;
import com.example.da_ql_khohang.databinding.ItemCategoryBinding;
import com.example.da_ql_khohang.databinding.ItemProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class category_Adapter extends RecyclerView.Adapter<category_Adapter.ViewHolder> implements Filterable {


    private Context context;
    private List<category_model> list;
    private List<category_model> list_filter;

    private category_DAO dao;
    public category_Adapter(Context context, List<category_model> list) {
        this.context = context;
        this.list = list;
        this.list_filter = list;
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = list_filter;
                } else {
                    ArrayList<category_model> listFilter = new ArrayList<>();
                    for (category_model cate : list_filter) {
                        if (cate.getTenLoai().toLowerCase().contains(search)) {
                            listFilter.add(cate);
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
                list = (ArrayList<category_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
                showDelete(index,bottomSheetDialog);
            }
        });

        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(index,bottomSheetDialog);
            }
        });
    }

    private void showDelete(int position,BottomSheetDialog BSDialog) {
        dao = new category_DAO(context);
        int check = dao.deleteCate(list.get(position).getId());
        switch (check) {
            case 1: //load lại danh sách
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setNegativeButton("Có", (dialog, which) -> {
                    list.clear();
                    list = dao.getDataCate();
                    notifyDataSetChanged();
                    BSDialog.dismiss();
                });
                builder.setPositiveButton("Không", null);
                builder.show();
                break;
            case -1: // có sách tồn tại
                Toast.makeText(context, "Không thể xóa loại SP này vì đã có SP thuộc thể loại này", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void showDialogUpdate(int position,BottomSheetDialog BSDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_category, null);

        DialogUpdateCategoryBinding dialogBinding = DialogUpdateCategoryBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogBinding.edNameCate.setText(list.get(position).getTenLoai());

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoai = dialogBinding.edNameCate.getText().toString();
                int id = list.get(position).getId();
                category_model cate = new category_model(id, tenLoai);
                dao = new category_DAO(context);
                if (tenLoai.isEmpty()) { //tenLoai.matches("[a-zA-Z ]+")
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.updateCate(cate)) {
                        list = dao.getDataCate();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update loại SP Thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        BSDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update loại SP Thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
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
