package me.yugy.cnbeta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.Bind;
import me.yugy.app.common.core.BaseFragment;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.adapter.NewsListAdapter;
import me.yugy.cnbeta.request.NewsListRequest;
import me.yugy.cnbeta.response.NewsListResponse;

public class NewsListFragment extends BaseFragment {

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Bind(R.id.list) ListView mList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        addRequest(new NewsListRequest(-1, -1, new Response.Listener<NewsListResponse>() {
            @Override
            public void onResponse(NewsListResponse response) {
                if (response.checkValidate()) {
                    mList.setAdapter(new NewsListAdapter(response.newsList));
                } else {
                    Toast.makeText(getActivity(), "failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }
}
