package com.wuyinlei.bean.invesator;

import java.io.Serializable;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class InvestCom implements Serializable{
    /**
     * financeEventId: 10783,
     * address1: 123,
     * address2: 22301,
     * address3: 3230104,
     * industry2: 210096,
     * financePhase: "A_PLUS",
     * story: "",
     * autoScore: 0.199457,
     * industry: "CONSUMER_LIFESTYLE",
     * androidLink: "",
     * status: "CLAIMED",
     * operationStatus: "OPEN",
     * krRate: 38.14,
     * managerId: 49651,
     * weibo: "http://weibo.com/u/3372047074",
     * startDate: 1373904000000,
     * hasManager: true,
     * isStartup: true,
     * crowdfundingId: 0,
     * trendChart: "https://krplus-pic.b0.upaiyun.com/trend_chart/20160219_pic/11753.png",
     * pictures: "https://krplus-pic.b0.upaiyun.com/201512/03/70645bbf95594084a4b00112c33efe7f.jpg",
     * webLink: "",
     * id: 11753,
     * pcLink: "http://mochameizhuang.com/",
     * weixin: "抹茶美报",
     * fundId: 0,
     * name: "抹茶美妆",
     * createDate: 1426051647000,
     * isCompleted: 0,
     * logo: "https://krplus-pic.b0.upaiyun.com/201511/18/81d22db87f0148a2be44af6df471bb80.jpg",
     * krScore: 511.21,
     * website: "http://www.mocha.cn",
     * iphoneAppstoreLink: "https://itunes.apple.com/cn/app/id641597561",
     * ipadAppstoreLink: "https://itunes.apple.com/cn/app/id641597561",
     * isDeleted: false,
     * intro: "云遥科技旗下抹茶美妆是一款专业化妆品资讯分享社区，致力于帮助用户找到最适合化妆品。 抹茶美妆有美妆论坛、美妆达人视频、美妆推荐、精品试用、私人定制等功能，可以帮助用户学会各种化妆技巧、分享化妆心得，还有机会领取免费试用化妆品。",
     * updateDate: 1455928477000,
     * source: "CREATION",
     * faId: 0,
     * companyType: "WEB",
     * fullName: "成都云遥互动科技有限公司",
     * brief: "化妆品资讯分享社区"
     */
    private String financeEventId;
    private String address1;
    private String address2;
    private String industry2;
    private String financePhase;
    private String story;
    private String autoScore;
    private String industry;
    private String androidLink;
    private String status;
    private String operationStatus;
    private String krRate;
    private String managerId;
    private String weibo;
    private String startDate;
    private String hasManager;
    private String isStartup;
    private String crowdfundingId;
    private String trendChart;
    private String pictures;
    private String webLink;
    private String id;
    private String pcLink;
    private String weixin;
    private String name;
    private String createDate;
    private String logo;
    private String website;
    private String iphoneAppstoreLink;
    private String intro;
    private String fullName;
    private String brief;

    public InvestCom() {
    }

    public InvestCom(String financeEventId, String address1, String address2, String industry2, String financePhase, String story, String autoScore, String industry, String androidLink, String status, String operationStatus, String krRate, String managerId, String weibo, String startDate, String hasManager, String isStartup, String crowdfundingId, String trendChart, String pictures, String webLink, String id, String pcLink, String weixin, String name, String createDate, String logo, String website, String iphoneAppstoreLink, String intro, String fullName, String brief) {
        this.financeEventId = financeEventId;
        this.address1 = address1;
        this.address2 = address2;
        this.industry2 = industry2;
        this.financePhase = financePhase;
        this.story = story;
        this.autoScore = autoScore;
        this.industry = industry;
        this.androidLink = androidLink;
        this.status = status;
        this.operationStatus = operationStatus;
        this.krRate = krRate;
        this.managerId = managerId;
        this.weibo = weibo;
        this.startDate = startDate;
        this.hasManager = hasManager;
        this.isStartup = isStartup;
        this.crowdfundingId = crowdfundingId;
        this.trendChart = trendChart;
        this.pictures = pictures;
        this.webLink = webLink;
        this.id = id;
        this.pcLink = pcLink;
        this.weixin = weixin;
        this.name = name;
        this.createDate = createDate;
        this.logo = logo;
        this.website = website;
        this.iphoneAppstoreLink = iphoneAppstoreLink;
        this.intro = intro;
        this.fullName = fullName;
        this.brief = brief;
    }

    public String getFinanceEventId() {
        return financeEventId;
    }

    public void setFinanceEventId(String financeEventId) {
        this.financeEventId = financeEventId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getIndustry2() {
        return industry2;
    }

    public void setIndustry2(String industry2) {
        this.industry2 = industry2;
    }

    public String getFinancePhase() {
        return financePhase;
    }

    public void setFinancePhase(String financePhase) {
        this.financePhase = financePhase;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAutoScore() {
        return autoScore;
    }

    public void setAutoScore(String autoScore) {
        this.autoScore = autoScore;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAndroidLink() {
        return androidLink;
    }

    public void setAndroidLink(String androidLink) {
        this.androidLink = androidLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getKrRate() {
        return krRate;
    }

    public void setKrRate(String krRate) {
        this.krRate = krRate;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getHasManager() {
        return hasManager;
    }

    public void setHasManager(String hasManager) {
        this.hasManager = hasManager;
    }

    public String getIsStartup() {
        return isStartup;
    }

    public void setIsStartup(String isStartup) {
        this.isStartup = isStartup;
    }

    public String getCrowdfundingId() {
        return crowdfundingId;
    }

    public void setCrowdfundingId(String crowdfundingId) {
        this.crowdfundingId = crowdfundingId;
    }

    public String getTrendChart() {
        return trendChart;
    }

    public void setTrendChart(String trendChart) {
        this.trendChart = trendChart;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPcLink() {
        return pcLink;
    }

    public void setPcLink(String pcLink) {
        this.pcLink = pcLink;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIphoneAppstoreLink() {
        return iphoneAppstoreLink;
    }

    public void setIphoneAppstoreLink(String iphoneAppstoreLink) {
        this.iphoneAppstoreLink = iphoneAppstoreLink;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Override
    public String toString() {
        return "InvestCom{" +
                "financeEventId='" + financeEventId + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", industry2='" + industry2 + '\'' +
                ", financePhase='" + financePhase + '\'' +
                ", story='" + story + '\'' +
                ", autoScore='" + autoScore + '\'' +
                ", industry='" + industry + '\'' +
                ", androidLink='" + androidLink + '\'' +
                ", status='" + status + '\'' +
                ", operationStatus='" + operationStatus + '\'' +
                ", krRate='" + krRate + '\'' +
                ", managerId='" + managerId + '\'' +
                ", weibo='" + weibo + '\'' +
                ", startDate='" + startDate + '\'' +
                ", hasManager='" + hasManager + '\'' +
                ", isStartup='" + isStartup + '\'' +
                ", crowdfundingId='" + crowdfundingId + '\'' +
                ", trendChart='" + trendChart + '\'' +
                ", pictures='" + pictures + '\'' +
                ", webLink='" + webLink + '\'' +
                ", id='" + id + '\'' +
                ", pcLink='" + pcLink + '\'' +
                ", weixin='" + weixin + '\'' +
                ", name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                ", logo='" + logo + '\'' +
                ", website='" + website + '\'' +
                ", iphoneAppstoreLink='" + iphoneAppstoreLink + '\'' +
                ", intro='" + intro + '\'' +
                ", fullName='" + fullName + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }
}
