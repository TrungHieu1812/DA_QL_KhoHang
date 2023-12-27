package com.example.da_ql_khohang.ThongKe.FragmentTK;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class frgThongKeTien extends Fragment {


    public frgThongKeTien() {
        // Required empty public constructor
    }
    // Lấy ngày hiện tại
    Calendar calendar = Calendar.getInstance();
    private int initialYear = calendar.get(Calendar.YEAR);
    private int initialMonth = calendar.get(Calendar.MONTH);
    private int initialDay = calendar.get(Calendar.DAY_OF_MONTH);
    TextView tvTonghop,tvDoanhThuNhap,tvDoanhThuXuat,tvTime;
    Button btnSoLieu,btnBieuDo;
    Spinner spCalendar;
    private LinearLayout llThongKe,llSoLieu;
    private PieChart pieChartNhap,pieChartXuat;

    private TabLayout  tabLayout;
    private ViewPager2 viewPager2;
    ThongKeDao tkDAO;
    ArrayList<ThongKe_model> listNhap = new ArrayList<>();
    ArrayList<ThongKe_model> listXuat = new ArrayList<>();
    ThongKeAdapter adapter;
    ImageView imgCalendar;
    RecyclerView rcvDSNhap;

    Button btnNhap,btnXuat;
    NumberFormat numberFormat = NumberFormat.getInstance();
    frgThongKeTienNhap frg = new frgThongKeTienNhap();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thong_ke_tien, container, false);

        getView(view);
        llSoLieu.setVisibility(View.VISIBLE);
        tkDAO = new ThongKeDao(getContext());
        listNhap = (ArrayList<ThongKe_model>) tkDAO.getTKTien(0);
        listXuat = (ArrayList<ThongKe_model>) tkDAO.getTKTien(1);
        loadDS(listNhap);

        btnSoLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSoLieu.setVisibility(View.VISIBLE);
                llThongKe.setVisibility(View.GONE);
            }
        });

        getSpinner();
        chonloai(listNhap,listXuat);
        String formattedDoanhThu = numberFormat.format(tkDAO.getDoanhThu(1)-tkDAO.getDoanhThu(0));
        tvTonghop.setText("Doanh thu : "+formattedDoanhThu+" VND");
        tvTime.setText("Tổng");
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(tvTime);
            }
        });
        btnBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llThongKe.setVisibility(View.VISIBLE);
                llSoLieu.setVisibility(View.GONE);
                getBieuDo(pieChartNhap,listNhap);
                getBieuDo(pieChartXuat,listXuat);

                String formattedDoanhThuNhap = numberFormat.format(tkDAO.getDoanhThu(0));
                String formattedDoanhThuXuat = numberFormat.format(tkDAO.getDoanhThu(1));
                tvDoanhThuNhap.setText("Tổng nhập : "+formattedDoanhThuNhap+" VNĐ");
                tvDoanhThuXuat.setText("Tổng xuất : "+formattedDoanhThuXuat+" VNĐ");
            }
        });

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
        tvTime = view.findViewById(R.id.tvTime);
        imgCalendar = view.findViewById(R.id.imgCalendar);
        rcvDSNhap = view.findViewById(R.id.rcvDSNhap);
        btnNhap = view.findViewById(R.id.btnNhap);
        btnXuat = view.findViewById(R.id.btnXuat);
    }

    public void getBieuDo(PieChart pieChart,ArrayList<ThongKe_model> list){
        if (list.size() == 0){
            pieChart.setVisibility(View.GONE);
        }else {
            pieChart.setVisibility(View.VISIBLE);
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
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

    private void showDatePickerDialog(TextView tv) {
        // Hiển thị DatePickerDialog để chọn ngày
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Xử lý ngày đã chọn
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth); // Lưu ý: Tháng trong DatePickerDialog được đếm từ 0, nên cần +1
                String currentDateStr = selectedDate.format(formatter);
                tv.setText("Ngày "+currentDateStr);
                ArrayList<ThongKe_model> listNhap = tkDAO.getTKTienTheoNgay(0,currentDateStr,currentDateStr);
                ArrayList<ThongKe_model> listXuat = tkDAO.getTKTienTheoNgay(1,currentDateStr,currentDateStr);
                // set biểu đồ
                getBieuDo(pieChartNhap,listNhap);
                getBieuDo(pieChartXuat,listXuat);
                // set tksl
                getTKDT(listNhap,listXuat);
                loadDS(listNhap);
                chonloai(listNhap,listXuat);
                tvTime.setText("Ngày "+currentDateStr);
            }
        }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();
    }
    private void getTKDT(ArrayList<ThongKe_model> listNhap,ArrayList<ThongKe_model> listXuat){
        int tongNhap = 0;
        int tongXuat = 0;
        for (ThongKe_model tk : listNhap){
            tongNhap += tk.getTongTien();
        }
        for (ThongKe_model tk : listXuat){
            tongXuat += tk.getTongTien();
        }

        int doanhThu = tongXuat - tongNhap;
        String formattedDoanhThuNhap = numberFormat.format(tongNhap);
        String formattedDoanhThuXuat = numberFormat.format(tongXuat);
        String formattedDoanhThu = numberFormat.format(doanhThu);
        tvDoanhThuNhap.setText("Tổng nhập : "+formattedDoanhThuNhap+" VNĐ");
        tvDoanhThuXuat.setText("Tổng xuất : "+formattedDoanhThuXuat+" VNĐ");
        tvTonghop.setText("Doanh thu : "+formattedDoanhThu+" VND");
    }
    public void loadDS(ArrayList<ThongKe_model> list){
        adapter = new ThongKeAdapter(getContext(),list);
        adapter.setCurrentFragment(getContext());
        rcvDSNhap.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDSNhap.setAdapter(adapter);
    }

    private void chonloai(ArrayList<ThongKe_model> listNhap,ArrayList<ThongKe_model> listXuat){
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDS(listNhap);
                setClickButton(btnNhap,btnXuat);
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDS(listXuat);
                setClickButton(btnXuat,btnNhap);
            }
        });
    }

    public void setClickButton(Button b1,Button b2){
        b1.setBackgroundResource(R.drawable.khungdn);
        b2.setBackgroundResource(R.drawable.khung);
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.DKGRAY);

    }
    private void getSpinner(){

        ArrayAdapter< String > Arrcalendar = new ArrayAdapter <>(getContext(), android.R.layout.simple_spinner_item);
        Arrcalendar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Arrcalendar.add("Tất cả");
        Arrcalendar.add("Hôm nay");
        Arrcalendar.add("Hôm qua");
        Arrcalendar.add("Tuần này");
        Arrcalendar.add("Tuần trước");
        Arrcalendar.add("Tháng này");
        Arrcalendar.add("Tháng trước");

        spCalendar.setAdapter(Arrcalendar);
        spCalendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
                String select = (String) parent.getItemAtPosition(position);
                pieChartNhap.clear();
                pieChartXuat.clear();
                if (select.equals("Tất cả")){
                    getBieuDo(pieChartNhap,listNhap);
                    getBieuDo(pieChartXuat,listXuat);
                    // set tksl
                    loadDS(listNhap);
                    chonloai(listNhap,listXuat);
                    getTKDT(listNhap,listXuat);

                } else if (select.equals("Hôm nay")) {
                    dsachTheoNgay(0);
                } else if (select.equals("Hôm qua")) {
                    dsachTheoNgay(1);
                } else if (select.equals("Tuần này")){
                    dsachTheoTuan(0);
                } else if (select.equals("Tuần trước")){
                    dsachTheoTuan(1);
                } else if (select.equals("Tháng này")){
                    dsachTheoThang(0);
                }else if (select.equals("Tháng trước")){
                    dsachTheoThang(1);
                }
                pieChartNhap.invalidate();
                pieChartXuat.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView < ? > parent) {

            }
        });
    }
    private void dsachTheoNgay(int type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime(); // Lấy ngày hôm nay
        String currentDateStr = dateFormat.format(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Thêm 1 ngày vào ngày hiện tại
        Date yesterdayDate = calendar.getTime(); // Lấy ngày hôm qua
        String yesterdayDateStr = dateFormat.format(yesterdayDate); // Định dạng ngày mới thành chuỗi

        ArrayList<ThongKe_model> listNhap = new ArrayList<>();
        ArrayList<ThongKe_model> listXuat = new ArrayList<>();

        if (type == 1){
            listNhap = tkDAO.getTKTienTheoNgay(0,yesterdayDateStr,yesterdayDateStr);
            listXuat = tkDAO.getTKTienTheoNgay(1,yesterdayDateStr,yesterdayDateStr);
            tvTime.setText("Ngày "+yesterdayDateStr);
        } else {
            listNhap = tkDAO.getTKTienTheoNgay(0,currentDateStr,currentDateStr);
            listXuat = tkDAO.getTKTienTheoNgay(1,currentDateStr,currentDateStr);
            tvTime.setText("Ngày "+currentDateStr);
        }
        // set biểu đồ
        getBieuDo(pieChartNhap,listNhap);
        getBieuDo(pieChartXuat,listXuat);
        // set tksl
        getTKDT(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+currentDateStr);
    }
    private void dsachTheoTuan(int type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        if (type == 1){
            // lấy tuần trước
            calendar.add(Calendar.WEEK_OF_YEAR,-1);
        }

        // Thiết lập ngày trong tuần là Thứ Hai (Calendar.MONDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = calendar.getTime();
        String thuHai = dateFormat.format(monday);

        // Thiết lập ngày trong tuần là Chủ Nhật (Calendar.SUNDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date sunday = calendar.getTime();
        String chuNhat = dateFormat.format(sunday);

        ArrayList<ThongKe_model> listNhap = tkDAO.getTKTienTheoNgay(0,thuHai,chuNhat);
        ArrayList<ThongKe_model> listXuat = tkDAO.getTKTienTheoNgay(1,thuHai,chuNhat);
        // set biểu đồ
        getBieuDo(pieChartNhap,listNhap);
        getBieuDo(pieChartXuat,listXuat);
        // set tksl
        getTKDT(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+thuHai+" - "+chuNhat);
    }
    private void dsachTheoThang(int type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        if (type == 1){
            // Lấy tháng trước
            calendar.add(Calendar.MONTH, -1);
        }

        // Lấy ngày đầu tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dauThang = calendar.getTime();
        String ngayDau = dateFormat.format(dauThang);

        // Lấy ngày cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date cuoiThang = calendar.getTime();
        String ngayCuoi = dateFormat.format(cuoiThang);

        ArrayList<ThongKe_model> listNhap = tkDAO.getTKTienTheoNgay(0,ngayDau,ngayCuoi);
        ArrayList<ThongKe_model> listXuat = tkDAO.getTKTienTheoNgay(1,ngayDau,ngayCuoi);
        // set biểu đồ
        getBieuDo(pieChartNhap,listNhap);
        getBieuDo(pieChartXuat,listXuat);

        // set tksl
        getTKDT(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+ngayDau+" - "+ngayCuoi);
    }

}