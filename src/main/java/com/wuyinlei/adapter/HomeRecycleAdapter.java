package com.wuyinlei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.ArticleTv;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.widget.RoundAngleImageView;

import java.util.List;

/**
 * Created by 若兰 on 2016/1/27.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;

    private Context mContext;

    private int type = 0; //如果是0 的话，普通列表   1  视频列表

    private LayoutInflater mInflater;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    private List<HomeNewsBean> mHomeNewsBeans;

    private List<ArticleTv> mArticleTvs;

    /**
     * 这里是文章title的type
     */
    String[] masks = new String[]{"最新文章", "早期项目", "B轮后", "资本", "深度", "行研"};

    /**
     * 对应的每个type的颜色
     */
    int[] mask_colors = new int[]{R.color.mask_tags_1, R.color.mask_tags_3, R.color.mask_tags_5
            , R.color.mask_tags_7,
            R.color.mask_tags_9, R.color.mask_tags_10};

    public void setArticleTvs(List<ArticleTv> articleTvs) {
        mArticleTvs = articleTvs;
    }

    public void setHomeNewsBeans(List<HomeNewsBean> homeNewsBeans) {
        mHomeNewsBeans = homeNewsBeans;
    }

    public HomeRecycleAdapter(Context context, int type) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.type = type;

        mImageLoader = ImageLoader.getInstance();

        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_ITEM) {
            if (type == 1) {
                //普通列表
                view = mInflater.inflate(R.layout.item_home_new_layout, parent, false);
                view.setOnClickListener(this);
                return new ItemViewHolder(view);
            }
        } else if (viewType == TYPE_FOOT){
            view = mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
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

    /**
     * 数据绑定
     *
     * @param holder
     * @param position 每一项的索引
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            HomeNewsBean homeNewsBean = mHomeNewsBeans.get(position);

            //绑定数据
            ItemViewHolder _holder = (ItemViewHolder) holder;

            _holder.itemView.setTag(homeNewsBean);

            _holder.item_news_tv_title.setText(homeNewsBean.getTitle());
            _holder.item_news_tv_type.setText(homeNewsBean.getMask());
            _holder.item_news_tv_time.setText(homeNewsBean.getDatetext());
            _holder.item_news_tv_name.setText(homeNewsBean.getAuthorBean().getName());
            mImageLoader.displayImage(homeNewsBean.getAuthorBean().getAvatar(), ((ItemViewHolder) holder).item_news_img_icon, mOptions);
            mImageLoader.displayImage(homeNewsBean.getImgurl(), _holder.item_news_image_content, mOptions);

            String mask = homeNewsBean.getMask();
            int index = 0;
            for (int i = 0;i < masks.length;i++){
                if (masks[i].equals(mask)) {
                    index = i;
                    break;
                }
            }
            //给type设置颜色
            _holder.item_news_tv_type.setTextColor(mContext.getResources().getColor(mask_colors[index]));
            _holder.item_news_tv_arrow.setBackgroundColor(mContext.getResources().getColor(mask_colors[index]));
        }else if (holder instanceof FootViewHolder){
            //上拉加载更多布局
        }
    }

    @Override
    public int getItemCount() {
        return mHomeNewsBeans == null ? 0 : mHomeNewsBeans.size() + 1;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onClick(v,v.getTag());
        }
    }

    /**
     * 普通列表的
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        //item_news_img_icon  item_news_tv_name   item_news_tv_time  item_news_tv_type
        //item_news_tv_title  item_news_image_content
        //定义控件
        private RoundAngleImageView item_news_img_icon;
        private TextView item_news_tv_arrow;
        private TextView item_news_tv_name;
        private TextView item_news_tv_time;
        private TextView item_news_tv_type;
        private TextView item_news_tv_title;
        private ImageView item_news_image_content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_news_img_icon = (RoundAngleImageView) itemView.findViewById(R.id.item_news_img_icon);
            item_news_tv_name = (TextView) itemView.findViewById(R.id.item_news_tv_name);
            item_news_tv_time = (TextView) itemView.findViewById(R.id.item_news_tv_time);
            item_news_tv_type = (TextView) itemView.findViewById(R.id.item_news_tv_type);
            item_news_tv_title = (TextView) itemView.findViewById(R.id.item_news_tv_title);
            item_news_image_content = (ImageView) itemView.findViewById(R.id.item_news_image_content);
            item_news_tv_arrow = (TextView) itemView.findViewById(R.id.item_news_tv_arrow);
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

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
