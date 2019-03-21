package com.example.a08_bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
    private RecyclerView rvBucketList;
    private BucketListAdapter bucketListAdapter;
    private List<BucketListItem> bucketList = new ArrayList<>();
    private BucketListRoomDatabase db;

    public static final String EXTRA_BUCKETLIST = "BucketList";
    public static final int REQUESTCODE = 1234;

    private GestureDetector gestureDetector;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = BucketListRoomDatabase.getDatabase(this);

        initToolbar();
        initRecyclerView();
        initFAB();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bucket List");
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        bucketListAdapter = new BucketListAdapter(bucketList);
        rvBucketList = findViewById(R.id.rv_bucket_list);
        rvBucketList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBucketList.setAdapter(bucketListAdapter);
        rvBucketList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        // Delete an item from the shopping list on long press.
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = rvBucketList.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {

                    int adapterPosition = rvBucketList.getChildAdapterPosition(child);
                    //Log.v("MyActivity", bucketList.get(adapterPosition).toString());
                    deleteBucketListItem(bucketList.get(adapterPosition));
                }
            }
        });
        rvBucketList.addOnItemTouchListener(this);
        getAllBucketListItems();
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

    private void insertBucketListItem(final BucketListItem bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListItemDao().insert(bucketListItem);
                getAllBucketListItems();
            }
        });
    }

    private void deleteBucketListItem(final BucketListItem bucketListItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListItemDao().delete(bucketListItem);
                getAllBucketListItems();
            }
        });
    }

    private void deleteAllBucketListItems(final List<BucketListItem> bucketListItems) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListItemDao().delete(bucketListItems);
                getAllBucketListItems();
            }
        });
    }

    private void getAllBucketListItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<BucketListItem> bucketListItems = db.bucketListItemDao().getAllBucketListItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(bucketListItems);
                    }
                });
            }
        });
    }

    private void updateUI(List<BucketListItem> bucketListItems) {
        bucketList.clear();
        bucketList.addAll(bucketListItems);
        bucketListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE) {
            if(resultCode == RESULT_OK) {
                BucketListItem newlyCreatedBLItem = data.getParcelableExtra(MainActivity.EXTRA_BUCKETLIST);
                insertBucketListItem(newlyCreatedBLItem);
                updateUI(bucketList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_items) {
            deleteAllBucketListItems(bucketList);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
