package me.yugy.cnbeta.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.Bind;
import me.yugy.app.common.core.BaseFragment;
import me.yugy.app.common.utils.TextUtils;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.listener.OnPagerSelectListener;
import me.yugy.cnbeta.model.News;
import me.yugy.cnbeta.model.NewsDetail;
import me.yugy.cnbeta.request.NewsDetailRequest;
import me.yugy.cnbeta.response.NewsDetailResponse;

public class NewsDetailFragment extends BaseFragment implements OnPagerSelectListener{

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("news", news);
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.webview) WebView mWebView;

    private News mNews;
    @Nullable NewsDetail mNewsDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNews = getArguments().getParcelable("news");
    }

    public void setNews(News news) {
        if (!mNews.equals(news)) {
            mNews = news;
            mNewsDetail = null;
            mWebView.loadUrl("javascript:setEmpty()");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mWebView != null) {
            mWebView.destroy();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/template/detail.html");
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onIdleSelected() {
        if (mNewsDetail == null) {
            addRequest(new NewsDetailRequest(mNews.sid, new Response.Listener<NewsDetailResponse>() {
                @Override
                public void onResponse(NewsDetailResponse response) {
                    if (response.checkValidate()) {
                        mNewsDetail = response.newsDetail;
                        mNews.commentCount = mNewsDetail.commentCount;
                        mNews.viewCount = mNewsDetail.viewCount;
                        if (mWebView != null) {
                            mWebView.loadUrl("javascript:setTitle('" + mNews.title + "')");
                            CharSequence time = TextUtils.getRelativeTimeDisplayString(
                                    getContext(), mNews.timestamp);
                            mWebView.loadUrl("javascript:setTime('" + time + "')");
                            mWebView.loadUrl("javascript:setFrom('test')");
                            mWebView.loadUrl("javascript:setContent('" + mNewsDetail.bodyText + "')");
                            mWebView.loadUrl("javascript:setCommentCount('" + mNews.commentCount + "条评论')");
                        }
                    } else {
                        // TODO: 4/17/16
                        Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //// TODO: 4/17/16
                    Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
