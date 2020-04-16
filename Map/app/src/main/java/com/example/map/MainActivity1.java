package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_account, edit_pasword;
    private Button btn_login, btn_register;
    private String account, password;
    private DBHelper dbHelper;
    public static String nameString;
    private MD5Utils md5Utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
    }

    void initView(){
        edit_account = (EditText)findViewById(R.id.username);
        edit_account.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    edit_account.clearFocus();
                }
                return false;
            }
        });

        edit_pasword = (EditText)findViewById(R.id.password);
        edit_pasword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    edit_pasword.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_pasword.getWindowToken(), 0);
                }
                return false;
            }
        });

        btn_login = (Button)findViewById(R.id.log_button);
        btn_register = (Button)findViewById(R.id.register_button);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        md5Utils = new MD5Utils();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.log_button:
                if(edit_account.getText().toString().trim().equals("") | edit_pasword.getText().toString().trim().equals("")){
                    Toast.makeText(this, "Please Enter Account or Password", Toast.LENGTH_SHORT).show();
                }else{
                    readUserInfo();
                }
                break;
            case R.id.register_button:
                Intent intent = new Intent(MainActivity1.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void readUsersInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("UsersInfo",MODE_PRIVATE);
        account = sharedPreferences.getString("username","");
        password = sharedPreferences.getString("password","");
    }

    protected void readUserInfo(){
        String passwordMD = md5Utils.encode(edit_pasword.getText().toString());
        if (login(edit_account.getText().toString(), passwordMD)){
            Toast.makeText(this, "Log in Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
            intent.putExtra("Username", edit_account.getText().toString());
            startActivity(intent);
            nameString = edit_account.getText().toString().trim();
        }else{
            Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean login(String username, String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECt * from usertable where username=? and password=?", new String[]{username,password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
}
