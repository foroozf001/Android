package com.example.a12_rijksmuseumapp.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_ARTID = "artid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_HEADERIMG = "headerimg";
        public static final String COLUMN_WEBIMG = "webimg";
    }
}
