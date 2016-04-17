package me.yugy.cnbeta.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import butterknife.Bind;
import me.yugy.app.common.core.BaseFragment;
import me.yugy.app.common.utils.DebugUtils;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.adapter.NewsListAdapter;
import me.yugy.cnbeta.database.NewsDatabase;
import me.yugy.cnbeta.listener.OnNewsListActionListener;
import me.yugy.cnbeta.model.News;
import me.yugy.cnbeta.request.NewsListRequest;
import me.yugy.cnbeta.response.NewsListResponse;
import me.yugy.cnbeta.widget.ListLoadingFooterView;

public class NewsListFragment extends BaseFragment {

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Bind(R.id.list)
    ListView mList;
    private ListLoadingFooterView mLoadingFooterView;

    private NewsListAdapter mAdapter;
    @Nullable
    private OnNewsListActionListener mNewsActionListener;
    private boolean mIsLoading = false;
    private boolean mHasMore = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingFooterView = new ListLoadingFooterView(getActivity());
        mList.addFooterView(mLoadingFooterView, null, false);
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == mAdapter.getCount() && !mIsLoading && mHasMore) {
                        loadNextPage();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mNewsActionListener != null) {
                    mNewsActionListener.onShowNewsDetail(mAdapter.getItem(position));
                }
            }
        });

        if (NewsDatabase.getInstance().getNewsCount() == 0) {
            getData();
        } else {
            showLoadData();
        }
    }

    private void showLoadData() {
        ArrayList<News> newsList = NewsDatabase.getInstance().getNewsList();
        mAdapter = new NewsListAdapter(newsList);
        mList.setAdapter(mAdapter);
    }

    private void getData() {
        mIsLoading = true;
        addRequest(new NewsListRequest(-1, -1, new Response.Listener<NewsListResponse>() {
            @Override
            public void onResponse(NewsListResponse response) {
                if (response.checkValidate()) {
                    if (response.newsList.size() < 20) {
                        mHasMore = false;
                    }
                    NewsDatabase.getInstance().saveNewsList(response.newsList);
                    mAdapter = new NewsListAdapter(response.newsList);
                    mList.setAdapter(mAdapter);

                } else {
                    // TODO: 4/17/16
                    Toast.makeText(getActivity(), "failed...", Toast.LENGTH_SHORT).show();
                }
                mIsLoading = false;
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mIsLoading = false;
                        // TODO: 4/17/16
                        Toast.makeText(getActivity(), "failed...", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void loadNextPage() {
        DebugUtils.log("loadNextPage");
        mIsLoading = true;
        addRequest(new NewsListRequest(-1, mAdapter.getItem(mAdapter.getCount() - 1).sid,
                new Response.Listener<NewsListResponse>() {
                    @Override
                    public void onResponse(NewsListResponse response) {
                        if (response.checkValidate()) {
                            if (response.newsList.size() < 20) {
                                mHasMore = false;
                            }
                            NewsDatabase.getInstance().saveNewsList(response.newsList);
                            mAdapter.append(response.newsList);
                        } else {
                            // TODO: 4/17/16
                            Toast.makeText(getActivity(), "failed...", Toast.LENGTH_SHORT).show();
                        }
                        mIsLoading = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mIsLoading = false;
                        // TODO: 4/17/16
                        Toast.makeText(getActivity(), "failed...", Toast.LENGTH_SHORT).show();
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
