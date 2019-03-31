package zyh.com.util;

import java.util.regex.Pattern;

/**
 * @详情 正则判断
 */
public class JudgeUtil {

    public static final String REG_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    //判断手机号
    public static boolean isPhone(String mphone) {
        return Pattern.matches(REG_MOBILE, mphone);
    }

    //判断是否为空
    public static boolean isNull(String mnull) {
        return mnull.equals("");
    }

    //判断密码的长度
    public static boolean isPass(String mpass) {
        return mpass.length() < 6;
    }


    public final static String UTF_8 = "UTF-8";
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }



}
