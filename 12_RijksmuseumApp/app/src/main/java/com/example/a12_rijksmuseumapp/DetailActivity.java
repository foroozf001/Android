package com.example.a12_rijksmuseumapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a12_rijksmuseumapp.data.FavoriteDbHelper;
import com.example.a12_rijksmuseumapp.model.Art;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailActivity extends AppCompatActivity {
    TextView titleOfArtPiece;
    ImageView headerImage, webImage;
    private FavoriteDbHelper favoriteDbHelper;
    private Art favorite;
    private final AppCompatActivity activity = DetailActivity.this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();

        headerImage = findViewById(R.id.thumbnail_image_header);
        webImage = findViewById(R.id.artCover);
        titleOfArtPiece = findViewById(R.id.title);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("title")) {
            String headerImg = getIntent().getExtras().getString("headerImage");
            String webImg = getIntent().getExtras().getString("webImage");
            String title = getIntent().getExtras().getString("title");

            Glide.with(this)
                    .load(headerImg)
                    .placeholder(R.drawable.ic_refresh_black_24dp)
                    .into(headerImage);

            Glide.with(this)
                    .load(webImg)
                    .placeholder(R.drawable.ic_refresh_black_24dp)
                    .into(webImage);

            titleOfArtPiece.setText(title);
        }

        MaterialFavoriteButton mfb = findViewById(R.id.favorite_button);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mfb.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            SharedPreferences.Editor editor = getSharedPreferences("com.example.a12_rijksmuseumapp.DetailActivity", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite added", true);
                            editor.commit();
                            saveFavorite();
                            Snackbar.make(buttonView, "Added to favorites", Snackbar.LENGTH_SHORT).show();
                        } else {
                            String art_id = getIntent().getExtras().getString("id");
                            favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
                            favoriteDbHelper.deleteFavorite(art_id);
                            SharedPreferences.Editor editor = getSharedPreferences("com.example.a12_rijksmuseumapp.DetailActivity", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite removed", false);
                            editor.commit();
                            Snackbar.make(buttonView, "Removed from favorites", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    public void saveFavorite() {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        favorite = new Art();
        String art_id = getIntent().getExtras().getString("id");
        String title = getIntent().getExtras().getString("title");
        String headerImgUrl = getIntent().getExtras().getString("headerImage");
        String webImgUrl = getIntent().getExtras().getString("webImage");

        favorite.setId(art_id);
        favorite.setLongTitle(title);
        favorite.setHeaderImageUrl(headerImgUrl);
        favorite.setWebImageUrl(webImgUrl);

        favoriteDbHelper.addFavorite(favorite);
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
