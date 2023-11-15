package com.example.da_ql_khohang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_in extends AppCompatActivity {
    EditText edUser, edPassword;
    Button btnLogin;
    TextView tvSign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edUser.getText().toString();
                String pass = edPassword.getText().toString();
                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Sign_in.this, "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }else{

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
