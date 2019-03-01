package com.example.a06_studentportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PortalAdapter extends RecyclerView.Adapter<PortalAdapter.ViewHolder> {
    private List<PortalObject> mPortalObjects;
    final private PortalClickListener mPortalClickListener;

    public interface PortalClickListener {
        void portalOnClick (int i);
    }

    public PortalAdapter(List<PortalObject> mPortals, PortalClickListener mPortalClickListener) {
        this.mPortalObjects = mPortals;
        this.mPortalClickListener = mPortalClickListener;
    }

    @NonNull
    @Override
    public PortalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        PortalAdapter.ViewHolder viewHolder = new PortalAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PortalAdapter.ViewHolder viewHolder, int i) {
        PortalObject portal = mPortalObjects.get(i);
        viewHolder.textView.setText(portal.getmPortalName());
    }

    @Override
    public int getItemCount() {
        return mPortalObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mPortalClickListener.portalOnClick(clickedPosition);
        }
    }
}
