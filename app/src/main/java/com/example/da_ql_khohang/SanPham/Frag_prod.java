package com.example.da_ql_khohang.SanPham;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.BottomsheetProdBinding;
import com.example.da_ql_khohang.databinding.DialogAddProdBinding;
import com.example.da_ql_khohang.databinding.FragProdBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class Frag_prod extends Fragment {
    static FragProdBinding binding;

    static List<Product_model> prodList;
     prod_Adapter adapter;

     DialogAddProdBinding dialogBinding;

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

        prodList = new ArrayList<>();

        Product_model prods = new Product_model(
                1,"iphone 15",25000,1200,7462,"18/12/2023","20/12/2023","https://hoanghamobile.com/Uploads/2023/09/13/iphone-15-pink-pure-back-iphone-15-pink-pure-front-2up-screen-usen.png");
        Product_model prod2 = new Product_model(
                2,"iphone 15",25000,1200,7462,"18/12/2023","20/12/2023","https://hoanghamobile.com/Uploads/2023/09/13/iphone-15-pink-pure-back-iphone-15-pink-pure-front-2up-screen-usen.png");

        prodList.add(prods);
        prodList.add(prod2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(layoutManager);
        adapter = new prod_Adapter(getActivity(),prodList);
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_prod, null);

        dialogBinding = DialogAddProdBinding.inflate(inflater, view, false);
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