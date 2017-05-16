package club.ah12530.myim.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.ah12530.myim.R;
import club.ah12530.myim.adapter.AnimatorLinstenerAdapter;
import club.ah12530.myim.presenter.SplashPresenter;
import club.ah12530.myim.presenter.impl.SplashPresenterImpl;
import club.ah12530.myim.ui.view.SplashView;

public class SplasheActivity extends BaseActivity implements SplashView {

    @BindView(R.id.splash_img)
    ImageView splashImg;

    private SplashPresenter splashPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashe);
        ButterKnife.bind(this);

        //判断是否登录,登录Mainactivity,未登录LoginActivity
        init();
    }

    private void init() {
        splashPresenter = new SplashPresenterImpl(this);
        splashPresenter.checkLogin();
    }

    @Override
    public void onCheckdLogin(boolean isLogin) {
        if (isLogin){
            startActivity(MainActivity.class,true);
        }else {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(splashImg,"alpha",0,1).setDuration(2000);
            alphaAnimator.start();
            alphaAnimator.addListener(new AnimatorLinstenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(LoginActivity.class,true);
                }
            });
        }
    }
}
