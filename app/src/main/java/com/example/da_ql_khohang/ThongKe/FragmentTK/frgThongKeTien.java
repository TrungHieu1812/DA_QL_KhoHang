package com.example.da_ql_khohang.ThongKe.FragmentTK;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.ThongKe.AdapterTK.adapterTKLT;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class frgThongKeTien extends Fragment {


    public frgThongKeTien() {
        // Required empty public constructor
    }

    private TabLayout  tabLayout;
    private ViewPager2 viewPager2;
    adapterTKLT adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thong_ke_tien, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewpage2);

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
}