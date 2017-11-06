package com.ayhalo.gankmeizi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayhalo.gankmeizi.R;
import com.ayhalo.gankmeizi.bean.MeiZi;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * GankMeiZi
 * Created by Halo on 2017/7/4.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgHolder> {

    private static final String TAG = "ImgAdapter";
    private LayoutInflater inflater;
    private int resourceID;
    private Context context;
    private List<MeiZi> img;

    public ImgAdapter(Context context, int resourceID,List<MeiZi> images) {
        this.context = context;
        this.img = images;
        this.resourceID = resourceID;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ImgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImgHolder holder = new ImgHolder(inflater.inflate(resourceID, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImgHolder holder, int position) {
        if (holder != null){
            MeiZi resultsBean = img.get(position);
            Glide.with(context)
                    .load(resultsBean.getUrl())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.imageView);
        }
    }


    public void refresh(List<MeiZi> list) {
        this.img = list;
    }

    @Override
    public int getItemCount() {
        if (img == null) {
            return 0;
        } else {
            return img.size();
        }
    }

    public static class ImgHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImgHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_mz);
        }
    }
}

