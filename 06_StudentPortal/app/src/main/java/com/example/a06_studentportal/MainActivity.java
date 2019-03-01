package com.example.a06_studentportal;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PortalAdapter.PortalClickListener{

    //Local
    private List<PortalObject> mPortals;
    private PortalAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static final String EXTRA_PORTAL = "Portal";
    public static final int REQUESTCODE = 1234;
    private int mModifyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPortals = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        updateUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PortalObject newPortal = new PortalObject("https://www.sis.hva.nl", "SIS HVA");
                mPortals.add(newPortal);
                updateUI();
            }
        });
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new PortalAdapter(mPortals, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void portalOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        mModifyPosition = i;
        intent.putExtra(EXTRA_PORTAL, mPortals.get(i));
        startActivityForResult(intent, REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE) {
            if(resultCode == RESULT_OK) {
                PortalObject updatedPortalObject = data.getParcelableExtra(MainActivity.EXTRA_PORTAL);
                mPortals.set(mModifyPosition, updatedPortalObject);
                updateUI();
            }
        }
    }
}
