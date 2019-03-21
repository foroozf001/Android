package com.example.a08_bucketlist_s;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder>{
    private List<BucketListItem> bucketList;
    private BucketListItemListener listener;

    public BucketListAdapter(List<BucketListItem> bucketList, BucketListItemListener listener) {
        this.bucketList = bucketList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.bucket_list_item, viewGroup, false);
        return new BucketListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.ViewHolder viewHolder, int i) {
        BucketListItem item = bucketList.get(i);
        viewHolder.tvTitle.setText(bucketList.get(viewHolder.getAdapterPosition()).getTitle());
        viewHolder.tvDescription.setText(bucketList.get(viewHolder.getAdapterPosition()).getDescription());

        if(bucketList.get(i).getCompleted()) {
            viewHolder.checkBox.setChecked(true);
            viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvDescription.setPaintFlags(viewHolder.tvDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.checkBox.setChecked(false);
            viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvDescription.setPaintFlags(viewHolder.tvDescription.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return bucketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        private CheckBox checkBox;
        private TextView tvTitle;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //textView = itemView.findViewById(android.R.id.text1);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvTitle = itemView.findViewById(R.id.tv_cb_title);
            tvDescription = itemView.findViewById(R.id.tv_cb_description);
        }
    }
}
