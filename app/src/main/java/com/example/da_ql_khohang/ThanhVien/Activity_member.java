package com.example.da_ql_khohang.ThanhVien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.ActivityMainBinding;
import com.example.da_ql_khohang.databinding.ActivityMemberBinding;
import com.example.da_ql_khohang.databinding.DialogThemNvBinding;


import java.util.List;

public class Activity_member extends AppCompatActivity {

    static ActivityMemberBinding binding;
    static List<Member_Model> nv_list;
    Member_DAO memberDao;
    Member_Adapter adapter;
    DialogThemNvBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        binding = ActivityMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#567DF4"));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        loadData();

        binding.flbtnAddNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        memberDao = new Member_DAO(this);
        nv_list = memberDao.getAll_mem();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvNv.setLayoutManager(linearLayoutManager);
        adapter = new Member_Adapter(this, nv_list,memberDao);
        adapter.notifyDataSetChanged();
        binding.rcvNv.setAdapter(adapter);
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_member.this); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_them_nv, null);

        dialogBinding = DialogThemNvBinding.inflate(inflater, view, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        dialogBinding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ten_nv = dialogBinding.etThemTenNv.getText().toString();
                String username_nv = dialogBinding.etThemUsernameNv.getText().toString();
                String passwd_nv = dialogBinding.etThemPasswdNv.getText().toString();
                String mail_nv = dialogBinding.etThemEmailNv.getText().toString();
                String avatar = dialogBinding.etThemAvatarNv.getText().toString();

                if (ten_nv.isEmpty() || username_nv.isEmpty() || passwd_nv.isEmpty() || mail_nv.isEmpty()) {
                    Toast.makeText(Activity_member.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Member_Model member = new Member_Model();
                    member.setFullname(ten_nv);
                    member.setUsername(username_nv);
                    member.setPasswd(passwd_nv);
                    member.setEmail(mail_nv);
                    member.setAvatar(avatar);
                    member.setRole(1);
                    boolean check = memberDao.insert_mem(member);
                    Toast.makeText(Activity_member.this, member.getAvatar(), Toast.LENGTH_SHORT).show();
                    if (check) {
                        Toast.makeText(Activity_member.this, "Thêm Thành viên thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(Activity_member.this, "Thêm Thành viên Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                }


            }
        });
        dialogBinding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();

            }
        });


    }

}