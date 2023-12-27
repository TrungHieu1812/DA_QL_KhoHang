package com.example.da_ql_khohang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.da_ql_khohang.HoaDon.Frag_bill;
import com.example.da_ql_khohang.SanPham.Frag_prod;
import com.example.da_ql_khohang.ThanhVien.Frag_profile;
import com.example.da_ql_khohang.ThongKe.Frag_Statistical;
import com.example.da_ql_khohang.databinding.ActivityMainBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;

    static FragmentManager manager;

    public static SharedPreferences shared;

    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        shared = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        role = MainActivity.shared.getInt("role",1);

        manager = getSupportFragmentManager();





        //set cái nào mở đầu tiên
        binding.bottomNav.show(1, true);

        binding.bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_homee));
        binding.bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.icon_theloai));
        binding.bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.icon_bill));
        binding.bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.icon_thongke));
        binding.bottomNav.add(new MeowBottomNavigation.Model(5, R.drawable.icon_member));

        getWindow().setStatusBarColor(Color.parseColor("#567DF4"));

        binding.bottomNav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                return null;
            }
        });


        binding.bottomNav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        loadFrag(new HomeFragment());
                        break;
                    case 2:
                        loadFrag(new Frag_prod());
                        break;
                    case 3:
                        loadFrag(new Frag_bill());
                        break;
                    case 4:
                        loadFrag(new Frag_Statistical());
                        break;
                    case 5:
                        loadFrag(new Frag_profile());
                        break;
                }

                return null;
            }
        });


    }


    public static void loadFrag(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        binding.container.setVisibility(View.VISIBLE);
//        binding.layoutDashboard1.setVisibility(View.GONE);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}