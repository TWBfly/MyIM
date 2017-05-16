package club.ah12530.myim.presenter.impl;


import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import club.ah12530.myim.module.User;
import club.ah12530.myim.presenter.RegistPresenter;
import club.ah12530.myim.ui.view.RegistView;
import club.ah12530.myim.uitls.ThreadUitl;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegistPresenterImpl implements RegistPresenter {

    private RegistView registView;

    public RegistPresenterImpl(RegistView registView) {
        this.registView = registView;
    }

    /**注册*/
    @Override
    public void regist(final String username, final String pwd) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(pwd);
        user.signUp(new SaveListener<User>(){

            @Override
            public void done(final User user, final BmobException e) {
                if (e==null){
                    //成功
                    ThreadUitl.runOnStubThread(new Runnable() {
                        @Override
                        public void run() {
                            //注册失败会抛出HyphenateException
                            try {
                                EMClient.getInstance().createAccount(username, pwd);//同步方法
                                //注册环信成功
                                ThreadUitl.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        registView.onRegist(username,pwd,true,null);
                                    }
                                });

                            } catch (final HyphenateException e1) {
                                e1.printStackTrace();
                                //环信注册失败,删除Bmob数据
                               user.delete();
                               ThreadUitl.runOnMainThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       registView.onRegist(username,pwd,false,e1.getMessage());
                                   }
                               });

                            }
                        }
                    });

                }else {
                    //失败
                    registView.onRegist(username,pwd,false,e.getMessage());
                }
            }
        });


    }
}
