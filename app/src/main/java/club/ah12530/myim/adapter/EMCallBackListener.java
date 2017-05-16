package club.ah12530.myim.adapter;

import com.hyphenate.EMCallBack;

import club.ah12530.myim.uitls.ThreadUitl;


public abstract class EMCallBackListener implements EMCallBack {
    @Override
    public void onSuccess() {
        ThreadUitl.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                logoutSuccess();
            }
        });
    }

    @Override
    public void onError(int i, String s) {
        ThreadUitl.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                logoutError();
            }
        });
    }

    @Override
    public void onProgress(int i, String s) {

    }

    protected abstract void logoutSuccess();
    protected abstract void logoutError();
}
