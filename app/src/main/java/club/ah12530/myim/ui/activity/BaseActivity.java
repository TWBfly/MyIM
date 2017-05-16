package club.ah12530.myim.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import club.ah12530.myim.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void startActivity(Class clazz,boolean isFinish){
        startActivity(new Intent(this,clazz));
        if (isFinish){
            finish();
        }

    }




}
