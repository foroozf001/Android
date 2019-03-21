package com.example.a08_bucketlist;

import android.app.Activity;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText mTitle;
    EditText mDescription;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mTitle = findViewById(R.id.etTitle);
        mDescription = findViewById(R.id.etDescription);
        mButton = findViewById(R.id.btnGereed);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                    final BucketListItem BLItem = new BucketListItem(title, description);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_BUCKETLIST, BLItem);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Snackbar.make(v, "Enter some data", Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
