package com.example.da_ql_khohang.ThanhVien;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.databinding.DialogThemNvBinding;
import com.example.da_ql_khohang.databinding.FragMemberBinding;

import java.util.ArrayList;
import java.util.List;


public class Frag_member extends Fragment {
    static FragMemberBinding binding;
    static List<Member_Model> nv_list;

    Member_Adapter adapter;
    DialogThemNvBinding dialogBinding;

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
        nv_list = new ArrayList<>();
        Member_Model nv = new Member_Model(1, "Ngọc Hải","Haihtnph21513@gmail.com", "123456","nhân viên", 0654555365, "https://ae01.alicdn.com/kf/HTB1ksagbcnrK1RkHFrdq6xCoFXak/Funny-Cat-Costume-Uniform-Suit-Cat-Clothes-Costume-Puppy-Clothes-Dressing-Up-Suit-Party-Clothing-For.jpg_640x640.jpg");
        Member_Model nv2 = new Member_Model(2, "Ngọc Hải","Haihtnph21513@gmail.com","123456", "nhân viên", 0152565155, "https://ae01.alicdn.com/kf/HTB1ksagbcnrK1RkHFrdq6xCoFXak/Funny-Cat-Costume-Uniform-Suit-Cat-Clothes-Costume-Puppy-Clothes-Dressing-Up-Suit-Party-Clothing-For.jpg_640x640.jpg");

        nv_list.add(nv);
        nv_list.add(nv2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvNv.setLayoutManager(layoutManager);
        adapter = new Member_Adapter(getActivity(), nv_list);
        adapter.notifyDataSetChanged();
        binding.rcvNv.setAdapter(adapter);

        binding.flbtnAddNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_them_nv, null);

        dialogBinding = DialogThemNvBinding.inflate(inflater, view, false);
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