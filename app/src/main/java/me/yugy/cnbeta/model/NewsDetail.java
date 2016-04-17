package me.yugy.cnbeta.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.yugy.app.common.network.Validator;
import me.yugy.app.common.utils.DebugUtils;

public class NewsDetail implements Validator.Validate {

    @Expose @SerializedName("bodytext") public String bodyText;
    @Expose @SerializedName("comments") public int commentCount;
    @Expose @SerializedName("counter") public int viewCount;
    @Expose @SerializedName("sid") public int sid;

    @Override
    public boolean checkValidate() {
        if (TextUtils.isEmpty(bodyText)) {
            DebugUtils.log("bodyText is empty.");
            return false;
        }
        if (commentCount < 0) {
            commentCount = 0;
        }
        if (viewCount < 0) {
            viewCount = 0;
        }
        if (sid < 0) {
            sid = 0;
        }
        return true;
    }
}
