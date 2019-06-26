package com.hellom.memory.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class IntelligentLoadRecyclerView extends RecyclerView {

    public IntelligentLoadRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public IntelligentLoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntelligentLoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(new IntelligentLoadScrollListener());
    }

    private class IntelligentLoadScrollListener extends OnScrollListener {

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    //空闲，未滚动
                    resumeRequests();
                    break;
                case SCROLL_STATE_DRAGGING:
                    //手指在屏幕上
                    pauseRequests();
                    break;
                case SCROLL_STATE_SETTLING:
                    //由于惯性仍在滚动
                    pauseRequests();
                    break;
            }
        }

        private void resumeRequests() {
            Glide.with(getContext()).resumeRequests();
        }

        private void pauseRequests() {
            Glide.with(getContext()).pauseRequests();
        }
    }
}
