package com.example.a12_rijksmuseumapp.Activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.example.a12_rijksmuseumapp.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(extras);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, detailFragment)
                .commit();
    }
}
