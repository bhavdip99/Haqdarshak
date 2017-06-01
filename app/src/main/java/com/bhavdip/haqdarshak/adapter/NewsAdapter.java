package com.bhavdip.haqdarshak.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhavdip.haqdarshak.R;
import com.bhavdip.haqdarshak.WebViewActivity;
import com.bhavdip.haqdarshak.model.News;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by bhavdip on 29/5/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newses;
    private int rowLayout;
    private Context context;

    public NewsAdapter(List<News> newses, int rowLayout, Context context) {
        this.newses = newses;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, final int position) {
        holder.newsTitle.setText(newses.get(position).getTitle());
        holder.data.setText(newses.get(position).getPublishedAt());
        holder.newsDescription.setText(newses.get(position).getDescription());
        holder.rating.setText(newses.get(position).getAuthor());

        holder.img_News.setVisibility(View.VISIBLE);

        Uri imageUri = Uri.parse(newses.get(position).getUrlToImage());
        String Imageuri = String.valueOf(imageUri);
        String ImgExt = Imageuri.substring(Imageuri.lastIndexOf("."));
        holder.img_News.setAspectRatio(1);

        if (ImgExt.contains(".gif")) {

            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setControllerListener(listener)
                    .setUri(imageUri)
                    .setAutoPlayAnimations(true)
                    .build();
            holder.img_News.setController(controller);
        } else {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setControllerListener(listener)
                    .setUri(imageUri)
                    .setAutoPlayAnimations(false)
                    .build();
            holder.img_News.setController(controller);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, newses.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView newsesLayout;
        TextView newsTitle;
        TextView data;
        TextView newsDescription;
        TextView rating;
        SimpleDraweeView img_News;


        public NewsViewHolder(View v) {
            super(v);
            newsesLayout = (CardView) v.findViewById(R.id.news_layout);
            newsTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            newsDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            img_News = (SimpleDraweeView) v.findViewById(R.id.img_News);
        }
    }
}
