package com.example.da_ql_khohang.TheLoai;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.SanPham.prod_Adapter;
import com.example.da_ql_khohang.databinding.DialogAddCategoryBinding;
import com.example.da_ql_khohang.databinding.DialogAddProdBinding;
import com.example.da_ql_khohang.databinding.FragCategoryBinding;
import com.example.da_ql_khohang.databinding.FragMemberBinding;

import java.util.ArrayList;
import java.util.List;


public class Frag_category extends Fragment {
    private FragCategoryBinding binding;
    private List<category_model> cateList;
    private category_Adapter adapter;
    private DialogAddCategoryBinding dialogBinding;
    private category_DAO dao;

    public Frag_category() {
    }


    public static Fragment newInstance() {
        Frag_category fragment = new Frag_category();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        dao = new category_DAO(getContext());
        cateList = dao.getDataCate();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(linearLayoutManager);
        adapter = new category_Adapter(getActivity(),cateList);
        adapter.notifyDataSetChanged();
        binding.rcv.setAdapter(adapter);
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_category, null);

        dialogBinding = DialogAddCategoryBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogBinding.edNameCate.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insertCate(dialogBinding.edNameCate.getText().toString());
                    Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                    loadData();
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


}