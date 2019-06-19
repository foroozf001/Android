package com.example.a12_rijksmuseumapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a12_rijksmuseumapp.database.AppDatabase;
import com.example.a12_rijksmuseumapp.database.FavoriteEntry;
import com.example.a12_rijksmuseumapp.viewModel.AppExecutors;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {
    TextView titleOfArtPiece;
    ImageView headerImage, webImage;
    private AppDatabase mDb;
    List<FavoriteEntry> entries = new ArrayList<>();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    String id, title, headerImgUrl, webImgUrl;
    private MaterialFavoriteButton materialFavoriteButton;

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        materialFavoriteButton = view.findViewById(R.id.favorite_button);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getContext());

        headerImage = view.findViewById(R.id.thumbnail_image_header);
        webImage = view.findViewById(R.id.artCover);
        titleOfArtPiece = view.findViewById(R.id.title);

        appBarLayout = view.findViewById(R.id.appbar);

        collapsingToolbarLayout =
                view.findViewById(R.id.collapsing_toolbar);

        initCollapsingToolbar();
        title = getArguments().getString("title");
        titleOfArtPiece.setText(title);

        if(!getArguments().getString("id").isEmpty()) {
            id = getArguments().getString("id");
            headerImgUrl = getArguments().getString("headerImage");
            webImgUrl = getArguments().getString("webImage");
            title = getArguments().getString("title");

            Glide.with(this)
                    .load(headerImgUrl)
                    .placeholder(R.drawable.ic_sync_black_24dp)
                    .into(headerImage);

            Glide.with(this)
                    .load(webImgUrl)
                    .placeholder(R.drawable.ic_sync_black_24dp)
                    .into(webImage);

            webImage.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                    PhotoView photoView = mView.findViewById(R.id.imagePhotoView);
                    Glide.with(getContext())
                            .load(webImgUrl)
                            .placeholder(R.drawable.ic_sync_black_24dp)
                            .into(photoView);
                    mBuilder.setView(mView);
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();
                }
            });

            titleOfArtPiece.setText(title);
        } else {
            Toast.makeText(getContext(), "No API Data", Toast.LENGTH_SHORT).show();
        }

        checkStatus(title);
        return view;
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
                                        String art_id = getArguments().getString("id");
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
        collapsingToolbarLayout.setTitle(" ");
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
