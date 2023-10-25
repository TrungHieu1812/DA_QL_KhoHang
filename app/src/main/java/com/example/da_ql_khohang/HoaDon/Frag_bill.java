package com.example.da_ql_khohang.HoaDon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.da_ql_khohang.databinding.FragBillBinding;
import com.example.da_ql_khohang.databinding.FragProdBinding;


public class Frag_bill extends Fragment {
    static FragBillBinding binding;


    public Frag_bill() {
    }


    public static Fragment newInstance() {
        Frag_bill fragment = new Frag_bill();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }




}