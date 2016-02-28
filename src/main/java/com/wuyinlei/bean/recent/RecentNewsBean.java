package com.wuyinlei.bean.recent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class RecentNewsBean implements Serializable{

    /**
     * createTime: 1455766364407,
     * headImageUrl: "https://krplus-pic.b0.upaiyun.com/201602/19/4f9a5e81d1dfffd11e441f2b23378ec3.jpg",
     * updateTime: 1455853779085,
     * status: "online",
     * link: "http://chuang.36kr.com/huodong#/activityApply/details/228",
     * categoryId: "0",
     * activityBriefArray: [],
     * applyEndTime: 2147443200000,
     * tagId: "14",
     * applyBeginTime: -28800000,
     * proposerRole: "audience",
     * listImageUrl: "https://krplus-pic.b0.upaiyun.com/201602/19/965b2e63c1f15da8b3f4430ba1099f8d.jpg",
     * city: "北京",
     * activityId: "228",
     * title: "超级氪路演·北京站",
     * activityBeginTime: 1456637400000,
     * activityEndTime: 1456650000000,
     * activityExtraItemArray: [],
     * description: "【脉圈 六膳门 邦美智洗 爱打听周边游】2016年2月18日线下项目路演报名入口",
     * auditPhases: [ ]
     */
    private String createTime;
    private String headImageUrl;
    private String updateTime;
    private String status;
    private String link;
    private String categoryId;
    private List<ActivityBriefArray> activityBriefArray;

    private String applyEndTime;
    private String tagId;
    private String applyBeginTime;
    private String proposerRole;
    private String listImageUrl;

    private String city;
    private String activityId;
    private String title;
    private String activityBeginTime;
    private String activityEndTime;
    private List<ActivityExtraItemArray> activityExtraItemArray;
    private String description;

    public RecentNewsBean() {
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<ActivityBriefArray> getActivityBriefArray() {
        return activityBriefArray;
    }

    public void setActivityBriefArray(List<ActivityBriefArray> activityBriefArray) {
        this.activityBriefArray = activityBriefArray;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(String applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public String getProposerRole() {
        return proposerRole;
    }

    public void setProposerRole(String proposerRole) {
        this.proposerRole = proposerRole;
    }

    public String getListImageUrl() {
        return listImageUrl;
    }

    public void setListImageUrl(String listImageUrl) {
        this.listImageUrl = listImageUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityBeginTime() {
        return activityBeginTime;
    }

    public void setActivityBeginTime(String activityBeginTime) {
        this.activityBeginTime = activityBeginTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public List<ActivityExtraItemArray> getActivityExtraItemArray() {
        return activityExtraItemArray;
    }

    public void setActivityExtraItemArray(List<ActivityExtraItemArray> activityExtraItemArray) {
        this.activityExtraItemArray = activityExtraItemArray;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RecentNewsBean{" +
                "createTime='" + createTime + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status='" + status + '\'' +
                ", link='" + link + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", activityBriefArray=" + activityBriefArray +
                ", applyEndTime='" + applyEndTime + '\'' +
                ", tagId='" + tagId + '\'' +
                ", applyBeginTime='" + applyBeginTime + '\'' +
                ", proposerRole='" + proposerRole + '\'' +
                ", listImageUrl='" + listImageUrl + '\'' +
                ", city='" + city + '\'' +
                ", activityId='" + activityId + '\'' +
                ", title='" + title + '\'' +
                ", activityBeginTime='" + activityBeginTime + '\'' +
                ", activityEndTime='" + activityEndTime + '\'' +
                ", activityExtraItemArray=" + activityExtraItemArray +
                ", description='" + description + '\'' +
                '}';
    }
}
