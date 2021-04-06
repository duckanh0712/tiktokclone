package com.example.tiktukapp.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiktukapp.R;
import com.example.tiktukapp.data.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context context;
    List<Video> videoList = new ArrayList<>();

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video,viewGroup,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.author.setText(videoList.get(position).getUploadBy());
        holder.title.setText(videoList.get(position).getTitle());
        holder.likeCount.setText(videoList.get(position).getLike().toString());
        holder.videoUrl.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.videoUrl.setVideoPath(videoList.get(position).getUrl());
        holder.videoUrl.start();

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView author , title, likeCount;
        VideoView videoUrl;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.txtUser);
            title = (TextView) itemView.findViewById(R.id.txtContent);
            likeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            videoUrl = (VideoView) itemView.findViewById(R.id.playVideo);
            //            share = (ImageView) itemView.findViewById(R.id.share_image);
//            like = (ImageView) itemView.findViewById(R.id.like_image);
//            videoUrl.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.setLooping(true);
//                }
//            });
        }

    }
}
