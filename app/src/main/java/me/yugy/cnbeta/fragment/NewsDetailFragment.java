package me.yugy.cnbeta.fragment;

import android.os.Build;
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
import me.yugy.app.common.utils.VersionUtils;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);
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
                        if (mWebView != null) {
                            String content =
                                    "<html>\n" +
                                        "<head>\n" +
                                            "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"./style.css\" />\n" +
                                            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />\n" +
                                        "</head>\n" +
                                        "<body>\n" +
                                            response.newsDetail.bodyText + "\n" +
                                        "</body>\n" +
                                    "</html>";
                            mWebView.loadDataWithBaseURL("file:///android_asset/template/detail.html",
                                    content, "text/html; charset=utf-8", "UTF-8", null);
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
