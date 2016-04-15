package me.yugy.cnbeta.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.yugy.app.common.core.BaseAdapter;
import me.yugy.app.common.core.BaseHolder;
import me.yugy.cnbeta.databinding.ItemNewsListBinding;
import me.yugy.cnbeta.model.News;

public class NewsListAdapter extends BaseAdapter<News, NewsListHolder> {

    public NewsListAdapter(@Nullable ArrayList<News> data) {
        super(data);
    }

    @Override
    public NewsListHolder generateHolder(LayoutInflater inflater, ViewGroup parent, int itemType) {
        ItemNewsListBinding binding = ItemNewsListBinding.inflate(inflater, parent, false);
        return new NewsListHolder(binding);
    }

}
class NewsListHolder extends BaseHolder<News> {

    ItemNewsListBinding binding;

    public NewsListHolder(ItemNewsListBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void parse(News data) {
        binding.setNews(data);
        binding.executePendingBindings();
    }
}
