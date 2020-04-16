package com.example.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;
import java.util.Map;

public class Notebook extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String name;
    private BottomBar bottomBar;
    private MemoSQLHelper sqlHelper;
    private MemoAdapter ma;

    public final int REQUEST_CODE_ADD = 1;
    public final int REQUEST_CODE_MOD = 2;


    ImageButton btnM,btnS ,btnC ,btnH;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebookpage);
        initSQL();
        initView();
        initView1();

    }
    void initView1(){
        btnM = findViewById(R.id.main_button);
        btnS = findViewById(R.id.search_button);
        btnC = findViewById(R.id.comment_button);
        btnH = findViewById(R.id.home_button);

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Notebook.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notebook.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notebook.this, Notebook.class);
                startActivity(intent);
            }
        });

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notebook.this, Meactivity.class);
                startActivity(intent);
            }
        });
    }

    void initSQL(){
        sqlHelper = new MemoSQLHelper(this);
    }

    void initView(){
        Intent intent = getIntent();
        name = intent.getStringExtra("Username");
        ListView lv = findViewById(R.id.listview);
        ma = new MemoAdapter(this, sqlHelper, R.layout.note_item);
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);
        registerForContextMenu(lv);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ListView.AdapterContextMenuInfo info =
                (ListView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.listview_delete:
                ma.deleteData(info.position);
                ma.notifyDataSetChanged();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_add:
                // Start ContentActivity to add a new note
                Intent intent = new Intent(this, ContentActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get result of ContentActivity
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Get title, content, modtime from Intent
            String title = data.getExtras().getString(MemoContract.MemoTable.COLUMN_NAME_TITLE);
            String content = data.getExtras().getString(MemoContract.MemoTable.COLUMN_NAME_CONTENT);
            long modtime = data.getExtras().getLong(MemoContract.MemoTable.COLUMN_NAME_MODTIME);
            // if it is a new note, just add it
            // if it is a modification of old one, get id and update it
            if (requestCode == REQUEST_CODE_ADD) {
                ma.addMemo(title, content, MainActivity1.nameString, modtime);
                ;
            } else if (requestCode == REQUEST_CODE_MOD) {
                int id = data.getExtras().getInt(MemoContract.MemoTable._ID);
                ma.updateMemo(id, title, content, MainActivity1.nameString, modtime);
            }
            ma.freshData();
            ma.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Notebook.this, ContentActivity.class);
        Map<String, Object> item = (Map) ma.getItem(position);
        // Put the title, content and id into the Intent
        intent.putExtra(MemoContract.MemoTable.COLUMN_NAME_TITLE,
                (String)item.get(MemoContract.MemoTable.COLUMN_NAME_TITLE));
        intent.putExtra(MemoContract.MemoTable.COLUMN_NAME_CONTENT,
                (String)item.get(MemoContract.MemoTable.COLUMN_NAME_CONTENT));
        intent.putExtra(MemoContract.MemoTable._ID,
                (int)item.get(MemoContract.MemoTable._ID));
        startActivityForResult(intent, REQUEST_CODE_MOD);
    }

}
