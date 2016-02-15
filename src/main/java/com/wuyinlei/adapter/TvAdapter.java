package com.wuyinlei.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyinlei.activity.R;
import com.wuyinlei.bean.ArticleTv;

import java.util.List;

/**
 * Created by 若兰 on 2016/1/28.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class TvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;

    List<ArticleTv> mArticleTvs;

    private LayoutInflater mInflater;

    public TvAdapter(List<ArticleTv> articleTvs) {
        mArticleTvs = articleTvs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.page_fragment_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onClick(v,v.getTag());
                    }
                }
            });
            return new MyViewHolder(view);
        }else if (viewType == TYPE_FOOT) {
            view = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_FOOT;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            ArticleTv articleTv = mArticleTvs.get(position);
            MyViewHolder tvHolder = (MyViewHolder) holder;
            tvHolder.itemView.setTag(articleTv);
            tvHolder.page_fragment_item_length.setText(articleTv.getLength());
            tvHolder.page_fragment_item_time.setText(articleTv.getTime());
            tvHolder.page_fragment_item_title.setText(articleTv.getTitle());
        }
    }


    @Override
    public int getItemCount() {
        return mArticleTvs == null ? 0 : mArticleTvs.size() + 1;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView page_fragment_item_img;
        ImageView tv;
        TextView page_fragment_item_title;
        TextView page_fragment_item_length;
        TextView page_fragment_item_time;

        public MyViewHolder(final View itemView) {
            super(itemView);
            page_fragment_item_img = (ImageView) itemView.findViewById(R.id.page_fragment_item_img);
            tv = (ImageView) itemView.findViewById(R.id.play_tv);
            page_fragment_item_title = (TextView) itemView.findViewById(R.id.page_fragment_item_title);
            page_fragment_item_length = (TextView) itemView.findViewById(R.id.page_fragment_item_length);
            page_fragment_item_time = (TextView) itemView.findViewById(R.id.page_fragment_item_time);
        }

    }

    /**
     * 上拉加载更多的进度
     */
    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener{
       void onClick(View v,Object o);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
