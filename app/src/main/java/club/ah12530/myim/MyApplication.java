package club.ah12530.myim;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import cn.bmob.v3.Bmob;


public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        inithuanxin();

        initBmob();
    }

    private void inithuanxin() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private void initBmob() {
        Bmob.initialize(this, "e16ff3f365788f794b3cbc3af03033cf");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        Bmob.initialize(this, "e16ff3f365788f794b3cbc3af03033cf","bmob");
    }

    public static  Context getContext(){
        return context;
    }
}
