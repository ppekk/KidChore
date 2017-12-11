package com.example.shile.sqlmustsuccessfulone;

import android.content.ContentValues;
import android.content.DialogInterface;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;


import com.example.shile.sqlmustsuccessfulone.db.ChoreContract;
import com.example.shile.sqlmustsuccessfulone.db.ChoreDbHelper;

import java.util.ArrayList;

public class ParentChore extends AppCompatActivity {
    private static final String TAG = "ParentChore";
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
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_chore:
                final EditText choreEditText = new EditText(this);
                //final EditText choreNameEditText = new EditText(this);
                //final EditText choreTimeEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new chore")
                        .setMessage("Which chore needs to be done?")
                        .setView(choreEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //String chore = String.valueOf(choreEditText.getText());
                                //Log.d(TAG, "Chore to add: " + chore);
                                String chore = String.valueOf(choreEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(ChoreContract.ChoreEntry.COL_CHORE_TITLE, chore);
                                db.insertWithOnConflict(ChoreContract.ChoreEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

