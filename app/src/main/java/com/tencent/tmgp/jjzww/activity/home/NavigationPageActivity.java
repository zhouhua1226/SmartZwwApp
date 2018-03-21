package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yincong on 2018/3/12 16:03
 * 修改人：
 * 修改时间：
 * 类描述：导航页
 */
public class NavigationPageActivity extends BaseActivity {

    @BindView(R.id.yindao_vp)
    ViewPager yindaoVp;
    @BindView(R.id.lijitiyan_tv)
    TextView lijitiyanTv;
    private PagerAdapter pagerAdapter;
    int draw[] = {R.drawable.app_yd_guess1, R.drawable.app_yd_guess2, R.drawable.app_yd_guess3};
    private String TAG = "NavigationActivity--";
    //记录当前选中位置
    int CURRENTINDEX = 0;
    //底部小图片
    View[] dos;
    ImageView yindao_img;
    int flage;
    List<View> dots;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yindao;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initAdapter();
        yindaoVp.setAdapter(pagerAdapter);
        initListener();

        lijitiyanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
    }

    //创建adapter方法
    private void initAdapter() {
        pagerAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView imageView = new ImageView(NavigationPageActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(draw[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ImageView imageView = new ImageView(NavigationPageActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(draw[position]);
                container.removeView(imageView);
            }

            @Override
            public int getCount() {
                return draw == null ? 0 : draw.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == (View) object;
            }
        };

    }

    //adapter滑动监听
    private void initListener() {
        yindaoVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "viewpager位置Scrolled=" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "viewpager位置Selected=" + position);
                if(position==2){
                    lijitiyanTv.setVisibility(View.VISIBLE);
                }else {
                    lijitiyanTv.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "viewpager位置Changed=" + state);
            }
        });
        //adapter点击重写
        yindaoVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flage == 0) {
//                            int item = viewPager.getCurrentItem();
//                            if (item ==2) {
//                                Intent intent = new Intent(YinDaoActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
                        }
                        break;

                }
                return false;
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
