package com.tencent.tmgp.jjzww.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.tmgp.jjzww.R;


/**
 * Created by cx on 2017/11/16
 */

public class EmptyLayout extends RelativeLayout implements View.OnClickListener {

    private View mView;
    private ImageView iv_empty;
    private ImageView iv_loading;
    private ImageView iv_error;
    private int mState = 0;
    private final int STATE_DISMISS = 0, STATE_EMPTY = 1, STATE_LOADING = 2, STATE_ERROR = 3;
    private OnClickReTryListener onClickReTryListener;

    public EmptyLayout(Context context) {
        super(context);
        init(context);
    }

    public EmptyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.layout_emptyview, null);
        iv_empty = (ImageView) mView.findViewById(R.id.iv_empty);
        iv_loading = (ImageView) mView.findViewById(R.id.iv_loading);
        iv_error = (ImageView) mView.findViewById(R.id.iv_error);
        setListener();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(layoutParams);
        addView(mView);
    }

    private void setListener() {
        iv_empty.setOnClickListener(this);
//        iv_loading.setOnClickListener(this);
        iv_error.setOnClickListener(this);
    }

    private void refreshView() {
        switch (mState) {
            case STATE_DISMISS:
                cleanState();
                break;
            case STATE_EMPTY:
                cleanState();
                if (iv_empty.getVisibility() != VISIBLE) {
                    iv_empty.setVisibility(VISIBLE);
                }
                break;
            case STATE_LOADING:
                cleanState();
                if (iv_loading.getVisibility() != VISIBLE) {
                    iv_loading.setVisibility(VISIBLE);
                }
                break;
            case STATE_ERROR:
                cleanState();
                if (iv_error.getVisibility() != VISIBLE) {
                    iv_error.setVisibility(VISIBLE);
                }
                break;
            default:
                break;

        }
    }

    private void cleanState() {
        if (iv_empty.getVisibility() != GONE) {
            iv_empty.setVisibility(GONE);
        }
        if (iv_loading.getVisibility() != GONE) {
            iv_loading.setVisibility(GONE);
        }
        if (iv_error.getVisibility() != GONE) {
            iv_error.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (onClickReTryListener != null) {
            onClickReTryListener.onClickReTry(v);
        }
    }

    public static interface OnClickReTryListener {
        void onClickReTry(View view);
    }

    public void setOnClickReTryListener(OnClickReTryListener onClickReTryListener) {
        this.onClickReTryListener = onClickReTryListener;
    }

    public void dismiss() {
        mState = STATE_DISMISS;
        refreshView();
    }

    public void showEmpty() {
        mState = STATE_EMPTY;
        refreshView();
    }

    public void showLoading() {
        mState = STATE_LOADING;
        refreshView();
    }

    public void showError() {
        mState = STATE_ERROR;
        refreshView();
    }
}
