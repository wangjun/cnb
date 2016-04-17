package me.yugy.cnbeta.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.yugy.app.common.network.BaseResponse;
import me.yugy.app.common.utils.ValidateUtils;
import me.yugy.cnbeta.model.NewsDetail;

public class NewsDetailResponse extends BaseResponse {

    @Expose @SerializedName("result") public NewsDetail newsDetail;

    @Override
    public boolean checkValidate() {
        return ValidateUtils.check(newsDetail);
    }
}
