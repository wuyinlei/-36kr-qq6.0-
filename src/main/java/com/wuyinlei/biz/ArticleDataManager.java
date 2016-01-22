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
        this.articleId = articleId;
    }

    /**
     * 进行根据HTML Document对象抓取文章相关信息
     *
     * @param document
     * @return
     */
    public ArticleBean getArticleBean(Document document) {
        ArticleBean articleBean = new ArticleBean();
        //首先获取局部的文章相关数据
        Element singleElement = document.select("article.single-post").first();
        //获取标题
        //single-post__title
        String title = singleElement.select("h1.single-post__title").first().text();
        //获取时间
        String datetime = singleElement.select("time.timeago").first().attr("datetime");
        String datetext = singleElement.select("time.timeago").first().text();
        //获取头图片
        String headImage = ImageUtils.getCutImageUrl(singleElement.select("div.single-post-header__headline").first().select("img[src]").first().attr("src"));
        //获取文章内容
        String context = Jsoup.parseBodyFragment(singleElement.select("section.article").first().toString()).toString();
        articleBean.setTitle(title);
        articleBean.setDatetime(datetime);
        articleBean.setDatetext(datetext);
        articleBean.setHeadImage(headImage);
        articleBean.setContext(CTextUtils.replaceSSymbol(context));
        //抓取搜索标签
        Elements tagElements = singleElement.select("span.tag-item");
        if (tagElements != null && tagElements.size() > 0) {
            List<TagBean> tagBeans = new ArrayList<TagBean>();
            for (Element element : tagElements) {
                Element a_Element = element.select("a").first();
                String href = a_Element.attr("abs:href");
                String tagname = a_Element.text();
                tagBeans.add(new TagBean(href, tagname));
            }
            articleBean.setTagBeans(tagBeans);
        }
        //开始抓取用户信息
        String author_Str = HttpRequest.sendPost("http://36kr.com/asynces/posts/author_info", "url_code=" + articleId);
        AuthorBean bean = null;
        try {
            JSONObject authorObject = new JSONObject(author_Str);
            String name = authorObject.getString("name");
            String description = CTextUtils.replaceEmail(authorObject.getString("tagline"));
            String avatar = ImageUtils.getCutImageUrl(authorObject.getString("avatar"));
            String badge = authorObject.getString("role");
            String article_total = authorObject.getString("posts_count");
            String read_number = authorObject.getString("views_count");
            String href = "http:" + authorObject.getString("more_articles");
            bean = new AuthorBean();
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
        return articleBean;
    }

    /**
     * 进行抓取文章详情
     *
     * @param document
     * @return
     */
    public ArticleBean getArticleBean_WYL(Document document) {
        ArticleBean bean = new ArticleBean();
        Element article_detail = document.select("article.single-post").first();
        //获取title
        String title = article_detail.select("h1.single-post__title").first().text();

        //获取发表时间
        String datetime = article_detail.select("time.timeago").first().attr("datetime");
        //已经发表的时间
        String datetext = article_detail.select("time.timeago").first().text();
        //System.out.print(datetime + timeago);

        //获取图片链接
        Element image_detail = article_detail.select("div.single-post-header__headline").first();
        String imageUrl = ImageUtils.getCutImageUrl(image_detail.select("img").first().attr("src"));

        //System.out.println(imageUrl);
        //获取文章内容
        String context = article_detail.select("section.article").first().toString();
        //System.out.print(context);
        bean.setDatetime(datetime);
        bean.setContext(context);
        bean.setHeadImage(imageUrl);
        bean.setTitle(title);
        bean.setDatetext(datetext);

        /**
         * 获取标签内容
         */
        Elements tag_detail = article_detail.select("section.single-post-tags").first().select("span.tag-item");
        List<TagBean> tagbeans = new ArrayList<>();
        //System.out.println(tag_detail);
        for (Element tags : tag_detail) {
            TagBean tag = new TagBean();
            String href = tags.select("a").first().attr("abs:href");
            String tagName = tags.text();
            tag.setHref(href);
            tag.setTagname(tagName);
            tagbeans.add(tag);
            //System.out.print(href + "   " + tagName);
        }

        bean.setTagBeans(tagbeans);

        /**
         * 获取到作者信息
         */
      /*  Element author_detail = document.select("div.article-side").first();
        Element author_element = author_detail.select("div#article-latestArticles").first();

        System.out.print(author_detail);*/
        AuthorBean authorBean = new AuthorBean();

        String result = HttpRequest.sendPost("http://36kr.com/asynces/posts/author_info", "url_code=" + articleId);
        //System.out.print(result);
        try {
            JSONObject authorObject = new JSONObject(result);
            //文章作者
            String name = authorObject.getString("name");
            authorBean.setName(name);
            //作者头像
            String avatar = authorObject.getString("avatar");
            authorBean.setAvatar(avatar);
            //作者等级
            String badge = authorObject.getString("role");
            authorBean.setBadge(badge);
            //作者个人说明
            String description = authorObject.getString("tagline");
            authorBean.setDescription(description);
            //作者主页地址
            String href = "http:" + authorObject.getString("more_articles");
            authorBean.setHref(href);
            //文章总量
            String article_total = authorObject.getString("posts_count");
            authorBean.setArticle_total(article_total);
            //阅读总量
            String read_number = authorObject.getString("views_count");
            authorBean.setRead_number(read_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bean.setAuthorBean(authorBean);


        //System.out.print(first);

        return bean;
    }
}
