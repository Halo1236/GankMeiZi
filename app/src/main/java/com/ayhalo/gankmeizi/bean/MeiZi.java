package com.ayhalo.gankmeizi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * GankMeiZi
 * Created by Halo on 2017/7/4.
 */

@Entity
public class MeiZi {

    /**
     * _id : 59f9674c421aa90fe50c01c6
     * createdAt : 2017-11-01T14:18:52.937Z
     * desc : 11-1
     * publishedAt : 2017-11-01T14:20:59.209Z
     * source : chrome
     * type : 福利
     * url : http://7xi8d6.com1.z0.glb.clouddn.com/20171101141835_yQYTXc_enakorin_1_11_2017_14_16_45_351.jpeg
     * used : true
     * who : daimajia
     */

    @Id(autoincrement = true)
    private Long id;

    @Transient
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @Generated(hash = 184800246)
    public MeiZi(Long id, String createdAt, String desc, String publishedAt, String source, String type,
            String url, boolean used, String who) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    @Generated(hash = 142896928)
    public MeiZi() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public boolean getUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

