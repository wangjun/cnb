package me.yugy.cnbeta.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yugy.app.common.core.BaseActivity;
import me.yugy.cnbeta.R;
import me.yugy.cnbeta.fragment.NewsListFragment;

public class MainActivity extends BaseActivity {

    @Bind(R.id.pager) ViewPager mPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return NewsListFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }
}
