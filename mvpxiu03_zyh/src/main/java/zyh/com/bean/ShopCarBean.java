package zyh.com.bean;

import java.io.Serializable;

public class ShopCarBean implements Serializable {

    private int commodityId;
    private String commodityName;
    private int count;
    private String pic;
    private double price;
    private boolean ischeck=false;

    public ShopCarBean(int commodityId, String commodityName, int count, String pic, double price) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.count = count;
        this.pic = pic;
        this.price = price;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "QueryShoppingBean{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", count=" + count +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", ischeck=" + ischeck +
                '}';
    }
}
