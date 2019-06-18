package com.example.a12_rijksmuseumapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.a12_rijksmuseumapp.adapter.*;
import com.example.a12_rijksmuseumapp.database.FavoriteEntry;
import com.example.a12_rijksmuseumapp.model.*;
import com.example.a12_rijksmuseumapp.api.*;
import com.example.a12_rijksmuseumapp.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Art> artList;
    private MainActivityViewModel viewModel;
    public static ProgressDialog pd;
    private AppCompatActivity activity = MainActivity.this;
    public static SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = Adapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getAllArtPieces().observe(this, new Observer<List<Art>>() {
            @Override
            public void onChanged(@Nullable List<Art> list) {
                artList = list;
                updateUI();
            }
        });

        checkSortOrder();
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        artList = new ArrayList<>();

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new Adapter(this, artList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapList(artList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "Preferences updated");
        checkSortOrder();
    }

    private void checkSortOrder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.sort_order_relevance)
        );
        if (sortOrder.equals(this.getString(R.string.sort_order_favorites))) {
            initViews2();
        } else {
            viewModel.getAllArtPieces(BuildConfig.RIJKSMUSEUM_API_TOKEN, "json", 100, "", sortOrder, true);
        }
    }

    private void initViews2() {
        recyclerView = findViewById(R.id.recycler_view);
        artList = new ArrayList<>();
        adapter = new Adapter(this, artList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getAllFavorites();
    }

    private void getAllFavorites() {
        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                artList.clear();
                artList.addAll(favoriteDbHelper.getAllFavorites());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();*/
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> imageEntries) {
                List<Art> objects = new ArrayList<>();
                for (FavoriteEntry entry : imageEntries){
                    Art object = new Art();
                    object.setId(entry.getArtid());
                    object.setLongTitle(entry.getTitle());
                    object.setHeaderImageUrl(entry.getHeaderImg());
                    object.setWebImageUrl(entry.getWebImg());

                    objects.add(object);
                }
                adapter.swapList(objects);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkSortOrder();
    }
}
