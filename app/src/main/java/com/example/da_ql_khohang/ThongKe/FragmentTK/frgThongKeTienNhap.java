package com.example.da_ql_khohang.ThongKe.FragmentTK;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.ThongKe.AdapterTK.ThongKeAdapter;
import com.example.da_ql_khohang.ThongKe.DAO.ThongKeDao;
import com.example.da_ql_khohang.ThongKe.ThongKe_model;

import java.text.NumberFormat;
import java.util.ArrayList;

public class frgThongKeTienNhap extends Fragment {
    RecyclerView rcv_SoLuong;
    ThongKeDao tkDAO;
    ArrayList<ThongKe_model> list;
    ThongKeAdapter adapter;

    public frgThongKeTienNhap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_frg_thong_ke_so_luong_nhap, container, false);
        rcv_SoLuong = view.findViewById(R.id.rcv_SoLuong);
        tkDAO = new ThongKeDao(getContext());
        list = (ArrayList<ThongKe_model>) tkDAO.getTKTien(0);
        rcv_SoLuong.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThongKeAdapter(getContext(),list);
        adapter.setCurrentFragment(getContext());
        rcv_SoLuong.setAdapter(adapter);
        return view;
    }
    public void loadDS(Context context,ArrayList<ThongKe_model> list1,RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ThongKeAdapter adapter1 = new ThongKeAdapter(context,list1);
        recyclerView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }
}