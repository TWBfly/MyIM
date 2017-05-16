package club.ah12530.myim.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.ah12530.myim.R;
import club.ah12530.myim.presenter.RegistPresenter;
import club.ah12530.myim.presenter.impl.RegistPresenterImpl;
import club.ah12530.myim.ui.view.RegistView;
import club.ah12530.myim.uitls.Constant;
import club.ah12530.myim.uitls.ProgressDialogUtil;
import club.ah12530.myim.uitls.SPUtils;
import club.ah12530.myim.uitls.ToastyUtil;

public class RegistActivity extends BaseActivity implements RegistView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    private RegistPresenter registPresenter;
    private String username;
    private String pwd;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        registPresenter = new RegistPresenterImpl(this);
        etPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == R.id.et_pwd){
                    if (actionId == EditorInfo.IME_ACTION_DONE){
                        regist();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        regist();
    }

    private void regist() {
        username = etUsername.getText().toString().trim();
        pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            ToastyUtil.error(this,"请填写用户名");
         return;
        }else if (!username.matches("^[a-zA-Z]\\w{2,19}$")){
            ToastyUtil.error(this,"用户名请输入字母和英文");
            return;
        } else if (TextUtils.isEmpty(pwd)){
            ToastyUtil.error(this,"请填写密码");
            return;
        }else if (!pwd.matches("^[0-9]{3,20}$")){
            ToastyUtil.error(this,"密码只能输入3到20位数字");
            return;
        }
        ProgressDialogUtil.create(this);
        ProgressDialogUtil.showDialog("正在注册中...");
        registPresenter.regist(username,pwd);
    }

    @Override
    public void onRegist(String username, String pwd, boolean isRegist, String msg) {
        if (isRegist){
            //注册成功,保存账号密码,跳转MainActivity
            ToastyUtil.success(this,"注册成功");
            ProgressDialogUtil.dismissDialog();
            SPUtils.put(this, Constant.REGIST_USERNAME_KEY,username);
            SPUtils.put(this,Constant.REGIST_PWD_KEY,pwd);
            startActivity(LoginActivity.class,true);
        }else {
            ToastyUtil.error(this,"注册失败"+msg);
            ProgressDialogUtil.dismissDialog();
        }
    }
}
