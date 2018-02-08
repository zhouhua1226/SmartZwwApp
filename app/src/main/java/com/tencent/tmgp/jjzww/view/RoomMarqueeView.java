package com.tencent.tmgp.jjzww.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.Marquee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yincong on 2018/2/8 15:24
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class RoomMarqueeView extends ViewFlipper {

    private Context mContext;
    private List<Marquee> marquees;
    private boolean isSetAnimDuration = false;
    private OnItemClickListener onItemClickListener;
    private TextView tvMarquee;
    private int interval = 2000;//播放时间间隔
    private int animDuration = 500;
    private int textSize = 12;
    private boolean isImage = true;

    public RoomMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        if (marquees == null) {
            marquees = new ArrayList<>();
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
        isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = px2sp(mContext, textSize);
        }

        typedArray.recycle();

        setFlipInterval(interval);
    }

    // 根据公告字符串启动轮播
    public void startWithText(final String notice) {
        if (TextUtils.isEmpty(notice)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startWithFixedWidth(notice, getWidth());
            }
        });
    }

    // 根据公告字符串列表启动轮播
    public void startWithList(List<Marquee> marquees) {
        setMarquees(marquees);
        start();
    }

    // 根据宽度和公告字符串启动轮播
    private void startWithFixedWidth(String notice, int width) {
        int noticeLength = notice.length();
        int dpW = px2dip(mContext, width);
        int limit = dpW / textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width !");
        }
        List list = new ArrayList();
        if (noticeLength <= limit) {
            list.add(notice);
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                list.add(notice.substring(startIndex, endIndex));
            }
        }
        marquees.addAll(list);
        start();
    }

    // 启动轮播
    public boolean start() {
        if (marquees == null || marquees.size() == 0) return false;
        removeAllViews();
        resetAnimation();

        for (int i = 0; i < marquees.size(); i++) {
            final View view = createView(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(finalI, view);
                    }
                }
            });
            addView(view);
        }

        if (marquees.size() > 1) {
            startFlipping();
        } else {
            stopFlipping();
        }
        return true;
    }

    private void resetAnimation() {
        clearAnimation();

        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.in_animation);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.out_animation);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    // 创建ViewFlipper下的View
    private View createView(int position) {
        Marquee marquee = marquees.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_room_marquee, null);
        ImageView ivMarquee = (ImageView) view.findViewById(R.id.ivMarquee);
        tvMarquee = (TextView) view.findViewById(R.id.tvMarquee);
        tvMarquee.setText(Html.fromHtml(marquee.getTitle()));
        if (isImage) {
            ivMarquee.setVisibility(VISIBLE);
            Glide.with(mContext)
                    .load(marquee.getImgUrl())
                    .dontAnimate()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(ivMarquee);
        }
        tvMarquee.setTextSize(textSize);
        view.setTag(position);
        return view;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List<Marquee> getMarquees() {
        return marquees;
    }

    public void setMarquees(List<Marquee> marquees) {
        this.marquees = marquees;
    }

    public void setImage(boolean image) {
        this.isImage = image;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View textView);
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
