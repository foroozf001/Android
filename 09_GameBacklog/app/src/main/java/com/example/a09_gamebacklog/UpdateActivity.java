package com.example.a09_gamebacklog;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static java.security.AccessController.getContext;

public class UpdateActivity extends AppCompatActivity {

    private EditText title;
    private EditText platform;
    private Spinner status;

    private int adapterPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.gameTitle);
        status = findViewById(R.id.statusSpinner);
        platform = findViewById(R.id.gamePlatform);

        Bundle extras = getIntent().getExtras();
        adapterPosition = extras.getInt("EXTRA_POSITION");
        Game gameToUpdate = extras.getParcelable("EXTRA_GAME");

        if (gameToUpdate != null) {
            title.setText(gameToUpdate.getTitle());
            platform.setText(gameToUpdate.getPlatform());
        }


        this.setTitle("Update game");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int holderint = getResources().getIdentifier("status_array", "array", this.getClass().getPackage().getName());
                String[] items = getResources().getStringArray(holderint);

                Intent intent = new Intent();
                int selectedId = (int) status.getSelectedItemId();

                if (adapterPosition > -1) {
                    Game newGame = new Game(title.getText().toString(), platform.getText().toString(), items[selectedId]);
                    Bundle extras = new Bundle();
                    extras.putInt("EXTRA_POSITION", adapterPosition);
                    extras.putParcelable("EXTRA_GAME", newGame);
                    intent.putExtras(extras);
                } else {
                    Game newGame = new Game(title.getText().toString(), platform.getText().toString(), items[selectedId]);
                    intent.putExtra(MainActivity.EXTRA_GAMEBACKLOG, newGame);
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
