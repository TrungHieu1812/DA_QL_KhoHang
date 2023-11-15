package com.example.da_ql_khohang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_up extends AppCompatActivity {
    TextView tvEmail, tvUser, tvlogin;
    EditText edPasscode, edConfirm;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getView();
    }
    public void getView(){
        tvEmail = findViewById(R.id.tvEmail);
        tvUser = findViewById(R.id.tvUser);
        tvlogin = findViewById(R.id.tvLogin);
        edPasscode = findViewById(R.id.edPasscode);
        edConfirm = findViewById(R.id.edConfirm);
        btnLogin = findViewById(R.id.btnLogin);
    }
}