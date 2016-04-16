package me.yugy.cnbeta.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yugy.app.common.core.BaseActivity;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.fragment.NewsListFragment;
import me.yugy.cnbeta.listener.OnNewsListActionListener;
import me.yugy.cnbeta.model.News;
import me.yugy.cnbeta.widget.NewsViewPager;

public class MainActivity extends BaseActivity implements OnNewsListActionListener{

    @Bind(R.id.pager)
    NewsViewPager mPager;
    private MainFragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("资讯");
        }

        mPager.setScrollDurationFactor(2.0d);

        mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mFragmentAdapter);
        UpdateManager.register(this);
    }

    @Override
    public void onShowNewsDetail(News news) {
        mFragmentAdapter.setShowDetail();
        mPager.post(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public void onShowNewsComment(int sid) {
        mFragmentAdapter.setShowComment();
        mPager.post(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(3);
            }
        });
    }

    private static class MainFragmentAdapter extends FragmentPagerAdapter {

        private boolean mHaveShowDetail = false;
        private boolean mHaveShowComment = false;

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setShowDetail() {
            mHaveShowDetail = true;
            mHaveShowComment = false;
            notifyDataSetChanged();
        }

        public void setShowComment() {
            mHaveShowDetail = mHaveShowComment = true;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return NewsListFragment.newInstance();
                case 1: return new ListFragment();
                case 2: return new ListFragment();
                default: return null;
            }
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public int getCount() {
            if (!mHaveShowDetail && !mHaveShowComment) {
                return 1;
            } else if (mHaveShowDetail && !mHaveShowComment) {
                return 2;
            } else if (mHaveShowDetail && mHaveShowComment) {
                return 3;
            }
            return 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CrashManager.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UpdateManager.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateManager.unregister();
    }
}
