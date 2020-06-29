package com.creapple.myhelper.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("stock_at")
    @Expose
    private String stockAt;
    @SerializedName("remain_stat")
    @Expose
    private String remainStat;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public String getStockAt() {
        return stockAt;
    }

    public void setStockAt(String stockAt) {
        this.stockAt = stockAt;
    }

    public String getRemainStat() {
        return remainStat;
    }

    public void setRemainStat(String remainStat) {
        this.remainStat = remainStat;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}