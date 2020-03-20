package com.example.logdemo.log;

/***
 *
 * @author pengZheng
 * 功能页面
 */
public enum OperateModule {

    Login("登录"),
    Maindata("主数据"),
    Purchase("采购管理"),
    Order("订单管理"),
    Stock("库存管理"),
    Gsp("GSP管理"),
    Finance("财务管理"),
    Sys("系统管理");

    private String text;

    OperateModule(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
