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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.TheLoai.category_DAO;
import com.example.da_ql_khohang.TheLoai.category_model;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogAddProdBinding;
import com.example.da_ql_khohang.databinding.DialogUpdateProdBinding;
import com.example.da_ql_khohang.databinding.ItemProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class prod_Adapter extends RecyclerView.Adapter<prod_Adapter.ViewHolder> implements Filterable {


    private Context context;
    private List<Product_model> list;
    private List<Product_model> list_filter;

    private Product_DAO dao;

    private ArrayList<HashMap<String, Object>> listHM;

    public prod_Adapter(Context context, List<Product_model> list, ArrayList<HashMap<String, Object>> listHM, Product_DAO dao) {
        this.context = context;
        this.list = list;
        this.list_filter = list;
        this.listHM = listHM;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProdBinding binding = ItemProdBinding.inflate(LayoutInflater.from(parent.getContext()));
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int i) {
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
        binding = BottomsheetProdBinding.inflate(inflater, view, false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.tvTenSP.setText(list.get(index).getTenSP());
        binding.tvMaSP.setText(String.valueOf(list.get(index).getId()));
        binding.tvQty.setText(String.valueOf(list.get(index).getSoLuong()));
        binding.tvGiaSP.setText(String.valueOf(list.get(index).getGiaBan()));
        binding.tvSoLuukho.setText(String.valueOf(list.get(index).getSoLuuKho()));

        category_DAO catDao = new category_DAO(context);
        category_model cat = catDao.getCatById(list.get(index).getMaLoai());
        if (cat != null) {
            binding.tvLoaiSP.setText(String.valueOf(cat.getTenLoai()));
        }

        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDelete(index, bottomSheetDialog);
            }
        });

        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(index,list.get(index), bottomSheetDialog);
            }
        });
    }

    private void showDelete(int position, BottomSheetDialog BSDialog) {
        dao = new Product_DAO(context);
        int check = dao.deleteProd(list.get(position).getId());
        switch (check) {
            case 1: //load lại danh sách
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setNegativeButton("Có", (dialog, which) -> {
                    list.clear();
                    list = dao.getAllProduct();
                    notifyDataSetChanged();
                    BSDialog.dismiss();
                });
                builder.setPositiveButton("Không", null);
                builder.show();
                break;
            case -1:
                Toast.makeText(context, "Không thể xóa SP này vì đã có Hóa đơn thuộc thể loại này", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showDialog(int indexx, Product_model prod, BottomSheetDialog BSDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_prod, null);

        DialogUpdateProdBinding dialogBinding = DialogUpdateProdBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogBinding.edNameProd.setText(prod.getTenSP());
        dialogBinding.edPriceProd.setText(prod.getGiaBan() + "");
        dialogBinding.edQtyProd.setText(prod.getSoLuong() + "");
        dialogBinding.edSolkhoProd.setText(prod.getSoLuuKho() + "");
        dialogBinding.edImgProd.setText(prod.getImg());
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1});
        dialogBinding.spn.setAdapter(simpleAdapter);
        int index = 0;
        int position = -1;
        for (HashMap<String, Object> item : listHM) {
            if ((int) item.get("maLoai") == prod.getMaLoai()) {
                position = index;
            }
            index++;
        }
        dialogBinding.spn.setSelection(position);

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(indexx).getId();
                String tensp = dialogBinding.edNameProd.getText().toString();
                String giasp = dialogBinding.edPriceProd.getText().toString();
                String soluong = dialogBinding.edQtyProd.getText().toString();
                String soLuuKho = dialogBinding.edSolkhoProd.getText().toString();
                String anhSP = dialogBinding.edImgProd.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) dialogBinding.spn.getSelectedItem();
                int maloai = (int) hs.get("maLoai");


                if (tensp.isEmpty() || giasp.isEmpty() || soluong.isEmpty() || soLuuKho.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Product_model prod = new Product_model(id,tensp, Integer.parseInt(giasp), Integer.parseInt(soluong), Integer.parseInt(soLuuKho), anhSP, maloai);

                    boolean check = dao.updateProd(prod);
                    if (check) {
                        list = dao.getAllProduct();
                        Toast.makeText(context, "Update SP thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                        BSDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Update SP Thất bại", Toast.LENGTH_SHORT).show();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {
                    list = list_filter;
                } else {
                    ArrayList<Product_model> listFilter = new ArrayList<>();
                    for (Product_model prod : list_filter) {
                        if (prod.getTenSP().toLowerCase().contains(search)) {
                            listFilter.add(prod);
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
                list = (ArrayList<Product_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProdBinding binding;

        public ViewHolder(@NonNull ItemProdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
