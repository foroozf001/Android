package com.example.a12_rijksmuseumapp.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a12_rijksmuseumapp.Activity.SettingsActivity;
import com.example.a12_rijksmuseumapp.BuildConfig;
import com.example.a12_rijksmuseumapp.R;
import com.example.a12_rijksmuseumapp.adapter.Adapter;
import com.example.a12_rijksmuseumapp.database.FavoriteEntry;
import com.example.a12_rijksmuseumapp.model.Art;
import com.example.a12_rijksmuseumapp.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Art> artList;
    private MainActivityViewModel viewModel;
    public static final String LOG_TAG = Adapter.class.getName();
    private String searchQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        initViews();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG)
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
        return view;
    }

    private void initViews() {
        artList = new ArrayList<>();
        searchQuery = "";
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new Adapter(getContext(), artList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapList(artList);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) {
                    searchQuery = "";
                    checkSortOrder();
                    Toast.makeText(getActivity(), "Search query is empty", Toast.LENGTH_LONG).show();
                } else {
                    searchQuery = s;
                    checkSortOrder();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.sort_order_relevance)
        );
        if (sortOrder.equals(this.getString(R.string.sort_order_favorites))) {
            initViewsFavorites();
        } else {
            if (!searchQuery.isEmpty()) {
                viewModel.getAllArtPieces(BuildConfig.RIJKSMUSEUM_API_TOKEN, "json", 100, searchQuery, sortOrder, true);
            } else {
                viewModel.getAllArtPieces(BuildConfig.RIJKSMUSEUM_API_TOKEN, "json", 100, "", sortOrder, true);
            }
        }
    }

    private void initViewsFavorites() {
        artList = new ArrayList<>();
        adapter = new Adapter(getContext(), artList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getAllFavorites();
    }

    private void getAllFavorites() {
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
