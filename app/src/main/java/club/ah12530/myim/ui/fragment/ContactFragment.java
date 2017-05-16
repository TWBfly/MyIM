package club.ah12530.myim.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.ah12530.myim.R;
import club.ah12530.myim.adapter.ContactAdapter;
import club.ah12530.myim.presenter.ContactFragmentPresenter;
import club.ah12530.myim.presenter.impl.ContactFragmentPresenterImpl;
import club.ah12530.myim.ui.view.ContactFragmentView;
import club.ah12530.myim.ui.widget.ConstactLayout;
import club.ah12530.myim.uitls.ToastyUtil;

/**
 * 联系人
 */
public class ContactFragment extends BaseFragment implements ContactFragmentView {

    @BindView(R.id.contactLayout)
    ConstactLayout contactLayout;
    Unbinder unbinder;

    private ContactFragmentPresenter contactFragmentPresenter;
    private ContactAdapter adapter;
    private SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //下拉刷新
            contactFragmentPresenter.updateContacts();

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder = ButterKnife.bind(this, view);
        contactFragmentPresenter = new ContactFragmentPresenterImpl(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactFragmentPresenter.initContacts();
        contactLayout.setOnRefreshListener(listener);
    }

    @Override
    public void onInitContacts(List<String> contactList) {
        adapter = new ContactAdapter(contactList);
        contactLayout.setAdapter(adapter);
    }

    @Override
    public void updateContacts(boolean b, String o) {
        if (b){
            //从环信获取联系人成功,更新UI
            adapter.notifyDataSetChanged();
            //隐藏
            contactLayout.setRefreshing(false);
        }else {
            //失败
            ToastyUtil.error(getContext(),"获取联系人失败"+o);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
