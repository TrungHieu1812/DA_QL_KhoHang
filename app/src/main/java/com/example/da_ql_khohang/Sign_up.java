package com.example.da_ql_khohang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_ql_khohang.ThanhVien.Member_DAO;
import com.example.da_ql_khohang.ThanhVien.Member_Model;
import com.google.android.material.textfield.TextInputEditText;

public class Sign_up extends AppCompatActivity {
    EditText tvEmail, tvUser;
    TextView tvlogin;
    TextInputEditText edPasscode, edConfirm;
    Button btnSign_up;
    Member_DAO memberDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getView();
        memberDao = new Member_DAO(this);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_up.this, Sign_in.class));
            }
        });
        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });
    }

    private void register() {
        String user = tvUser.getText().toString();
        String email = tvEmail.getText().toString();
        String pass = edPasscode.getText().toString();
        String confirm = edConfirm.getText().toString();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        // ktra đăng ký
        Member_Model tv = new Member_Model(user,user,pass,email,"nhanvien");
        if (memberDao.insert_mem(tv) != -1) {
            startActivity(new Intent(Sign_up.this, Sign_in.class));
            Toast.makeText(Sign_up.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Sign_up.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void getView() {
        tvEmail = findViewById(R.id.tvEmail);
        tvUser = findViewById(R.id.tvUser);
        tvlogin = findViewById(R.id.tvlogin);
        edPasscode = findViewById(R.id.edPasscode);
        edConfirm = findViewById(R.id.edConfirm);
        btnSign_up = findViewById(R.id.btnLogin);
    }
}