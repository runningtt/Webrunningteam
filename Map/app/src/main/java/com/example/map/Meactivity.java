package com.example.map;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meactivity extends AppCompatActivity {
    ImageButton btnM,btnS ,btnC ,btnH;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me);


        initView();
    }
    void initView(){
        btnM = findViewById(R.id.main_button);
        btnS = findViewById(R.id.search_button);
        btnC = findViewById(R.id.comment_button);
        btnH = findViewById(R.id.home_button);

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Meactivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Meactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Meactivity.this, Notebook.class);
                startActivity(intent);
            }
        });

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Meactivity.this, Meactivity.class);
                startActivity(intent);
            }
        });
    }
}
