package cn.com.avatek.pole.drawmap.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.drawmap.models.NodeModel;
import cn.com.avatek.pole.drawmap.utils.ScreenUtil;


/**
 * Created by owant on 09/02/2017.
 */
@SuppressLint("AppCompatCustomView")
public class NodeView extends TextView {

    public NodeModel<String> treeNode = null;

    public NodeView(Context context) {
        this(context, null, 0);
        this.context=context;
    }

    public NodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }

    public NodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setTextColor(Color.WHITE);
        setPadding(10, 10, 10, 10);

        Drawable drawable = context.getResources().getDrawable(R.drawable.node_view_bg);
        setBackgroundDrawable(drawable);
        this.context=context;
    }

    public NodeModel<String> getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(NodeModel<String> treeNode) {
        this.treeNode = treeNode;
        setSelected(treeNode.isFocus());
        setText(treeNode.getValue());
    }


//    变成可拖动的
    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;
    private Context context;

    //是否拖动
    private boolean isDrag=false;

    public boolean isDrag() {
        return isDrag;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        screenWidth= ScreenUtil.getScreenWidth(context);
        screenHeight= ScreenUtil.getScreenHeight(context)-getStatusBarHeight();

    }
    public int getStatusBarHeight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }


    private float downX;
    private float downY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDrag=false;
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("kid","ACTION_MOVE");
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    int l,r,t,b;
                    //当水平或者垂直滑动距离大于10,才算拖动事件
                    if (Math.abs(xDistance) >10 || Math.abs(yDistance)>10) {
                        Log.e("kid","Drag");
                        isDrag=true;
                        l = (int) (getLeft() + xDistance);
                        r = l+width;
                        t = (int) (getTop() + yDistance);
                        b = t+height;
                        //不划出边界判断,此处应按照项目实际情况,因为本项目需求移动的位置是手机全屏,
                        // 所以才能这么写,如果是固定区域,要得到父控件的宽高位置后再做处理
                        if(l<0){
                            l=0;
                            r=l+width;
                        }else if(r>screenWidth){
                            r=screenWidth;
                            l=r-width;
                        }
                        if(t<0){
                            t=0;
                            b=t+height;
                        }else if(b>screenHeight){
                            b=screenHeight;
                            t=b-height;
                        }
                        Log.e("ACTION_MOVE","l="+l+",t="+t+",r="+r+",b="+b);
                        treeNode.setCoorl(l);
                        treeNode.setCoort(t);
                        treeNode.setCoorr(r);
                        treeNode.setCoorb(b);
                        this.layout(l, t, r, b);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    setPressed(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

}