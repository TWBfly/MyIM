package club.ah12530.myim.presenter.impl;


import com.hyphenate.chat.EMClient;

import club.ah12530.myim.presenter.SplashPresenter;
import club.ah12530.myim.ui.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView splashView;

    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
    }

    /**是否登录*/
    @Override
    public void checkLogin() {
        if (EMClient.getInstance().isLoggedInBefore()&&EMClient.getInstance().isConnected()){
            splashView.onCheckdLogin(true);
        }else {
            splashView.onCheckdLogin(false);
        }
    }
}
