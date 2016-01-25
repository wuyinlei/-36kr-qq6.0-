package com.wuyinlei.biz;

import com.wuyinlei.bean.ArticleTv;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class ArticleTvManager {
	public List<ArticleTv>getArticleTvBeans(Document document){
		ArrayList<ArticleTv>arTvManagers=new ArrayList<>();
		Elements videos=document.select("li.video-item");
		ArticleTv articleTv=null;
		for(Element video:videos){
			articleTv=new ArticleTv();
			String tvUrl=video.select("a").first().absUrl("href").replaceAll("\\\u003d", "");
			articleTv.setTvUrl(tvUrl);
			articleTv.setImgUrl(video.select("a").first().select("div.video-poster img").first().attr("src"));
			Element  video_info=video.select("a").first().select("div.video-info").first();
			articleTv.setTitle(video_info.select("p").first().text());
			String time=video_info.select("p").last().select("span.v-time").text();
			articleTv.setLength(video_info.select("p").last().text().replaceAll(time, "").trim());
			articleTv.setTime(time);
			arTvManagers.add(articleTv);
			
			
		}
		return arTvManagers;
	}
}
