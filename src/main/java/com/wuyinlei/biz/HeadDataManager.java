package com.wuyinlei.biz;

import com.wuyinlei.bean.AdHeadBean;
import com.wuyinlei.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class HeadDataManager {
	public List<AdHeadBean>getHeadBeans(Document document){
		ArrayList<AdHeadBean>adHeadBeans=new ArrayList<>();
		Elements headElements=document.select("div.head-images").select("a[data-lazyload]");
		AdHeadBean adHeadBean=null;
		for(Element element:headElements){
			adHeadBean=new AdHeadBean();
			adHeadBean.setTitle(element.text());
			adHeadBean.setHref(element.absUrl("href"));
			adHeadBean.setImgurl(ImageUtils.getCutImageUrl(element.attr("data-lazyload")));
			adHeadBean.setMask(element.select("span").first().text());
			adHeadBeans.add(adHeadBean);
		}
		return adHeadBeans;
	}
	
}
