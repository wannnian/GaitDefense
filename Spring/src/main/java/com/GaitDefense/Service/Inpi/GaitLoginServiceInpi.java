package com.GaitDefense.Service.Inpi;


import com.GaitDefense.Mapper.GaitLoginMapper;
import com.GaitDefense.Service.GaitLoginService;
import com.GaitDefense.pojo.GaitLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GaitLoginServiceInpi implements GaitLoginService {
    @Autowired
    private GaitLoginMapper gaitLoginMapper;

    @Override
    public void addGaitLogin(GaitLogin gaitLogin) {
        gaitLoginMapper.insert(gaitLogin);
    }

    @Override
    public List<GaitLogin> getAllGaitLogin() {
        return gaitLoginMapper.getAll();
    }

    @Override
    public void deleteGaitLogin(int id) {
        gaitLoginMapper.delete(id);
    }

    @Override
    public GaitLogin getGaitLoginById(int id) {
        return gaitLoginMapper.getGaitLoginById(id);
    }

    @Override
    public void updateGaitLogin(GaitLogin gaitLogin) {
        gaitLoginMapper.updateGaitLogin(gaitLogin);
    }
}
