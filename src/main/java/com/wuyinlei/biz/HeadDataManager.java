package com.wuyinlei.biz;

import com.wuyinlei.bean.AdHeadBean;
import com.wuyinlei.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页顶部广告数据抓取工具类
 * @author jiangqq
 *
 */
public class HeadDataManager {
	public HeadDataManager(){
   
     }
	/**
	 * 进行根据地址抓取顶部广告数据
	 * @param document
	 * @return
	 */
	public List<AdHeadBean> getHeadBeans(Document document){
	   List<AdHeadBean> adHeadBeans=new ArrayList<AdHeadBean>();;
	   Elements elements=document.select("div.head-images");
	   Elements links = elements.first().select("a[data-lazyload]"); //带有data-lazyload属性的a元素
	   for (Element element : links) {
		  String title=element.text();
		  String href=element.attr("href");
		  String imgurl= ImageUtils.getCutImageUrl(element.attr("data-lazyload"));
		  String mask= element.select("span").first().text();
		  AdHeadBean bean=new AdHeadBean();
		  bean.setTitle(title);
		  bean.setImgurl(imgurl);
		  bean.setHref(href);
		  bean.setMask(mask);
		  adHeadBeans.add(bean);
	   }
       return adHeadBeans;
	}


	/**
	 * 抓取首页顶部广告数据
	 * @param document
	 * @return
     */
	public List<AdHeadBean> getHeadBeans_WYL(Document document){
		List<AdHeadBean> adHeadBeens = new ArrayList<>();
		//Elements select = document.select("div.head-images").first().select("a[data-lazyload]");

		Elements headElements = document.select("div.head-images").first().select("a[data-lazyload]");

		//System.out.print(headElements);
		for (Element element:headElements) {
			//获取到图片地址
			String imgurl = ImageUtils.getCutImageUrl(element.attr("data-lazyload"));
			//System.out.print(imgurl);
			//获取到文章详细地址
			String href = element.attr("href");
			//String mask = element.select("span.mask-tags").text();
			//获取到文章类型
			String mask = element.select("span").first().text();
			//获取到title
			String title = element.text();
			adHeadBeens.add(new AdHeadBean(title,imgurl,href,mask));
		}
		//System.out.print(headElements);
		return adHeadBeens;
		//return null;
	}

	

	
}
