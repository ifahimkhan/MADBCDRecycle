package com.fahim.madbcdrecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VideoItem> videoItems;

    public VideoAdapter(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoItem videoItem = videoItems.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(videoItem.getTitle());
        viewHolder.channel.setText(videoItem.getChannel_title());
        Glide.with(((ViewHolder) holder).imageView.getContext()).load(videoItem.getThumbnail_url()).into(((ViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title, channel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_thumbnail);
            title = itemView.findViewById(R.id.text_title);
            channel = itemView.findViewById(R.id.text_channel);
        }


    }

}