package com.example.a09_gamebacklog;

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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener{
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> games = new ArrayList<>();
    private GameRoomDatabase db;

    public static final String EXTRA_GAMEBACKLOG = "GameBacklog";
    public static final String EXTRA_POSITION = "Position";
    public static final String EXTRA_GAME = "Game";
    public static final int REQUESTCODE_CREATE = 1234;
    public static final int REQUESTCODE_UPDATE = 4321;

    private GestureDetector gestureDetector;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = GameRoomDatabase.getDatabase(this);

        initToolbar();
        initRecyclerView();
        initFAB();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Game Backlog");
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        gameAdapter = new GameAdapter(games);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(gameAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("EXTRA_POSITION", adapterPosition);
                    extras.putParcelable("EXTRA_GAME", games.get(adapterPosition));
                    intent.putExtras(extras);
                    startActivityForResult(intent, REQUESTCODE_UPDATE);
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    deleteGame(games.get(adapterPosition));
                }
            }
        });
        recyclerView.addOnItemTouchListener(this);
        getAllGames();
    }

    private void insertGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().insert(game);
                getAllGames();
            }
        });
    }
    private void deleteGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().delete(game);
                getAllGames();
            }
        });
    }

    private void deleteAllGames(final List<Game> games) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().delete(games);
                getAllGames();
            }
        });
    }

    private void updateGame(final Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gameDao().update(game);
                getAllGames();
            }
        });
    }

    private void getAllGames() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<Game> gameList = db.gameDao().getAllGames();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(gameList);
                    }
                });
            }
        });
    }

    private void updateUI(List<Game> gameList) {
        games.clear();
        games.addAll(gameList);
        gameAdapter.notifyDataSetChanged();
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("EXTRA_POSITION", -1);
                extras.putParcelable("EXTRA_GAME", null);
                intent.putExtras(extras);
                startActivityForResult(intent, REQUESTCODE_CREATE);
            }
        });
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
            deleteAllGames(games);
            Snackbar.make(findViewById(android.R.id.content), "Deleted all games", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE_CREATE) {
            if(resultCode == RESULT_OK) {
                Game newGame = data.getParcelableExtra(MainActivity.EXTRA_GAMEBACKLOG);
                insertGame(newGame);
                updateUI(games);
                Snackbar.make(findViewById(android.R.id.content), "New game added successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else if (requestCode == REQUESTCODE_UPDATE) {
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Game updatedGame = extras.getParcelable("EXTRA_GAME");
                int adapterPosition = extras.getInt("EXTRA_POSITION");
                games.get(adapterPosition).setTitle(updatedGame.getTitle());
                games.get(adapterPosition).setStatus(updatedGame.getStatus());
                games.get(adapterPosition).setPlatform(updatedGame.getPlatform());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                games.get(adapterPosition).setDate(sdf.format(new Date()));
                updateGame(games.get(adapterPosition));
                updateUI(games);
                Snackbar.make(findViewById(android.R.id.content), "Game updated successfully", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
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
