package com.example.map;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;



import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {

    ListView listView;
    VideoAdapter va;

    ImageButton btnM,btnS ,btnC ,btnH;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.videos);
        va = new VideoAdapter(this, R.layout.video_item, getData(), VideoConstant.videoUrls[0],
                VideoConstant.videoTitles[0],VideoConstant.videoHeads[0]);
        listView.setAdapter(va);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if(GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if(GSYVideoManager.instance().getPlayTag().equals(VideoAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(MainActivity2.this)){
                            return;
                        }
                        GSYVideoManager.releaseAllVideos();
                        listView.deferNotifyDataSetChanged();
                    }
                }
            }
        });
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
                Intent intent = new Intent( MainActivity2.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Notebook.class);
                startActivity(intent);
            }
        });

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Meactivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    static List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<>();
        int N = 4;
        for(int i = 0; i < N; ++i){
            Map<String, Object> map = new HashMap<>();
            map.put("share", android.R.drawable.btn_plus);
            map.put("name", "dlwlrma" + i);
            map.put("message", "i love gym" + i);
            list.add(map);
        }
        return list;

    }
}
