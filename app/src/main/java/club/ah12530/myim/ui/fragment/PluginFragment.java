package club.ah12530.myim.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.ah12530.myim.MyApplication;
import club.ah12530.myim.R;
import club.ah12530.myim.presenter.PluginFragmentPresenter;
import club.ah12530.myim.presenter.impl.PluginFragmentPresentImpl;
import club.ah12530.myim.ui.activity.LoginActivity;
import club.ah12530.myim.ui.activity.MainActivity;
import club.ah12530.myim.ui.view.PluginFragmentView;
import club.ah12530.myim.uitls.ToastyUtil;

/**
 * 动态
 */
public class PluginFragment extends BaseFragment implements PluginFragmentView {

    @BindView(R.id.btn_logout)
    Button btnLogout;
    Unbinder unbinder;

    private PluginFragmentPresenter pluginFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plugin, container, false);
        pluginFragmentPresenter = new PluginFragmentPresentImpl(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        logout();
    }

    private void logout() {
        pluginFragmentPresenter.logout();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLogout(boolean isLogout) {
        if (isLogout){
            ToastyUtil.success(MyApplication.getContext(),"退出成功");
            MainActivity activity = (MainActivity) getActivity();
            activity.startActivity(LoginActivity.class,true);
        }else {
            ToastyUtil.error(MyApplication.getContext(),"退出失败");
        }
    }
}
