package me.yugy.cnbeta.listener;

import me.yugy.cnbeta.model.News;

public interface OnNewsListActionListener {

    void onShowNewsDetail(News news);

    void onShowNewsComment(int sid);

}
