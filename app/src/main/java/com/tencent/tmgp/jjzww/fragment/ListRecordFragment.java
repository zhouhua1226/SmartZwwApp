package com.tencent.tmgp.jjzww.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.ListRecordAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.view.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/9/27.
 */
public class ListRecordFragment extends BaseFragment {
    @BindView(R.id.listrecord_recycrview)
    RecyclerView listrecordRecycrview;
    Unbinder unbinder;
    private ListRecordAdapter listRecordAdapter;
    private List<String>list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listrecord;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initlist();
        listRecordAdapter=new ListRecordAdapter(getContext(),list);
        listrecordRecycrview.setAdapter(listRecordAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listrecordRecycrview.setLayoutManager(linearLayoutManager);
        listrecordRecycrview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        OnClick();

    }

    private void initlist(){
        list=new ArrayList<String>();
        for (int i=0;i<20;i++){
            list.add(i+"");
        }
    }

    private void OnClick(){
        listRecordAdapter.setOnItemClickListener(new ListRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MyToast.getToast(getContext(),"我要看回放"+position).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                MyToast.getToast(getContext(),"我要看回放"+position).show();
            }
        });


    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}
