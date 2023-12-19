package com.example.da_ql_khohang.ThongKe.FragmentTK;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class frgThongKeSoLuong extends Fragment {

    public frgThongKeSoLuong() {
        // Required empty public constructor
    }
    // Lấy ngày hiện tại
    Calendar calendar = Calendar.getInstance();
    private int initialYear = calendar.get(Calendar.YEAR);
    private int initialMonth = calendar.get(Calendar.MONTH);
    private int initialDay = calendar.get(Calendar.DAY_OF_MONTH);
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    TextView tvTonghop,tvTonghopXuat,tvTime;
    Button btnSoLieu,btnBieuDo;
    private LinearLayout llThongKe,llSoLieu;
    private RadarChart radarChart;

    ThongKeDao tkDao;
    ImageView imgCalendar;
    ArrayList<ThongKe_model> listNhap = new ArrayList<>();
    ArrayList<ThongKe_model> listXuat = new ArrayList<>();
    adapterTKSL adapter;
    Spinner spCalendar;
    RecyclerView rcvDSNhap;

    Button btnNhap,btnXuat;

    frgThongKeSoLuongNhap frg = new frgThongKeSoLuongNhap();
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
        // set tksl
        getTKSL(listNhap,listXuat);

        getSpinner();
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Tổng");
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(tvTime);
            }
        });


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
        tvTonghopXuat = view.findViewById(R.id.tvTonghopXuat);
        btnSoLieu = view.findViewById(R.id.btnSoLieu);
        btnBieuDo = view.findViewById(R.id.btnBieuDo);
        radarChart = view.findViewById(R.id.chart);
        spCalendar = view.findViewById(R.id.spCalendar);
        rcvDSNhap = view.findViewById(R.id.rcvDSNhap);
        btnNhap = view.findViewById(R.id.btnNhap);
        btnXuat = view.findViewById(R.id.btnXuat);
        tvTime = view.findViewById(R.id.tvTime);
        imgCalendar = view.findViewById(R.id.imgCalendar);
    }

    public void getBieuDo(ArrayList<ThongKe_model> listNhap,ArrayList<ThongKe_model> listXuat){
        ArrayList<RadarEntry> listChartNhap = new ArrayList<>();
        ArrayList<RadarEntry> listChartXuat= new ArrayList<>();

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


            // Kiểm tra xem sản phẩm có tồn tại trong listXuat hay không
            boolean existsInListXuat = false;
            for (ThongKe_model nhap : listNhap) {
                if (nhap.getTensp().equals(tk.getTensp())) {
                    existsInListXuat = true;
                    break;
                }
            }

            // Nếu sản phẩm không tồn tại trong listXuat, thêm giá trị mặc định hoặc giá trị tương ứng
            if (!existsInListXuat) {
                labels.add(tk.getTensp());
                listChartNhap.add(new RadarEntry(0)); // Thêm giá trị mặc định vào listChartXuat
                listNhap.add(new ThongKe_model(tk.getTensp(), 0)); // Thêm sản phẩm mới vào listXuat
            }
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

    private void getSpinner(){

        ArrayAdapter< String > Arrcalendar = new ArrayAdapter <>(getContext(), android.R.layout.simple_spinner_item);
        Arrcalendar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Arrcalendar.add("Tất cả");
        Arrcalendar.add("Hôm nay");
        Arrcalendar.add("Tuần này");
        Arrcalendar.add("Tháng này");

        spCalendar.setAdapter(Arrcalendar);
        spCalendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parent, View view, int position, long id) {
                String select = (String) parent.getItemAtPosition(position);
                radarChart.clear();
                if (select.equals("Tất cả")){
                    ArrayList<ThongKe_model> listNhap = (ArrayList<ThongKe_model>) tkDao.getTKSL(0);
                    ArrayList<ThongKe_model> listXuat = (ArrayList<ThongKe_model>) tkDao.getTKSL(1);
                    getBieuDo(listNhap,listXuat);
                    // set tksl
                    getTKSL(listNhap,listXuat);
                    loadDS(listNhap);
                    chonloai(listNhap,listXuat);

                } else if (select.equals("Hôm nay")) {
                    dsachTheoNgay();
                } else if (select.equals("Tuần này")){
                    dsachTheoTuan();
                } else if (select.equals("Tháng này")){
                    dsachTheoThang();
                }
                radarChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView < ? > parent) {

            }
        });
    }

    private void dsachTheoNgay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime(); // Lấy ngày hôm nay
        String currentDateStr = dateFormat.format(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Thêm 1 ngày vào ngày hiện tại
        Date tomorrowDate = calendar.getTime(); // Lấy ngày mới
        String tomorrowDateStr = dateFormat.format(tomorrowDate); // Định dạng ngày mới thành chuỗi
        ArrayList<ThongKe_model> listNhap = tkDao.getSLSPTheoNgay(0,currentDateStr,tomorrowDateStr);
        ArrayList<ThongKe_model> listXuat = tkDao.getSLSPTheoNgay(1,currentDateStr,tomorrowDateStr);
        // set biểu đồ
        getBieuDo(listNhap,listXuat);
        // set tksl
        getTKSL(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+currentDateStr);
    }
    private void dsachTheoTuan(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        // Thiết lập ngày trong tuần là Thứ Hai (Calendar.MONDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = calendar.getTime();
        String thuHai = dateFormat.format(monday);

        // Thiết lập ngày trong tuần là Chủ Nhật (Calendar.SUNDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date sunday = calendar.getTime();
        String chuNhat = dateFormat.format(sunday);

        ArrayList<ThongKe_model> listNhap = tkDao.getSLSPTheoNgay(0,thuHai,chuNhat);
        ArrayList<ThongKe_model> listXuat = tkDao.getSLSPTheoNgay(1,thuHai,chuNhat);
        // set biểu đồ
        getBieuDo(listNhap,listXuat);
        // set tksl
        getTKSL(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+thuHai+" - "+chuNhat);
    }
    private void dsachTheoThang(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        // Lấy ngày đầu tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dauThang = calendar.getTime();
        String ngayDau = dateFormat.format(dauThang);

        // Lấy ngày cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date cuoiThang = calendar.getTime();
        String ngayCuoi = dateFormat.format(cuoiThang);

        ArrayList<ThongKe_model> listNhap = tkDao.getSLSPTheoNgay(0,ngayDau,ngayCuoi);
        ArrayList<ThongKe_model> listXuat = tkDao.getSLSPTheoNgay(1,ngayDau,ngayCuoi);
        // set biểu đồ
        getBieuDo(listNhap,listXuat);

        // set tksl
        getTKSL(listNhap,listXuat);
        loadDS(listNhap);
        chonloai(listNhap,listXuat);
        tvTime.setText("Ngày "+ngayDau+" - "+ngayCuoi);
    }

    private void getTKSL(ArrayList<ThongKe_model> listNhap,ArrayList<ThongKe_model> listXuat){
        int tongNhap = 0;
        int tongXuat = 0;
        for (ThongKe_model tk : listNhap){
            tongNhap += tk.getSoLuong();
        }
        for (ThongKe_model tk : listXuat){
            tongXuat += tk.getSoLuong();
        }
        tvTonghop.setText("Tổng nhập : "+tongNhap);
        tvTonghopXuat.setText("Tổng xuất : "+tongXuat);
    }
    public void loadDS(ArrayList<ThongKe_model> list){
        frg.loadDS(getContext(),list,rcvDSNhap);
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
                ArrayList<ThongKe_model> listNhap = new ArrayList<>(tkDao.getSLSPTheoNgay(0,currentDateStr,currentDateStr));
                ArrayList<ThongKe_model> listXuat = new ArrayList<>(tkDao.getSLSPTheoNgay(1,currentDateStr,currentDateStr));
                chonloai(listNhap,listXuat);
                loadDS(listNhap);
                // set biểu đồ
                getBieuDo(listNhap,listXuat);

                // set tksl
                getTKSL(listNhap,listXuat);
                radarChart.invalidate();
            }
        }, initialYear, initialMonth, initialDay);

        datePickerDialog.show();
    }
}