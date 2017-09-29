package com.ayhalo.gankmeizi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ayhalo.gankmeizi.DetilsActivity;
import com.ayhalo.gankmeizi.R;
import com.ayhalo.gankmeizi.adapter.ItemDecoration;
import com.ayhalo.gankmeizi.adapter.comAdapter;
import com.ayhalo.gankmeizi.bean.Api;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * GankMeiZi
 * Created by Halo on 2017/7/10.
 */

public class comFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String baseUrl = "http://gank.io/api/data/";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private comAdapter adapter;
    private RequestQueue mQueue;
    private static int page = 1;
    private String type = "";
    private List<Api.ResultsBean> resutls = new ArrayList<>();
    //控件是否已经初始化
    private boolean isCreateView = false;
    //是否已经加载过数据
    private boolean isLoadData = false;

    public comFragment(String type) {
        super();
        this.type = type;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new ItemDecoration(getContext(),ItemDecoration.VERTICAL_LIST));
        adapter = new comAdapter(getContext(), R.layout.item_list, resutls);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiableItem = layoutManager.findLastVisibleItemPosition();
                if (lastVisiableItem + 1 == adapter.getItemCount()) {
                    // TODO: 2017/7/7
                    page++;
                    downLoadImg(page);
                }
            }
        });
        adapter.setOnItemClickListener(new comAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),resutls.get(position).getUrl(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetilsActivity.class);
                intent.putExtra("url",resutls.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
                baseUrl + type + "/10/" + page, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(type, "onResponse: " + jsonObject.toString());
                        Api api = new Gson().fromJson(jsonObject.toString(), Api.class);
                        resutls.addAll(api.getResults());
                        adapter.refresh(resutls);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(type, "onErrorResponse: " + volleyError.toString());
            }
        });
        mQueue.add(jsonRequest);
    }

    @Override
    public void onRefresh() {
        downLoadImg(1);
        swipeRefreshLayout.setRefreshing(false);
    }

}
