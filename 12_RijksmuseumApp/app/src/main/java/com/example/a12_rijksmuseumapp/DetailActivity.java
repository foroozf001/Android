package com.example.a12_rijksmuseumapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a12_rijksmuseumapp.database.AppDatabase;
import com.example.a12_rijksmuseumapp.database.FavoriteEntry;
import com.example.a12_rijksmuseumapp.model.Art;
import com.example.a12_rijksmuseumapp.viewModel.AppExecutors;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView titleOfArtPiece;
    ImageView headerImage, webImage;
    private Art favorite;
    private final AppCompatActivity activity = DetailActivity.this;
    private AppDatabase mDb;
    List<FavoriteEntry> entries = new ArrayList<>();

    String id, title, headerImgUrl, webImgUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();

        mDb = AppDatabase.getInstance(getApplicationContext());

        headerImage = findViewById(R.id.thumbnail_image_header);
        webImage = findViewById(R.id.artCover);
        titleOfArtPiece = findViewById(R.id.title);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("title")) {
            id = getIntent().getExtras().getString("id");
            headerImgUrl = getIntent().getExtras().getString("headerImage");
            webImgUrl = getIntent().getExtras().getString("webImage");
            title = getIntent().getExtras().getString("title");

            Glide.with(this)
                    .load(headerImgUrl)
                    .placeholder(R.drawable.ic_refresh_black_24dp)
                    .into(headerImage);

            Glide.with(this)
                    .load(webImgUrl)
                    .placeholder(R.drawable.ic_refresh_black_24dp)
                    .into(webImage);

            titleOfArtPiece.setText(title);
        } else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

        checkStatus(title);
    }


    public void saveFavorite(){
        final FavoriteEntry favoriteEntry = new FavoriteEntry(id, title, headerImgUrl, webImgUrl);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().insertFavorite(favoriteEntry);
            }
        });
    }

    private void deleteFavorite(final String art_id){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().deleteFavoriteWithId(art_id);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void checkStatus(final String artName){
        final MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                entries.clear();
                entries = mDb.favoriteDao().loadAll(artName);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                if (entries.size() > 0){
                    materialFavoriteButton.setFavorite(true);
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite == true) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, "Added to favorites",
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        deleteFavorite(id);
                                        Snackbar.make(buttonView, "Removed from favorites",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }else {
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite == true) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, "Added to favorites",
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        String art_id = getIntent().getExtras().getString("id");
                                        deleteFavorite(art_id);
                                        Snackbar.make(buttonView, "Removed from favorites",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }.execute();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.art_details));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
