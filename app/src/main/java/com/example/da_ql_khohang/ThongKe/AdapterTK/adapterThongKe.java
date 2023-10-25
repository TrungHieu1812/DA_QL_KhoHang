package com.example.da_ql_khohang.ThongKe.AdapterTK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeSoLuong;
import com.example.da_ql_khohang.ThongKe.FragmentTK.frgThongKeTien;

public class adapterThongKe extends FragmentStateAdapter {

    public adapterThongKe(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new frgThongKeSoLuong();
        } else if (position == 1) {
            return new frgThongKeTien();
        }
        return new frgThongKeSoLuong();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
