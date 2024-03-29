package me.yugy.cnbeta.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.yugy.app.common.network.Validator;
import me.yugy.app.common.utils.DebugUtils;

/**
 * Created by yugy on 4/15/16.
 */
public class News implements Validator.Validate, Parcelable {

    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

    @Expose @SerializedName("comments") public int commentCount;
    @Expose @SerializedName("counter") public int viewCount;
    @Expose @SerializedName("pubtime") public String publishTime;
    @Expose @SerializedName("sid") public int sid;
    @Expose @SerializedName("thumb") public String thumb;
    @Expose @SerializedName("title") public String title;
    @Expose @SerializedName("topic") public int topicId;
    @Expose @SerializedName("topic_logo") public String topicLogo;

    //field below won't be returned from server side.
    @Expose @SerializedName("readed") public boolean readed = false;
    @Expose @SerializedName("timestamp") public long timestamp;

    @Override
    public boolean checkValidate() {
        if (commentCount < 0) {
            commentCount = 0;
        }
        if (viewCount < 0) {
            viewCount = 0;
        }
        if (TextUtils.isEmpty(publishTime)) {
            DebugUtils.log("publishTime is empty.");
            return false;
        } else {
            try {
                timestamp = sDateFormat.parse(publishTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.commentCount);
        dest.writeInt(this.viewCount);
        dest.writeString(this.publishTime);
        dest.writeInt(this.sid);
        dest.writeString(this.thumb);
        dest.writeString(this.title);
        dest.writeInt(this.topicId);
        dest.writeString(this.topicLogo);
        dest.writeByte(readed ? (byte) 1 : (byte) 0);
        dest.writeLong(this.timestamp);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.commentCount = in.readInt();
        this.viewCount = in.readInt();
        this.publishTime = in.readString();
        this.sid = in.readInt();
        this.thumb = in.readString();
        this.title = in.readString();
        this.topicId = in.readInt();
        this.topicLogo = in.readString();
        this.readed = in.readByte() != 0;
        this.timestamp = in.readLong();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (o instanceof News) {
            News news = (News) o;
            return news.sid == sid;
        }
        return false;
    }
}
