package com.wuyinlei.biz;

import com.wuyinlei.bean.ArticleBean;
import com.wuyinlei.bean.AuthorBean;
import com.wuyinlei.bean.TagBean;
import com.wuyinlei.utils.CTextUtils;
import com.wuyinlei.utils.HttpRequest;
import com.wuyinlei.utils.ImageUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class ArticleDataManager {
	private String articleId;

	public ArticleDataManager(String articleId) {
		super();
		this.articleId = articleId;
	}
	
	public ArticleBean getArticleBean(Document document){
		ArticleBean articleBean=new ArticleBean();
		Element singleElement=document.select("article.single-post").first();
		String title=singleElement.select("h1.single-post__title").first().text();
		//获取时间
		String dateTime=singleElement.select("time.timeago").first().attr("datetime");
		String dateText=singleElement.select("time.timeago").first().text();
		//获取头图片
		String headImage= ImageUtils.getCutImageUrl(singleElement.select("div.single-post-header__headline").first().select("img[src]").first().absUrl("src"));
		//获取文章内容
		String content= Jsoup.parseBodyFragment(singleElement.select("section.article").toString()).toString();
		articleBean.setTitle(title);
    	articleBean.setDatetime(dateTime);
    	articleBean.setDatetext(dateText);
    	articleBean.setHeadImage(headImage);
    	articleBean.setContext(CTextUtils.replaceSSymbol(content));
    	//获取标签
    	//抓取搜索标签
        Elements tagElements=singleElement.select("span.tag-item");
        if(tagElements!=null&&tagElements.size()>0){
        	List<TagBean> tagBeans=new ArrayList<TagBean>();
        	for (Element element : tagElements) {
        	   Element a_Element=element.select("a").first();
			   String href=a_Element.attr("abs:href");
			   String tagname=a_Element.text();
			   tagBeans.add(new TagBean(href, tagname));
        	}
        	articleBean.setTagBeans(tagBeans);
        }

		//开始抓取用户信息
		String author_Str= HttpRequest.sendPost("http://36kr.com/asynces/posts/author_info", "url_code=" + articleId);
		AuthorBean bean=null;
		try {
			JSONObject authorObject=new JSONObject(author_Str);
			String name=authorObject.getString("name");
			String description=CTextUtils.replaceEmail(authorObject.getString("tagline"));
			String avatar=ImageUtils.getCutImageUrl(authorObject.getString("avatar"));
			String badge=authorObject.getString("role");
			String article_total=authorObject.getString("posts_count");
			String read_number=authorObject.getString("views_count");
			String href="http:"+authorObject.getString("more_articles");
			bean=new AuthorBean();
			bean.setName(name);
			bean.setDescription(description);
			bean.setAvatar(avatar);
			bean.setBadge(badge);
			bean.setArticle_total(article_total);
			bean.setRead_number(read_number);
			bean.setHref(href);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		articleBean.setAuthorBean(bean);

        //System.out.println("返回数据为:"+HttpRequest.sendPost("http://36kr.com/asynces/posts/author_info", "url_code="+articleId));
		return articleBean;
	}
	
	
}
