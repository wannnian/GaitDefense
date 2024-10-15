package com.GaitDefense.Service;

import com.GaitDefense.pojo.College;

import java.util.List;

public interface CollegeService {
    List<College> getCollegeList();
    Integer getCollegeIdByName(String collegeName);
}
