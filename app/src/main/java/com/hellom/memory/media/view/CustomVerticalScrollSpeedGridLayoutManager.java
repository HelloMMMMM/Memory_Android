package com.hellom.memory.media.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class CustomVerticalScrollSpeedGridLayoutManager extends GridLayoutManager {

    private double scrollSpeedRatio = 0.6d;

    public void setScrollSpeedRatio(double scrollSpeedRatio) {
        this.scrollSpeedRatio = scrollSpeedRatio;
    }

    public CustomVerticalScrollSpeedGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomVerticalScrollSpeedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomVerticalScrollSpeedGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int tempDy = super.scrollVerticallyBy((int) (scrollSpeedRatio * dy), recycler, state);
        if (tempDy == (int) (scrollSpeedRatio * dy)) {
            return dy;
        }
        return tempDy;
    }
}
