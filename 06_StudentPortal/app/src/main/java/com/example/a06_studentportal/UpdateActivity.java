package com.example.a06_studentportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText mPortalName;
    EditText mPortalLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mPortalName = findViewById(R.id.editTextName);
        mPortalLink = findViewById(R.id.editTextLink);

        final PortalObject portalUpdate = getIntent().getParcelableExtra(MainActivity.EXTRA_PORTAL);
        mPortalLink.setText(portalUpdate.getmPortalLink());
        mPortalName.setText(portalUpdate.getmPortalName());

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String portalName = mPortalName.getText().toString();
                String portalLink = mPortalLink.getText().toString();

                if(!TextUtils.isEmpty(portalName) && !TextUtils.isEmpty(portalLink)) {
                    portalUpdate.setmPortalLink(portalLink);
                    portalUpdate.setmPortalName(portalName);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_PORTAL, portalUpdate);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Snackbar.make(v, "Enter some data", Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
