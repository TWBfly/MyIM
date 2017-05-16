package club.ah12530.myim.presenter.impl;

import com.hyphenate.chat.EMClient;

import club.ah12530.myim.adapter.EMCallBackListener;
import club.ah12530.myim.presenter.PluginFragmentPresenter;
import club.ah12530.myim.ui.view.PluginFragmentView;


public class PluginFragmentPresentImpl implements PluginFragmentPresenter {
    private PluginFragmentView pluginFragmentView;

    public PluginFragmentPresentImpl(PluginFragmentView pluginFragmentView) {
        this.pluginFragmentView = pluginFragmentView;
    }

    @Override
    public void logout() {
        EMClient.getInstance().logout(true, new EMCallBackListener() {
            @Override
            protected void logoutSuccess() {
                pluginFragmentView.onLogout(true);
            }

            @Override
            protected void logoutError() {
                pluginFragmentView.onLogout(false);
            }
        });

    }
}
