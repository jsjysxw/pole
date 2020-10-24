package cn.com.avatek.pole.ctrlview.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.entity.ClassContent;

/**
 * Created by shenxw on 2017/4/17.
 */

public class OdHonView extends CommonView implements View.OnClickListener{

    private TextView tv_title,tv_title1,tv_title2,tv_title3,tv_con1,tv_con2,tv_con3,tv_time1,tv_time2,tv_time3;
    private LinearLayout ll_more;
    private RelativeLayout head_view1,head_view2,head_view3;
    private LinearLayout tab_1,tab_2,tab_3;
    private ImageView iv1,iv2,iv3;

    public OdHonView(Context context) {
        this(context, null);
    }

    public OdHonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.od_honview, this, true);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_title3 = (TextView) findViewById(R.id.tv_title3);
        tv_con1 = (TextView) findViewById(R.id.tv_con1);
        tv_con2 = (TextView) findViewById(R.id.tv_con2);
        tv_con3 = (TextView) findViewById(R.id.tv_con3);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        tab_1 = (LinearLayout) findViewById(R.id.tab_1);
        tab_2 = (LinearLayout) findViewById(R.id.tab_2);
        tab_3 = (LinearLayout) findViewById(R.id.tab_3);
        head_view1 = (RelativeLayout) findViewById(R.id.head_view1);
        head_view2 = (RelativeLayout) findViewById(R.id.head_view2);
        head_view3 = (RelativeLayout) findViewById(R.id.head_view3);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        tab_1.setOnClickListener(this);
        tab_2.setOnClickListener(this);
        tab_3.setOnClickListener(this);
        ll_more.setOnClickListener(this);

    }

    @Override
    public void setDetailData(ClassContent classContent) {
        super.setDetailData(classContent);
        if(classContent.getContent()!=null&&classContent.getContent().size()==3){
            tv_con1.setText(classContent.getContent().get(0).getContent());
            tv_con2.setText(classContent.getContent().get(1).getContent());
            tv_con3.setText(classContent.getContent().get(2).getContent());
            tv_title.setText(classContent.getName());
            if (classContent.getContent().get(0).getPic_url() != null && !classContent.getContent().get(0).getPic_url().equals(""))
                Glide.with(getContext()).load(classContent.getContent().get(0).getPic_url()).into(iv1);
            if (classContent.getContent().get(1).getPic_url() != null && !classContent.getContent().get(1).getPic_url().equals(""))
                Glide.with(getContext()).load(classContent.getContent().get(1).getPic_url()).into(iv2);
            if (classContent.getContent().get(2).getPic_url() != null && !classContent.getContent().get(2).getPic_url().equals(""))
                Glide.with(getContext()).load(classContent.getContent().get(2).getPic_url()).into(iv3);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tab_1:
                if(classContent.getContent()!=null&&classContent.getContent().size()==3)
                    onShowListListenner.onShowChanged(classContent.getContent().get(0).getItem_id(),"",classContent.getContent().get(0).getClass_title());
                break;
            case R.id.tab_2:
                if(classContent.getContent()!=null&&classContent.getContent().size()==3)
                    onShowListListenner.onShowChanged(classContent.getContent().get(1).getItem_id(),"",classContent.getContent().get(0).getClass_title());
                break;
            case R.id.tab_3:
                if(classContent.getContent()!=null&&classContent.getContent().size()==3)
                    onShowListListenner.onShowChanged(classContent.getContent().get(2).getItem_id(),"",classContent.getContent().get(0).getClass_title());
                break;
            case R.id.ll_more:
                if(classContent.getContent()!=null&&classContent.getContent().size()==3)
                    onShowListListenner.onShowChanged(classContent.getClass_id(),"1",classContent.getName());
                break;
            default:

                break;
        }

    }
}
