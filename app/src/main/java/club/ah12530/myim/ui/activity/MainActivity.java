package club.ah12530.myim.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.ah12530.myim.R;
import club.ah12530.myim.ui.fragment.BaseFragment;
import club.ah12530.myim.uitls.FragmentFactory;
import club.ah12530.myim.uitls.ToastyUtil;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private int[] tabs = {R.string.Contact,R.string.Conversation,R.string.Plugin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initBottomNavigationBar();
        initFirstFragment();//初始化第一个Fragment

    }

    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("消息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initBottomNavigationBar() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.conversation_selected_2, tabs[0]))
                .addItem(new BottomNavigationItem(R.mipmap.contact_selected_2, tabs[1]))
                .addItem(new BottomNavigationItem(R.mipmap.plugin_selected_2, tabs[2]))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                //判断fragment是否被添加,没有添加直接添加,添加则直接显示
                BaseFragment fragment = FragmentFactory.getFragment(position);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()){
                    fragmentTransaction.add(R.id.fl_content,fragment,position+"");
                }
                fragmentTransaction.show(fragment).commit();
                tvTitle.setText(tabs[position]);

            }
            @Override
            public void onTabUnselected(int position) {
                //隐藏Fragment
                getSupportFragmentManager().beginTransaction().hide(FragmentFactory.getFragment(position)).commit();

            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }
    private void initFirstFragment() {
        //Fragment重影覆盖
        //ACtivity会保存历史Fragment,如果保存就全部移除
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        for (int i = 0; i < tabs.length; i++) {
//            Fragment fragmentById = supportFragmentManager.findFragmentById(i);
//            if (fragmentById!=null){
//                fragmentTransaction.remove(fragmentById);
//            }
//        }
//        fragmentTransaction.commit();

        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, FragmentFactory.getFragment(0),"0").commit();
        tvTitle.setText(tabs[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder builder = (MenuBuilder) menu;
        builder.setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_add_friend:
                startActivity(AddFriendActivity.class,false);
                break;
            case R.id.menu_scan:
                ToastyUtil.success(this,"分享好友");

                break;
            case R.id.menu_about:
                ToastyUtil.success(this,"关于我们");
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return true;
    }
}
