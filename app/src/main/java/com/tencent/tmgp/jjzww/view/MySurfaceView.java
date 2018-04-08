package com.tencent.tmgp.jjzww.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tencent.tmgp.jjzww.activity.ctrl.presenter.CtrlCompl;
import com.tencent.tmgp.jjzww.activity.ctrl.view.IctrlView;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.util.List;
/**
 * Created by chen on 2018/3/30.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, IctrlView {
    private static final String TAG ="MySurfaceView-------" ;
    private SurfaceHolder holder;
    private Bitmap bitmap;
    private CtrlCompl ctrlCompl=null;
    private String url1=null;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        holder = this.getHolder();
        ctrlCompl=new CtrlCompl(this,context);
    }
    private void drawCanvas(Bitmap bitmap){
        Canvas canvas = holder.lockCanvas();
        if(canvas != null){
            canvas.drawBitmap(bitmap, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void setPlayUrl(String uri){
        this.url1=uri;
        ctrlCompl.startPlayVideo(this,url1);
        holder.addCallback(this);

    }
    public CtrlCompl getCtrlCompl(){
        return ctrlCompl;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Utils.showLogE(TAG, "surfaceChanged!!!!!!!!!!!!!!");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
       // this.drawCanvas(bitmap);
        ctrlCompl.startPlayVideo(this,url1);
        Utils.showLogE(TAG, "surfaceCreated!!!!!!!!!!!!!!");
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        ctrlCompl.stopPlayVideo();
        Utils.showLogE(TAG, "surfaceDestroyed!!!!!!!!!!!!!!");
    }

    @Override
    public void getTime(int time) {

    }
    @Override
    public void getTimeFinish() {

    }
    @Override
    public void getUserInfos(List<String> list, boolean is) {

    }

    @Override
    public void getRecordErrCode(int code) {

    }

    @Override
    public void getRecordSuecss() {

    }

    @Override
    public void getRecordAttributetoNet(String time, String name) {

    }

    @Override
    public void getPlayerErcErrCode(int code) {

    }

    @Override
    public void getPlayerSucess() {

    }

    @Override
    public void getVideoPlayConnect() {

    }

    @Override
    public void getVideoPlayStart() {

    }

    @Override
    public void getVideoStop() {

    }
}
