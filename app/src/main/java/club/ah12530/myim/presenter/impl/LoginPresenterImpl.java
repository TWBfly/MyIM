package club.ah12530.myim.presenter.impl;


import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import club.ah12530.myim.presenter.LoginPresenter;
import club.ah12530.myim.ui.view.LoginView;
import club.ah12530.myim.uitls.ThreadUitl;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(final String username, final String pwd) {
        EMClient.getInstance().login(username,pwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                ThreadUitl.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        loginView.onLogin(username,pwd,true,null);
                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                loginView.onLogin(username,pwd,false,message);
            }
        });
    }
}
