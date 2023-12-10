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
import android.widget.TextView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.SanPham.Product_DAO;
import com.example.da_ql_khohang.SanPham.Product_model;
import com.example.da_ql_khohang.ThongKe.AdapterTK.adapterTKSL;
import com.example.da_ql_khohang.ThongKe.DAO.ThongKeDao;
import com.example.da_ql_khohang.ThongKe.ThongKe_model;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class frgThongKeSoLuong extends Fragment {

    public frgThongKeSoLuong() {
        // Required empty public constructor
    }

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    TextView tvTonghop;
    Button btnSoLieu,btnBieuDo;
    private LinearLayout llThongKe,llSoLieu;
    private RadarChart radarChart;

    ThongKeDao tkDao;
    ArrayList<ThongKe_model> listNhap = new ArrayList<>();
    ArrayList<ThongKe_model> listXuat = new ArrayList<>();
    adapterTKSL adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thong_ke_so_luong, container, false);
        getView(view);

        llSoLieu.setVisibility(View.VISIBLE);
        tkDao = new ThongKeDao(getContext());
        listNhap = (ArrayList<ThongKe_model>) tkDao.getTKSL(0);
        listXuat = (ArrayList<ThongKe_model>) tkDao.getTKSL(1);
        int tongNhap = 0;
        int tongXuat = 0;
        for (ThongKe_model tk : listNhap){
            tongNhap += tk.getSoLuong();
        }
        for (ThongKe_model tk : listXuat){
            tongXuat += tk.getSoLuong();
        }
        int tonKho = tongNhap - tongXuat;
        tvTonghop.setText("Kho còn : "+tonKho+ " sản phẩm");

        btnSoLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSoLieu.setVisibility(View.VISIBLE);
                llThongKe.setVisibility(View.GONE);
            }
        });

        btnBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llThongKe.setVisibility(View.VISIBLE);
                llSoLieu.setVisibility(View.GONE);
                getBieuDo();
            }
        });
        adapter = new adapterTKSL(getActivity());
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

    private void getView(View view){
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewpage2);
        llSoLieu = view.findViewById(R.id.llSoLieu);
        llThongKe = view.findViewById(R.id.llThongKe);
        tvTonghop = view.findViewById(R.id.tvTonghop);
        btnSoLieu = view.findViewById(R.id.btnSoLieu);
        btnBieuDo = view.findViewById(R.id.btnBieuDo);
        radarChart = view.findViewById(R.id.chart);
    }

    public void getBieuDo(){
        ArrayList<RadarEntry> listChartNhap = new ArrayList<>();
        ArrayList<RadarEntry> listChartXuat= new ArrayList<>();
        ThongKeDao tkDAO = new ThongKeDao(getContext());
        ArrayList<ThongKe_model> listNhap = (ArrayList<ThongKe_model>) tkDAO.getTKSL(0);
        ArrayList<ThongKe_model> listXuat = (ArrayList<ThongKe_model>) tkDAO.getTKSL(1);

        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < listNhap.size(); i++) {
            ThongKe_model tk = listNhap.get(i);
            listChartNhap.add(new RadarEntry(tk.getSoLuong()));
            labels.add(tk.getTensp()); // Thêm giá trị vào danh sách labels

            // Kiểm tra xem sản phẩm có tồn tại trong listXuat hay không
            boolean existsInListXuat = false;
            for (ThongKe_model xuat : listXuat) {
                if (xuat.getTensp().equals(tk.getTensp())) {
                    existsInListXuat = true;
                    break;
                }
            }

            // Nếu sản phẩm không tồn tại trong listXuat, thêm giá trị mặc định hoặc giá trị tương ứng
            if (!existsInListXuat) {
                listChartXuat.add(new RadarEntry(0)); // Thêm giá trị mặc định vào listChartXuat
                listXuat.add(new ThongKe_model(tk.getTensp(), 0)); // Thêm sản phẩm mới vào listXuat
            }
        }
        for (int i = 0; i < listXuat.size(); i++) {
            ThongKe_model tk = listXuat.get(i);
            listChartXuat.add(new RadarEntry(tk.getSoLuong()));
        }

        RadarDataSet dataSetNhap = new RadarDataSet(listChartNhap,"Nhập Kho");
        dataSetNhap.setColor(Color.RED);
        dataSetNhap.setLineWidth(2f);
        dataSetNhap.setValueTextColor(Color.RED);
        dataSetNhap.setValueTextSize(14f);

        RadarDataSet dataSetXuat = new RadarDataSet(listChartXuat,"Xuất Kho");
        dataSetXuat.setColor(Color.GREEN);
        dataSetXuat.setLineWidth(2f);
        dataSetXuat.setValueTextColor(Color.GREEN);
        dataSetXuat.setValueTextSize(14f);

        RadarData data = new RadarData();
        data.addDataSet(dataSetNhap);
        data.addDataSet(dataSetXuat);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.setData(data);
        radarChart.getDescription().setText("Biểu đồ số lượng nhập/xuất");

    }
}