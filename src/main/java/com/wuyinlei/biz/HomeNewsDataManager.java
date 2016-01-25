package com.wuyinlei.biz;


import com.wuyinlei.bean.AuthorBean;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.utils.CTextUtils;
import com.wuyinlei.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class HomeNewsDataManager {
    public List<HomeNewsBean> getHomeBeans(Document document) {
        List<HomeNewsBean> homeBeans = new ArrayList<>();
        int topSize = 0;
        //去掉头条的显示，也就是说不抓取头条
        Elements topElements = document.getElementsByClass("article-Top");
        if (topElements != null && topElements.size() > 0) {
            topSize = topElements.size();
        }
        Elements elements = document.select("div.articles").first().select("article");
        AuthorBean authorBean = null;
        HomeNewsBean bean = null;
        for (int i=topSize;i<elements.size();i++) {
            Element element=elements.get(i);
            Element a_pic_element = element.select("a.pic").first();
            String imgUrl = "";
            String mask = "";
            if (a_pic_element != null) {
                imgUrl = ImageUtils.getCutImageUrl(a_pic_element.absUrl("data-lazyload"));
                mask = a_pic_element.text();
                //desc信息 连接地址和标题
                Element desc_element = element.select("div.desc").first();
                String href = desc_element.select("a.title").first().absUrl("href");
                //进行href过滤 因为网站又文章列表无法点击 ，所以这边直接滤过了
                if (href.equals("javascript:void(0)")) {
                    continue;
                }
                String tId = CTextUtils.getTitleId(href);
                String title = desc_element.select("a.title").first().text();
                //作者信息
                Element author_element = desc_element.select("div.author").first();
                Element a_element = author_element.select("a").first();
                String author_href = a_element.absUrl("href");
                String author_avatar = author_element.select("span.avatar").first().attr("data-lazyload");
                String author_name = a_element.select("a").first().text();
                Element time_element = author_element.select("time.timeago").first();
                String datetime = "";
                String datetext = "";
                if (time_element != null) {
                    datetime = author_element.select("time.timeago").first().attr("title");
                    datetext = author_element.select("time.timeago").first().text();
                } else {
                    datetime = author_element.select("abbr.timeago").first().attr("title");
                    datetext = author_element.select("abbr.timeago").first().text();
                }
                //文章简介
                String brief = desc_element.select("div.brief").first().text();
                authorBean = new AuthorBean();
                authorBean.setName(author_name);
                authorBean.setAvatar(author_avatar);
                authorBean.setHref(author_href);
                bean = new HomeNewsBean();
                bean.settId(tId);
                bean.setImgurl(imgUrl);
                bean.setMask(mask);
                bean.setHref(href);
                bean.setTitle(title);
                bean.setAuthorBean(authorBean);
                bean.setDatetime(datetime);
                bean.setBrief(brief);
                bean.setDatetext(datetext);
                bean.setAuthorBean(authorBean);
                homeBeans.add(bean);
            }

        }
        return homeBeans;
    }
}
