package com.ayhalo.gankmeizi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayhalo.gankmeizi.R;
import com.ayhalo.gankmeizi.bean.Api;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * GankMeiZi
 * Created by Halo on 2017/7/10.
 */

public class comAdapter extends RecyclerView.Adapter<comAdapter.comHolder> {

    private static final String TAG = "comAdapter";
    private LayoutInflater inflater;
    private int resourceID;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<Api.ResultsBean> resultsBeen;

    public comAdapter(Context context, int resourceID, List<Api.ResultsBean> resultsBeen) {
        this.context = context;
        this.resourceID = resourceID;
        this.inflater = LayoutInflater.from(context);
        this.resultsBeen = resultsBeen;
    }

    @Override
    public comHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        comHolder comholder = new comHolder(inflater.inflate(resourceID, parent, false));
        return comholder;
    }

    @Override
    public void onBindViewHolder(final comHolder holder, final int position) {
        final Api.ResultsBean resultsBean = resultsBeen.get(position);
        holder.image.setVisibility(View.GONE);
        if (resultsBean.getImages() != null && resultsBean.getImages().size() > 0) {
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(resultsBean.getImages().get(0))
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.image);
        }
        holder.text.setText(resultsBean.getDesc());
        holder.author.setText(resultsBean.getWho()+'.'+resultsBean.getPublishedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
    }

    public void refresh(List<Api.ResultsBean> list) {
        this.resultsBeen = list;
    }

    @Override
    public int getItemCount() {
        if (resultsBeen == null) {
            return 0;
        } else {
            return resultsBeen.size();
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public static class comHolder extends RecyclerView.ViewHolder {

        TextView text;
        TextView author;
        ImageView image;

        public comHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text_title);
            author = (TextView) itemView.findViewById(R.id.author);
            image = (ImageView) itemView.findViewById(R.id.image_title);
        }
    }
}
