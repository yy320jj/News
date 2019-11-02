package com.example.ccbb.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ccbb.myapplication.db.DBHelper;

public class RegisterActivity extends Activity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private DBHelper db;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        initViews();

        onclick();
    }

    private void onclick() {
        //注册的按钮的点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = db.getWritableDatabase();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                int code = register(username, password);
                switch (code) {
                    case 0:
                        Toast.makeText(getApplication(),"注册成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("password",password);
                        SharedPreferences sharedPreferences = getSharedPreferences("REGISTER",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("intent",true);
                        editor.commit();
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Toast.makeText(getApplication(),"账号或密码不得为空",Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplication(),"账号已被注册",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private int register(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return 1;
        }
        String sqlRaw = "select username from usr where username = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sqlRaw, new String[]{username});
        if (cursor.getCount() != 0) {
            return 2;
        }
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        sqLiteDatabase.insert("usr",null,values);
        cursor.close();
        return 0;
    }

    private void initViews() {
        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);

        btnRegister = findViewById(R.id.btn_register);

        db = new DBHelper(RegisterActivity.this);
    }
}
