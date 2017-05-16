package club.ah12530.myim.ui.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.ah12530.myim.R;
import club.ah12530.myim.presenter.LoginPresenter;
import club.ah12530.myim.presenter.impl.LoginPresenterImpl;
import club.ah12530.myim.ui.view.LoginView;
import club.ah12530.myim.uitls.Constant;
import club.ah12530.myim.uitls.ProgressDialogUtil;
import club.ah12530.myim.uitls.SPUtils;
import club.ah12530.myim.uitls.ToastyUtil;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_newuser)
    TextView tvNewuser;

    private LoginPresenter loginPresenter;
    private int REQUEST_SDCODE = 100;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        etUsername.setTextColor(Color.BLACK);
        etPwd.setTextColor(Color.BLACK);

        loginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_newuser:
                //注册
                startActivity(RegistActivity.class,false);
                break;
        }
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
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
        //在登录之前,动态申请权限
        applypermiss();
        //登录显示progress
        ProgressDialogUtil.create(this);
        ProgressDialogUtil.showDialog("玩命登录中....");
        loginPresenter.login(username,pwd);

    }

    private void applypermiss() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_SDCODE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_SDCODE){
            if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                //授权
                login();
            }else {
                ToastyUtil.error(this,"授权失败");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String usrname = (String) SPUtils.get(LoginActivity.this, Constant.REGIST_USERNAME_KEY,"");
        String pwd = (String) SPUtils.get(LoginActivity.this, Constant.REGIST_PWD_KEY, "");
        etUsername.setText(usrname);
        etPwd.setText(pwd);
    }

    @Override
    public void onLogin(String username, String pwd, boolean isLogin, String msg) {
        ProgressDialogUtil.dismissDialog();
        if (isLogin){
            //保存用户,调到Mainactivity
            SPUtils.put(LoginActivity.this,Constant.LOGIN_USERNAME_KEY,username);
            SPUtils.put(LoginActivity.this,Constant.LOGIN_PWD_KEY,pwd);
            startActivity(MainActivity.class,true);
        }else {
            ToastyUtil.error(this,"登录失败,请重新登录");
        }

    }






}
