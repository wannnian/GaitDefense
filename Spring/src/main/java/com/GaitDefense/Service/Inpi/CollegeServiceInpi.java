package com.GaitDefense.Service.Inpi;

import com.GaitDefense.Mapper.CollegeMapper;
import com.GaitDefense.Service.CollegeService;
import com.GaitDefense.pojo.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceInpi implements CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> getCollegeList() {
        return collegeMapper.getCollegeList();
    }

    @Override
    public Integer getCollegeIdByName(String collegeName){
        return collegeMapper.getCollegeIdByName(collegeName);
    }
}
