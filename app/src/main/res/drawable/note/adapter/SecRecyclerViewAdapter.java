package com.anew.note.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anew.note.R;
import com.anew.note.model.SecModel;

import java.util.ArrayList;

/**
 * Created by pig on 2016/11/28.
 */

public class SecRecyclerViewAdapter extends RecyclerView.Adapter<SecRecyclerViewAdapter.SecViewHolder>{
    private Context context;
    private ArrayList<SecModel> list;
    public SecRecyclerViewAdapter(Context context,ArrayList<SecModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public SecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        SecViewHolder  viewHolder = new SecViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SecViewHolder holder, int position) {
        holder.secTitle.setText(list.get(position).getTitle());
        holder.secContent.setText(list.get(position).getContent());
        holder.secDate.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class  SecViewHolder extends RecyclerView.ViewHolder{
        TextView secTitle,secContent,secDate;
        public SecViewHolder(View itemView) {
            super(itemView);
            secTitle = (TextView) itemView.findViewById(R.id.tv_secTitle);
            secContent = (TextView) itemView.findViewById(R.id.tv_secContent);
            secDate = (TextView) itemView.findViewById(R.id.tv_secDate);
        }
    }
}
