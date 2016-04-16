package me.yugy.cnbeta.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class NewsViewPager extends ViewPager {

    private DurationScroller mScroller;

    public NewsViewPager(Context context) {
        this(context, null);
    }

    public NewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new DurationScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOverScrollMode(OVER_SCROLL_NEVER);
        setPageTransformer(false, new DepthPageTransformer());
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }

    class DurationScroller extends Scroller {

        private double mScrollFactor = 1;

        public DurationScroller(Context context) {
            super(context);
        }

        public DurationScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public DurationScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        /**
         * Set the factor by which the duration will change
         */
        public void setScrollDurationFactor(double scrollFactor) {
            mScrollFactor = scrollFactor;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
        }

    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position >= -1) {
                if (position <= 0) {
                    view.setTranslationX(pageWidth * -position);
                } else if (position <= 1) {
                    view.setTranslationX(0);
                }
            }
        }
    }

}

