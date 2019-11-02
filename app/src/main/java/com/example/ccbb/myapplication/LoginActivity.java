package com.example.ccbb.myapplication;
//注册页面
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ccbb.myapplication.db.DBHelper;

public class LoginActivity extends Activity {
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox checkBox;
    private Button btnLogin;
    private Button btnRegister;
    private DBHelper db;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        initViews();
        //SharedPreferences用来保存注册的东西
        SharedPreferences spIntent = getSharedPreferences("REGISTER", MODE_PRIVATE);
        if (spIntent.getBoolean("intent", false)) {
            //接受注册页面传来的数据
            getDataFromRegister();
            SharedPreferences.Editor editor = spIntent.edit();//editor是编辑工具
            editor.putBoolean("intent",false);//用编辑工具将消息放入
            editor.commit();//提交消息
        } else {
            getSP();
        }

        onclick();

    }

    private void getSP() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        boolean IF = sp.getBoolean("IF", false);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (IF) {
            etPassword.setText(password);
            etUsername.setText(username);
            checkBox.setChecked(true);
        }
    }

    private void getDataFromRegister() {
        Intent intent = getIntent();
        String username = null;
        String password = null;
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        if (username != null && password != null) {
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }

    private void onclick() {

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db = new DBHelper(LoginActivity.this);
                sqLiteDatabase = db.getWritableDatabase();
                String sql = "select username ,password from usr where username = ? and password = ?";
                Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{etUsername.getText().toString().trim(), etPassword.getText().toString().trim()});
                if (cursor.getCount() == 1) {
                    if (checkBox.isChecked()) {
                        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
                        SharedPreferences.Editor et = sp.edit();
                        et.putString("username", etUsername.getText().toString().trim());
                        et.putString("password", etPassword.getText().toString().trim());
                        et.putBoolean("IF", true);
                        et.commit();
                    }
                    Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(getApplication(), "账号或密码错误", Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                    etUsername.setText("");
                }
                cursor.close();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initViews() {
        etUsername = findViewById(R.id.et_username_login);
        etPassword = findViewById(R.id.et_password_login);
        checkBox = findViewById(R.id.checkbox);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }//基础操作，获取组件
}
