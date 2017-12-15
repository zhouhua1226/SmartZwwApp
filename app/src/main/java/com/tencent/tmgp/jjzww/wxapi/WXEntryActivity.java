package com.tencent.tmgp.jjzww.wxapi;

import android.os.Bundle;

import com.easy.ysdk.EasyShareApi;
import com.easy.ysdk.share.ShareWX;
import com.tencent.mm.sdk.modelbase.BaseResp;


public class WXEntryActivity extends com.tencent.ysdk.module.user.impl.wx.YSDKWXEntryActivity {

//    private IWXAPI api;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		//为了进行分享，不得已创建两个api
		if(EasyShareApi.getInstance().getIwxapi()!=null){
			EasyShareApi.getInstance().getIwxapi().handleIntent(getIntent(),WXEntryActivity.this);
		}
	}


	@Override
	public void onResp(BaseResp baseResp) {
		//super里集成了微信登录和微信支付，一定要保留
		super.onResp(baseResp);
		//以下逻辑为处理微信分享
		ShareWX.onResp(baseResp);



	}
}
