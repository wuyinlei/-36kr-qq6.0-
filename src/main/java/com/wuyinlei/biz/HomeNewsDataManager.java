package com.wuyinlei.biz;

import com.wuyinlei.bean.AuthorBean;
import com.wuyinlei.bean.HomeNewsBean;
import com.wuyinlei.url.Config;
import com.wuyinlei.utils.CTextUtils;
import com.wuyinlei.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页新闻数据抓取管理器
 * @author jiangqq
 *
 */
public class HomeNewsDataManager {
  public HomeNewsDataManager(){ 
  }
  /**
   * 进行抓取首页信息数据
   * @param document
   * @return
   */
  public List<HomeNewsBean> getHomeNewsBeans(Document document){
	  List<HomeNewsBean> homeNewsBeans=new ArrayList<HomeNewsBean>();
	  Elements elements= document.select("div.articles").first().select("article");
	  for (Element element : elements) {
		  //图标以及文章类型
		  Element a_pic_element=element.select("a.pic").first();
		  String imgurl="";
		  String mask="";
		  if(a_pic_element!=null){
			   imgurl= ImageUtils.getCutImageUrl(a_pic_element.attr("data-lazyload"));
			   mask=a_pic_element.text();
		  }
		  //desc信息 连接地址和标题
		  Element desc_element=element.select("div.desc").first();
		  String href=desc_element.select("a.title").first().attr("href");
		  //进行href过滤 因为网站又文章列表无法点击 ，所以这边直接滤过了
		  if(href.equals("javascript:void(0)")){
			  continue;
		  }
		  String tId= CTextUtils.getTitleId(href);
		  href= Config.CRAWLER_URL+href;
		  String title=desc_element.select("a.title").first().text();
		  //作者信息
		  Element author_element=desc_element.select("div.author").first();
		  //查找只存在data-lazyload属性的a标签
		  Element link = author_element.select("a").first();
		  String author_href=Config.CRAWLER_URL+link.attr("href");
		  String avatar=link.select("span.avatar").first().attr("data-lazyload");
		  String name=link.text();
		  //时间
		  Element time_element=author_element.select("time.timeago").first();
		  String datetime="";
		  String datetext="";
		  if(time_element!=null){
			   datetime=author_element.select("time.timeago").first().attr("title"); 
			   datetext=author_element.select("time.timeago").first().text();
		  }else {
			   datetime=author_element.select("abbr.timeago").first().attr("title"); 
			   datetext=author_element.select("abbr.timeago").first().text();
		}
		  //文章简介
		  String brief=desc_element.select("div.brief").first().text();
		  AuthorBean authorBean=new AuthorBean();
		  authorBean.setName(name);
		  authorBean.setAvatar(avatar);
		  authorBean.setHref(author_href);
		  HomeNewsBean bean=new HomeNewsBean();
		  bean.settId(tId);
		  bean.setImgurl(imgurl);
		  bean.setMask(mask);
		  bean.setHref(href);
		  bean.setTitle(title);
		  bean.setAuthorBean(authorBean);
		  bean.setDatetime(datetime);
		  bean.setBrief(brief);
		  bean.setDatetext(datetext);
		  homeNewsBeans.add(bean);
	  }
	  return homeNewsBeans;
  }
  
  /**
   * 抓取文章类别数据 根据分类
   * @param document
   * @return
   */
  public List<HomeNewsBean> getHomeNewsBeans_WYL(Document document){
	  List<HomeNewsBean> homeNewsBeans=new ArrayList<HomeNewsBean>();

	  /**
	   * 获取到articles节点下的所有数据
	   * document.select("div.articles")
	   *
	   * 获取到articles节点下article节点下的数据
	   * document.select("div.articles").first().select("article")
	   */
	  Elements elementHomeNews = document.select("div.articles").first().select("article");

	  for (Element element:elementHomeNews) {

		  /**
		   * 获取到pic节点下的数据
		   */
		  Element pic_element = element.select("a.pic").first();
		  //获取到图片地址
		  String imgurl = ImageUtils.getCutImageUrl(pic_element.attr("data-lazyload"));
		  //获取到文章描述
		  String href = pic_element.attr("abs:href");
		  //获取到文章类型
		  String mask = pic_element.text();
		  //获取到文章id
		  String aId = CTextUtils.getArticleId(href);

		  /**
		   * 获取到desc节点下的数据
		   */
		  Element desc_element = element.select("div.desc").first();
		  //文章标题
		  String title = desc_element.select("a.title").first().text();
		  //文章简述
		  String brief = desc_element.select("div.brief").first().text();


		  /**
		   * 获取到desc节点下author节点下的数据
		   */
		  Element author_element = desc_element.select("div.author").first();
		  //获取发表时间
		  String datetime = author_element.select("time.timeago").first().attr("datetime");
		  //文章发表时间时间 已计算
		  String datetext = author_element.select("time.timeago").first().text();

		  /**
		   * 获取到author节点下的a数据
		   */
		  Element author_info = author_element.select("a").first();
		  AuthorBean author = new AuthorBean();
		  //获取作者主页地址
		  String au_href = author_info.attr("abs:href");
		  author.setHref(au_href);

		  /**
		   * 获取author节点下span下的avatar数据
		   */
		  Element avatar_info = author_info.select("span.avatar").first();
		  //头像地址
		  String avatar = avatar_info.attr("data-lazyload");
		  //作者名字
		  String name = author_info.select("span.name").first().text();

		  author.setAvatar(avatar);
		  author.setName(name);

		  //System.out.print(article_element);
		  //System.out.println(author);
		  HomeNewsBean bean = new HomeNewsBean();
		  bean.setTitle(title);
		  bean.setBrief(brief);
		  bean.settId(aId);
		  bean.setDatetext(datetext);
		  bean.setDatetime(datetime);
		  bean.setImgurl(imgurl);
		  bean.setHref(href);
		  bean.setMask(mask);
		  bean.setAuthorBean(author);
		  homeNewsBeans.add(bean);
	  }

	  //System.out.print(elementHomeNews);
	  //return homeNewsBeans;
	  return homeNewsBeans;
  }
}
