package com.example.a09_gamebacklog_mv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = CreateActivity.class.getSimpleName();

    private TextView titleInput;
    private TextView platformInput;
    private Spinner statusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleInput = findViewById(R.id.gameTitle);
        platformInput = findViewById(R.id.gamePlatform);
        statusInput = findViewById(R.id.statusSpinner);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, titleInput.getText() + " | " + titleInput.toString());
                Log.d(TAG, platformInput.getText() + " | " + platformInput.toString());

                Intent intent = new Intent();
                // In java, enums cannot be cast directly to int or vice versa, so the "best" solution is to take
                // all the values in an array, and select the enum value from there... seems legit.
                intent.putExtra(MainActivity.EXTRA_BACKLOG_ITEM,
                        new Game(titleInput.getText().toString(), platformInput.getText().toString(),
                                GameStatus.values()[(int)statusInput.getSelectedItemId()]));

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(Activity.RESULT_CANCELED, null);
        finish();

        return true;
    }

}
