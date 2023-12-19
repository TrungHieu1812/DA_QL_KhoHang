package com.example.da_ql_khohang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    CheckBox cbRemember;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getView();

        getWindow().setStatusBarColor(Color.parseColor("#567DF4"));

        checkRemember();


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
                    if (memberDao.checkLogin(user, pass) == true){
                        if (cbRemember.isChecked()) {
                            editor.putString("username", user);
                            editor.putString("password", pass);
                            editor.putBoolean("isChecked", cbRemember.isChecked());
                            editor.apply();
                        } else {
                            editor.clear();
                            editor.apply();
                        }
                        edUser.setText("");
                        edPassword.setText("");
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
        cbRemember = findViewById(R.id.cbRemember);

    }


    private void checkRemember() {
        shared = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        editor = shared.edit(); // gọi dòng trên và edit vào nó
        boolean isCheck = shared.getBoolean("isChecked", false);
        if (isCheck) {
            edUser.setText(shared.getString("username", ""));
            edPassword.setText(shared.getString("password", ""));
            cbRemember.setChecked(isCheck);
        }
    }
}
