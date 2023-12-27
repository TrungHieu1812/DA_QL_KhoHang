package com.example.da_ql_khohang.ThanhVien;

import static android.content.Context.MODE_PRIVATE;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.MainActivity;
import com.example.da_ql_khohang.Sign_in;
import com.example.da_ql_khohang.databinding.FragProfileBinding;


public class Frag_profile extends Fragment {

    FragProfileBinding binding;

    private SharedPreferences sharedPreferences;

    public Frag_profile() {
    }


    public static Fragment newInstance() {
        Frag_profile fragment = new Frag_profile();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        Glide.with(this).load(sharedPreferences.getString("avatar", "")).into(binding.imgAvt);
        binding.tvFullname.setText(sharedPreferences.getString("fullName", ""));
        binding.tvUsername.setText(sharedPreferences.getString("userName", ""));
        binding.tvEmail2.setText(sharedPreferences.getString("email", ""));

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.shared.getInt("role", 0) == 0) {
                    startActivity(new Intent(getActivity(), Activity_member.class));
                } else {
                    Toast.makeText(getActivity(), "Bạn không có quyền truy cập chức năng này!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), Sign_in.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", null);
                builder.show();
            }
        });

    }


}