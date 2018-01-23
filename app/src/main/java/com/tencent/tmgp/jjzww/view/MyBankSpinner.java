package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.home.NewAddressActivity;
import com.tencent.tmgp.jjzww.adapter.MySpinnerAdapter;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.util.List;
import java.util.Map;

public class MyBankSpinner extends Dialog {

    private ListView listView;
    private MySpinnerAdapter sAdapter;
    private List<Map<String, String>> listString;
    private Context context;
    private int index;
    private int type; // 类型
    private static String province;

    public MyBankSpinner(Context context, List<Map<String, String>> list,
                         int index, int type) {
        super(context);
        init(context, list, index, type);
        // TODO Auto-generated constructor stub
    }

    public MyBankSpinner(Context context, List<Map<String, String>> list,
                         int index, int type, boolean cancelable,
                         OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context, list, index, type);
        // TODO Auto-generated constructor stub
    }

    public MyBankSpinner(Context context, List<Map<String, String>> list,
                         int index, int type, int theme) {
        super(context, theme);
        init(context, list, index, type);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_dialog);
        listView = (ListView) this.findViewById(R.id.spinner_dialog_listView);
        sAdapter = new MySpinnerAdapter(context, listString, index);
        listView.setAdapter(sAdapter);
        listView.setOnItemClickListener(new MyItemClickListener());

    }

    /**
     * 初始化
     *
     * @param context
     * @param list
     */
    private void init(Context context, List<Map<String, String>> list,
                      int index, int type) {
        this.context = context;
        this.listString = list;
        this.index = index;
        this.type = type;
    }

    public void updateAdapter() {
        sAdapter.notifyDataSetChanged();
    }

    /**
     * ListView Item 点击监听
     * @author SLS003
     */
    class MyItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long arg3) {
            String str = listString.get(position).get("name");
            switch (type) {
                case Utils.PROVINCE_TYPE:
                    province =str;
                    NewAddressActivity activity = (NewAddressActivity) context;
                    // 如果省份改变了 则将城市的值清空
                    if (activity.province_index != position) {
                        activity.city_index = -1;
                        activity.newaddressDqTv.setText("");
                    }
                    activity.province_index = position;
                    sAdapter.setIndex(position);
                    activity.changCity();
                    break;
                case Utils.CITY_TYPE:
                    activity = (NewAddressActivity) context;
                    // 如果省份改变了 则将城市的值清空
                    if (activity.city_index != position) {
                        activity.newaddressDqTv.setText("");
                    }
                    activity.newaddressDqTv.setText(province + "" + str);
                    activity.city_index = position;
                    break;
                default:
                    break;
            }
            sAdapter.setIndex(position);
            sAdapter.notifyDataSetChanged();
            MyBankSpinner.this.dismiss();
        }

    }

}
