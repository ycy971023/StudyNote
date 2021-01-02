package com.example.mycomicapplication.until;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class ScrollBottomScrollView extends ScrollView {
    private OnScrollBottomListener listener;
    private int calCount;

    public interface OnScrollBottomListener {
        void scrollToBottom();
    }

    public void onScrollViewScrollToBottom(OnScrollBottomListener l) {
        listener = l;
    }

    public void unRegisterOnScrollViewScrollToBottom() {
        listener = null;
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = this.getChildAt(0);
        if (this.getHeight() + this.getScrollY() == view.getHeight()) {
            calCount++;
            if (calCount == 1) {
                if (listener != null) {
                    listener.scrollToBottom();
                }
            }
        } else {
            calCount = 0;
        }
    }
}
