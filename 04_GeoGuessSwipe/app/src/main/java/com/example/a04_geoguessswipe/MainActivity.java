package com.example.a04_geoguessswipe;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private static final String DEBUG_TAG = "MyActivity";
    List<GeoObject> mGeoObjects;
    RecyclerView mGeoRecyclerView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeoObjects = new ArrayList<>();
        mGeoRecyclerView = findViewById(R.id.recyclerView);

        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
            mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i],
                    GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i],
                    GeoObject.PRE_DEFINED_GEO_OBJECT_INEUROPE[i]));
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, LinearLayoutManager.VERTICAL);

        mGeoRecyclerView.setLayoutManager(mLayoutManager);
        mGeoRecyclerView.setHasFixedSize(true);

        final GeoObjectAdapter mAdapter = new GeoObjectAdapter(this, mGeoObjects);
        mGeoRecyclerView.setAdapter(mAdapter);

        mGeoRecyclerView.addOnItemTouchListener(this);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        if (swipeDir == 4 && mGeoObjects.get(position).getmGeoInEurope()) { //left
                            Toast.makeText(MainActivity.this, "Je antwoord is juist! " + mGeoObjects.get(position).getmGeoName() +  " ligt in Europa.", Toast.LENGTH_SHORT).show();
                        } else if (swipeDir == 8 && !mGeoObjects.get(position).getmGeoInEurope()) {
                            Toast.makeText(MainActivity.this, "Je antwoord is juist! " + mGeoObjects.get(position).getmGeoName() +  " ligt niet in Europa.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Je antwoord is onjuist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGeoRecyclerView);
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        int mAdapterPosition = rv.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(e)) {
            Toast.makeText(this, mGeoObjects.get(mAdapterPosition).getmGeoName(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
