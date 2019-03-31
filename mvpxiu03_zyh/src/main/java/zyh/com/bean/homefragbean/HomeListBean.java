package zyh.com.bean.homefragbean;

import java.util.List;

public class HomeListBean {

    private int id;
    private String name;
    private List<ComListBean> commodityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComListBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<ComListBean> commodityList) {
        this.commodityList = commodityList;
    }
}
