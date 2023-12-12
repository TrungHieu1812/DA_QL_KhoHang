package com.example.da_ql_khohang.ThongKe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.da_ql_khohang.MainActivity;
import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.ThongKe.AdapterTK.adapterThongKe;
import com.example.da_ql_khohang.databinding.FragStatisticalBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Frag_Statistical extends Fragment {
    FragStatisticalBinding binding;


    public Frag_Statistical() {
    }


    public static Fragment newInstance() {
        Frag_Statistical fragment = new Frag_Statistical();
        return fragment;
    }

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    adapterThongKe adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragStatisticalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewpage2);
        tabLayout = view.findViewById(R.id.tabLayout);
        adapter = new adapterThongKe(getActivity());
        viewPager2.setAdapter(adapter);

        //
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Thống kê số lượng");
                } else if (position == 1) {
                    tab.setText("Thống kê lượng tiền");
                }
            }
        }).attach();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragStatisticalBinding.inflate(inflater, container, false);
//
//
//        return view;
//    }





}