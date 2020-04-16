package com.example.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;



public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText edit_name, edit_password, edit_password2;
    private Button btn_yes, btn_cancel;
    private DBHelper dbHelper;
    private MD5Utils md5Utils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
        dbHelper = new DBHelper(this);
    }

    void initView(){
        edit_name = (EditText)findViewById(R.id.username_regis);
        edit_name.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for(int i = start; i < end; i++){
                            if(!Character.isLetterOrDigit(source.charAt(i)) &&
                            !Character.toString(source.charAt(i)).equals("_")){
                                Toast.makeText(RegisterActivity.this, "Only alphabet,number", Toast.LENGTH_LONG).show();
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
        edit_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    edit_name.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_name.getWindowToken(), 0);
                }
                return false;
            }
        });

        edit_password = (EditText)findViewById(R.id.password_regis1);
        edit_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId ==  EditorInfo.IME_ACTION_DONE){
                    String s = v.getText().toString();
                    System.out.println("v:****** v:"+s.length());
                    if(s.length() >= 6){
                        System.out.println("****** s:"+s.length());
                        edit_password.clearFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit_password.getWindowToken(), 0);
                    }else {
                        Toast.makeText(RegisterActivity.this, "At least 6", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        edit_password2 = (EditText)findViewById(R.id.password_regis2);
        edit_password2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    edit_password2.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_password2.getWindowToken(), 0);
                }
                return false;
            }
        });

        btn_yes = (Button)findViewById(R.id.reg_button);
        btn_cancel = (Button)findViewById(R.id.cancel_button);
        btn_yes.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        md5Utils = new MD5Utils();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.reg_button:
                if(CheckDatainDB(edit_name.getText().toString())){
                    Toast.makeText(this,"Name is used", Toast.LENGTH_LONG).show();
                }else {
                    if (edit_password.getText().toString().trim().equals(edit_password2.getText().toString())){
                        String passwordM = md5Utils.encode(edit_password.getText().toString());
                        registerUserInfo(edit_name.getText().toString(),passwordM);
                        Toast.makeText(this, "Successfully Register", Toast.LENGTH_LONG).show();
                        Intent register_intent = new Intent(RegisterActivity.this, MainActivity2.class);
                        startActivity(register_intent);
                    }else{
                        Toast.makeText(this, "Passwords are different", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.cancel_button:
                Intent login_intent = new Intent(RegisterActivity.this, MainActivity2.class);
                startActivity(login_intent);
                break;
        }
    }

    private void saveUsersInfo(){
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("UsersInfo", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", edit_name.getText().toString());
        if(edit_password.getText().toString().equals(edit_password2.getText().toString())){
            editor.putString("password", edit_password.getText().toString());
        }
        editor.commit();
    }

    private void registerUserInfo(String username, String userpassword){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password",userpassword);
        db.insert("usertable", null, values);
        db.close();
    }

    public boolean CheckDatainDB(String value){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from usertable where username =?", new String[]{value});
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

}
