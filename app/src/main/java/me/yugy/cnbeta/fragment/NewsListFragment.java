package me.yugy.cnbeta.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.Bind;
import me.yugy.app.common.core.BaseFragment;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.adapter.NewsListAdapter;
import me.yugy.cnbeta.database.NewsDatabase;
import me.yugy.cnbeta.listener.OnNewsListActionListener;
import me.yugy.cnbeta.request.NewsListRequest;
import me.yugy.cnbeta.response.NewsListResponse;

public class NewsListFragment extends BaseFragment {

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Bind(R.id.list) ListView mList;

    private NewsListAdapter mAdapter;
    @Nullable private OnNewsListActionListener mNewsActionListener;

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
                    NewsDatabase.getInstance().saveNewsList(response.newsList);
                    mAdapter = new NewsListAdapter(response.newsList);
                    mList.setAdapter(mAdapter);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (mNewsActionListener != null) {
                                mNewsActionListener.onShowNewsDetail(mAdapter.getItem(position));
                            }
                        }
                    });
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mNewsActionListener = (OnNewsListActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " should implement " +
                    OnNewsListActionListener.class.getCanonicalName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNewsActionListener = null;
    }
}
