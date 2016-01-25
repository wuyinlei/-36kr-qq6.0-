package com.wuyinlei.biz;

import com.wuyinlei.bean.CategoriesBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;



public class CategoryDataManager {
	public  List<CategoriesBean>getCategoriesBeans(Document document){
		ArrayList<CategoriesBean>categoriesBeans=new ArrayList<>();
		Elements elements=document.select("div.categories").select("a");
		CategoriesBean categoriesBean;
		for(Element element:elements){
			categoriesBean=new CategoriesBean();
			categoriesBean.setTitle(element.text());
			categoriesBean.setHref(element.absUrl("href"));
			categoriesBean.setData_type(element.attr("data-type"));
			categoriesBeans.add(categoriesBean);
		}
		return categoriesBeans;
	}
}
