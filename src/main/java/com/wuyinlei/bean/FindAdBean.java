package com.wuyinlei.bean;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class FindAdBean {

    /**
     * id: 570,
     type: 1,
     weight: 99,
     img_url: "https://krplus-pic.b0.upaiyun.com/201602/19/6190344c1fff09f95a251635ed63b142.jpg",
     link_url: "https://z.36kr.com/investorValidate?krsrc=zcrzbanner_pc",
     title: "认证引导",
     operator: 51,
     background_color: null,
     deleted_at: null,
     created_at: "2016-02-19 17:03:31",
     updated_at: "2016-02-19 17:03:31"
     */
    private String id;
    private String type;
    private String weight;
    private String img_url;
    private String link_url;
    private String title;
    private String operator;
    private String background_color;
    private String deleted_at;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "FindAdBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", weight='" + weight + '\'' +
                ", img_url='" + img_url + '\'' +
                ", link_url='" + link_url + '\'' +
                ", title='" + title + '\'' +
                ", operator='" + operator + '\'' +
                ", background_color='" + background_color + '\'' +
                ", deleted_at='" + deleted_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
