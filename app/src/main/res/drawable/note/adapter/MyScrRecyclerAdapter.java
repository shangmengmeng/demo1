package com.anew.note.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MyScrRecyclerAdapter extends RecyclerView.Adapter<MyScrRecyclerAdapter.MyViewHolder> {
    private Context context;
    private MyScrRecyclerAdapter.MyViewHolder holder;
    private ArrayList<SecModel> mlist;

    public MyScrRecyclerAdapter(Context context, ArrayList<SecModel> mlist) {
        this.context = context;
        this.mlist = mlist;
    }
    public interface OnItemClickListioner{
        void OnItemClickListioner(View v,int position);
        void OnItemcLongClickListioner(View v ,int position);
    }
    private OnItemClickListioner listioner;
    public void OnItemClickListiner(OnItemClickListioner listioner){
        this.listioner = listioner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        holder = new MyScrRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mlist.get(position).getTitle().length()>5){
            String a =mlist.get(position).getTitle().substring(0,5);
            holder.text_title.setText(a+context.getResources().getString(R.string.text_shenglvehao));
        }else {
            String a =mlist.get(position).getTitle();
            holder.text_title.setText(a);
        }
       if (mlist.get(position).getContent().length()>37){
           String b =mlist.get(position).getContent().substring(0,37);
           holder.text_content.setText(b+context.getResources().getString(R.string.text_shenglvehao));
       }
        else {
           String b =mlist.get(position).getContent();
           holder.text_content.setText(b);
       }
        String c = mlist.get(position).getDate();
        holder.text_date.setText(c);
        if (listioner!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listioner.OnItemClickListioner(holder.itemView,holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listioner.OnItemcLongClickListioner(holder.itemView,holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView text_title,text_content,text_date;
        public MyViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_title_item);
            text_content = (TextView) itemView.findViewById(R.id.text_content_item);
            text_date = (TextView) itemView.findViewById(R.id.text_date_item);
        }
    }
}
