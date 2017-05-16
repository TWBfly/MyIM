package club.ah12530.myim.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import club.ah12530.myim.R;


public class ConstactLayout extends RelativeLayout {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.tv_float)
//    TextView tvFloat;
//    @BindView(R.id.slideBar)
//    SlideBar slideBar;    自定义控件会找不到

    public ConstactLayout(Context context) {
        this(context, null);
    }

    public ConstactLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ConstactLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.constact_layout, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        TextView tv_float = (TextView) findViewById(R.id.tv_float);
        SlideBar slideBar = (SlideBar) findViewById(R.id.slideBar);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        swipeRefreshLayout.setOnRefreshListener(listener);
    }
    public void setRefreshing(boolean boo){
        swipeRefreshLayout.setRefreshing(boo);
    }




}
