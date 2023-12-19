package com.example.da_ql_khohang.SanPham;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.MainActivity;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.TheLoai.category_DAO;
import com.example.da_ql_khohang.TheLoai.category_model;
import com.example.da_ql_khohang.databinding.DialogAddProdBinding;
import com.example.da_ql_khohang.databinding.FragProdBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Frag_prod extends Fragment {
    private FragProdBinding binding;

    private List<Product_model> prodList;
    private prod_Adapter adapter;

    private DialogAddProdBinding dialogBinding;

    private Product_DAO dao;


    public Frag_prod() {
    }


    public static Fragment newInstance() {
        Frag_prod fragment = new Frag_prod();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragProdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        loadData();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }


    private void loadData() {
        dao = new Product_DAO(getContext());
        prodList = dao.getAllProduct();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(linearLayoutManager);
        adapter = new prod_Adapter(getActivity(),prodList,getLoaiSP(),dao);
        adapter.notifyDataSetChanged();
        binding.rcv.setAdapter(adapter);
    }



    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_prod, null);

        dialogBinding = DialogAddProdBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getLoaiSP(),
                android.R.layout.simple_spinner_item,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1});

        dialogBinding.spn.setAdapter(simpleAdapter);

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = dialogBinding.edNameProd.getText().toString();
                String giasp = dialogBinding.edPriceProd.getText().toString();
                String soluong = dialogBinding.edQtyProd.getText().toString();
                String soLuuKho = dialogBinding.edSolkhoProd.getText().toString();
                String anhSP = dialogBinding.edImgProd.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) dialogBinding.spn.getSelectedItem();
                int maloai = (int) hs.get("maLoai");



                if (tensp.isEmpty() || giasp.isEmpty() || soluong.isEmpty() || soLuuKho.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dao.insertProd(
                            tensp,Integer.parseInt(giasp),Integer.parseInt(soluong),Integer.parseInt(soLuuKho),anhSP, maloai
                    );
                    if (check) {
                        Toast.makeText(getContext(), "Thêm SP thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), "Thêm SP Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
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



    public ArrayList<HashMap<String, Object>> getLoaiSP() {
        category_DAO cate_DAO = new category_DAO(getContext());
        ArrayList<category_model> list = cate_DAO.getDataCate();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (category_model loai : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", loai.getId());
            hs.put("tenLoai", loai.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }



}