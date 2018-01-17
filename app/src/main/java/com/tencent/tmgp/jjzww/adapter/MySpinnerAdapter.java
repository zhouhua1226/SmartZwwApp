package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator 修改日期：2013-3-7
 */
public class MySpinnerAdapter extends BaseAdapter {

	// 上下文本
	private Context context;

	private List<Map<String, String>> listMap;

	private int index = -1;
	
	/** 构造方法 实现初始化 */
	public MySpinnerAdapter(Context context, List<Map<String, String>> listMap, int index) {
		this.context = context;
		this.listMap = listMap;
		this.index=index;
	}
	
	public void setIndex(int index){
		this.index=index;
	}
	
	public int getIndex(){
		return index;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMap.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listMap.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/** 获得视图*/
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		// 判断View是否为空
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			// 得到布局文件
			view = inflater.inflate(R.layout.bankinfo_dialog_items, null);
			// 得到控件
			
			holder.imgView = (ImageView) view
					.findViewById(R.id.bankinfo_dialog_img_btn);
			
			holder.tv_name = (TextView) view
					.findViewById(R.id.bankinfo_dialog_tvName);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// 给控件赋值
		holder.tv_name.setText(listMap.get(position).get("name"));
		holder.imgView.setBackgroundResource(R.drawable.mycatchrecord_unselect);
		
		if (position == index) 
		{
			holder.imgView
					.setBackgroundResource(R.drawable.mycatchrecord_select);
		}

		return view;
	}
	
	/** 静态类 */
	static class ViewHolder {
		ImageView imgView;
		TextView tv_name;
	}
}
