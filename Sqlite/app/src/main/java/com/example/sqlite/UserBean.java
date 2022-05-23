package com.example.sqlite;

public class UserBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    public String name;
    public String phone;
    public String money;

}
