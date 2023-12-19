package com.example.da_ql_khohang;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.da_ql_khohang.databinding.ActivityMainBinding;
import com.example.da_ql_khohang.databinding.ActivitySignUpBinding;
import com.google.android.material.textfield.TextInputEditText;

public class Sign_up extends AppCompatActivity {


    ActivitySignUpBinding binding;
    Member_DAO memberDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#567DF4"));


        memberDao = new Member_DAO(this);
        binding.tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_up.this, Sign_in.class));
            }
        });
        binding.btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });
    }

    private void register() {
        String username = binding.tvUser.getText().toString();
        String email = binding.tvEmail.getText().toString();
        String pass = binding.edPasscode.getText().toString();
        String confirm = binding.edConfirm.getText().toString();
        String fullname = binding.tvFullname.getText().toString();
        String avatar = binding.tvAvatar.getText().toString();


        if (username.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()|| fullname.isEmpty()) {
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
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
        Member_Model member = new Member_Model();
        member.setFullname(fullname);
        member.setUsername(username);
        member.setPasswd(pass);
        member.setEmail(email);
        member.setAvatar(avatar);
        member.setRole(1);
        if (memberDao.insert_mem(member) != false) {
            startActivity(new Intent(Sign_up.this, Sign_in.class));
            Toast.makeText(Sign_up.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Sign_up.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}