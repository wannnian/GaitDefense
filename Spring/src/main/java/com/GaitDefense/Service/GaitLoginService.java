package com.GaitDefense.Service;

import com.GaitDefense.pojo.GaitLogin;

import java.util.List;

public interface GaitLoginService {
//    查询全部步态录入表数据
    List<GaitLogin> getAllGaitLogin();
//    添加步态录入表数据
    void addGaitLogin(GaitLogin gaitLogin);
//    删除步态录入表数据
    void deleteGaitLogin(int id);
//    修改步态录入表数据
    GaitLogin getGaitLoginById(int id);
    void updateGaitLogin(GaitLogin gaitLogin);
}
