package com.example.da_ql_khohang.ThongKe.AdapterTK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeTienNhap;
import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeTienXuat;

public class adapterTKLT extends FragmentStateAdapter {
    public adapterTKLT(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new frgThongKeTienXuat();
        } else if (position == 0) {
            return new frgThongKeTienNhap();
        }
        return new frgThongKeTienNhap();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
