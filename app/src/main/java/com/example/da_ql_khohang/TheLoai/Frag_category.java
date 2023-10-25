package com.example.da_ql_khohang.TheLoai;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    static FragCategoryBinding binding;


    static List<category_model> cateList;
    category_Adapter adapter;

    DialogAddCategoryBinding dialogBinding;

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

        cateList = new ArrayList<>();

        category_model cate1 = new category_model(1,"Apple");
        category_model cate2 = new category_model(2,"SamSung");

        cateList.add(cate1);
        cateList.add(cate2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(layoutManager);
        adapter = new category_Adapter(getActivity(),cateList);
        adapter.notifyDataSetChanged();
        binding.rcv.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


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