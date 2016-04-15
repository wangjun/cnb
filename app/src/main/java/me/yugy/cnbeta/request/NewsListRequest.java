package me.yugy.cnbeta.request;

import com.android.volley.Response;

import me.yugy.app.common.network.GsonRequest;
import me.yugy.cnbeta.ApiConfig;
import me.yugy.cnbeta.response.NewsListResponse;

/**
 * Created by yugy on 4/15/16.
 */
public class NewsListRequest extends GsonRequest<NewsListResponse> {
    public NewsListRequest(int startSid, int endSid, Response.Listener<NewsListResponse> listener,
                           Response.ErrorListener errorListener) {
        super(Method.GET, ApiConfig.getNewsList(startSid, endSid), null, listener, errorListener);
    }
}
