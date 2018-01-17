package com.tencent.tmgp.jjzww.activity.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.view.GifView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yincong on 2018/1/16 17:01
 * 修改人：
 * 修改时间：
 * 类描述：新闻活动类
 */
public class NewsWebActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.newsweb_top_layout)
    RelativeLayout newswebTopLayout;
    @BindView(R.id.newsweb_webview)
    WebView webView;
    @BindView(R.id.newsweb_none_tv)
    TextView newswebNoneTv;
    @BindView(R.id.newsweb_gif_view)
    GifView newswebGifView;

    private String TAG="NewsWebActivity";
    private WebSettings s;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newsweb;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        fresh();

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void loadUrl(){
        webView.loadUrl("http://www.baidu.com");
        webView.reload();
    }

    //设置属性
    @SuppressLint("JavascriptInterface")
    private void fresh(){
        loadUrl();
        s= webView.getSettings();
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
        s.setBlockNetworkImage(true);
        s.setRenderPriority(WebSettings.RenderPriority.HIGH);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);//关闭WebView中缓存
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(this,"Native");

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

            }
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view,url);
                s.setBlockNetworkImage(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //当进度走到100的时候做自己的操作，我这边是弹出dialog
                if(progress == 100){

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
}
