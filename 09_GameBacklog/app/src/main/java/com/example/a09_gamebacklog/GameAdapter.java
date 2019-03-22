package com.example.a09_gamebacklog;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<Game> games;

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.game_card_layout, viewGroup, false);
        return new GameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Game game = games.get(i);
        viewHolder.tvTitle.setText(games.get(viewHolder.getAdapterPosition()).getTitle());
        viewHolder.tvPlatform.setText(games.get(viewHolder.getAdapterPosition()).getPlatform());
        viewHolder.tvStatus.setText(games.get(viewHolder.getAdapterPosition()).getStatus());
        viewHolder.tvDate.setText(games.get(viewHolder.getAdapterPosition()).getDate());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvPlatform;
        private TextView tvStatus;
        private TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textViewGameTitle);
            tvPlatform = itemView.findViewById(R.id.textViewGamePlatform);
            tvStatus = itemView.findViewById(R.id.textViewGameStatus);
            tvDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
