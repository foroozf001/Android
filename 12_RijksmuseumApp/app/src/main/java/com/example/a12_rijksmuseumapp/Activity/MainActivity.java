package com.example.a12_rijksmuseumapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a12_rijksmuseumapp.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainFragment())
                .commit();
    }

}
