package com.ayhalo.gankmeizi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ayhalo.gankmeizi.R;
import com.ayhalo.gankmeizi.adapter.ImgAdapter;
import com.ayhalo.gankmeizi.bean.MeiZi;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * GankMeiZi
 * Created by Halo on 2017/7/7.
 */

public class mzFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "meizi";
    private static final String baseUrl = "http://gank.io/api/data/";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImgAdapter adapter;
    private List<MeiZi.ResultsBean> resutls = new ArrayList<>();
    private RequestQueue mQueue;
    private MeiZi meizi;
    private static int page = 1;
    //控件是否已经初始化
    private boolean isCreateView = false;
    //是否已经加载过数据
    private boolean isLoadData = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        //LinearLayoutManager layoutManager =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //mRecyclerView.setLayoutManager(layoutManager);

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ImgAdapter(getContext(), R.layout.item_image, resutls);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lastVisiableItem = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                Log.d(TAG, "onScrolled: " + lastVisiableItem[lastVisiableItem.length - 1]);
                Log.d(TAG, "getItemCount: " + adapter.getItemCount());
                if (lastVisiableItem[lastVisiableItem.length - 1] + 1 == adapter.getItemCount()) {
                    // TODO: 2017/7/7
                    page++;
                    downLoadImg(page);
                }
            }
        });
        isCreateView = true;
        return view;
    }


    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        //如果没有加载过就加载，否则就不再加载了
        if (!isLoadData) {
            //加载数据操作
            downLoadImg(page);
            isLoadData = true;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint())
            lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void downLoadImg(int page) {
        mQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                baseUrl + "福利/10/" + page, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: " + jsonObject.toString());
                        meizi = new Gson().fromJson(jsonObject.toString(), MeiZi.class);
                        resutls.addAll(meizi.getResults());
                        adapter.refresh(resutls);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "onErrorResponse: " + volleyError.toString());
            }
        });
        Log.d(TAG, "downLoadImg: " + mQueue.toString());
        mQueue.add(jsonRequest);
    }

    @Override
    public void onRefresh() {
        downLoadImg(1);
        swipeRefreshLayout.setRefreshing(false);
    }
}
