package zyh.com.bean.orderfragbean;

public class OrderListDatabean {

    /**
     * commentStatus : 1
     * commodityCount : 1
     * commodityId : 4
     * commodityName : 佩佩防晕染眼线液笔
     * commodityPic : http://172.17.8.100/images/small/commodity/mzhf/cz/2/1.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/2/2.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/2/3.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/2/4.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/2/5.jpg
     * commodityPrice : 19
     * orderDetailId : 1148
     */

    private int commentStatus;
    private int commodityCount;
    private int commodityId;
    private String commodityName;
    private String commodityPic;
    private double commodityPrice;
    private int orderDetailId;

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
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

    public String getCommodityPic() {
        return commodityPic;
    }

    public void setCommodityPic(String commodityPic) {
        this.commodityPic = commodityPic;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
