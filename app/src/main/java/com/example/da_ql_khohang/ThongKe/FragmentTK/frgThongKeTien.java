package com.example.da_ql_khohang.ThongKe.FragmentTK;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.ThongKe.AdapterTK.ThongKeAdapter;
import com.example.da_ql_khohang.ThongKe.AdapterTK.adapterTKLT;
import com.example.da_ql_khohang.ThongKe.DAO.ThongKeDao;
import com.example.da_ql_khohang.ThongKe.ThongKe_model;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.ArrayList;

public class frgThongKeTien extends Fragment {


    public frgThongKeTien() {
        // Required empty public constructor
    }
    TextView tvTonghop,tvDoanhThuNhap,tvDoanhThuXuat;
    Button btnSoLieu,btnBieuDo;
    Spinner spCalendar;
    private LinearLayout llThongKe,llSoLieu;
    private PieChart pieChartNhap,pieChartXuat;

    private TabLayout  tabLayout;
    private ViewPager2 viewPager2;
    ThongKeDao tkDAO;
    adapterTKLT adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thong_ke_tien, container, false);

        getView(view);
        llSoLieu.setVisibility(View.VISIBLE);
        tkDAO = new ThongKeDao(getContext());

        btnSoLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSoLieu.setVisibility(View.VISIBLE);
                llThongKe.setVisibility(View.GONE);
            }
        });
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedDoanhThu = numberFormat.format(tkDAO.getDoanhThu(1)-tkDAO.getDoanhThu(0));
        tvTonghop.setText("Doanh thu : "+formattedDoanhThu+" VND");
        btnBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llThongKe.setVisibility(View.VISIBLE);
                llSoLieu.setVisibility(View.GONE);
                getBieuDo(pieChartNhap,0);
                getBieuDo(pieChartXuat,1);

                String formattedDoanhThuNhap = numberFormat.format(tkDAO.getDoanhThu(0));
                String formattedDoanhThuXuat = numberFormat.format(tkDAO.getDoanhThu(1));
                tvDoanhThuNhap.setText("Tổng nhập : "+formattedDoanhThuNhap+" VNĐ");
                tvDoanhThuXuat.setText("Tổng xuất : "+formattedDoanhThuXuat+" VNĐ");
            }
        });

        adapter = new adapterTKLT(getActivity());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Nhập Kho");
                }else if (position == 1){
                    tab.setText("Xuất Kho");
                }
            }
        }).attach();
        return view;
    }

    public void getView(View view){
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewpage2);
        llSoLieu = view.findViewById(R.id.llSoLieu);
        llThongKe = view.findViewById(R.id.llThongKe);
        tvTonghop = view.findViewById(R.id.tvTonghop);
        btnSoLieu = view.findViewById(R.id.btnSoLieu);
        btnBieuDo = view.findViewById(R.id.btnBieuDo);
        pieChartNhap = view.findViewById(R.id.chartNhap);
        pieChartXuat = view.findViewById(R.id.chartXuat);
        tvDoanhThuNhap = view.findViewById(R.id.tvDoanhThuNhap);
        tvDoanhThuXuat = view.findViewById(R.id.tvDoanhThuXuat);
        spCalendar = view.findViewById(R.id.spCalendar);
    }

    public void getBieuDo(PieChart pieChart, int type){
        ThongKeDao tkDAO = new ThongKeDao(getContext());
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<ThongKe_model> list = new ArrayList<>(tkDAO.getTKTien(type));
        for (ThongKe_model tk : list){
            entries.add(new PieEntry((float) tk.getTongTien(),tk.getTensp()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries,"Biểu đồ tiền nhập/xuất");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(pieDataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

}