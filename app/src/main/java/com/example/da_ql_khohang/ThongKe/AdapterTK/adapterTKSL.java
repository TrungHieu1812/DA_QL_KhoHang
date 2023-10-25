package com.example.da_ql_khohang.ThongKe.AdapterTK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeSoLuongNhap;
import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeSoLuongXuat;

public class adapterTKSL extends FragmentStateAdapter {
    public adapterTKSL(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new frgThongKeSoLuongXuat();
        } else if (position == 0) {
            return new frgThongKeSoLuongNhap();
        }
        return new frgThongKeSoLuongNhap();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
