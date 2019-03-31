package zyh.com.bean.orderfragbean;

public class CreateBean {

    private int commodityId;
    private int amount;

    public CreateBean(int commodityId, int amount) {
        this.commodityId = commodityId;
        this.amount = amount;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return amount;
    }

    public void setCount(int count) {
        this.amount = count;
    }
}
