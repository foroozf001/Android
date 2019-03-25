package com.example.a09_gamebacklog_mv;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<Game> games;

    public GameAdapter(List<Game> listGames) { this.games = listGames; }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_layout, viewGroup, false);
        GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Game item = games.get(i);

        // Update UI
        viewHolder.updateUI(item);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void swapList (List<Game> newList) {
        games = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView platform;
        TextView date;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextview);
            platform = itemView.findViewById(R.id.platformTextview);
            date = itemView.findViewById(R.id.dateTextview);
            status = itemView.findViewById(R.id.statusTextview);
        }

        public void updateUI(Game item) {
            title.setText(item.getTitle());
            platform.setText(item.getPlatform());
            status.setText(item.getStatus().toString());

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(item.getLastUpdated());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY", new Locale("NL_nl"));
            date.setText(dateFormat.format(c.getTime()));
        }
    }
}

