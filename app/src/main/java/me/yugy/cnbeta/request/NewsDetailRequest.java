package me.yugy.cnbeta.request;

import android.support.annotation.Nullable;

import com.android.volley.Response;

import me.yugy.app.common.network.GsonRequest;
import me.yugy.cnbeta.ApiConfig;
import me.yugy.cnbeta.response.NewsDetailResponse;

public class NewsDetailRequest extends GsonRequest<NewsDetailResponse> {
    public NewsDetailRequest(int sid, @Nullable Response.Listener<NewsDetailResponse> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, ApiConfig.getNewsDetail(sid), null, listener, errorListener);
    }
}
