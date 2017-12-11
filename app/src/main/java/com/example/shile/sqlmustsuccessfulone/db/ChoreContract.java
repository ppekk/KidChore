package com.example.shile.sqlmustsuccessfulone.db;

import android.provider.BaseColumns;

public class ChoreContract {
    public static final String DB_NAME = "com.bu.bkrtv.kidchore.db";
    public static final int DB_VERSION = 1;

    public class ChoreEntry implements BaseColumns {
        public static final String TABLE = "chores";

        public static final String COL_CHORE_TITLE = "title";
        //public static final String COL_CHORE_NAME = "name";
        //public static final String COL_CHORE_TIME = "time";
    }
}
