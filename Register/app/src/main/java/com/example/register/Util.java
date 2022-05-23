package com.example.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static boolean checkPassword(String pwd) {
        String pattern = "^[A-Za-z0-9_]{6,12}$";//判断密码是否合法的正则表达式 字母或数字或下划线6-12位
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pwd);
        return m.matches();
    }
    public static boolean checkname(String name) {
        String pattern = "^[\\u4e00-\\u9fa5]{2,5}$";//判断姓名是否合法的正则表达式 字符2-5位
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(name);
        return m.matches();
    }
    public static boolean checkemail(String email) {
        String pattern = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$";//判断邮箱是否合法的正则表达式 字符2-14位例如：1@qq.com
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        return m.matches();
    }
}
