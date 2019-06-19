package com.example.a12_rijksmuseumapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.a12_rijksmuseumapp.DetailActivity;
import com.example.a12_rijksmuseumapp.R;
import com.example.a12_rijksmuseumapp.model.Art;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context mContext;
    private List<Art> artList;

    public Adapter(Context mContext, List<Art> artList) {
        this.mContext = mContext;
        this.artList = artList;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.art_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter.MyViewHolder viewHolder, int i) {
        try {
            String url = artList.get(i).getHeaderImageUrl();
            viewHolder.title.setText(artList.get(i).getLongTitle());
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.ic_sync_black_24dp)
                    .into(viewHolder.thumbnail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return artList.size();
    }

    public void swapList(List<Art> newList) {
        artList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Art clickedDataItem = artList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", clickedDataItem.getId());
                        bundle.putString("title", clickedDataItem.getLongTitle());
                        bundle.putString("headerImage", clickedDataItem.getHeaderImageUrl());
                        bundle.putString("webImage", clickedDataItem.getWebImageUrl());
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        //Toast.makeText(v.getContext(), "You have selected " + clickedDataItem.getLongTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
