package me.yugy.cnbeta.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.yugy.app.common.network.Validator;
import me.yugy.app.common.utils.DebugUtils;

/**
 * Created by yugy on 4/15/16.
 */
public class News implements Validator.Validate {

    @Expose @SerializedName("comments") public int commentCount;
    @Expose @SerializedName("counter") public int viewCount;
    @Expose @SerializedName("pubtime") public String publicTime;
    @Expose @SerializedName("sid") public int sid;
    @Expose @SerializedName("thumb") public String thumb;
    @Expose @SerializedName("title") public String title;
    @Expose @SerializedName("topic") public int topicId;
    @Expose @SerializedName("topic_logo") public String topicLogo;

    @Override
    public boolean checkValidate() {
        if (commentCount < 0) {
            commentCount = 0;
        }
        if (viewCount < 0) {
            viewCount = 0;
        }
        if (TextUtils.isEmpty(publicTime)) {
            DebugUtils.log("publicTime is empty.");
            return false;
        }
        if (sid < 0) {
            sid = 0;
        }
        if (TextUtils.isEmpty(thumb)) {
            DebugUtils.log("thumb is empty.");
            return false;
        }
        if (TextUtils.isEmpty(title)) {
            DebugUtils.log("title is empty");
            return false;
        }
        if (topicId < 0) {
            topicId = 0;
        }
        if (TextUtils.isEmpty(topicLogo)) {
            DebugUtils.log("topicLogo is empty");
        }
        return true;
    }
}
