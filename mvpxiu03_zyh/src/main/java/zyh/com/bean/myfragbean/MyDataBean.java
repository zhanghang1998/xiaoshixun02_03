package zyh.com.bean.myfragbean;

import java.io.Serializable;

public class MyDataBean implements Serializable {

    /**
     * createTime : 1546567063000
     * headPic : http://172.17.8.100/images/small/default/user.jpg
     * nickName : WJ_hw0z3
     * password : 05zKrG+hQIId1C8unpQHhjkbHCNN1f655Hv1d21UiA1FWtEmp4cI+/WRPfeQmvIm
     * phone : 15801135898
     * sex : 1
     * userId : 980
     */

    private long createTime;
    private String headPic;
    private String nickName;
    private String password;
    private String phone;
    private int sex;
    private int userId;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
