package me.yugy.cnbeta.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.yugy.app.common.network.BaseResponse;
import me.yugy.app.common.utils.ValidateUtils;
import me.yugy.cnbeta.model.News;

public class NewsListResponse extends BaseResponse {

    @Expose @SerializedName("result") public ArrayList<News> newsList;

    @Override
    public boolean checkValidate() {
        return ValidateUtils.check(newsList);
    }

}
