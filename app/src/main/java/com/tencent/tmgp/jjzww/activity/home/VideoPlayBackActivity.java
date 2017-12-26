package com.tencent.tmgp.jjzww.activity.home;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yincong on 2017/11/23 09:44
 * 修改人：
 * 修改时间：
 * 类描述：视频回放
 */
public class VideoPlayBackActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.doll_name)
    TextView dollName;
    @BindView(R.id.realplay_vv)
    VideoView realplayVv;

    private String TAG = "VideoPlayBackActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_videoplayback;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initVideo();  //初始化视频控件
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dollName.setText("视频回放");
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initVideo() {
        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        String time = getIntent().getStringExtra("time");
        String videoUrl1 = UserUtils.RECODE_URL + time + ".mp4";
        File file = new File(videoUrl1);
        Log.e(TAG, "录制视频目录=" + videoUrl1);
        Log.e(TAG, "视频目录=" + videoUrl1);
        Uri uri = Uri.parse(videoUrl1);
        //设置视频控制器
        MediaController mediaController = new MediaController(this);
        realplayVv.setMediaController(mediaController);
        mediaController.setMediaPlayer(realplayVv);
        //播放完成回调
        realplayVv.setOnCompletionListener(new MyPlayerOnCompletionListener());
        //设置视频路径
        //realplayVv.setVideoURI(uri);
        realplayVv.setVideoPath(file.getAbsolutePath());
        realplayVv.requestFocus();
        //开始播放视频
        realplayVv.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            MyToast.getToast(VideoPlayBackActivity.this, "播放完成").show();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
