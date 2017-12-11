package com.example.shile.sqlmustsuccessfulone;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shile.sqlmustsuccessfulone.db.ChoreContract;
import com.example.shile.sqlmustsuccessfulone.db.ChoreDbHelper;

import java.util.ArrayList;

public class ChildChore extends AppCompatActivity {

    private static final String TAG = "ChildChore";

    public ChoreDbHelper mHelper;
    public ListView mChoreListView;
    public ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chore);
        mHelper = new ChoreDbHelper(this);
        mChoreListView = (ListView) findViewById(R.id.parent_chores);

        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_parent, menu);
        MenuItem item = menu.findItem(R.id.action_add_chore);
        item.setVisible(false);
        //ImageButton locButton = (ImageButton) menu.findItem(R.id.action_add_chore);
        //locButton.setVisibility(View.GONE);
        return super.onCreateOptionsMenu(menu);
    }



    public void deleteChore(View view) {
        View parent = (View) view.getParent();
        TextView choreTextView = (TextView) parent.findViewById(R.id.chore_title);
        String chore = String.valueOf(choreTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(ChoreContract.ChoreEntry.TABLE,
                ChoreContract.ChoreEntry.COL_CHORE_TITLE + " = ?",
                new String[]{chore});
        db.close();
        updateUI();
    }


    private void updateUI()
    {
        ArrayList<String> choreList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(ChoreContract.ChoreEntry.TABLE,
                new String[]{ChoreContract.ChoreEntry._ID, ChoreContract.ChoreEntry.COL_CHORE_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(ChoreContract.ChoreEntry.COL_CHORE_TITLE);
            choreList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.parent_chore_item,
                    R.id.chore_title,
                    choreList);
            mChoreListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(choreList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();


    }



}

