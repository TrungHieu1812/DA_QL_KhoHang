package com.example.da_ql_khohang.ThongKe.AdapterTK;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_ql_khohang.R;
import com.example.da_ql_khohang.ThongKe.DAO.ThongKeDao;
import com.example.da_ql_khohang.ThongKe.ThongKe_model;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.viewholder> {
    private Context currentFragment;
    public void setCurrentFragment(Context fragment) {
        currentFragment = fragment;
    }
    ThongKeDao tkDAO;
    private final Context context;
    private final ArrayList<ThongKe_model> list;

    public ThongKeAdapter(Context context, ArrayList<ThongKe_model> list) {
        this.context = context;
        this.list = list;
        tkDAO =new ThongKeDao(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thong_ke_so_luong,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        if (position >= 0 && position < list.size() ){
            ThongKe_model tk = list.get(position);
            holder.txt_SL.setText(tk.getSoLuong()+"");
            holder.txt_tenSp.setText(tk.getTensp()+"");
            holder.img_sp.setImageURI(tk.getHinhsp());

            if (context.equals(currentFragment)){
                holder.txt_giaSp.setVisibility(View.VISIBLE);
                holder.txt_SL.setVisibility(View.GONE);
                numberFormat(tk.getTongTien(),holder.txt_giaSp);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0){
            return  list.size();
        }
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        ImageView img_sp;
        TextView txt_tenSp,txt_giaSp,txt_SL;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_sp = itemView.findViewById(R.id.img_sp);
            txt_tenSp = itemView.findViewById(R.id.txt_tenSp);
            txt_giaSp = itemView.findViewById(R.id.txt_giaSp);
            txt_SL = itemView.findViewById(R.id.txt_SL);
        }
    }
    public void numberFormat(long doanhThu, TextView textView){
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedDoanhThu = numberFormat.format(doanhThu);
        textView.setText(formattedDoanhThu+" VNĐ");
    }
}
