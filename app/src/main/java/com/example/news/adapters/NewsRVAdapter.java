package com.example.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Articles;
import com.example.news.activity.NewsDetailActivity;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    //making recyclerview adapter so that we can pass in the contents in it.
    private ArrayList<Articles> articlesArrayList;
    //arraylist to hold the articles
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        //inflating the xml file to view object and passing it to the viewHolder method
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRVAdapter.ViewHolder holder, int position) {
        //specifying the textViews and imageViews
        holder.titleTextView.setText(articlesArrayList.get(position).getTitle());
        holder.subTitleTextView.setText(articlesArrayList.get(position).getDescription());
        Picasso.get().load(articlesArrayList.get(position).getUrlToImage()).into(holder.newsImageView);

        //basically we are adding on click listener in the CardView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                // we pass in the required data through intent and go to the next activity.
                intent.putExtra("title",articlesArrayList.get(position).getTitle());
                intent.putExtra("description",articlesArrayList.get(position).getDescription());
                intent.putExtra("imageURL",articlesArrayList.get(position).getUrlToImage());
                intent.putExtra("content",articlesArrayList.get(position).getContent());
                intent.putExtra("url",articlesArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //just getting the size of the arraylist
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView,subTitleTextView;
        private ImageView newsImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.newsHeading);
            subTitleTextView = itemView.findViewById(R.id.newsSubHeading);
            newsImageView = itemView.findViewById(R.id.newsImage);
        }
    }
}
