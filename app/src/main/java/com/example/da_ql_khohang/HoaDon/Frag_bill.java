package com.example.da_ql_khohang.HoaDon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.HoaDon.model.bill;
import com.example.da_ql_khohang.R;


import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.databinding.DialogThemHoaDonBinding;
import com.example.da_ql_khohang.databinding.FragBillBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Frag_bill extends Fragment {
    private FragBillBinding binding;
    private DialogThemHoaDonBinding hoaDonBinding;

    private ArrayList < bill > listbill;
    private bill_Adapter adapter;
    private Product_model productModel;

    private RecyclerView rcv;
    private FloatingActionButton fab1;
    private bill_dao dao;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listbill = new ArrayList <>();
        binding.fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill hd = new bill();
                showDialog(hd);
            }
        });

        bill_dao billDao = new bill_dao(getActivity());
        listbill = (ArrayList < bill >) billDao.getAllPhieu();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcv.setLayoutManager(layoutManager);
        adapter = new bill_Adapter((Context) getActivity(), listbill);
        binding.rcv.setAdapter(adapter);

    }

    private void showDialog(bill hd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_them_hoa_don, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView ed_ma_nhan_vien = view.findViewById(R.id.ed_ma_nhan_vien);
        Spinner spn_loai = view.findViewById(R.id.spn_trangthai);
        Spinner spn_sp = view.findViewById(R.id.spn_sp);
        EditText ed_sl = view.findViewById(R.id.ed_sl);
        EditText khoLuuTru = view.findViewById(R.id.ed_ngay_xuat);

        Button btnxacnhan = view.findViewById(R.id.btn_xac_nhan_hoa_don);
        Button btnhuy = view.findViewById(R.id.btn_huy_hoa_don);

        ArrayAdapter < String > ArrTenSP = new ArrayAdapter <>(getContext(), android.R.layout.simple_spinner_item);
        ArrTenSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList < Product_model > listsp = new ArrayList <>();
        Product_DAO spDAO = new Product_DAO(getContext());
        listsp.addAll(spDAO.getAllProduct());

        for (Product_model sp : listsp) {
            ArrTenSP.add(sp.getTenSP());
        }

        spn_sp.setAdapter(ArrTenSP);

        spn_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
                String select = (String) parent.getItemAtPosition(position);
                for (Product_model sp : listsp) {
                    if (sp.getTenSP().equals(select)) {
                        hd.setMaSP(sp.getId());
                        // Perform any additional actions based on the selected product
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView < ? > parent) {
                // Handle the case when nothing is selected
            }
        });
        ArrayAdapter < String > loaiPhieu = new ArrayAdapter <>(getContext(), android.R.layout.simple_spinner_item);
        loaiPhieu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loaiPhieu.add("Phiếu nhập");
        loaiPhieu.add("Phiếu xuất");

        spn_loai.setAdapter(loaiPhieu);

        spn_loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
                String select = (String) parent.getItemAtPosition(position);
                if (select.equals("Phiếu nhập")) {
                    hd.setLoai(0);
                } else {
                    hd.setLoai(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView < ? > parent) {

            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mavn = ed_ma_nhan_vien.getText().toString();
                String sl = ed_sl.getText().toString();
                String kho = khoLuuTru.getText().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date currentDate = new Date();
                String formattedDate = dateFormat.format(currentDate);

                hd.setNgayluutru(formattedDate);
                hd.setMathanhvien(mavn);
                hd.setSl(Integer.parseInt(sl));
                hd.setKhoLuuTru(kho);

                dao = new bill_dao(getContext());
                dao.insert_bill(hd);
                listbill.clear();
                listbill.addAll(dao.getAllPhieu());
                adapter.notifyDataSetChanged();
                // Dismiss the dialog
                alertDialog.dismiss();

                // Show a toast message
//                Toast.makeText(getContext(), "Hóa đơn đã được thêm", Toast.LENGTH_SHORT).show();
            }
        });

        // Implement the click listener for the "Hủy" button
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });
    }


}