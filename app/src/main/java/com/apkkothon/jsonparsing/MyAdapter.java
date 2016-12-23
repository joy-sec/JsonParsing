package com.apkkothon.jsonparsing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 12/23/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    ArrayList<PostModel> postModelList;
    Context context;

    public MyAdapter(Context context, List<PostModel> postModelList) {
        this.context=context;
        this.postModelList = (ArrayList<PostModel>) postModelList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        PostModel current =postModelList.get(position);
        holder.tittle.setText(current.getPost());
        holder.pubDate.setText(current.getExcerpt());
        Picasso.with(context).load(current.getImage()).into(holder.thumb);


    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tittle,pubDate;
        ImageView thumb;
        public MyHolder(View itemView) {
            super(itemView);
            tittle=(TextView)itemView.findViewById(R.id.tittle_text);
            pubDate=(TextView)itemView.findViewById(R.id.date_text);
            thumb=(ImageView)itemView.findViewById(R.id.image_view);


        }
    }


}
