package com.tencent.tmgp.jjzww.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.ListRankAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/9/27.
 */
public class ListRankFragment extends BaseFragment {
    @BindView(R.id.listrank_recycrview)
    RecyclerView listrankRecycrview;
    Unbinder unbinder;
    private ListRankAdapter listRankAdapter;
    private List<String> list;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_listrank;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initlist();
        initData();
        OnClick();

    }

    private void initData() {
//        listRankAdapter=new ListRankAdapter(getContext(),list);
//        listrankRecycrview.setAdapter(listRankAdapter);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        listrankRecycrview.setLayoutManager(linearLayoutManager);
//        listrankRecycrview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
}

    private void initlist(){
        list= new ArrayList<String>();
        for (int i=0;i<10;i++){

            list.add(""+i);
        }
    }

    public void OnClick(){
        listRankAdapter.setOnItemClickListener(new ListRankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position) {
//                MyToast.getToast(getContext(),"我要查看"+position).show();
            }

        });
    }

}
