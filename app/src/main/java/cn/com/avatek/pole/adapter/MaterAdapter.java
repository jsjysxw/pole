package cn.com.avatek.pole.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.entity.ContBean;
import cn.com.avatek.pole.entity.MaterBean;

public class MaterAdapter extends RecyclerView.Adapter<MaterAdapter.ViewHolder> {

    public List<MaterBean> contBeanList = null;
    private Context context;
    private View view;

    public MaterAdapter(List<MaterBean> datas) {
        this.contBeanList = datas;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_num, viewGroup, false);
        context = viewGroup.getContext();
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,int position) {
        viewHolder.mTextView.setText(contBeanList.get(position).getName());
        if(contBeanList.get(position).getNum()>0) {
            viewHolder.tv_con1.setText(contBeanList.get(position).getNum()+"");
        }else {
            if("0".equals(contBeanList.get(position).getType())){
                viewHolder.tv_con1.setText(">");
            }else {
                viewHolder.tv_con1.setText("点击输入数量");
            }
        }
//        if (contBeanList.get(position).getPic_url() != null)
//            Glide.with(context).load(contBeanList.get(position).getPic_url()).into(viewHolder.iv1);

        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            });
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return contBeanList.size();

    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public TextView tv_con1;
        public ImageView iv1;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_title1);
//            iv1 = (ImageView) view.findViewById(R.id.iv1);
            tv_con1 = (TextView) view.findViewById(R.id.tv_time1);
        }
    }
    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
