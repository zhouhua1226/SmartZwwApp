package com.tencent.tmgp.jjzww.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.ListRankAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.UserBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class RankFragmentTwo extends BaseFragment {
    private static final String TAG = "RankFragmentTwo-";
    @BindView(R.id.ranktwo_recyclerbiew)
    RecyclerView ranktwoRecyclerbiew;
    private ListRankAdapter listRankAdapter;
    private List<UserBean> list=new ArrayList<>();
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranktwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initlist();
        OnClick();

    }

    private void initData() {
        listRankAdapter=new ListRankAdapter(getContext(),list);
        ranktwoRecyclerbiew.setAdapter(listRankAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        ranktwoRecyclerbiew.setLayoutManager(linearLayoutManager);
        ranktwoRecyclerbiew.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void initlist(){
        HttpManager.getInstance().getListRank(new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> listRankBeanResult) {
                list =listRankBeanResult.getData().getAppUser();
                Utils.showLogE(TAG, "=====" + list.size());
                listRankAdapter.notify(list);
            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "RankFragmentTwo::::" + e.getMessage());
            }
        });

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
