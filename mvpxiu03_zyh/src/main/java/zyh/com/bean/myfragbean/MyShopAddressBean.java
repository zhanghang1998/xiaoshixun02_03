package zyh.com.bean.myfragbean;

//收货地址列表bean类
public class MyShopAddressBean {


    /**
     * address : 北京市 海淀区 上地七街 软件园南站 八维
     * createTime : 1547257135000
     * id : 580
     * phone : 15801135898
     * realName : 张雨航
     * userId : 980
     * whetherDefault : 1
     * zipCode : 410527
     */

    private String address;
    private long createTime;
    private int id;
    private String phone;
    private String realName;
    private int userId;
    private int whetherDefault;
    private String zipCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWhetherDefault() {
        return whetherDefault;
    }

    public void setWhetherDefault(int whetherDefault) {
        this.whetherDefault = whetherDefault;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
