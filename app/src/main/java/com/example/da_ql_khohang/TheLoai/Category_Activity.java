package com.example.da_ql_khohang.TheLoai;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.da_ql_khohang.MainActivity;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.ActivityCategoryBinding;
import com.example.da_ql_khohang.databinding.ActivityMemberBinding;
import com.example.da_ql_khohang.databinding.DialogAddCategoryBinding;

import java.util.List;

public class Category_Activity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private List<category_model> cateList;
    private category_Adapter adapter;
    private DialogAddCategoryBinding dialogBinding;
    private category_DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setStatusBarColor(Color.parseColor("#567DF4"));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());


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
        dao = new category_DAO(this);
        cateList = dao.getDataCate();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcv.setLayoutManager(linearLayoutManager);
        adapter = new category_Adapter(this,cateList);
        adapter.notifyDataSetChanged();
        binding.rcv.setAdapter(adapter);
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) this).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_category, null);

        dialogBinding = DialogAddCategoryBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogBinding.edNameCate.getText().toString().equals("")) {
                    Toast.makeText(Category_Activity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insertCate(dialogBinding.edNameCate.getText().toString());
                    Toast.makeText(Category_Activity.this, "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
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