package com.example.da_ql_khohang.ThanhVien;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.DialogThemNvBinding;
import com.example.da_ql_khohang.databinding.FragMemberBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class Frag_member extends Fragment {
    static FragMemberBinding binding;
    static List<Member_Model> nv_list;
    Member_DAO memberDao;
    Member_Adapter adapter;
  //  DialogThemNvBinding dialogBinding;
  RecyclerView rcv_nv;
    public Frag_member() {
    }


    public static Fragment newInstance() {
        Frag_member fragment = new Frag_member();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragMemberBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_nv= view.findViewById(R.id.rcv_nv);

        loadData();

        binding.flbtnAddNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }


    private void loadData() {
        memberDao = new Member_DAO(getContext());
        nv_list = memberDao.getAll_mem();
        adapter = new Member_Adapter(getContext(), nv_list);
        rcv_nv.setAdapter(adapter);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);



        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_nv);
        TextInputEditText ten_nv = dialog.findViewById(R.id.et_them_ten_nv);
        TextInputEditText username_nv = dialog.findViewById(R.id.et_them_username_nv);
        TextInputEditText passwd_nv = dialog.findViewById(R.id.et_them_passwd_nv);
        TextInputEditText level_nv = dialog.findViewById(R.id.et_them_level_nv);
        TextInputEditText mail_nv = dialog.findViewById(R.id.et_them_email_nv);
        Button btnXacnhan = dialog.findViewById(R.id.btnXacnhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);


dialog.show();

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                memberDao = new Member_DAO(getContext());
                Member_Model member = new Member_Model();
                if(ten_nv.getText().toString().isEmpty() || username_nv.getText().toString().isEmpty() || passwd_nv.getText().toString().isEmpty()|| level_nv.getText().toString().isEmpty()||mail_nv.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    member.setTen(ten_nv.getText().toString());
                    member.setUsername(username_nv.getText().toString());
                    member.setPasswd(passwd_nv.getText().toString());
                    member.setLevel(level_nv.getText().toString());
                    member.setEmail(mail_nv.getText().toString());
                    if (memberDao.insert_mem(member) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        loadData();
                    } else {
                        Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });


    }
}