package com.example.a09_gamebacklog_mv;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int ADD_REQUESTCODE = 1337;
    public static final int UPDATE_REQUESTCODE = 26;
    public static final String EXTRA_BACKLOG_ITEM = "Backlog_object";

    private MainViewModel mainViewModel;
    private List<Game> games;

    private GameAdapter gameAdapter;

    private RecyclerView recyclerView;

    private GestureDetector gestureDetector;

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        games = new ArrayList<>();
        mainViewModel.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> backlogItems) {
                Log.d(TAG, "MainViewModel observer triggered");
                games = backlogItems;
                for (Game item : backlogItems) {
                    Log.d(TAG, "#" + item.getId().toString() + " | Title: " + item.getTitle());
                }
                updateUI();
            }
        });

        gameAdapter = new GameAdapter(games);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        recyclerView.addOnItemTouchListener(this);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int adapterPosition = viewHolder.getAdapterPosition();

                final Game item = games.get(adapterPosition);
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content),
                        getString(R.string.deleted) + " " + item.getTitle(),
                        Snackbar.LENGTH_LONG).setAction(R.string.undo,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mainViewModel.insert(item);
                            }
                        }).show();

                mainViewModel.delete(games.get(adapterPosition));
            }
        };

        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);

                startActivityForResult(intent, ADD_REQUESTCODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_item) {
            // delete items
            deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(data != null) {
                Game item = data.getParcelableExtra(EXTRA_BACKLOG_ITEM);
                if(item == null) {
                    return;
                }

                if(requestCode == ADD_REQUESTCODE) {
                    mainViewModel.insert(item);
                } else if (requestCode ==  UPDATE_REQUESTCODE) {
                    mainViewModel.update(item);
                }
            }
        }
    }

    private void updateUI() {
        gameAdapter.swapList(games);
    }

    private void deleteAll() {
        final List<Game> deletedItems = games;

        Snackbar.make(MainActivity.this.findViewById(android.R.id.content), R.string.all_deleted,
                Snackbar.LENGTH_LONG).setAction(R.string.undo,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainViewModel.insert(deletedItems);
                    }
                }).show();

        mainViewModel.delete(games);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int itemLocation = recyclerView.getChildAdapterPosition(child);

        if(child != null && gestureDetector.onTouchEvent(motionEvent)) {
            Game item = games.get(itemLocation);

            // Go to edit activity
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra(EXTRA_BACKLOG_ITEM, item);
            startActivityForResult(intent, UPDATE_REQUESTCODE);
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
