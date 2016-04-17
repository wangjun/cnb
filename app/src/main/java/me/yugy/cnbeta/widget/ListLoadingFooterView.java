package me.yugy.cnbeta.widget;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import me.yugy.cnbeta.R;

public class ListLoadingFooterView extends FrameLayout {
    public ListLoadingFooterView(Context context) {
        this(context, null);
    }

    public ListLoadingFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListLoadingFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int padding = getResources().getDimensionPixelOffset(R.dimen.loading_footer_padding);
        setPadding(padding, padding, padding, padding);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminateDrawable(new CircularProgressDrawable.Builder(context)
                .color(ResourcesCompat.getColor(getResources(), R.color.colorAccent, getContext().getTheme()))
                .build());
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(progressBar, lp);
    }
}
