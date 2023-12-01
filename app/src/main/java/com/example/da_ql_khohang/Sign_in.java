package com.example.da_ql_khohang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_ql_khohang.ThanhVien.Member_DAO;

public class Sign_in extends AppCompatActivity {
    EditText edUser, edPassword;
    Button btnLogin;
    TextView tvSign_up;

    Member_DAO memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getView();
        tvSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_in.this, Sign_up.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getText().toString();
                String pass = edPassword.getText().toString();
                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Sign_in.this, "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    memberDao = new Member_DAO(Sign_in.this);
                    if (memberDao.checkLogin(user, pass) == 1){
                        startActivity(new Intent(Sign_in.this, MainActivity.class));
                        Toast.makeText(Sign_in.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Sign_in.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void getView(){
        edUser = findViewById(R.id.edUser);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSign_up = findViewById(R.id.tvSign_up);

    }
}
