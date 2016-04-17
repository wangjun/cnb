package me.yugy.cnbeta.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yugy.app.common.core.BaseActivity;
import me.yugy.app.common.utils.ViewPagerUtils;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.fragment.NewsDetailFragment;
import me.yugy.cnbeta.fragment.NewsListFragment;
import me.yugy.cnbeta.listener.OnNewsListActionListener;
import me.yugy.cnbeta.listener.OnPagerSelectListener;
import me.yugy.cnbeta.model.News;
import me.yugy.cnbeta.widget.NewsViewPager;

public class MainActivity extends BaseActivity implements OnNewsListActionListener{

    @Bind(R.id.pager) NewsViewPager mPager;

    private MainFragmentAdapter mFragmentAdapter;
    private News mCurrentNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("资讯");
        }

        mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(mFragmentAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Fragment fragment = ViewPagerUtils.getFragmentFromPager(
                            getSupportFragmentManager(), mPager, mPager.getCurrentItem());
                    if (fragment != null && fragment instanceof OnPagerSelectListener) {
                        ((OnPagerSelectListener) fragment).onIdleSelected();
                    }
                }
            }
        });
        UpdateManager.register(this);
    }

    @Override
    public void onShowNewsDetail(News news) {
        mCurrentNews = news;
        mFragmentAdapter.setShowDetail();
        setPagerCurrentItem(1);
    }

    private void setPagerCurrentItem(final int index) {
        mPager.setScrollDurationFactor(2.0f);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && mPager.getCurrentItem() == index) {
                    mPager.setScrollDurationFactor(1.0f);
                    mPager.removeOnPageChangeListener(this);
                }
                super.onPageScrollStateChanged(state);
            }
        });
        mPager.post(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(index);
            }
        });
    }

    @Override
    public void onShowNewsComment(int sid) {
        mFragmentAdapter.setShowComment();
        mPager.post(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(2);
            }
        });
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter {

        private boolean mHaveShowDetail = false;
        private boolean mHaveShowComment = false;

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setShowDetail() {
            if (!mHaveShowDetail || mHaveShowComment) {
                mHaveShowDetail = true;
                mHaveShowComment = false;
                notifyDataSetChanged();
            }
            Fragment fragment = ViewPagerUtils.getFragmentFromPager(
                    getSupportFragmentManager(), mPager, 1);
            if (fragment != null && fragment instanceof NewsDetailFragment) {
                ((NewsDetailFragment) fragment).setNews(mCurrentNews);
            }
        }

        public void setShowComment() {
            mHaveShowDetail = mHaveShowComment = true;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return NewsListFragment.newInstance();
                case 1: return NewsDetailFragment.newInstance(mCurrentNews);
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

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 1) {
            setPagerCurrentItem(0);
        } else if (mPager.getCurrentItem() == 2) {
            setPagerCurrentItem(1);
        } else {
            super.onBackPressed();
        }
    }
}


