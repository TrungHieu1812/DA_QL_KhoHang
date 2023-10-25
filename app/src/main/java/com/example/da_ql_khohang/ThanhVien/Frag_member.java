package com.example.da_ql_khohang.ThanhVien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.da_ql_khohang.databinding.FragMemberBinding;
import com.example.da_ql_khohang.databinding.FragProdBinding;


public class Frag_member extends Fragment {
    static FragMemberBinding binding;


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




}