package com.example.FootBallUp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<NewsData> localDataSet;
    private static View.OnClickListener onClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_title;
        public TextView TextView_content;
        public SimpleDraweeView ImageView_title;
        public View rootView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            TextView_title = view.findViewById(R.id.TextView_title);
            TextView_content = view.findViewById(R.id.TextView_content);
            ImageView_title = view.findViewById(R.id.ImageView_title);
            rootView = view;

            view.setClickable(true);
            view.setEnabled(true);
            view.setOnClickListener(onClickListener);
        }

    }

    public MyAdapter(ArrayList<NewsData> dataSet, Context context, View.OnClickListener onClick) {

        localDataSet = dataSet;
        onClickListener = onClick;
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        LinearLayout view = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_news, viewGroup, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        NewsData news = localDataSet.get(position);

        viewHolder.TextView_title.setText(news.getTitle());

        String content = news.getContent();

        if(content != null && content.length() > 0 ){
            viewHolder.TextView_content.setText(content);
        }else{
            viewHolder.TextView_content.setText("-");
        }

        Uri uri = Uri.parse(news.getUrlToImage());
        viewHolder.ImageView_title.setImageURI(uri);

        //tag
        viewHolder.rootView.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        // 삼항 연산자
        return localDataSet == null ? 0 : localDataSet.size();
    }
    public NewsData getNews(int position){
        return localDataSet == null ? null : localDataSet.get(position);
    }
}