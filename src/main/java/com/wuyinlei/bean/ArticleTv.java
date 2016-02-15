package com.wuyinlei.bean;


import java.io.Serializable;

public class ArticleTv implements Serializable{
	private String imgUrl;
	private String tvUrl;
	private String title;
	private String length;
	private String time;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTvUrl() {
		return tvUrl;
	}
	public void setTvUrl(String tvUrl) {
		this.tvUrl = tvUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}