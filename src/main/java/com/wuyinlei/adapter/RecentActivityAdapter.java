package com.wuyinlei.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.recent.RecentNewsBean;
import com.wuyinlei.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/25.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class RecentActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    private List<RecentNewsBean> mRecentNewsBeans;
    private LayoutInflater mInflater;


    public RecentActivityAdapter(List<RecentNewsBean> recentNewsBeans) {
        mRecentNewsBeans = recentNewsBeans;
        mImageLoader = ImageLoader.getInstance();
        options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.mipmap.defaultbg)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.item_recent_news_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onClick(v,v.getTag());
                }
            }
        });
        return new RecentActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecentActivityViewHolder){
            RecentNewsBean recentNewsBean = mRecentNewsBeans.get(position);
            RecentActivityViewHolder viewHolder = (RecentActivityViewHolder) holder;
            viewHolder.itemView.setTag(recentNewsBean);
            mImageLoader.displayImage(recentNewsBean.getListImageUrl(),viewHolder.recentitemimglogo);
            viewHolder.recentitemtvtitle.setText(recentNewsBean.getTitle());
            //活动地点
            viewHolder.recentitemtvlocation.setText(recentNewsBean.getCity());
            //活动开始时间设置
            String beginDate= DateUtil.getFormatDate(new Date(Long.parseLong(recentNewsBean.getActivityBeginTime())));
            String endDate= DateUtil.getFormatDate(new Date(Long.parseLong(recentNewsBean.getActivityEndTime())));
            if (beginDate.equals(endDate)) {
                viewHolder.recentitemtvtimetext.setText(beginDate);
            } else {
                viewHolder.recentitemtvtimetext.setText(beginDate + "到" +endDate);
            }

            //报名状态
            long nowTime=System.currentTimeMillis();
            long begin=Long.parseLong(recentNewsBean.getActivityBeginTime());
            long end=Long.parseLong(recentNewsBean.getActivityEndTime());
            if(nowTime<=begin){
                //报名中
               viewHolder.recentitemtvstatus.setText("报名中");
                viewHolder.recentitemtvstatus.setBackgroundResource(R.drawable.icon_activity_jin);
            }else if(nowTime>begin&&nowTime<=end){
                //活动中
                viewHolder.recentitemtvstatus.setText("活动中");
                viewHolder.recentitemtvstatus.setBackgroundResource(R.drawable.icon_activity_wei);
            }else {
                //已结束
                viewHolder.recentitemtvstatus.setText("已结束");
                viewHolder.recentitemtvstatus.setBackgroundResource(R.drawable.icon_activity_yi);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mRecentNewsBeans.size();
    }

    class RecentActivityViewHolder extends RecyclerView.ViewHolder{

        private ImageView recentitemimglogo;
        private TextView recentitemtvtitle;
        private TextView recentitemtvlocation;
        private TextView recentitemtvstatus;
        private TextView recentitemtvtimetext;

        public RecentActivityViewHolder(View itemView) {
            super(itemView);

            recentitemimglogo = (ImageView) itemView.findViewById(R.id.recent_item_img_logo);
            recentitemtvtitle = (TextView) itemView.findViewById(R.id.recent_item_tv_title);
            recentitemtvlocation = (TextView) itemView.findViewById(R.id.recent_item_tv_location);
            recentitemtvstatus = (TextView) itemView.findViewById(R.id.recent_item_tv_status);
            recentitemtvtimetext = (TextView) itemView.findViewById(R.id.recent_item_tv_timetext);
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
