package com.wuyinlei.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.widgetutils.AutoGallery;
import com.example.widgetutils.FlowIndicator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.utils.adapter.helper.BaseAdapterHelper;
import com.utils.adapter.helper.QuickAdapter;
import com.utils.adapter.refresh.PullToRefreshListView;
import com.wuyinlei.DefineView;
import com.wuyinlei.activity.NewsDetailActivity;
import com.wuyinlei.activity.R;
import com.wuyinlei.bean.AdHeadBean;
import com.wuyinlei.bean.CategoriesBean;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.biz.HeadDataManager;
import com.wuyinlei.biz.HomeNewsDataManager;
import com.wuyinlei.http.OkHttpManager;
import com.wuyinlei.url.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by 若兰 on 2016/1/23.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class HomeFragment extends BaseFragment implements DefineView {

    private View mView;

    private static final String KEY = "EXTRA";

    /**
     * 分类数据
     */
    private CategoriesBean mCategoriesBean;

    /**
     * 主页新闻
     */
    private List<HomeNewsBean> homeBeans;

    /**
     * 项目集合
     */
    private String[] masks;

    /**
     * 下拉刷新组件
     */
    private PullToRefreshListView home_list_view;

    private List<AdHeadBean> mHeadBeans;

    private QuickAdapter<HomeNewsBean> mQuickAdapter;

    /**
     * 轮播框
     */
    private AutoGallery headline_image_gallery;

    /**
     * 指示器
     */
    private FlowIndicator headline_circle_indicator;

    //
    private FrameLayout home_framlayuot;
    //三个加载提示布局
    private LinearLayout empty, error, loading;

    /**
     * Gallery索引
     */
    private int gallerySelectedPosition = 0;

    /**
     * 指示器索引
     */
    private int circleSelectedPosition = 0;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    private View headView;
    private LayoutInflater mInflater;

    public static HomeFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    private int[] mask_colors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.home_fragment, container, false);

        }
        mInflater = LayoutInflater.from(getActivity());
        headView = mInflater.inflate(R.layout.gallery_indicator_layout, null);
        initView();
        initValidata();
        initListener();
        return mView;
    }

    /**
     * 初始化布局控件
     */
    @Override
    public void initView() {
        home_list_view = (PullToRefreshListView) mView.findViewById(R.id.home_list_view);
        home_list_view.addHeaderView(headView);
        home_framlayuot = (FrameLayout) mView.findViewById(R.id.home_framlayout);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        headline_image_gallery = (AutoGallery) headView.findViewById(R.id.headline_image_gallery);
        headline_circle_indicator = (FlowIndicator) headView.findViewById(R.id.headline_circle_indicator);
    }


    @Override
    public void initValidata() {

        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defaultbg)
                .cacheOnDisk(true)   //设置保存在sdcard中
                .cacheInMemory(true) //设置保存在内存当中
                .build();
        /**
         * 这里是文章title的type
         */
        masks = new String[]{"最新文章", "早期项目", "B轮后", "资本", "深度", "行研"};

        /**
         * 对应的每个type的颜色
         */
        mask_colors = new int[]{R.color.mask_tags_1, R.color.mask_tags_3, R.color.mask_tags_5
                , R.color.mask_tags_7,
                R.color.mask_tags_9, R.color.mask_tags_10};
        home_list_view.setVisibility(View.INVISIBLE);
        home_framlayuot.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);

        /**
         * 数据请求
         */
        OkHttpManager.getAsync(mCategoriesBean.getHref(), new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                // Log.d("HomeFragment", "加载失败");
            }

            @Override
            public void requestSuccess(String result) throws Exception {

                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
                mHeadBeans = new HeadDataManager().getHeadBeans(document);
                homeBeans = new HomeNewsDataManager().getHomeBeans(document);
                // Log.d("HomeFragment", homeBeans.toString());
                if (mHeadBeans != null && homeBeans != null) {
                    bindData();
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

        //总共有多少项
        int topSize = mHeadBeans.size();

        //设置指示器的多少项
        headline_circle_indicator.setCount(topSize);
        //当前选择项
        headline_circle_indicator.setSeletion(circleSelectedPosition);

        //设置画廊的总数
        headline_image_gallery.setLength(topSize);
        gallerySelectedPosition = topSize * 50 + circleSelectedPosition;
        //设置当前显示
        headline_image_gallery.setSelection(gallerySelectedPosition);
        //设置轮播间隔
        headline_image_gallery.setDelayMillis(3000);
        //开启轮播
        headline_image_gallery.start();

        headline_image_gallery.setAdapter(new GalleryAdapter());

        /**
         * 在这里监听Gallery图片被按下的是第几个，然后取得数值，赋值给circleSelectedPosition
         */
        headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circleSelectedPosition = position;
                gallerySelectedPosition = circleSelectedPosition % mHeadBeans.size();
                headline_circle_indicator.setSeletion(gallerySelectedPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (homeBeans != null) {

            home_list_view.setVisibility(View.VISIBLE);
            home_framlayuot.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);

            //item_news_img_icon  item_news_tv_name   item_news_tv_time  item_news_tv_type
            //item_news_tv_title  item_news_image_content
            mQuickAdapter = new QuickAdapter<HomeNewsBean>(getContext(), R.layout.item_home_new_layout, homeBeans) {
                @Override
                protected void convert(BaseAdapterHelper helper, HomeNewsBean item) {
                    String mask = item.getMask();
                    /**
                     * 对控件进行绑定，并且赋值
                     */
                    helper.setText(R.id.item_news_tv_name, item.getAuthorBean().getName())
                            .setText(R.id.item_news_tv_time, item.getDatetext())
                            .setText(R.id.item_news_tv_type, mask)
                            .setText(R.id.item_news_tv_title, item.getTitle());

                    /**
                     * 通过imageLoader加载图片
                     */
                    mImageLoader.displayImage(item.getImgurl(), (ImageView) helper.getView(R.id.item_news_image_content), mOptions);
                    mImageLoader.displayImage(item.getImgurl(), (ImageView) helper.getView(R.id.item_news_img_icon), mOptions);
                    int index = 0;
                    /**
                     * 在这里取得type对应的号
                     */
                    for (int i = 0; i < masks.length; i++) {
                        if (masks[i].equals(mask)) {
                            index = i;
                            break;
                        }
                    }
                    //通过拿到type对应的index，去修改要显示的颜色
                    TextView tv_type = helper.getView(R.id.item_news_tv_type);
                    tv_type.setTextColor(getActivity()
                            .getResources().getColor(mask_colors[index]));
                    helper.getView(R.id.item_news_tv_arrow).setBackgroundColor(
                            getActivity().getResources().getColor(mask_colors[index])
                    );

                }
            };
            home_list_view.setAdapter(mQuickAdapter);
            home_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent(getActivity(), NewsDetailActivity.class);
                    mIntent.putExtra("news_item", homeBeans.get((int) id));
                    getActivity().startActivity(mIntent);
                }
            });

            headline_image_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   /* Intent mIntent = new Intent(getActivity(), NewsDetailActivity.class);
                    mIntent.putExtra("news_item", mHeadBeans.get((int) id));
                    getActivity().startActivity(mIntent);*/
                }
            });

        } else {
            home_list_view.setVisibility(View.INVISIBLE);
            home_framlayuot.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Gallery适配器
     */
    class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return mHeadBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_gallery_layout, null);
                holder.header_image = (ImageView) convertView.findViewById(R.id.item_head_gallery_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            /**
             * 加载轮播广告图片
             */
            mImageLoader.displayImage(mHeadBeans.get(position % mHeadBeans.size()).getImgurl(), holder.header_image, mOptions);

            //Picasso.with(mView.getContext()).load(mHeadBeans.get(position%mHeadBeans.size()).getImgurl()).into(holder.header_image);
            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView header_image;
    }
}
