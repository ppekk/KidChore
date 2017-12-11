package com.example.shile.sqlmustsuccessfulone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shile.sqlmustsuccessfulone.db.ChoreDbHelper;

public class KidchoreStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kidchore_start);
    }

    public void enterApp(View view) {
        Intent i = new Intent(getApplicationContext(),ParentNChild.class);
        startActivity(i);
        setContentView(R.layout.parent_nchild);
    }
}
