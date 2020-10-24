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

public class OdVerView extends CommonView implements View.OnClickListener{

    private TextView tv_title,tv_num1,tv_num2,tv_num3,tv_con1,tv_con2,tv_con3,ledou1,ledou2,ledou3;
    private LinearLayout ll_more;
    private RelativeLayout head_view1,head_view2,head_view3;
    private LinearLayout tab_1,tab_2,tab_3;
    private ImageView iv1,iv2,iv3;

    public OdVerView(Context context) {
        this(context, null);
    }

    public OdVerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.od_verview, this, true);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num3 = (TextView) findViewById(R.id.tv_num3);
        tv_con1 = (TextView) findViewById(R.id.tv_con1);
        tv_con2 = (TextView) findViewById(R.id.tv_con2);
        tv_con3 = (TextView) findViewById(R.id.tv_con3);
        ledou1 = (TextView) findViewById(R.id.ledou1);
        ledou2 = (TextView) findViewById(R.id.ledou2);
        ledou3 = (TextView) findViewById(R.id.ledou3);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        head_view1 = (RelativeLayout) findViewById(R.id.head_view1);
        head_view2 = (RelativeLayout) findViewById(R.id.head_view2);
        head_view3 = (RelativeLayout) findViewById(R.id.head_view3);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);

        tab_1 = (LinearLayout) findViewById(R.id.tab_1);
        tab_2 = (LinearLayout) findViewById(R.id.tab_2);
        tab_3 = (LinearLayout) findViewById(R.id.tab_3);

        tab_1.setOnClickListener(this);
        tab_2.setOnClickListener(this);
        tab_3.setOnClickListener(this);
        ll_more.setOnClickListener(this);

    }

    @Override
    public void setDetailData(ClassContent classContent) {
        super.setDetailData(classContent);
        if(classContent.getContent()!=null&&classContent.getContent().size()==3){
            tv_num1.setText(classContent.getContent().get(0).getCount());
            tv_num2.setText(classContent.getContent().get(1).getCount());
            tv_num3.setText(classContent.getContent().get(2).getCount());
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
