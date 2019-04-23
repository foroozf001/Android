package com.example.a10_numberstrivia;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnTrivia;
    TextView tvTrivia;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTrivia = findViewById(R.id.btn_trivia);
        tvTrivia = findViewById(R.id.tv_trivia);

        btnTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getRandomNumberTrivia();
            }
        });


        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getTrivia().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTrivia.setText(s);
            }
        });
    }
}