package com.wuyinlei.bean;


public class TagBean {
	private String href;//标签地址
	private String tagname;//标签名字

	public TagBean() {
		super();
	}

	public TagBean(String href, String tagname) {
		super();
		this.href = href;
		this.tagname = tagname;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return "TagBean [href=" + href + ", tagname=" + tagname + "]";
	}

}
