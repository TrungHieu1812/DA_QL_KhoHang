package com.example.da_ql_khohang;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.da_ql_khohang.HoaDon.Frag_bill;
import com.example.da_ql_khohang.SanPham.Frag_prod;
import com.example.da_ql_khohang.ThanhVien.Activity_member;
import com.example.da_ql_khohang.TheLoai.Category_Activity;
import com.example.da_ql_khohang.ThongKe.Frag_Statistical;
import com.example.da_ql_khohang.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    static FragmentManager manager;

    public static SharedPreferences shared;

    int role;

    public HomeFragment() {
    }

    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shared = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        Glide.with(this).load(shared.getString("avatar", "")).into(binding.imgAvt);
        role = MainActivity.shared.getInt("role",1);

        manager = getActivity().getSupportFragmentManager();

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        binding.imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        onClickDashBoard();
        //set cái nào mở đầu tiên


    }

    private void onClickDashBoard() {
        binding.cardProduct.setOnClickListener(view -> {
            loadFrag(new Frag_prod());
            MainActivity.binding.bottomNav.show(2, true);
        });
        binding.cardCaegory.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), Category_Activity.class));

        });
        binding.cardBill.setOnClickListener(view -> {
            loadFrag(new Frag_bill());
            MainActivity.binding.bottomNav.show(3, true);
        });
        binding.cardMember.setOnClickListener(view -> {
            if (role == 1){
                Toast.makeText(getContext(), "Bạn ko được phép truy cập", Toast.LENGTH_SHORT).show();
            } else if (role == 0){
                startActivity(new Intent(getActivity(), Activity_member.class));
            }
        });
        binding.cardStatitics.setOnClickListener(view -> {
            loadFrag(new Frag_Statistical());
            MainActivity.binding.bottomNav.show(4, true);
        });
    }

    public void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        binding.container.setVisibility(View.VISIBLE);
        binding.layoutDashboard1.setVisibility(View.GONE);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}