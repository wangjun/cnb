package me.yugy.cnbeta.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import me.yugy.app.common.core.BaseAdapter;
import me.yugy.app.common.core.BaseHolder;
import me.yugy.app.common.widget.RelativeTimeTextView;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.model.News;

public class NewsListAdapter extends BaseAdapter<News, NewsListHolder> {

    public NewsListAdapter(@Nullable ArrayList<News> data) {
        super(data);
    }

    @Override
    public NewsListHolder generateHolder(LayoutInflater inflater, ViewGroup parent, int itemType) {
        View view = inflater.inflate(R.layout.item_news_list, parent, false);
        return new NewsListHolder(view);
    }

}
class NewsListHolder extends BaseHolder<News> {

    @Bind(R.id.image) ImageView image;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.time) RelativeTimeTextView time;

    public NewsListHolder(View view) {
        super(view);
    }

    @Override
    public void parse(News data) {
        title.setText(data.title);
        time.setReferenceTime(data.timestamp);
        ImageLoader.getInstance().displayImage(data.thumb, image);
    }
}
