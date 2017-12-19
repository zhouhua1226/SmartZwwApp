package com.tencent.tmgp.jjzww.activity.home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.Base64;
import com.tencent.tmgp.jjzww.utils.BitmapUtils;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.jph.takephoto.model.CropOptions;
import com.tencent.tmgp.jjzww.view.MyToast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by hongxiu on 2017/9/26.
 */
public class TakePhotoActivity extends com.jph.takephoto.app.TakePhotoActivity implements View.OnClickListener {
    private static final String TAG = "TakePhotoActivity-";
    private TextView shootTv;
    private TextView photoTv;
    private TextView cancelTv;
    private Uri imageUri;
    private CropOptions cropOptions;
    private File file;
    private String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        shootTv = (TextView) findViewById(R.id.shoot_tv);
        photoTv = (TextView) findViewById(R.id.photo_tv);
        cancelTv = (TextView) findViewById(R.id.cancel_tv);
        shootTv.setOnClickListener(this);
        photoTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);

        file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        imageUri=Uri.fromFile(file);
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoot_tv:
                getTakePhoto().onPickFromCaptureWithCrop(imageUri,cropOptions);

                break;
            case R.id.photo_tv:
                getTakePhoto().onPickFromDocumentsWithCrop(imageUri, cropOptions);
//                getTakePhoto().onPickFromGalleryWithCrop(imageUri,cropOptions);
                break;
            case R.id.cancel_tv:
                finish();
                break;
        }
    }

    @Override
    public void takeSuccess(String imagePath) {
        Utils.showLogE(TAG,imagePath);
        String string=UserUtils.UserPhone;
        String str = android.util.Base64.encodeToString(string.getBytes(), android.util.Base64.DEFAULT);
        Bitmap bitmap= BitmapUtils.compressImageFromFile(imagePath);
        base64=Base64.encode(BitmapUtils.compressBmpFromBmp(bitmap));
        getFaceImage(UserUtils.USER_ID,base64);
    }


    private void getFaceImage(String userId,String faceImage){
        HttpManager.getInstance().getFaceImage(userId, faceImage, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> result) {
                Utils.showLogE(TAG,result.getMsg());
                UserUtils.UserImage= UrlUtils.USERFACEIMAGEURL+result.getData().getAppUser().getIMAGE_URL();
                MyToast.getToast(getApplicationContext(),"修改成功！").show();
                finish();
            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "getFaceImage::::" + e.getMessage());
            }
        });

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
