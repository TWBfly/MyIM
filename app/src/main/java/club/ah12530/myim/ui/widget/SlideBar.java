package club.ah12530.myim.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.util.DensityUtil;

import java.util.List;

import club.ah12530.myim.R;
import club.ah12530.myim.adapter.IContactAdapter;
import club.ah12530.myim.uitls.StringUtils;


public class SlideBar extends View {
    private static final String[] SECTIONS = {"搜","A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int mAvgWidth;
    private int mAvgHeight;
    private Paint paint;
    private TextView mTvFloat;
    private RecyclerView mRecyclerView;

    public SlideBar(Context context) {
        this(context,null);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        paint.setTextSize(DensityUtil.sp2px(getContext(),10));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#9c9c9c"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                /**
                 * 1.设置背景为灰色 矩形 圆角
                 * 2. move到哪个文本Section就把floatTitle 修改掉
                 * 3. 判断通讯录中正好有当前Section字母开头的用户，则定位RecyclerView的位置，让用户看见
                 */
                setBackgroundResource(R.drawable.slidebar_bk);
                showFloatAndScrollRecyclerView(event.getY());



                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 1. 隐藏背景(把背景变成全透明的)，隐藏floatTitle
                 */
                setBackgroundColor(Color.TRANSPARENT);
                if (mTvFloat!=null){
                    mTvFloat.setVisibility(GONE);
                }
                break;

            default:
                break;

        }
        return true;
    }

    private void showFloatAndScrollRecyclerView(float y) {
        /**
         * 根据y坐标计算点中的文本
         */
        int index = (int) (y/mAvgHeight);
        if (index<0){
            index = 0;
        }else if (index>SECTIONS.length-1){
            index = SECTIONS.length - 1;
        }
        String section = SECTIONS[index];
        /**
         * 获取FloatTitle(先让SlideBar找父控件，然后让父控件找FloatTitle)，然后设置section
         */
        if (mTvFloat==null){
            ViewGroup parent = (ViewGroup) getParent();
            mTvFloat = (TextView) parent.findViewById(R.id.tv_float);
            mRecyclerView = (RecyclerView) parent.findViewById(R.id.recyclerView);
        }
        mTvFloat.setVisibility(VISIBLE);
        mTvFloat.setText(section);

        /**
         * 拿到section后去判断这个section在RecyclerView中的所有数据中的脚标（也可能不存在）
         *
         *  通过RecyclerView获取到Adapter，通过Adapter获取到联系人数据
         */
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof IContactAdapter){
            IContactAdapter contactAdapter = (IContactAdapter) adapter;
            List<String> data = contactAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                String contact = data.get(i);
                if (section.equals(StringUtils.getInitial(contact))){
                    mRecyclerView.smoothScrollToPosition(i);
                    return;
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mAvgWidth = width / 2;
        mAvgHeight = height / SECTIONS.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i],mAvgWidth,mAvgHeight*(i+1),paint);
        }
    }
}
